package com.generic.bank.bankingapi.service;

import com.generic.bank.bankingapi.model.BankAccount;
import com.generic.bank.bankingapi.model.BankUser;
import com.generic.bank.bankingapi.repository.AccountRepository;
import com.generic.bank.bankingapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private BankUser user;
    private BankAccount bankAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new BankUser();
        user.setUserId(1L);
        user.setName("John Doe");

        bankAccount = new BankAccount();
        bankAccount.setAccountNumber("1234567890");
        bankAccount.setBalance(500.0);
        bankAccount.setUser(user);
    }

    @Test
    void testCreateAccount_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        bankAccount.setAccountType("SAVING");
        when(accountRepository.save(any(BankAccount.class))).thenReturn(bankAccount);

        BankAccount createdAccount = accountService.createAccount(1L, 500.0, "SAVINGS");

        assertNotNull(createdAccount);
        assertEquals("1234567890", createdAccount.getAccountNumber());
        assertEquals(500.0, createdAccount.getBalance());
        assertEquals("SAVING", createdAccount.getAccountType());

        verify(userRepository, times(1)).findById(anyLong());
        verify(accountRepository, times(1)).save(any(BankAccount.class));
    }

    @Test
    void testCreateAccount_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            accountService.createAccount(1L, 500.0, "SAVINGS");
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(anyLong());
        verify(accountRepository, times(0)).save(any(BankAccount.class));
    }

    @Test
    void testGetBalance_Success() {
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(bankAccount));

        double balance = accountService.getBalance("1234567890");

        assertEquals(500.0, balance);
        verify(accountRepository, times(1)).findByAccountNumber(anyString());
    }

    @Test
    void testGetBalance_AccountNotFound() {
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            accountService.getBalance("1234567890");
        });

        assertEquals("Account not found", exception.getMessage());
        verify(accountRepository, times(1)).findByAccountNumber(anyString());
    }

    @Test
    void testGetUserAccounts_Success() {
        when(accountRepository.findByBankUserId(anyLong())).thenReturn(Optional.of(List.of(bankAccount)));

        var accounts = accountService.getUserAccounts(1L);

        assertNotNull(accounts);
        assertFalse(accounts.isEmpty());
        assertEquals(1, accounts.size());
        assertEquals("1234567890", accounts.get(0).getAccountNumber());

        verify(accountRepository, times(1)).findByBankUserId(anyLong());
    }

    @Test
    void testGetUserAccounts_AccountNotFound() {
        when(accountRepository.findByBankUserId(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            accountService.getUserAccounts(1L);
        });

        assertEquals("Account doesn't exists for given userId", exception.getMessage());
        verify(accountRepository, times(1)).findByBankUserId(anyLong());
    }
}
