package com.example.enjoytrip.account.dao;

import com.example.enjoytrip.account.domain.Account;
import com.example.enjoytrip.account.domain.AccountRole;
import com.example.enjoytrip.account.dto.AccountRequestDto;
import com.example.enjoytrip.config.SecurityConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Import(SecurityConfig.class)
@ActiveProfiles("test")
@Transactional
@SpringBootTest
class AccountDaoTest {

    @Autowired
    AccountDao accountDao;

    @DisplayName("회원이 저장된다")
    @Test
    void join() {
        // given
        String email = "test11@email.com";
        String password = "pass";
        String nickname = "daeho";
        AccountRole accountRole = AccountRole.USER;
        Account account = createAccount(null, email, password, nickname, accountRole);

        // when
        Integer join = accountDao.join(account);

        // then
        assertThat(join).isEqualTo(1);
        assertThat(account.getAccountId()).isNotNull();
    }


    @DisplayName("회원 ID로 조회하는 테스트")
    @Test
    void findById_success() {
        // given
        String email = "test111@email.com";
        String password = "pass";
        String nickname = "daeho";
        AccountRole accountRole = AccountRole.USER;
        Account account = createAccount(null, email, password, nickname, accountRole);
        accountDao.join(account);

        // when
        Account byId = accountDao.findById(account.getAccountId());

        // then
        assertThat(byId).isNotNull();
        assertThat(byId.getAccountNickname()).isEqualTo(account.getAccountNickname());
    }

    @DisplayName("회원을 수정하는 테스트")
    @Test
    void update_success() {
        // given
        String email = "test111@email.com";
        String password = "pass";
        String nickname = "daeho";
        AccountRole accountRole = AccountRole.USER;
        Account account = createAccount(null, email, password, nickname, accountRole);
        accountDao.join(account);
        Account byId = accountDao.findById(account.getAccountId());
        byId.setAccountNickname("changed nickname");

        // when
        int update = accountDao.update(account);

        // then
        assertThat(update).isEqualTo(1);
    }

    @DisplayName("회원을 삭제하는 테스트")
    @Test
    void delete_success() {
        // given
        String email = "test111@email.com";
        String password = "pass";
        String nickname = "daeho";
        AccountRole accountRole = AccountRole.USER;
        Account account = createAccount(null, email, password, nickname, accountRole);
        accountDao.join(account);

        // when
        int delete = accountDao.delete(account.getAccountId());

        // then
        assertThat(delete).isEqualTo(1);
    }


    /* ---------------------------------------------------------------------*/

    private static AccountRequestDto createAccountDto(String email, String password,
                                                      String nickname, AccountRole accountRole) {
        return AccountRequestDto.builder()
                .accountEmail(email)
                .accountPassword(password)
                .accountNickname(nickname)
                .accountRole(accountRole)
                .build();
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