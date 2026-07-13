package com.sepehr.bankingsystem.repository.Accounts;

import com.sepehr.bankingsystem.entity.Accounts.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CurrencyRepository extends JpaRepository<Currency,Integer>{
}
