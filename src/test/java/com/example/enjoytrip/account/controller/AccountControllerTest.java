package com.example.enjoytrip.account.controller;

import com.example.enjoytrip.account.common.AccountTestUtil;
import com.example.enjoytrip.account.domain.Account;
import com.example.enjoytrip.account.domain.AccountRole;
import com.example.enjoytrip.account.dto.AccountRequestDto;
import com.example.enjoytrip.account.dto.AccountResponseDto;
import com.example.enjoytrip.account.service.AccountService;
import com.example.enjoytrip.config.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityConfig.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = AccountController.class)
class AccountControllerTest {

    @MockBean
    AccountService accountService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @DisplayName("회원가입 요청을 받는 테스트")
    @Test
    void join() throws Exception {
        // given
        Integer id = 1;
        String email = "test@email.com";
        String password = "pass";
        String nickname = "daeho";
        AccountRequestDto requestDto = createAccountRequestDto(email, password, nickname);
        given(accountService.join(any(AccountRequestDto.class))).willReturn(id);

        // when
        ResultActions resultActions = mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @DisplayName("회원 ID로 조회하는 테스트")
    @Test
    void findById_success() throws Exception {
        // given
        Integer id = 1;
        String email = "test123@email.com";
        String nickname = "daeho";
        AccountRole accountRole = AccountRole.USER;
        var accountResponseDto = createAccountResponseDto(id, email, nickname, accountRole);

        given(accountService.findById(any(Integer.class))).willReturn(accountResponseDto);

        // when
        ResultActions resultActions = mockMvc.perform(get("/accounts/{accountId}", id));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("accountId").value(String.valueOf(id)))
                .andExpect(jsonPath("accountEmail").value(email))
                .andExpect(jsonPath("accountPassword").doesNotExist())
                .andExpect(jsonPath("accountNickname").value(nickname))
                .andExpect(jsonPath("accountRole").value(accountRole.toString()));
    }

    @DisplayName("회원 수정하는 테스트")
    @Test
    void update_success() throws Exception{
        //given
        Integer id = 1;
        String email = "test123@email.com";
        String password = "pass";
        String nickname = "daeho";
        AccountRole accountRole = AccountRole.USER;
        Account account = createAccount(id, email, password, nickname, accountRole);
        given(accountService.update(any(Account.class))).willReturn(1);

        // when
        ResultActions resultActions = mockMvc.perform(put("/accounts/{accountId}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(account)));

        // then
        resultActions
                .andDo(print())
                .andExpect(jsonPath("code").exists())
                .andExpect(status().isOk());
    }


    @DisplayName("회원 삭제하는 테스트")
    @Test
    void delete_success() throws Exception{
        //given
        Integer id = 1;
        given(accountService.delete(any(Integer.class))).willReturn(1);

        // when
        ResultActions resultActions = mockMvc.perform(delete("/accounts/{accountId}", id));

        // then
        resultActions
                .andDo(print())
                .andExpect(jsonPath("code").exists())
                .andExpect(status().isOk());
    }





    /* ---------------------------------------------------------------------*/


    private static Account createAccount(Integer id, String email, String password, String nickname, AccountRole accountRole) {
        return Account.builder()
                .accountId(id)
                .accountEmail(email)
                .accountPassword(password)
                .accountNickname(nickname)
                .accountRole(accountRole)
                .build();
    }

    private static AccountRequestDto createAccountRequestDto(String email, String password, String nickname) {
        return AccountRequestDto.builder()
                .accountEmail(email)
                .accountPassword(password)
                .accountNickname(nickname)
                .build();
    }
    private static AccountResponseDto createAccountResponseDto(Integer id, String email,
                                                               String nickname, AccountRole accountRole) {
        return AccountResponseDto.builder()
                .accountId(id)
                .accountEmail(email)
                .accountNickname(nickname)
                .accountRole(accountRole)
                .build();
    }


}