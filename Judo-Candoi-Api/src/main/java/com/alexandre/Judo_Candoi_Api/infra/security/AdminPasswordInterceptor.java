package com.alexandre.Judo_Candoi_Api.infra.security;

import com.alexandre.Judo_Candoi_Api.infra.exceptions.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminPasswordInterceptor implements HandlerInterceptor {

    private static final String PASSWORD_HEADER = "X-Admin-Password";
    private final String configuredPassword;

    public AdminPasswordInterceptor(@Value("${app.admin.password:troque-essa-senha}") String configuredPassword) {
        this.configuredPassword = configuredPassword;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String providedPassword = request.getHeader(PASSWORD_HEADER);

        if (providedPassword != null && !providedPassword.isBlank() && providedPassword.equals(configuredPassword)) {
            return true;
        }

        throw new UnauthorizedException("Senha do painel invalida.");
    }
}
