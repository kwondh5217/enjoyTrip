package com.example.enjoytrip.account.common;

import com.example.enjoytrip.config.SecurityConfig;
import com.example.enjoytrip.jwt.JwtAuthenticationEntryPoint;
import com.example.enjoytrip.jwt.JwtAccessDeniedHandler;
import com.example.enjoytrip.jwt.TokenProvider;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@Import(SecurityConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Disabled
public class BaseControllerTest {
    @MockBean
    TokenProvider tokenProvider;
    @MockBean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @MockBean
    JwtAccessDeniedHandler jwtAccessDeniedHandler;
}
