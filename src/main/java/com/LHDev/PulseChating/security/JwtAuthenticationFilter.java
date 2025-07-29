package com.LHDev.PulseChating.security;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, 
        HttpServletResponse response, 
        FilterChain filterChain) throws ServletException, IOException{

            String path = request.getRequestURI();
            if (path.startsWith("/api/v1/auth/")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = null;

            if(request.getCookies() != null){
                for(Cookie cookie : request.getCookies()){
                    if("token".equals(cookie.getName())){
                        token = cookie.getValue();
                        break;
                    }
                }
            }

            if(token != null && jwtService.validateToken(token)){
                UUID userId = jwtService.extractUserid(token);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, List.of());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        }

}