package com.example.enjoytrip.account.service;

import com.example.enjoytrip.account.dao.AccountDao;
import com.example.enjoytrip.account.domain.Account;
import com.example.enjoytrip.exception.CustomException;
import com.example.enjoytrip.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetilsService implements UserDetailsService {

    private final AccountDao accountDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account byEmail = accountDao.findByEmail(username);
        if(byEmail == null){
            throw new CustomException(ErrorCode.UserNotFoundException);
        }

        return User.builder()
                .username(byEmail.getAccountEmail())
                .password(byEmail.getAccountPassword())
                .authorities(byEmail.getAccountRole().toString())
                .build();
    }
}
