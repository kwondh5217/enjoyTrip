package com.example.enjoytrip.account.domain;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Account {
    private Long accountId;
    private String accountEmail;
    private String accountPassword;
    private AccountRole role;
}
