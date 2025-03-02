package com.generic.bank.bankingapi.controller;


import com.generic.bank.bankingapi.bankapienum.Role;
import com.generic.bank.bankingapi.dto.AuthRequest;
import com.generic.bank.bankingapi.dto.LoginRequest;
import com.generic.bank.bankingapi.model.BankUser;
import com.generic.bank.bankingapi.service.AuthService;
import com.generic.bank.bankingapi.service.UserService;
import com.generic.bank.bankingapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthService authService;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    private AuthRequest authRequest;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        authRequest = new AuthRequest("John Doe", "johndoe", "password", Role.USER);
        loginRequest = new LoginRequest("johndoe", "password");
    }

    @Test
    void testRegisterUser_Success() {
        AuthRequest request = new AuthRequest("John Doe", "johndoe", "password123", Role.USER);

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        ResponseEntity<String> response = authController.registerUser(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User registered successfully!", response.getBody());

        verify(userRepository, times(1)).findByUsername(anyString());
        verify(userService, times(1)).createUser(anyString(), anyString(), anyString(), any(Role.class));
    }


    @Test
    void testRegisterUser_Failure_UsernameTaken() {
        when(userRepository.findByUsername(authRequest.getUsername())).thenReturn(Optional.of(mock(BankUser.class)));

        ResponseEntity<String> response = authController.registerUser(authRequest);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Username is already taken!", response.getBody());
    }

    @Test
    void testLogin_Success() {
        when(authService.authenticate(loginRequest)).thenReturn("mocked-jwt-token");

        ResponseEntity<String> response = authController.login(loginRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("mocked-jwt-token", response.getBody());
    }
}
