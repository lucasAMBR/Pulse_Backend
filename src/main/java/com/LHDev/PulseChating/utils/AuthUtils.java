package com.LHDev.PulseChating.utils;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {
        public static UUID getAuthenticatedUserId(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Erro de autenticação");
        }

        Object principal = auth.getPrincipal();

        if (principal instanceof UUID) {
            return (UUID) principal;
        }

        if (principal instanceof String) {
            try {
                return UUID.fromString((String) principal);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Principal não é um UUID válido.");
            }
        }

        throw new RuntimeException("Tipo inesperado de principal: " + principal.getClass());
    }
}
