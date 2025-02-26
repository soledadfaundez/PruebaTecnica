package com.bci.demo.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.bci.demo.service.JwtGenerateService;

@Service
public class JwtGenerateServiceImpl implements JwtGenerateService {

    // Inyectar la clave secreta desde las propiedades
    @Value("${jwt.secret}")
    private String secretKey;

    // Inyectar la duración del token desde las propiedades
    @Value("${jwt.expirationMs}")
    private long expirationMs;

    // Método para obtener la clave en formato HS256
    private Key getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // SFC: Clave secreta para la firma del token
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // SFC: Generar un token JWT
    public String createToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    // SFC: Validar y obtener el contenido del token
    public String validateToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); // Retorna el username
    }
}
