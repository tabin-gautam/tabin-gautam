package com.generic.bank.bankingapi.service;

import com.generic.bank.bankingapi.model.BankTransaction;

import java.util.List;

public interface TransactionService {
    public void transfer(String  fromAccountNumber, String toAccountNumber, double amount);
    public BankTransaction deposit(String accountNumber, double amount);
    public BankTransaction withdraw(String accountNumber, double amount);
    public List<BankTransaction> getTransactionHistory(String accountNumber);
}
