package com.sepehr.bankingsystem.repository;
import com.sepehr.bankingsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByNationalId(String nationalId);
}
