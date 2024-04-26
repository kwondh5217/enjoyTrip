package com.example.enjoytrip.account.service;

import com.example.enjoytrip.account.common.AccountTestUtil;
import com.example.enjoytrip.account.dao.AccountDao;
import com.example.enjoytrip.account.domain.Account;
import com.example.enjoytrip.account.domain.AccountRole;
import com.example.enjoytrip.account.dto.AccountRequestDto;
import com.example.enjoytrip.account.dto.AccountResponseDto;
import com.example.enjoytrip.config.SecurityConfig;
import com.example.enjoytrip.exception.CustomException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Import;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
@Import(SecurityConfig.class)
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    AccountDao accountDao;
    AccountService accountService;
    ModelMapper modelMapper;

    @BeforeEach
    void init () {
        modelMapper = new ModelMapper();
        accountService = new AccountServiceImpl(accountDao, modelMapper);
    }

    @DisplayName("회원을 ID로 조회하는 테스트")
    @Test
    void findById_success() {
        // given
        Integer id = 1;
        String email = "test123@email.com";
        String password = "pass";
        String nickname = "daeho";
        AccountRole accountRole = AccountRole.USER;
        Account account = createAccount(id, email, password, nickname, accountRole);
        given(accountDao.findById(any(Integer.class))).willReturn(account);

        // when
        AccountResponseDto byId = accountService.findById(id);

        // then
        Assertions.assertThat(byId.getAccountId()).isEqualTo(id);
    }

    @DisplayName("회원을 저장하는 테스트")
    @Test
    void join_success() {
        // given
        String email = "test123@email.com";
        String password = "pass";
        String nickname = "daeho";
        var requestDto = AccountTestUtil.createAccountRequestDto(email, password, nickname);

        given(accountDao.join(any(Account.class))).willReturn(1);
        given(accountDao.findByEmail(any(String.class))).willReturn(null);

        // when
        Integer returnCode = accountService.join(requestDto);

        // then
        Assertions.assertThat(returnCode).isEqualTo(1);
    }

    @DisplayName("중복된 회원 정보가 있어서 가입이 실패하는 테스트")
    @Test
    void join_fail() {
        // given
        var requestDto = AccountTestUtil.createAccountRequestDto("test", "pass", "test");
        given(accountDao.findByEmail(any(String.class)))
                .willReturn(createAccount(1, "test", "test", "test", AccountRole.USER));

        // expect
        org.junit.jupiter.api.Assertions.assertThrows(CustomException.class,
                () -> accountService.join(requestDto));
    }

    @DisplayName("회원을 수정하는 테스트")
    @Test
    void update_success() {
        // given
        Integer id = 1;
        String email = "test123@email.com";
        String password = "pass";
        String nickname = "daeho";
        AccountRole accountRole = AccountRole.USER;
        var account = AccountTestUtil.createAccount(id, email, password, nickname, accountRole);

        given(accountDao.update(any(Account.class))).willReturn(1);

        // when
        Integer returnCode = accountService.update(account);

        // then
        Assertions.assertThat(returnCode).isEqualTo(1);
    }

    @DisplayName("회원을 삭제하는 테스트")
    @Test
    void delete_success() {
        // given
        Integer id = 1;
        given(accountDao.delete(any(Integer.class))).willReturn(1);

        // when
        Integer returnCode = accountService.delete(id);

        // then
        Assertions.assertThat(returnCode).isEqualTo(1);
    }






    private static Account createAccount(Integer id, String email, String password, String nickname, AccountRole accountRole) {
        return Account.builder()
                .accountId(id)
                .accountEmail(email)
                .accountPassword(password)
                .accountNickname(nickname)
                .accountRole(accountRole)
                .build();
    }

}