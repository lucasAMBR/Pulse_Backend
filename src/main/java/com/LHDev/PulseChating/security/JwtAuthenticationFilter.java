package com.LHDev.PulseChating.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.LHDev.PulseChating.models.User;
import com.LHDev.PulseChating.repositories.UserRepository;
import com.LHDev.PulseChating.utils.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{

        Optional<String> jwtOpt = extractJwtFromCookie(request);
        if(jwtOpt.isEmpty()){
            filterChain.doFilter(request, response);
            return;
        }

        String token = jwtOpt.get();

        if(!jwtUtils.validateToken(token)){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT");
            return;
        }

        String csrfFromHeader = request.getHeader("X-CSRF-Token");
        String csrfFromJwt = jwtUtils.extractCsrfToken(token);

        if (csrfFromHeader == null || !csrfFromHeader.equals(csrfFromJwt)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF token inválido");
            return;
        }

        UUID userId = jwtUtils.extractUserid(token);

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
            user, null, Collections.emptyList()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }

    private Optional<String> extractJwtFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return Optional.empty();

        return Arrays.stream(request.getCookies())
                .filter(cookie -> "jwtToken".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst();
    }
}
