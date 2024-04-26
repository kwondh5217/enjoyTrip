package com.example.enjoytrip.account.common;

import com.example.enjoytrip.account.domain.Account;
import com.example.enjoytrip.account.domain.AccountRole;
import com.example.enjoytrip.account.dto.AccountRequestDto;
import com.example.enjoytrip.account.dto.AccountResponseDto;

public class AccountTestUtil {

    public static Account createAccount(Integer id, String email, String password, String nickname, AccountRole accountRole) {
        return Account.builder()
                .accountId(id)
                .accountEmail(email)
                .accountPassword(password)
                .accountNickname(nickname)
                .accountRole(accountRole)
                .build();
    }

    public static AccountRequestDto createAccountRequestDto(String email, String password, String nickname) {
        return AccountRequestDto.builder()
                .accountEmail(email)
                .accountPassword(password)
                .accountNickname(nickname)
                .build();
    }
    public static AccountResponseDto createAccountResponseDto(Integer id, String email,
                                                               String nickname, AccountRole accountRole) {
        return AccountResponseDto.builder()
                .accountId(id)
                .accountEmail(email)
                .accountNickname(nickname)
                .accountRole(accountRole)
                .build();
    }
}
