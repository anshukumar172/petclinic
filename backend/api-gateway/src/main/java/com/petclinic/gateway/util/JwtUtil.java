package com.petclinic.gateway.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    /**
     * Generate Secret Key
     */
    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Extract Claims
     */
    public Claims extractClaims(String token) {

        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Validate JWT
     */
    public boolean validateToken(String token) {

        try {

            Claims claims = extractClaims(token);

            return !claims.getExpiration()
                    .before(new Date());

        } catch (Exception e) {

            System.out.println(
                    "Invalid JWT Token : "
                            + e.getMessage());

            return false;
        }
    }

    /**
     * Get Username
     */
    public String getUsername(String token) {

        return extractClaims(token)
                .getSubject();
    }

    /**
     * Get Role
     */
    public String getRole(String token) {

        Object role = extractClaims(token)
                .get("role");

        return role != null
                ? role.toString()
                : null;
    }

    /**
     * Check Expiry
     */
    public boolean isTokenExpired(String token) {

        return extractClaims(token)
                .getExpiration()
                .before(new Date());
    }
}