package com.generic.bank.bankingapi.service;

import com.generic.bank.bankingapi.bankapienum.Role;
import com.generic.bank.bankingapi.model.BankUser;
import com.generic.bank.bankingapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private BankUser mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = new BankUser();
        mockUser.setUsername("testUser");
        mockUser.setName("Test User");
        mockUser.setRole(Role.USER);
        mockUser.setPassword("encodedPassword");
    }

    @Test
    void createUser_Success() {
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(BankUser.class))).thenReturn(mockUser);

        BankUser createdUser = userService.createUser("Test User", "testUser", "password123", Role.USER);

        assertNotNull(createdUser);
        assertEquals("testUser", createdUser.getUsername());
        assertEquals("Test User", createdUser.getName());
        assertEquals(Role.USER, createdUser.getRole());
        assertEquals("encodedPassword", createdUser.getPassword());

        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(BankUser.class));
    }

    @Test
    void getAllUsers_Success() {
        BankUser user1 = new BankUser();
        user1.setUsername("user1");
        user1.setName("User One");
        user1.setRole(Role.USER);
        user1.setPassword("password1");

        BankUser user2 = new BankUser();
        user2.setUsername("user2");
        user2.setName("User Two");
        user2.setRole(Role.USER);
        user2.setPassword("password2");

        List<BankUser> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        List<BankUser> allUsers = userService.getAllUsers();

        assertNotNull(allUsers);
        assertEquals(2, allUsers.size());
        assertEquals("user1", allUsers.get(0).getUsername());
        assertEquals("user2", allUsers.get(1).getUsername());

        verify(userRepository).findAll();
    }
}
