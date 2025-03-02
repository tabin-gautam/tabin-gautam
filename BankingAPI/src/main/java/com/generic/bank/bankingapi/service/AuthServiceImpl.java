package com.generic.bank.bankingapi.service;

import com.generic.bank.bankingapi.bankutil.JwtUtil;
import com.generic.bank.bankingapi.dto.AuthRequest;
import com.generic.bank.bankingapi.dto.LoginRequest;
import com.generic.bank.bankingapi.model.BankUser;
import com.generic.bank.bankingapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public String authenticate(LoginRequest request) {
        Optional<BankUser> user = userRepository.findByUsername(request.getUsername());

        if (user.isPresent() && passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
            return jwtUtil.generateToken(user.get());
        } else {
            throw new RuntimeException("Invalid credentials provided");
        }
    }

}


