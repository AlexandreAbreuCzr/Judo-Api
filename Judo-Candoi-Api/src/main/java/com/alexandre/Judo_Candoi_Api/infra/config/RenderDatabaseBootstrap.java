package com.alexandre.Judo_Candoi_Api.infra.config;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

public final class RenderDatabaseBootstrap {

    private static final String RENDER_H2_FILE_URL =
            "jdbc:h2:file:/var/data/judo_candoi;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;AUTO_SERVER=TRUE";

    private RenderDatabaseBootstrap() {
    }

    public static void apply() {
        apply(System.getenv(), System.getProperties());
    }

    static void apply(Map<String, String> env, Properties systemProperties) {
        if (hasAnyDatasourceUrl(env, systemProperties)) {
            return;
        }

        String renderDatabaseUrl = firstNonBlank(
                systemProperties.getProperty("DATABASE_URL"),
                env.get("DATABASE_URL")
        );

        if (hasText(renderDatabaseUrl) && applyRenderPostgresUrl(renderDatabaseUrl, env, systemProperties)) {
            return;
        }

        if (isRenderEnvironment(env, systemProperties)) {
            systemProperties.setProperty("DB_URL_JUDO", RENDER_H2_FILE_URL);
            setIfMissing("DB_USERNAME", "sa", env, systemProperties);
            System.err.println(
                    "[Judo-Candoi-Api] DATABASE_URL nao configurada. Usando fallback H2 em arquivo em /var/data/judo_candoi."
            );
        }
    }

    private static boolean applyRenderPostgresUrl(
            String renderDatabaseUrl,
            Map<String, String> env,
            Properties systemProperties
    ) {
        String jdbcUrl = toJdbcPostgresUrl(renderDatabaseUrl);
        if (!hasText(jdbcUrl)) {
            return false;
        }

        systemProperties.setProperty("DB_URL_JUDO", jdbcUrl);

        Credentials credentialsFromUrl = extractCredentials(jdbcUrl);
        setIfMissing(
                "DB_USERNAME",
                firstNonBlank(
                        credentialsFromUrl.username(),
                        systemProperties.getProperty("DATABASE_USERNAME"),
                        env.get("DATABASE_USERNAME"),
                        systemProperties.getProperty("DATABASE_USER"),
                        env.get("DATABASE_USER")
                ),
                env,
                systemProperties
        );
        setIfMissing(
                "DB_PASSWORD",
                firstNonBlank(
                        credentialsFromUrl.password(),
                        systemProperties.getProperty("DATABASE_PASSWORD"),
                        env.get("DATABASE_PASSWORD")
                ),
                env,
                systemProperties
        );

        return true;
    }

    private static boolean hasAnyDatasourceUrl(Map<String, String> env, Properties systemProperties) {
        return hasText(firstNonBlank(
                systemProperties.getProperty("DB_URL_JUDO"),
                env.get("DB_URL_JUDO"),
                systemProperties.getProperty("DB_URL"),
                env.get("DB_URL"),
                systemProperties.getProperty("spring.datasource.url"),
                env.get("SPRING_DATASOURCE_URL")
        ));
    }

    private static boolean isRenderEnvironment(Map<String, String> env, Properties systemProperties) {
        String renderFlag = firstNonBlank(
                systemProperties.getProperty("RENDER"),
                env.get("RENDER")
        );
        return "true".equalsIgnoreCase(renderFlag);
    }

    private static void setIfMissing(
            String propertyName,
            String value,
            Map<String, String> env,
            Properties systemProperties
    ) {
        if (!hasText(value)) {
            return;
        }

        String explicitFromSystem = systemProperties.getProperty(propertyName);
        String explicitFromEnv = env.get(propertyName);
        if (hasText(explicitFromSystem) || hasText(explicitFromEnv)) {
            return;
        }

        systemProperties.setProperty(propertyName, value);
    }

    private static String toJdbcPostgresUrl(String rawUrl) {
        String trimmed = rawUrl == null ? "" : rawUrl.trim();
        if (!hasText(trimmed)) {
            return null;
        }

        String lower = trimmed.toLowerCase();
        if (lower.startsWith("jdbc:postgresql://")) {
            return trimmed;
        }
        if (lower.startsWith("postgresql://")) {
            return "jdbc:" + trimmed;
        }
        if (lower.startsWith("postgres://")) {
            return "jdbc:postgresql://" + trimmed.substring("postgres://".length());
        }

        return null;
    }

    private static Credentials extractCredentials(String jdbcPostgresUrl) {
        if (!hasText(jdbcPostgresUrl) || !jdbcPostgresUrl.toLowerCase().startsWith("jdbc:")) {
            return Credentials.EMPTY;
        }

        String standardUrl = jdbcPostgresUrl.substring("jdbc:".length());
        try {
            URI uri = new URI(standardUrl);
            String userInfo = uri.getUserInfo();
            if (!hasText(userInfo)) {
                return Credentials.EMPTY;
            }

            String[] pieces = userInfo.split(":", 2);
            String username = decodeUrlComponent(pieces[0]);
            String password = pieces.length > 1 ? decodeUrlComponent(pieces[1]) : null;

            return new Credentials(username, password);
        } catch (URISyntaxException ignored) {
            return Credentials.EMPTY;
        }
    }

    private static String decodeUrlComponent(String raw) {
        if (!hasText(raw)) {
            return null;
        }

        return URLDecoder.decode(raw, StandardCharsets.UTF_8);
    }

    private static String firstNonBlank(String... values) {
        if (values == null) {
            return null;
        }

        for (String value : values) {
            if (hasText(value)) {
                return value.trim();
            }
        }

        return null;
    }

    private static boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private record Credentials(String username, String password) {
        private static final Credentials EMPTY = new Credentials(null, null);
    }
}
