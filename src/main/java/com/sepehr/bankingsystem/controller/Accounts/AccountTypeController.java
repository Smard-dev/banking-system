package com.sepehr.bankingsystem.controller.Accounts;

import com.sepehr.bankingsystem.entity.Accounts.AccountType;
import com.sepehr.bankingsystem.repository.Accounts.AccountTypeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/accountType")
public class AccountTypeController {
    private final AccountTypeRepository accountTypeRepository;

    public AccountTypeController(AccountTypeRepository accountTypeRepository) {
        this.accountTypeRepository = accountTypeRepository;
    }
    @GetMapping("/{id}")
    public AccountType getAccountTypeById(@PathVariable Integer id){
        return accountTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("type not found"));
    }
    @GetMapping
    public List<AccountType> getAllAccountTypes(){
        return accountTypeRepository.findAll();
    }
}
