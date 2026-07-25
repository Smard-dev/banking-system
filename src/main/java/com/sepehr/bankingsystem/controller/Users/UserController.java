package com.sepehr.bankingsystem.controller.Users;

import com.sepehr.bankingsystem.entity.Users.User;
import com.sepehr.bankingsystem.service.UserService;
import jakarta.validation.Valid;
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
    public User createUser(@Valid @RequestBody UserCreateRequest request) {
        User newUser = new User();
        newUser.setFirstName(request.firstName());
        newUser.setLastName(request.lastName());
        newUser.setNationalId(request.nationalId());
        newUser.setPhoneNumber(request.phoneNumber());
        newUser.setEmail(request.email());
        newUser.setDateOfBirth(request.dateOfBirth());
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





















