package com.example.enjoytrip.account.dao;

import com.example.enjoytrip.account.dto.AccountRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
        AccountRequestDto requestDto = AccountRequestDto.builder()
                .accountEmail("test@email.com")
                .accountPassword("pass")
                .accountNickname("daeho")
                .build();

        // when
        Integer join = accountDao.join(requestDto);

        // then
        assertThat(join).isNotNull();
    }

}