package com.example.enjoytrip.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

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

//      when
        String token = tokenProvider.generateToken(authentication);

//      then
        assertThat(token).isNotNull();
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        assertThat(claims.getSubject()).isEqualTo(username);
        assertThat(claims.get("auth").toString()).contains(authority.toString());
    }


}