package com.example.enjoytrip.account.controller;

import com.example.enjoytrip.account.dao.AccountDao;
import com.example.enjoytrip.account.domain.Account;
import com.example.enjoytrip.account.domain.AccountRole;
import com.example.enjoytrip.account.dto.AccountRequestDto;
import com.example.enjoytrip.account.service.AccountService;
import com.example.enjoytrip.jwt.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {

    @Autowired
    AccountService accountService;
    @Autowired
    TokenProvider tokenProvider;
    @Autowired
    AccountDao accountDao;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    AuthenticationManager authenticationManager;

    @DisplayName("회원정보가 데이터베이스에 있을 시, 로그인이 성공되고 토큰을 반환")
    @Transactional
    @Test
    void login_success() throws Exception{
        // given
        AccountRequestDto accountRequestDto = AccountRequestDto.builder()
                .accountEmail("test1@email.com")
                .accountPassword("pass")
                .accountNickname("test")
                .accountRole(AccountRole.USER)
                .build();
        accountService.join(accountRequestDto);

        AccountRequestDto loginRequestDto = AccountRequestDto.builder()
                .accountEmail("test1@email.com")
                .accountPassword("pass")
                .accountNickname("test")
                .accountRole(AccountRole.USER)
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDto)));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk());
    }


    @DisplayName("토큰의 회원 정보와 수정하려는 회원정보와 다를 경우에만 forbidden 에러 발생")
    @Transactional
    @Test
    void update_forbidden() throws Exception {
        // given
        String userEmail = "user@email.com";
        String password = "password";
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        AccountRequestDto accountRequestDto = AccountRequestDto.builder()
                .accountEmail(userEmail)
                .accountPassword("password")
                .accountNickname("test")
                .accountRole(AccountRole.USER)
                .build();
        accountService.join(accountRequestDto);
        Account byEmail = accountDao.findByEmail(accountRequestDto.getAccountEmail());
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userEmail+"1", password, Collections.singletonList(authority));
        String token = tokenProvider.generateToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);
        // when
        mockMvc.perform(put("/accounts/{id}", byEmail.getAccountId())
                .headers(httpHeaders)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(byEmail)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}