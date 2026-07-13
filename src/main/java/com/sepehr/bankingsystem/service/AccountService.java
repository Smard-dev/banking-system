package com.sepehr.bankingsystem.service;
import com.sepehr.bankingsystem.entity.Accounts.Account;
import com.sepehr.bankingsystem.entity.Accounts.Currency;
import com.sepehr.bankingsystem.entity.Accounts.AccountType;
import com.sepehr.bankingsystem.repository.Accounts.CurrencyRepository;
import com.sepehr.bankingsystem.repository.Accounts.AccountRepository;
import com.sepehr.bankingsystem.repository.Accounts.AccountTypeRepository;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CurrencyRepository currencyRepository;
    private final AccountTypeRepository accountTypeRepository;

    public AccountService(AccountRepository accountRepository, CurrencyRepository currencyRepository, AccountTypeRepository accountTypeRepository) {
        this.accountRepository = accountRepository;
        this.currencyRepository = currencyRepository;
        this.accountTypeRepository = accountTypeRepository;
    }

    public Account regesterAccount(Account newAccount){
        if (accountRepository.findByAccountNumber(newAccount.getAccountNumber()).isPresent()){
            throw new IllegalStateException("account with this accountNumber already exists");
        }
        if (newAccount.getAccountType()== null){
            throw new IllegalStateException("you should select your account type");
        }
        if (newAccount.getCurrency() == null) {
            Currency defaultCurrency = currencyRepository.findById(1)
                    .orElseThrow(() -> new IllegalStateException("Default currency not found"));
            newAccount.setCurrency(defaultCurrency);
        }
        newAccount.setCreateTime(LocalDateTime.now());
        return accountRepository.save(newAccount);
    }

    public Account getAccountById(Long id){
        Optional<Account> result = accountRepository.findById(id);

        if (result.isEmpty()){
            throw new IllegalStateException("there are any account with this id");
        }
        return result.get();
    }

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public void deleteAccount(Long id){
        if (!accountRepository.existsById(id)) {
            throw new IllegalStateException("already this account doesnt exist ");
        }
        accountRepository.deleteById(id);
    }
}

















