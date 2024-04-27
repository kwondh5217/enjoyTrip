package com.example.enjoytrip.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("TokenProvider 단위 테스트")
class TokenProviderTest {

    String SECRET_KEY = "4dqI8DCqUioD1X154heittWjgeXnDTC+1/yvGro5aP4="; // 테스트 용
    long EXPIRATION_TIME = 10800*1000;
    TokenProvider tokenProvider;
    SecretKey key;

    @BeforeEach
    void initTokenProvider(){
        this.tokenProvider = new TokenProvider(SECRET_KEY, EXPIRATION_TIME);
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    @DisplayName("tokenProvider 가 정상적으로 생성")
    @Test
    void crateTokenProvider(){
        Assertions.assertNotNull(tokenProvider);
    }

    @DisplayName("토큰 생성 메소드 호출 시, 정상토큰을 반환하며 유효성 검증")
    @Test
    void generateToken_success() {
        // given
        String username = "user";
        String password = "password";
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(username, password, Collections.singletonList(authority));

        // when
        String token = tokenProvider.generateToken(authentication);

        // then
        assertThat(token).isNotNull();
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        assertThat(claims.getSubject()).isEqualTo(username);
        assertThat(claims.get("auth").toString()).contains(authority.toString());
    }

    @DisplayName("토큰 서명이 잘못되었을 경우 에러를 발생")
    @Test
    void validateToken_fail_signature(){
        // given
        String username = "user";
        String invalidToken = Jwts.builder()
                .subject(username)
                .signWith(Jwts.SIG.HS256.key().build()) // 랜덤으로 생성된 키 값
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .compact();

        // expect
        Assertions.assertThrows(SignatureException.class,
                () -> tokenProvider.validateToken(invalidToken));
    }

    @DisplayName("토큰이 만료되었을 경우 에러를 발생")
    @Test
    void validateToken_fail_expire(){
        // given
        String username = "user";
        String invalidToken = Jwts.builder()
                .subject(username)
                .signWith(key)
                .expiration(new Date(System.currentTimeMillis()-1000))
                .compact();

        // expect
        Assertions.assertThrows(ExpiredJwtException.class,
                () -> tokenProvider.validateToken(invalidToken));
    }

    @DisplayName("토큰의 형식이 잘못되었을 경우 에러를 발생")
    @Test
    void validateToken_fail_invalid(){
        // given
        String illegalToken = "this.is.wrong.token.";

        // expect
        Assertions.assertThrows(MalformedJwtException.class,
                () -> tokenProvider.validateToken(illegalToken));
    }
}