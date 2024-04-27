package com.example.enjoytrip.account.service;

import com.example.enjoytrip.account.common.AccountTestUtil;
import com.example.enjoytrip.account.dao.AccountDao;
import com.example.enjoytrip.account.domain.Account;
import com.example.enjoytrip.account.domain.AccountRole;
import com.example.enjoytrip.account.dto.AccountRequestDto;
import com.example.enjoytrip.account.dto.AccountResponseDto;
import com.example.enjoytrip.exception.CustomException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

@DisplayName("AccountService 단위 테스트")
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    static final int VALID_ACCOUNT_ID = 1;
    static final int SUCCESS_CODE = 1;

    @Mock
    AccountDao accountDao;
    AccountService accountService;
    ModelMapper modelMapper;
    Account account;
    AccountRequestDto requestDto;
    AccountResponseDto responseDto;

    @BeforeEach
    void init () {
        modelMapper = new ModelMapper();
        accountService = new AccountServiceImpl(accountDao, modelMapper);

        String email = "test@email.com";
        String password = "pass";
        String nickname = "daeho";
        AccountRole accountRole = AccountRole.USER;

        account = AccountTestUtil.createAccount(VALID_ACCOUNT_ID, email, password, nickname, accountRole);
        requestDto = AccountTestUtil.createAccountRequestDto(email, password, nickname);
        responseDto = AccountTestUtil.createAccountResponseDto(VALID_ACCOUNT_ID, email, nickname, accountRole);
    }

    @DisplayName("일치하는 회원 ID 가 있다면, 조회 후 DTO 로 반환")
    @Test
    void findById_success() {
        // given
        given(accountDao.findById(any(Integer.class))).willReturn(account);

        // when
        AccountResponseDto byId = accountService.findById(VALID_ACCOUNT_ID);

        // then
        Assertions.assertThat(byId.getAccountId()).isEqualTo(account.getAccountId());
    }

    @DisplayName("회원저장 요청 시, 유니크 제약 조건 검사 후 저장하고 새로 할당된 ID 값을 반환")
    @Test
    void join_success() {
        // given : 중복되는 이메일을 가진 유저가 없을 경우만 가입이 진행됨
        given(accountDao.findByEmail(any(String.class))).willReturn(null);
        given(accountDao.join(any(Account.class))).willReturn(VALID_ACCOUNT_ID);

        // when
        Integer returnCode = accountService.join(requestDto);

        // then
        Assertions.assertThat(returnCode).isEqualTo(SUCCESS_CODE);
    }

    @DisplayName("회원저장 요청 시, 유니크 제약 조건이 위배된다면 custom 에러를 발생")
    @Test
    void join_fail() {
        // given
        given(accountDao.findByEmail(any(String.class))).willReturn(account);

        // expect
        assertThrows(CustomException.class, () -> accountService.join(requestDto));
    }

    @DisplayName("유효한 회원의 회원수정 요청 시, 성공코드를 반환")
    @Test
    void update_success() {
        // given
        given(accountDao.update(any(Account.class))).willReturn(SUCCESS_CODE);

        // when
        Integer returnCode = accountService.update(account);

        // then
        Assertions.assertThat(returnCode).isEqualTo(SUCCESS_CODE);
    }

    @DisplayName("유효한 회원의 회원삭제 요청 시, 성공코드를 반환")
    @Test
    void delete_success() {
        // given
        given(accountDao.delete(any(Integer.class))).willReturn(VALID_ACCOUNT_ID);

        // when
        Integer returnCode = accountService.delete(VALID_ACCOUNT_ID);

        // then
        Assertions.assertThat(returnCode).isEqualTo(SUCCESS_CODE);
    }

}