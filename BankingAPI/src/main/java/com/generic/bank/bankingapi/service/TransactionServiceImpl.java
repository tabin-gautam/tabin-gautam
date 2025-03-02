package com.generic.bank.bankingapi.service;

import com.generic.bank.bankingapi.bankapienum.TransactionType;
import com.generic.bank.bankingapi.model.BankAccount;
import com.generic.bank.bankingapi.model.BankTransaction;
import com.generic.bank.bankingapi.repository.AccountRepository;
import com.generic.bank.bankingapi.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for handling transactions between bank accounts.
 * This service supports transferring funds between accounts and retrieving transaction history.
 */
@Service
public class TransactionServiceImpl implements TransactionService {


    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Transfers funds between two bank accounts.
     *
     * @param fromAccountNumber The ID of the account from which funds are withdrawn.
     * @param toAccountNumber   The ID of the account to which funds are transferred.
     * @param amount        The amount to transfer.
     * @throws RuntimeException If either account does not exist, or if there are insufficient funds in the source account.
     */
    @Override
    @Transactional
    public void transfer(String  fromAccountNumber, String toAccountNumber, double amount) {
        BankAccount fromBankAccount = accountRepository.findByAccountNumber(fromAccountNumber)
                .orElseThrow(() -> new RuntimeException("From account not found"));
        BankAccount toBankAccount = accountRepository.findByAccountNumber(toAccountNumber)
                .orElseThrow(() -> new RuntimeException("To account not found"));

        if (fromBankAccount.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds");
        }

        fromBankAccount.setBalance(fromBankAccount.getBalance() - amount);
        toBankAccount.setBalance(toBankAccount.getBalance() + amount);
        accountRepository.save(fromBankAccount);
        accountRepository.save(toBankAccount);

        BankTransaction bankTransaction = new BankTransaction();
        bankTransaction.setFromAccountNumber(fromAccountNumber);
        bankTransaction.setToAccountNumber(toAccountNumber);
        bankTransaction.setAmount(amount);
        bankTransaction.setTransactionType(TransactionType.TRANSFER.toString());
        bankTransaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(bankTransaction);
    }


    /**
     * Retrieves the transaction history for a given account.
     *
     * @param accountNumber The  to retrieve the transaction history for given accountId.
     * @return A list of BankTransaction objects associated with the account.
     */
    @Override
    public List<BankTransaction> getTransactionHistory(String accountNumber) {
        BankAccount fromBankAccount = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("account not found"));
        return transactionRepository.findByAccountNumber(accountNumber);

    }

    @Transactional
    @Override
    public BankTransaction deposit(String accountNumber, double amount) {
        BankAccount account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        BankTransaction transaction = new BankTransaction();
        transaction.setFromAccountNumber(accountNumber);
        transaction.setToAccountNumber(accountNumber);
        transaction.setAmount(amount);
        transaction.setTransactionType("DEPOSIT");
        transaction.setTimestamp(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    @Transactional
    @Override
    public BankTransaction withdraw(String accountNumber, double amount) {
        BankAccount account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds");
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);

        BankTransaction transaction = new BankTransaction();
        transaction.setFromAccountNumber(accountNumber);
        transaction.setToAccountNumber(accountNumber);
        transaction.setAmount(amount);
        transaction.setTransactionType("WITHDRAWAL");
        transaction.setTimestamp(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }
}

