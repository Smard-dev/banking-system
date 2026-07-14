package com.sepehr.bankingsystem.controller;

import com.sepehr.bankingsystem.entity.Accounts.Account;
import com.sepehr.bankingsystem.service.AccountService;
import com.sepehr.bankingsystem.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/api/accounts")

public class AccountController {
    private final AccountService accountService;


    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public Account creatAccount(@RequestBody Account newAccount) {
        return accountService.regesterAccount(newAccount);
    }

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id ){
        return accountService.getAccountById(id);
    }

    @GetMapping
    public List<Account> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
    }
}
