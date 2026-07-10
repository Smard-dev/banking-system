package com.sepehr.bankingsystem.service;

import com.sepehr.bankingsystem.entity.User;
import com.sepehr.bankingsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User registerUser(User newUser){
        if (userRepository.findByNationalId(newUser.getNationalId()).isPresent()){
            throw new IllegalStateException("user with this national ID already exists");
        }
        newUser.setCreatedAt(LocalDateTime.now());
        return userRepository.save(newUser);
    }

    public User getUserById(Long id){
        Optional<User> result = userRepository.findById(id);
        // یه بار findById صدا زده میشه، نتیجه‌ش تو یه متغیر ذخیره میشه
        // به جای اینکه دوباره صداش بزنیم

        if (result.isEmpty()){
            throw new IllegalStateException("there aren't any user with this ID");
        }
        return result.get();
        // چون بالا چک کردیم isEmpty نیست، اینجا مطمئنیم get() امنه
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void deleteUser(Long id){
        if (!userRepository.existsById(id)){
            // existsById فقط چک میکنه ردیف هست یا نه، بدون گرفتن کل شیء
            // سبک‌تر از findById برای این حالت
            throw new IllegalStateException("user with this ID doesn't exist");
        }
        userRepository.deleteById(id);
    }
}