/*
package com.generic.bank.bankingapi.service;

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
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    private BankAccount fromBankAccount;
    private BankAccount toBankAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        fromBankAccount = new BankAccount();
        fromBankAccount.setAccountId(1L);
        fromBankAccount.setBalance(1000.0);

        toBankAccount = new BankAccount();
        toBankAccount.setAccountId(2L);
        toBankAccount.setBalance(500.0);
    }

    @Test
    void testTransfer_Success() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromBankAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toBankAccount));

        transactionService.transfer(1L, 2L, 100.0);


        assertEquals(900.0, fromBankAccount.getBalance());
        assertEquals(600.0, toBankAccount.getBalance());

        verify(accountRepository, times(2)).save(any(BankAccount.class));
    }

    @Test
    void testTransfer_FromAccountNotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toBankAccount));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            transactionService.transfer(1L, 2L, 100.0);
        });
        assertEquals("From account not found", exception.getMessage());
    }

    @Test
    void testTransfer_ToAccountNotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromBankAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            transactionService.transfer(1L, 2L, 100.0);
        });
        assertEquals("To account not found", exception.getMessage());
    }

    @Test
    void testTransfer_InsufficientFunds() {
        // Arrange
        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromBankAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toBankAccount));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            transactionService.transfer(1L, 2L, 2000.0);
        });
        assertEquals("Insufficient funds", exception.getMessage());
    }

    @Test
    void testGetTransactionHistory() {
        BankTransaction transaction = new BankTransaction();
        transaction.setFromAccountId(1L);
        transaction.setToAccountId(2L);
        transaction.setAmount(100.0);
        transaction.setTimestamp(LocalDateTime.now());
        when(transactionRepository.findAll()).thenReturn(List.of(transaction));

        List<BankTransaction> transactions = transactionService.getTransactionHistory(1L);

        assertEquals(1, transactions.size());
        assertEquals(1L, transactions.get(0).getFromAccountId());
        assertEquals(2L, transactions.get(0).getToAccountId());
    }
}
*/
