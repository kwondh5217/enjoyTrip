package com.example.enjoytrip.account.dao;

import com.example.enjoytrip.account.common.AccountTestUtil;
import com.example.enjoytrip.account.domain.Account;
import com.example.enjoytrip.account.domain.AccountRole;
import com.example.enjoytrip.touristspot.dao.TouristspotDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@ActiveProfiles("test")
@DisplayName("AccountDao 단위 테스트")
@Transactional
@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class AccountDaoTest {

    @Autowired
    AccountDao accountDao;
    @Autowired
    TouristspotDao touristspotDao;
    Account account;

    @BeforeEach
    void setUp() {
        String email = "test@email.com";
        String password = "pass";
        String nickname = "daeho";
        AccountRole accountRole = AccountRole.USER;

        account = AccountTestUtil.createAccount(null, email, password, nickname, accountRole);
    }

    @DisplayName("회원 저장 시, 저장된 회원에 새로운 ID가 할당되고 성공 코드를 반환")
    @Test
    void join() {
        // given : 회원저장에 필요한 데이터 설정
        final int successCode = 1;

        // when : 회원저장을 수행하는 메소드 호출
        Integer join = accountDao.join(account);

        // then : null이었던 ID 값이 AUTO INCREMENT 값으로 할당되었음을 검증
        assertThat(join).isEqualTo(successCode);
        assertThat(account.getAccountId()).isNotNull();
    }

    @DisplayName("유효한 회원 ID로 회원조회 요청 시, 일치하는 회원정보 반환")
    @Test
    void findById_success() {
        // given : 회원조회에 필요한 데이터 설정
        accountDao.join(account);

        // when : 회원조회를 수행하는 메소드 호출
        Account byId = accountDao.findById(account.getAccountId());

        // then : 반환된 값이 null 이 아니며, 일치하는 정보임을 검증
        assertThat(byId).isNotNull();
        assertThat(byId).isEqualTo(account);
    }

    @DisplayName("유효한 회원의 회원수정 요청 시, 회원정보를 수정하고 성공코드를 반환")
    @Test
    void update_success() {
        // given : 회원수정에 필요한 데이터 설정
        accountDao.join(account);
        String changeNickName = "change";
        account.setAccountNickname(changeNickName);

        // when : 회원수정을 수행하는 메소드 호출
        int update = accountDao.update(account);

        // then : 변경사항이 수정되었음과 성공코드를 반환하였음을 검증
        Account updatedAccount = accountDao.findById(account.getAccountId());
        assertThat(update).isEqualTo(1);
        assertThat(updatedAccount.getAccountNickname()).isEqualTo(changeNickName);
    }

    @DisplayName("유효한 회원의 회원삭제 요청 시, 회원을 삭제하고 성공코드를 반환")
    @Test
    void delete_success() {
        // given : 회원삭제에 필요한 데이터 설정
        accountDao.join(account);

        // when : 회원삭제를 수행하는 메소드 호출
        int delete = accountDao.delete(account.getAccountId());

        // then : 회원을 삭제하고 성공코드를 반환하였음을 검증
        Account deletedAccount = accountDao.findById(account.getAccountId());
        assertThat(delete).isEqualTo(1);
        assertThat(deletedAccount).isNull();
    }

//    @DisplayName("회원이 여행지를 관심목록에 추가")
//    @Test
//    void addTouristspot_success() {
//        // given
//        accountDao.join(account);
//        int id = 125266;
//        touristspotDao.findById(id);
//    }
}