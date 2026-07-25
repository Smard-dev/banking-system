package com.sepehr.bankingsystem.controller.Accounts;

import com.sepehr.bankingsystem.entity.Accounts.Account;
import com.sepehr.bankingsystem.entity.Accounts.AccountType;
import com.sepehr.bankingsystem.entity.Accounts.Currency;
import com.sepehr.bankingsystem.entity.Users.User;
import com.sepehr.bankingsystem.service.AccountService;
import jakarta.validation.Valid;
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
    public Account creatAccount(@Valid @RequestBody AccountCreateRequest request) {
        Account newAccount = new Account();
        User user = new User();
        user.setId(request.userId());
        newAccount.setUser(user);
        newAccount.setAccountNumber(request.accountNumber());

        AccountType type = new AccountType();
        type.setTypeId(request.accountTypeId());
        newAccount.setAccountType(type);

        if (request.currencyId() != null) {
            Currency currency = new Currency();
            currency.setId(request.currencyId());
            newAccount.setCurrency(currency);
        }
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
