package com.sepehr.bankingsystem.controller;

import com.sepehr.bankingsystem.entity.User;
import com.sepehr.bankingsystem.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")

public class UserController {
    private final UserService userService;
    public UserController (UserService userService){
       this.userService = userService;
    }

    @PostMapping
    public User createUser (@RequestBody User newUser){
        return userService.registerUser(newUser);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id ){
        return userService.getUserById(id);
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id ){
        userService.deleteUser(id);
    }
}





















