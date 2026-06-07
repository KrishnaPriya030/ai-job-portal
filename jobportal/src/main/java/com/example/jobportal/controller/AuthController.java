package com.example.jobportal.controller;

import com.example.jobportal.model.User;
import com.example.jobportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")


public class AuthController {
    @Autowired

private UserRepository userRepository;

@PostMapping("/register")
public String register(@RequestBody User user){
     User existingUser = userRepository.findByEmail(user.getEmail());
    
    if (existingUser != null) {
        return "Email already registered!";
    }
     userRepository.save(user);
    return "User registered successfully!";
}
    
@PostMapping("/login")

public String login(@RequestBody User user){

    User existingUser=userRepository.findByEmail(user.getEmail());
    if(existingUser==null){
        return "User Not Found!";
    }
     if (existingUser.getPassword().equals(user.getPassword())) {
            return "Login successful! Role: " + existingUser.getRole();
        } else {
            return "Wrong password!";
        }
    }
}
    


