package com.alexandre.Judo_Candoi_Api.infra.config;

import com.alexandre.Judo_Candoi_Api.infra.security.AdminPasswordInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final String allowedOriginsRaw;
    private final AdminPasswordInterceptor adminPasswordInterceptor;
    private final Path uploadRoot;

    public WebConfig(
            @Value("${app.cors.allowed-origins:http://localhost:5173}") String allowedOriginsRaw,
            @Value("${app.upload.dir:uploads}") String uploadDir,
            AdminPasswordInterceptor adminPasswordInterceptor
    ) {
        this.allowedOriginsRaw = allowedOriginsRaw;
        this.adminPasswordInterceptor = adminPasswordInterceptor;
        this.uploadRoot = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    @Override
    public void addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry registry) {
        String[] allowedOrigins = Arrays.stream(allowedOriginsRaw.split(","))
                .map(String::trim)
                .filter(origin -> !origin.isBlank())
                .toArray(String[]::new);

        CorsRegistration api = registry.addMapping("/api/**")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*");

        if (allowedOrigins.length == 0) {
            api.allowedOriginPatterns("*");
            return;
        }

        api.allowedOrigins(allowedOrigins);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminPasswordInterceptor)
                .addPathPatterns("/api/v1/admin/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadLocation = uploadRoot.toUri().toString();

        if (!uploadLocation.endsWith("/")) {
            uploadLocation += "/";
        }

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadLocation);
    }
}
