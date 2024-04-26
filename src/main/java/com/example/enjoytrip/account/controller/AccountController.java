package com.example.enjoytrip.account.controller;

import com.example.enjoytrip.account.domain.Account;
import com.example.enjoytrip.account.dto.AccountRequestDto;
import com.example.enjoytrip.account.dto.AccountResponseDto;
import com.example.enjoytrip.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<Integer> join(@RequestBody AccountRequestDto accountRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.join(accountRequestDto));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponseDto> findById(@PathVariable("accountId") Integer accountId){
        return ResponseEntity.ok(accountService.findById(accountId));
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<Map<String, Integer>> update(@PathVariable("accountId") Integer accountId,
                                                      @RequestBody Account account){
        Map<String, Integer> map = new HashMap<>();
        int returnCode = accountService.update(account);
        map.put("code", returnCode);
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Map<String, Integer>> delete(@PathVariable("accountId") Integer accountId){
        Map<String, Integer> map = new HashMap<>();
        int returnCode = accountService.delete(accountId);
        map.put("code", returnCode);
        return ResponseEntity.ok(map);
    }
}
