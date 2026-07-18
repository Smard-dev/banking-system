package com.sepehr.bankingsystem.repository.Transfers;

import com.sepehr.bankingsystem.entity.Transfers.Transfer;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransferRepository extends JpaRepository<Transfer,Long> {
    Optional<Transfer> findByReferenceNumber(String referenceNumber);
}
