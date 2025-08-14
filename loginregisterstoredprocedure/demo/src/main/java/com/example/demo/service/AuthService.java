package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.config.JwtUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public String register(String username, String password) {
        try {

            userRepository.registerUser(username, password);

            return "User registered successfully!";
        } catch (DataAccessException e) {

            Throwable cause = e.getMostSpecificCause();
            if (cause != null && cause.getMessage() != null) {
                return cause.getMessage().trim();
            }
            return "Registration failed!";
        }
    }

    public String login(String username, String password) {
        try {
            User user = userRepository.loginUser(username, password);
            if (user != null) {
                return "Login successful. Token: " + jwtUtil.generateToken(username);
            }
            return "Invalid username or password";
        } catch (DataAccessException e) {
            Throwable cause = e.getMostSpecificCause();
            return (cause != null && cause.getMessage() != null)
                    ? cause.getMessage().trim()
                    : "Login failed!";
        }
    }
}
