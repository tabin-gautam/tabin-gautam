/*
package com.generic.bank.bankingapi.service;

import com.generic.bank.bankingapi.bankapienum.Role;
import com.generic.bank.bankingapi.model.BankUser;
import com.generic.bank.bankingapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private BankUser user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new BankUser(1L, "Santiago Bernabou","Santiago@Bernabou", "pswd", Role.USER);
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(any(BankUser.class))).thenReturn(user);
        BankUser createdUser = userService.createUser("Santiago Bernabou", "Santiago@Bernabou", "pswd");

        assertNotNull(createdUser);
        assertEquals("Santiago Bernabou", createdUser.getUsername());
        assertEquals(1L, createdUser.getUserId());
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        List<BankUser> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("Santiago Bernabou", users.get(0).getUsername());
    }

    @Test
    void testCreateUser_UserAlreadyExists() {
        when(userRepository.save(any(BankUser.class))).thenReturn(user);
        BankUser createdUser = userService.createUser("Santiago Bernabou","Santiago@Bernabou", "pswd");
        assertEquals("Santiago Bernabou", createdUser.getUsername());
        assertEquals(1L, createdUser.getUserId());
        verify(userRepository, times(1)).save(any(BankUser.class));
    }
}
*/
