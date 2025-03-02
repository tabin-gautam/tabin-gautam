package com.generic.bank.bankingapi.controller;

import com.generic.bank.bankingapi.bankapienum.Role;
import com.generic.bank.bankingapi.dto.CreateAccountRequest;
import com.generic.bank.bankingapi.model.BankAccount;
import com.generic.bank.bankingapi.model.BankUser;
import com.generic.bank.bankingapi.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAccount_Success() {
        CreateAccountRequest request = new CreateAccountRequest(1L, "SAVINGS",100.0);
        BankUser  bankUser = new BankUser();
        bankUser.setUserId(1L);
        bankUser.setRole(Role.USER);
        BankAccount mockAccount = new BankAccount(1L,bankUser, "12345" ,"SAVINGS", 100.0);

        when(accountService.createAccount(1L, 100.0, "SAVINGS")).thenReturn(mockAccount);

        ResponseEntity<?> response = accountController.createAccount(request);

        assertEquals(OK, response.getStatusCode());
        assertEquals(mockAccount, response.getBody());
    }

    @Test
    void createAccount_InvalidUserId() {
        CreateAccountRequest request = new CreateAccountRequest(null, "SAVINGS",100.0);

        ResponseEntity<?> response = accountController.createAccount(request);

        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertEquals("User ID cannot be null", response.getBody());
    }

    @Test
    void createAccount_NegativeDeposit() {
        CreateAccountRequest request = new CreateAccountRequest(1L,  "SAVINGS",-10.0);

        ResponseEntity<?> response = accountController.createAccount(request);

        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertEquals("Initial deposit cannot be negative", response.getBody());
    }

    @Test
    void createAccount_UserNotFound() {
        CreateAccountRequest request = new CreateAccountRequest(1L, "SAVINGS", 100.0);

        when(accountService.createAccount(1L, 100.0, "SAVINGS")).thenThrow(new RuntimeException("User not found"));

        ResponseEntity<?> response = accountController.createAccount(request);

        assertEquals(NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody());
    }

    @Test
    void getBalance_Success() {
        when(accountService.getBalance("12345")).thenReturn(500.0);

        ResponseEntity<?> response = accountController.getBalance("12345");

        assertEquals(OK, response.getStatusCode());
        assertEquals(500.0, response.getBody());
    }

    @Test
    void getBalance_EmptyAccountNumber() {
        ResponseEntity<?> response = accountController.getBalance("");

        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertEquals("Account Number cannot be null or invalid", response.getBody());
    }

    @Test
    void getBalance_AccountNotFound() {
        when(accountService.getBalance("12345")).thenThrow(new RuntimeException("Account not found"));

        ResponseEntity<?> response = accountController.getBalance("12345");

        assertEquals(NOT_FOUND, response.getStatusCode());
        assertEquals("Account not found", response.getBody());
    }

    @Test
    void getUserAccounts_Success() {
        List<BankAccount> mockAccounts = List.of(new BankAccount(1L, new BankUser(), "12345", "SAVING", 500));

        when(accountService.getUserAccounts(1L)).thenReturn(mockAccounts);

        ResponseEntity<?> response = accountController.getUserAccounts(1L);

        assertEquals(OK, response.getStatusCode());
        assertEquals(mockAccounts, response.getBody());
    }

    @Test
    void getUserAccounts_AccountNotFound() {
        when(accountService.getUserAccounts(1L)).thenThrow(new RuntimeException("Account doesn't exist for given userId"));

        ResponseEntity<?> response = accountController.getUserAccounts(1L);

        assertEquals(NOT_FOUND, response.getStatusCode());
        assertEquals("Account doesn't exists for given userId", response.getBody());
    }
}
