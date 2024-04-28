package com.example.enjoytrip.account.controller;

import com.example.enjoytrip.account.common.AccountTestUtil;
import com.example.enjoytrip.account.common.BaseControllerTest;
import com.example.enjoytrip.account.domain.Account;
import com.example.enjoytrip.account.domain.AccountRole;
import com.example.enjoytrip.account.dto.AccountRequestDto;
import com.example.enjoytrip.account.dto.AccountResponseDto;
import com.example.enjoytrip.account.service.AccountService;
import com.example.enjoytrip.config.SecurityConfig;
import com.example.enjoytrip.jwt.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AccountController.class)
class AccountControllerTest extends BaseControllerTest {

    @MockBean
    AccountService accountService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    static final int VALID_ACCOUNT_ID = 1;
    Account account;
    AccountRequestDto requestDto;
    AccountResponseDto responseDto;

    @BeforeEach
    void setUp() {
        String email = "test@email.com";
        String password = "pass";
        String nickname = "daeho";
        AccountRole accountRole = AccountRole.USER;

        account = AccountTestUtil.createAccount(VALID_ACCOUNT_ID, email, password, nickname, accountRole);
        requestDto = AccountTestUtil.createAccountRequestDto(email, password, nickname);
        responseDto = AccountTestUtil.createAccountResponseDto(VALID_ACCOUNT_ID, email, nickname, accountRole);
    }

    @DisplayName("정상적인 회원가입 요청 시 201 Created 상태 코드와 회원 ID 반환")
    @Test
    void join_success() throws Exception {
        // given: 회원가입에 필요한 데이터와 서비스 응답을 설정
        given(accountService.join(any(AccountRequestDto.class))).willReturn(VALID_ACCOUNT_ID);

        // when: 회원가입 API를 호출
        ResultActions resultActions = mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));

        // then: API 응답이 201 Created 상태이고, 반환된 회원 ID가 정상적임을 검증
        resultActions
                .andExpect(status().isCreated())
                .andExpect(content().string(String.valueOf(VALID_ACCOUNT_ID)));
    }

    @DisplayName("유효한 회원 ID로 조회 요청 시 200 상태 코드와 회원정보 반환")
    @Test
    void findById_success() throws Exception {
        // given: 회원조회에 필요한 데이터와 서비스 응답을 설정
        given(accountService.findById(any(Integer.class))).willReturn(responseDto);

        // when: 회원조회 API 호출
        ResultActions resultActions = mockMvc.perform(
                get("/accounts/{accountId}", VALID_ACCOUNT_ID)
        );

        // then: API 응답이 200 상태이고, 반환된 회원정보가 정상적임을 검증
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId")
                        .value(String.valueOf(responseDto.getAccountId())))
                .andExpect(jsonPath("$.accountEmail")
                        .value(responseDto.getAccountEmail()))
                .andExpect(jsonPath("$.accountPassword")
                        .doesNotExist())
                .andExpect(jsonPath("$.accountNickname")
                        .value(responseDto.getAccountNickname()))
                .andExpect(jsonPath("$.accountRole")
                        .value(responseDto.getAccountRole().toString()));
    }

    @DisplayName("유효한 회원의 정보를 수정 요청 시 상태코드 200 반환")
    @Test
    void update_success() throws Exception{
        // given: 회원수정에 필요한 데이터와 서비스 응답을 설정
        given(accountService.update(any(Account.class))).willReturn(VALID_ACCOUNT_ID);

        // when : 회원수정 API 호출
        ResultActions resultActions = mockMvc.perform(
                put("/accounts/{accountId}", VALID_ACCOUNT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(account))
        );

        // then : 반환된 상태코드가 정상적임을 검증
        resultActions
                .andExpect(jsonPath("$.code").exists())
                .andExpect(status().isOk());
    }


    @DisplayName("유효한 회원의 회원삭제 요청 시 상태코드 200을 반환")
    @Test
    void delete_success() throws Exception{
        // given : 회원삭제에 필요한 서비스 응답을 설정
        int successCode = 1;
        given(accountService.delete(any(Integer.class))).willReturn(successCode);

        // when : 회원삭제 API 호출
        ResultActions resultActions = mockMvc.perform(
                delete("/accounts/{accountId}", VALID_ACCOUNT_ID)
        );

        // then : 반환된 상태코드가 정상적임을 검증
        resultActions
                .andExpect(jsonPath("$.code").exists())
                .andExpect(status().isOk());
    }
}