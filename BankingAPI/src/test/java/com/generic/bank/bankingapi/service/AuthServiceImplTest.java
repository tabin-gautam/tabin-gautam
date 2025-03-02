package com.generic.bank.bankingapi.service;

import com.generic.bank.bankingapi.bankutil.JwtUtil;
import com.generic.bank.bankingapi.dto.LoginRequest;
import com.generic.bank.bankingapi.model.BankUser;
import com.generic.bank.bankingapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    private BankUser mockUser;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initializes mocks

        // Create a mock BankUser and LoginRequest
        mockUser = new BankUser();
        mockUser.setUsername("testUser");
        mockUser.setPassword(new BCryptPasswordEncoder().encode("password123"));

        loginRequest = new LoginRequest("testUser", "password123");
    }

    @Test
    void authenticate_Success() {
        // Arrange
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("password123", mockUser.getPassword())).thenReturn(true);
        when(jwtUtil.generateToken(mockUser)).thenReturn("mockJwtToken");

        // Act
        String token = authService.authenticate(loginRequest);

        // Assert
        assertNotNull(token);
        assertEquals("mockJwtToken", token);

        // Verify interactions
        verify(userRepository).findByUsername("testUser");
        verify(passwordEncoder).matches("password123", mockUser.getPassword());
        verify(jwtUtil).generateToken(mockUser);
    }

    @Test
    void authenticate_InvalidCredentials() {
        // Arrange
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("wrongPassword", mockUser.getPassword())).thenReturn(false);

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            authService.authenticate(new LoginRequest("testUser", "wrongPassword"));
        });

        assertEquals("Invalid credentials provided", thrown.getMessage());

        // Verify interactions
        verify(userRepository).findByUsername("testUser");
        verify(passwordEncoder).matches("wrongPassword", mockUser.getPassword());
    }

    @Test
    void authenticate_UserNotFound() {
        // Arrange
        when(userRepository.findByUsername("nonExistentUser")).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            authService.authenticate(new LoginRequest("nonExistentUser", "password123"));
        });

        assertEquals("Invalid credentials provided", thrown.getMessage());

        // Verify interaction
        verify(userRepository).findByUsername("nonExistentUser");
    }
}
