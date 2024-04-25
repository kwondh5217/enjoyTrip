package com.example.enjoytrip.account.dto;

import com.example.enjoytrip.account.domain.AccountRole;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AccountRequestDto {
    private String accountEmail;
    private String accountPassword;
    private String accountNickname;
    private AccountRole accountRole;
}
