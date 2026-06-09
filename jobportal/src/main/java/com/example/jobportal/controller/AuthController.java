package com.example.jobportal.controller;

import com.example.jobportal.model.User;
import com.example.jobportal.repository.UserRepository;
import com.example.jobportal.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    // TEMPLATE: Always needed for user operations
    @Autowired
    private UserRepository userRepository;

    // TEMPLATE: Always needed for JWT operations
    @Autowired
    private JwtUtil jwtUtil;

    // TEMPLATE: Always needed for password encryption
    @Autowired
    private PasswordEncoder passwordEncoder;

    // CHANGE PER PROJECT: Registration logic
    @PostMapping("/register")
    public String register(@RequestBody User user) {

        // Check duplicate email
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            return "Email already registered!";
        }

        // TEMPLATE: Always encode password before saving
        // PURPOSE: Never store plain text passwords!
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
        return "User registered successfully!";
    }

    // CHANGE PER PROJECT: Login logic
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {

        Map<String, String> response = new HashMap<>();

        // Find user by email
        User existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser == null) {
            response.put("error", "User not found!");
            return response;
        }

        // TEMPLATE: Always use BCrypt matches for password check
        // PURPOSE: Compare plain password with encrypted password
        if (passwordEncoder.matches(user.getPassword(),
                existingUser.getPassword())) {

            // TEMPLATE: Always generate JWT token on successful login
            String token = jwtUtil.generateToken(
                existingUser.getEmail(),
                existingUser.getRole()
            );

            // CHANGE PER PROJECT: What to return after login
            response.put("token", token);
            response.put("role", existingUser.getRole());
            response.put("name", existingUser.getName());
            return response;

        } else {
            response.put("error", "Wrong password!");
            return response;
        }
    }
}