package com.sepehr.bankingsystem.controller.Accounts;

import com.sepehr.bankingsystem.entity.Accounts.Currency;
import com.sepehr.bankingsystem.repository.Accounts.CurrencyRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {
    private final CurrencyRepository currencyRepository;

    public CurrencyController(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;

    }
    @GetMapping("/{id}")
    public Currency getCurrencyById(@PathVariable Integer id) {
        return currencyRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Currency not found"));
    }

    @GetMapping
    public List<Currency> getAllCurrency(){
        return currencyRepository.findAll();
    }

}
