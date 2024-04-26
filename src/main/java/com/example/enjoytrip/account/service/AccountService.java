package com.example.enjoytrip.account.service;

import com.example.enjoytrip.account.domain.Account;
import com.example.enjoytrip.account.dto.AccountRequestDto;
import com.example.enjoytrip.account.dto.AccountResponseDto;

public interface AccountService {
    Integer join(AccountRequestDto accountRequestDto);
    AccountResponseDto findById(Integer accountId);
    int update(Account account);
    int delete(Integer accountId);
}
