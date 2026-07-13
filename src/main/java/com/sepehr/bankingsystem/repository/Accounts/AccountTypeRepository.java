package com.sepehr.bankingsystem.repository.Accounts;

import com.sepehr.bankingsystem.entity.Accounts.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AccountTypeRepository extends JpaRepository<AccountType,Integer> {
}
