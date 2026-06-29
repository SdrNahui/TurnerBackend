package com.example.turner.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;
    private static final long EXPIRATION_MS = 1000 * 60 * 60 * 24;

    private Key getSpringKey(){
        byte[] keyBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
     public String generateToken(String username, String role){
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS)).signWith(getSpringKey(),
                        SignatureAlgorithm.HS256).compact();
     }

     public String extractUsername(String token){
        return extractClaims(token).getSubject();
     }

     public String extractRole(String token){
        return extractClaims(token).get("role", String.class);
     }

     public boolean iesTokenValid(String token){
        try {
            extractClaims(token);
            return true;
        } catch (Exception e){
            return false;
        }

     }
     private Claims extractClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSpringKey()).build().parseClaimsJws(token).getBody();
     }





}
