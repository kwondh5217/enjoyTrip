package com.example.enjoytrip.account.dao;

import com.example.enjoytrip.account.domain.Account;
import com.example.enjoytrip.account.dto.AccountRequestDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountDao {
    Integer join(AccountRequestDto accountRequestDto);
}
