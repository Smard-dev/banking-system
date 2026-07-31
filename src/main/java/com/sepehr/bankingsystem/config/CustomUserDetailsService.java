package com.sepehr.bankingsystem.config;

import com.sepehr.bankingsystem.entity.Users.User;
import com.sepehr.bankingsystem.repository.Users.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    // implements یعنی این کلاس متعهد میشه متدهای UserDetailsService رو پیاده‌سازی کنه
    // UserDetailsService فقط یه متد داره: loadUserByUsername

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    // میگه این متد داره یه متد از interface والد رو پیاده‌سازی میکنه
    public UserDetails loadUserByUsername(String nationalId) throws UsernameNotFoundException {
        // اینجا از nationalId به عنوان "username" استفاده میکنیم
        // چون تو این پروژه، کد ملی نقش شناسه‌ی ورود رو داره

        User user = userRepository.findByNationalId(nationalId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + nationalId));

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> (GrantedAuthority) new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
                .toList();
        // اینجا هر Role کاربر رو به فرمتی که Spring Security میفهمه تبدیل میکنیم
        // پیشوند "ROLE_" یه قرارداد اجباری خود Spring Security هست

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getNationalId())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
        // این یه کلاس آماده‌ی خود Spring Security هست (اسمش هم User هست، برای همین با مسیر کامل نوشتمش
        // تا با کلاس User خودمون قاطی نشه) که اطلاعات لازم برای احراز هویت رو نگه میداره
    }
}