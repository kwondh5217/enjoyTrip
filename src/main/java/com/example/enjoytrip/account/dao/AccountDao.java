package com.example.enjoytrip.account.dao;

import com.example.enjoytrip.account.domain.Account;
import com.example.enjoytrip.account.dto.AccountRequestDto;
import com.example.enjoytrip.account.dto.AccountResponseDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountDao {
    Integer join(Account account);
    Account findById(Integer accountId);
    int update(Account account);
    int delete(Integer accountId);
    Account findByEmail(String email);
}
