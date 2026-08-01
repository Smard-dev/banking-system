package com.sepehr.bankingsystem.config;

import com.sepehr.bankingsystem.entity.Users.User;
import com.sepehr.bankingsystem.repository.Users.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// این import رو اضافه کن

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    // این annotation باعث میشه session دیتابیس تا آخر این متد باز بمونه
    // پس وقتی به user.getRoles() میرسیم (که lazy-load هست)، بدون خطا کار میکنه
    public UserDetails loadUserByUsername(String nationalId) throws UsernameNotFoundException {
        User user = userRepository.findByNationalId(nationalId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + nationalId));

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> (GrantedAuthority) new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
                .toList();

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getNationalId())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}