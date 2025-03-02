package com.generic.bank.bankingapi.controller;

import com.generic.bank.bankingapi.dto.TransferRequest;
import com.generic.bank.bankingapi.model.BankTransaction;
import com.generic.bank.bankingapi.service.TransactionService;
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

class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void transfer_Success() {
        TransferRequest request = new TransferRequest("12345", "67890", 100.0);
        doNothing().when(transactionService).transfer("12345", "67890", 100.0);

        ResponseEntity<String> response = transactionController.transfer(request);

        assertEquals(OK, response.getStatusCode());
        assertEquals("Transfer successful", response.getBody());
    }

    @Test
    void transfer_NegativeAmount() {
        TransferRequest request = new TransferRequest("12345", "67890", -50.0);

        ResponseEntity<String> response = transactionController.transfer(request);

        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertEquals("Transfer Amount must be positive", response.getBody());
    }

    @Test
    void transfer_Failed() {
        TransferRequest request = new TransferRequest("12345", "67890", 100.0);
        doThrow(new RuntimeException("Insufficient funds"))
                .when(transactionService).transfer("12345", "67890", 100.0);

        ResponseEntity<String> response = transactionController.transfer(request);

        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertEquals("Transfer Failed Insufficient funds", response.getBody());
    }

    @Test
    void deposit_Success() {
        BankTransaction mockTransaction = new BankTransaction();
        mockTransaction.setAmount(200);
        mockTransaction.setFromAccountNumber("12345");
        mockTransaction.setToAccountNumber("23456");
        mockTransaction.setTransactionType("WithDrawl");
        when(transactionService.deposit("12345", 200.0)).thenReturn(mockTransaction);

        ResponseEntity<BankTransaction> response = transactionController.deposit("12345", 200.0);

        assertEquals(OK, response.getStatusCode());
        assertEquals(mockTransaction, response.getBody());
    }

    @Test
    void withdraw_Success() {
        BankTransaction mockTransaction = new BankTransaction();
        mockTransaction.setAmount(200);
        mockTransaction.setFromAccountNumber("12345");
        mockTransaction.setToAccountNumber("23456");
        mockTransaction.setTransactionType("WithDrawl");
        when(transactionService.withdraw("12345", 100.0)).thenReturn(mockTransaction);

        ResponseEntity<BankTransaction> response = transactionController.withdraw("12345", 100.0);

        assertEquals(OK, response.getStatusCode());
        assertEquals(mockTransaction, response.getBody());
    }

    @Test
    void getTransactionHistory_Success() {
        BankTransaction mockTransaction = new BankTransaction();
        mockTransaction.setAmount(200);
        mockTransaction.setFromAccountNumber("12345");
        mockTransaction.setToAccountNumber("23456");
        mockTransaction.setTransactionType("WithDrawl");

        BankTransaction mockTransaction1 = new BankTransaction();
        mockTransaction.setAmount(500);
        mockTransaction.setFromAccountNumber("12345");
        mockTransaction.setTransactionType("Deposit");
        List<BankTransaction> mockTransactions = List.of(
               mockTransaction1, mockTransaction
        );
        when(transactionService.getTransactionHistory("12345")).thenReturn(mockTransactions);

        ResponseEntity<?> response = transactionController.getTransactionHistory("12345");

        assertEquals(OK, response.getStatusCode());
        assertEquals(mockTransactions, response.getBody());
    }

    @Test
    void getTransactionHistory_AccountNotFound() {
        when(transactionService.getTransactionHistory("12345")).thenReturn(List.of());

        ResponseEntity<?> response = transactionController.getTransactionHistory("12345");

        assertEquals(NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getTransactionHistory_InvalidAccountNumber() {
        ResponseEntity<?> response = transactionController.getTransactionHistory("");

        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid Account Number", response.getBody());
    }
}
