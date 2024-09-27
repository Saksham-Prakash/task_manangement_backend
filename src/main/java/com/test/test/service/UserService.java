package com.test.test.service;

import com.test.test.model.User;
import com.test.test.repository.UserRepo;
import com.test.test.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String signUp(User user) {
        if (userRepository.findByUserName(user.getUserName()) != null) {
            throw new RuntimeException("User already exists!");
        }
        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully!";
    }

    public String login(String userName, String password) {
        User user = userRepository.findByUserName(userName);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        // Generate JWT token upon successful login
        return jwtUtil.generateToken(user.getUserName());
    }
}