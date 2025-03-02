package com.generic.bank.bankingapi.service;

import com.generic.bank.bankingapi.dto.AuthRequest;
import com.generic.bank.bankingapi.dto.LoginRequest;

public interface AuthService {
    public String authenticate(LoginRequest request);
}
