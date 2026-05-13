/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hyperminetec.livro.security;

import com.hyperminetec.livro.entity.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author Kayqu
 */
@Service
public class TokenService {

   
    @Value("${api.security.token.secret}")
    private String secret;

    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String gerarToken(Usuario usuario) {
        return Jwts.builder()
                .subject(usuario.getEmail()) 
                .claim("role", usuario.getRole().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000)) 
                .signWith(getSigningKey())
                .compact();
    }

    public String validarToken(String token) {
        try {
            
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (Exception e) {
            
            return "";
        }
    }
}
