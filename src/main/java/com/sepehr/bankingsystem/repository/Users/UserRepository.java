package com.sepehr.bankingsystem.repository.Users;
import com.sepehr.bankingsystem.entity.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByNationalId(String nationalId);
}
