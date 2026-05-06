package com.fintech.user_services.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String extractEmail(String token) {
        return Jwts.parser().verifyWith((javax.crypto.SecretKey) getKey())
                .build().parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().verifyWith((javax.crypto.SecretKey) getKey())
                    .build().parseSignedClaims(token);
            return true;
        } catch (JwtException e) { return false; }
    }
}