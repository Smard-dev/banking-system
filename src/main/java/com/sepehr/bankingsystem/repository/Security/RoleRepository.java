package com.sepehr.bankingsystem.repository.Security;

import com.sepehr.bankingsystem.entity.Security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
}
