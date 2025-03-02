package com.generic.bank.bankingapi.service;

import com.generic.bank.bankingapi.bankapienum.TransactionType;
import com.generic.bank.bankingapi.model.BankAccount;
import com.generic.bank.bankingapi.model.BankTransaction;
import com.generic.bank.bankingapi.repository.AccountRepository;
import com.generic.bank.bankingapi.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private BankAccount fromAccount;
    private BankAccount toAccount;
    private BankTransaction transaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        fromAccount = new BankAccount();
        fromAccount.setAccountNumber("1234567890");
        fromAccount.setBalance(1000.0);

        toAccount = new BankAccount();
        toAccount.setAccountNumber("0987654321");
        toAccount.setBalance(500.0);

        transaction = new BankTransaction();
        transaction.setFromAccountNumber(fromAccount.getAccountNumber());
        transaction.setToAccountNumber(toAccount.getAccountNumber());
        transaction.setAmount(200.0);
        transaction.setTransactionType(TransactionType.TRANSFER.toString());
        transaction.setTimestamp(LocalDateTime.now());
    }

    @Test
    void testTransfer_Success() {
        when(accountRepository.findByAccountNumber("1234567890")).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByAccountNumber("0987654321")).thenReturn(Optional.of(toAccount));
        when(accountRepository.save(any(BankAccount.class))).thenReturn(fromAccount).thenReturn(toAccount);
        when(transactionRepository.save(any(BankTransaction.class))).thenReturn(transaction);

        transactionService.transfer("1234567890", "0987654321", 200.0);

        assertEquals(800.0, fromAccount.getBalance());
        assertEquals(700.0, toAccount.getBalance());
        verify(accountRepository, times(1)).save(fromAccount);
        verify(accountRepository, times(1)).save(toAccount);
        verify(transactionRepository, times(1)).save(any(BankTransaction.class));
    }

    @Test
    void testTransfer_AccountNotFound() {
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            transactionService.transfer("1234567890", "0987654321", 200.0);
        });

        assertEquals("From account not found", exception.getMessage());
        verify(accountRepository, times(1)).findByAccountNumber("1234567890");
        verify(accountRepository, times(0)).findByAccountNumber("0987654321");
        verify(transactionRepository, times(0)).save(any(BankTransaction.class));
    }

    @Test
    void testTransfer_InsufficientFunds() {
        when(accountRepository.findByAccountNumber("1234567890")).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByAccountNumber("0987654321")).thenReturn(Optional.of(toAccount));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            transactionService.transfer("1234567890", "0987654321", 1500.0);
        });

        assertEquals("Insufficient funds", exception.getMessage());
        verify(accountRepository, times(1)).findByAccountNumber("1234567890");
        verify(accountRepository, times(1)).findByAccountNumber("0987654321");
        verify(transactionRepository, times(0)).save(any(BankTransaction.class));
    }

    @Test
    void testGetTransactionHistory_Success() {
        when(accountRepository.findByAccountNumber("1234567890")).thenReturn(Optional.of(fromAccount));
        when(transactionRepository.findByAccountNumber("1234567890")).thenReturn(List.of(transaction));

        var history = transactionService.getTransactionHistory("1234567890");

        assertNotNull(history);
        assertFalse(history.isEmpty());
        assertEquals(1, history.size());
        assertEquals("1234567890", history.get(0).getFromAccountNumber());
        verify(transactionRepository, times(1)).findByAccountNumber("1234567890");
    }

    @Test
    void testGetTransactionHistory_AccountNotFound() {
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            transactionService.getTransactionHistory("1234567890");
        });

        assertEquals("account not found", exception.getMessage());
        verify(accountRepository, times(1)).findByAccountNumber("1234567890");
        verify(transactionRepository, times(0)).findByAccountNumber(anyString());
    }

    @Test
    void testDeposit_Success() {
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(fromAccount));
        when(accountRepository.save(any(BankAccount.class))).thenReturn(fromAccount);
        transaction.setTransactionType("DEPOSIT");
        when(transactionRepository.save(any(BankTransaction.class))).thenReturn(transaction);

        BankTransaction result = transactionService.deposit("1234567890", 500.0);

        assertEquals(1500.0, fromAccount.getBalance());
        assertEquals("DEPOSIT", result.getTransactionType());
        verify(accountRepository, times(1)).save(any(BankAccount.class));
        verify(transactionRepository, times(1)).save(any(BankTransaction.class));
    }

    @Test
    void testWithdraw_Success() {
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(fromAccount));
        when(accountRepository.save(any(BankAccount.class))).thenReturn(fromAccount);
        transaction.setTransactionType("WITHDRAWAL");
        when(transactionRepository.save(any(BankTransaction.class))).thenReturn(transaction);

        BankTransaction result = transactionService.withdraw("1234567890", 500.0);

        assertEquals(500.0, fromAccount.getBalance());
        assertEquals("WITHDRAWAL", result.getTransactionType());
        verify(accountRepository, times(1)).save(any(BankAccount.class));
        verify(transactionRepository, times(1)).save(any(BankTransaction.class));
    }

    @Test
    void testWithdraw_InsufficientFunds() {
        when(accountRepository.findByAccountNumber("1234567890")).thenReturn(Optional.of(fromAccount));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            transactionService.withdraw("1234567890", 1500.0);
        });

        assertEquals("Insufficient funds", exception.getMessage());
        verify(accountRepository, times(1)).findByAccountNumber("1234567890");
        verify(transactionRepository, times(0)).save(any(BankTransaction.class));
    }
}
