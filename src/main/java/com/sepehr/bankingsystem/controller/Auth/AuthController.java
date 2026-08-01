package com.sepehr.bankingsystem.controller.Auth;

import com.sepehr.bankingsystem.config.JwtService;
import com.sepehr.bankingsystem.entity.Security.Role;
import com.sepehr.bankingsystem.entity.Users.User;
import com.sepehr.bankingsystem.repository.Security.RoleRepository;
import com.sepehr.bankingsystem.repository.Users.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(UserRepository userRepository, RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                          JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        if (userRepository.findByNationalId(request.nationalId()).isPresent()) {
            throw new IllegalStateException("user with this national ID already exists");
        }

        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setNationalId(request.nationalId());
        user.setPhoneNumber(request.phoneNumber());
        user.setEmail(request.email());
        user.setDateOfBirth(request.dateOfBirth());
        user.setPassword(passwordEncoder.encode(request.password()));
        // پسورد رو قبل از ذخیره، هش میکنیم - هیچوقت پسورد خام ذخیره نمیشه
        user.setCreatedAt(LocalDateTime.now());

        Role customerRole = roleRepository.findAll().stream()
                .filter(r -> r.getRoleName().equals("CUSTOMER"))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("CUSTOMER role not found"));
        Set<Role> roles = new HashSet<>();
        roles.add(customerRole);
        user.setRoles(roles);
        // هر کاربر جدید به‌صورت پیش‌فرض role مشتری عادی میگیره

        userRepository.save(user);

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getNationalId())
                .password(user.getPassword())
                .authorities("ROLE_CUSTOMER")
                .build();

        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.nationalId(), request.password())
        );
        // این خط خودش چک میکنه پسورد درسته یا نه (با DaoAuthenticationProvider که تعریف کردیم)
        // اگه غلط بود، خودکار یه exception پرتاب میکنه (BadCredentialsException)

        User user = userRepository.findByNationalId(request.nationalId())
                .orElseThrow(() -> new IllegalStateException("user not found"));

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getNationalId())
                .password(user.getPassword())
                .authorities(user.getRoles().stream()
                        .map(r -> "ROLE_" + r.getRoleName())
                        .toArray(String[]::new))
                .build();

        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token);
    }
}