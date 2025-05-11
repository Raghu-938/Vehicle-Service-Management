package com.aits.VehicleServicesManagement.controller;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aits.VehicleServicesManagement.entity.Users;
import com.aits.VehicleServicesManagement.repo.UserRepository;
import com.aits.VehicleServicesManagement.security.JwtUtil;



@RestController
@RequestMapping("/auth")
public class AuthController {
 
    @Autowired private UserRepository userRepo;
    @Autowired private PasswordEncoder encoder;
    @Autowired private JwtUtil jwtUtil;
 
    @PostMapping("/register")
    public String register(@RequestBody Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
        return "User registered!";
    }
 
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Users loginUser) {
        Users user = userRepo.findByEmail(loginUser.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));
 
        if (encoder.matches(loginUser.getPassword(), user.getPassword())) {
            String token = jwtUtil.generateToken(user.getEmail());
            return Map.of("token", token, "role", user.getRole());
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}