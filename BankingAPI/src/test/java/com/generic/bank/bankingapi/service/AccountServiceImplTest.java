/*
package com.generic.bank.bankingapi.service;

import com.generic.bank.bankingapi.model.BankAccount;
import com.generic.bank.bankingapi.model.BankUser;
import com.generic.bank.bankingapi.repository.AccountRepository;
import com.generic.bank.bankingapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private BankUser testUser;
    private BankAccount testAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new BankUser();
        testUser.setUserId(1L);
        testUser.setUsername("Santiago Bernabou");

        testAccount = new BankAccount();
        testAccount.setAccountId(1L);
        testAccount.setUser(testUser);
        testAccount.setBalance(5000.0);
    }

    @Test
    void testCreateAccount_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(accountRepository.save(any(BankAccount.class))).thenReturn(testAccount);

        BankAccount createdAccount = accountService.createAccount(1L, 5000.0);

        assertNotNull(createdAccount);
        assertEquals(testUser, createdAccount.getUser());
        assertEquals(5000.0, createdAccount.getBalance());
    }

    @Test
    void testCreateAccount_UserNotFound() {
        when(userRepository.findById(20L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            accountService.createAccount(20L, 500.0);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testGetBalance_Success() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));

        double balance = accountService.getBalance(1L);

        assertEquals(5000.0, balance);
    }

    @Test
    void testGetBalance_AccountNotFound() {
        when(accountRepository.findById(20L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            accountService.getBalance(20L);
        });

        assertEquals("Account not found", exception.getMessage());
    }

    @Test
    void testGetUserAccounts_Success() {
        List<BankAccount> accounts = Arrays.asList(testAccount);
        when(accountRepository.findByBankUserId(1L)).thenReturn(Optional.of(accounts));

        List<BankAccount> retrievedAccounts = accountService.getUserAccounts(1L);

        assertFalse(retrievedAccounts.isEmpty());
        assertEquals(1, retrievedAccounts.size());
        assertEquals(testAccount, retrievedAccounts.get(0));
    }

    @Test
    void testGetUserAccounts_Fails_When_UserNotFound() {
        when(accountRepository.findByBankUserId(20L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            accountService.getUserAccounts(20L);
        });

        assertEquals("Account doesn't exists for given userId", exception.getMessage());

    }
}
*/
