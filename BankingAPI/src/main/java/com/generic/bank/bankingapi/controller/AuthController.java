package com.generic.bank.bankingapi.controller;

import com.generic.bank.bankingapi.bankapienum.Role;
import com.generic.bank.bankingapi.bankutil.JwtUtil;
import com.generic.bank.bankingapi.dto.AuthRequest;
import com.generic.bank.bankingapi.dto.LoginRequest;
import com.generic.bank.bankingapi.model.BankUser;
import com.generic.bank.bankingapi.repository.UserRepository;
import com.generic.bank.bankingapi.service.AuthService;
import com.generic.bank.bankingapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AuthRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        userService.createUser(request.getName(),
                request.getUsername(),
                request.getPassword(),
                request.getRole());
        log.info("User created");
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.authenticate(loginRequest);
        return ResponseEntity.ok(token);
    }
}
