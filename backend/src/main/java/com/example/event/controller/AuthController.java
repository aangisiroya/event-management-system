package com.example.event.controller;

import com.example.event.entity.User;
import com.example.event.repo.UserRepo;
import com.example.event.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired UserRepo userRepo;
    @Autowired JwtUtil jwtUtil;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        String role = body.getOrDefault("role", "USER");

        if (userRepo.findByUsername(username).isPresent())
            return Map.of("error", "exists");

        User u = new User();
        u.setUsername(username);
        u.setPassword(encoder.encode(password));
        u.setRole(role);

        userRepo.save(u);

        return Map.of("msg", "registered");
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        var o = userRepo.findByUsername(username);
        if (o.isEmpty()) return Map.of("error", "invalid");

        User u = o.get();

        if (!encoder.matches(password, u.getPassword()))
            return Map.of("error", "invalid");

        // FIX: include role in token
        String token = jwtUtil.generateToken(u.getUsername(), u.getRole());

        return Map.of("token", token, "role", u.getRole());
    }
}
