package com.sepehr.bankingsystem.repository;
import com.sepehr.bankingsystem.entity.Account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findByAccountNumber(String accountNumber);
}
