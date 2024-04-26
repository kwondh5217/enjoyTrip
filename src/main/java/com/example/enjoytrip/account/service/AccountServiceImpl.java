package com.example.enjoytrip.account.service;

import com.example.enjoytrip.account.dao.AccountDao;
import com.example.enjoytrip.account.domain.Account;
import com.example.enjoytrip.account.dto.AccountRequestDto;
import com.example.enjoytrip.account.dto.AccountResponseDto;
import com.example.enjoytrip.exception.CustomException;
import com.example.enjoytrip.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AccountServiceImpl implements AccountService{

    private final AccountDao accountDao;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public int delete(Integer accountId) {
        return accountDao.delete(accountId);
    }

    @Transactional
    @Override
    public Integer join(AccountRequestDto accountRequestDto) {
        Account byEmail = accountDao.findByEmail(accountRequestDto.getAccountEmail());
        if(byEmail != null){
            throw new CustomException(ErrorCode.DuplicateUserEmail);
        }
        Account account = modelMapper.map(accountRequestDto, Account.class);
        return accountDao.join(account);
    }

    @Override
    public AccountResponseDto findById(Integer accountId) {
        Account byId = accountDao.findById(accountId);
        AccountResponseDto responseDto = modelMapper.map(byId, AccountResponseDto.class);
        return responseDto;
    }

    @Transactional
    @Override
    public int update(Account account) {
        return accountDao.update(account);
    }
}
