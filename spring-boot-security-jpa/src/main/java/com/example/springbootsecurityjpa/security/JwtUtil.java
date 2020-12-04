package com.example.springbootsecurityjpa.security;

import com.example.springbootsecurityjpa.services.impl.UserDetailsImpl;
import com.example.springbootsecurityjpa.values.JwtValues;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

/**
 * @DATA: 2020/12/4
 */
@Component
@Slf4j
public class JwtUtil {
    @Autowired
    JwtValues jwtValues;

    public String generate(Authentication authentication){
        SecretKey key = Keys.hmacShaKeyFor(
                jwtValues.getSecret().getBytes(StandardCharsets.UTF_8));

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date( (new Date()).getTime() + jwtValues.getExpirationMs()))
                .signWith(key)
                .compact();
    }

    public String getUserFromToken(String token){
        SecretKey key = Keys.hmacShaKeyFor(
                jwtValues.getSecret().getBytes(StandardCharsets.UTF_8));

        String name = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return name;
    }

    public Boolean validate(String token){
        SecretKey key = Keys.hmacShaKeyFor(
                jwtValues.getSecret().getBytes(StandardCharsets.UTF_8));
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (SignatureException e){
            log.error("Invalid JWT signature: {}", e.getMessage());
        }catch (MalformedJwtException e){
            log.error("Invalid JWT token: {}", e.getMessage());
        }catch (ExpiredJwtException e){
            log.error("JWT token expired: {}" , e.getMessage());
        }catch (UnsupportedJwtException e){
            log.error("Jwt Token is not supported: {}", e.getMessage());
        }catch (IllegalArgumentException e){
            log.error("Jwt claims string is empty: {}", e.getMessage());
        }catch (Exception e){
            log.error("Unexpected error happen: {}",e.getMessage());
        }
        return false;
    }
}
