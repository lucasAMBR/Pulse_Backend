package com.LHDev.PulseChating.utils;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
    
    private final String jwtSecret = "uma-chave-secreta-bem-grande-e-segura-para-o-jwt-2025";
    private final long jwtExpirationMs = 86400000;

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(UUID userId, String csrfToken){
        return Jwts.builder()
            .setSubject(userId.toString())
            .claim("csrf", csrfToken)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public String generateCsrfToken() {
        return UUID.randomUUID().toString();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            
                return true;
        }catch (JwtException ex){
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(getSigningKey())
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }

    public UUID extractUserid(String token){
        return UUID.fromString(getClaims(token).getSubject());
    }

    public String extractCsrfToken(String token){
        return getClaims(token).get("csrf", String.class);
    }
}
