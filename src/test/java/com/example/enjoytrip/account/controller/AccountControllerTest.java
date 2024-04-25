package com.example.enjoytrip.account.controller;

import com.example.enjoytrip.account.dto.AccountRequestDto;
import com.example.enjoytrip.account.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        AccountRequestDto requestDto = AccountRequestDto.builder()
                .accountEmail("test@email.com")
                .accountPassword("pass")
                .accountNickname("daeho")
                .build();
        given(accountService.join(any(AccountRequestDto.class))).willReturn(id);

        // when & then
        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

}