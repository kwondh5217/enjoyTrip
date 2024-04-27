package com.example.enjoytrip.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private final String secretKey;
    private final long expirationTime;
    public SecretKey key;

    public TokenProvider(@Value("${jwt.secret}") String secretKey,
                         @Value("${jwt.expiration-time}") long expirationTime) {
        this.secretKey = secretKey;
        this.expirationTime = expirationTime;
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }


    public String generateToken(Authentication authentication){
        return Jwts.builder()
                .subject(authentication.getName())
                .claim(AUTHORITIES_KEY, authentication.getAuthorities())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+expirationTime))
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token){
        Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return true;
    }

    public String getEmailFromToken(String token){
        return getPayload(token).getSubject();
    }

    public Collection<? extends GrantedAuthority> getAuthoritiesFromToken(String token){
        return Arrays.stream(getPayload(token).get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }


    private Claims getPayload(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
