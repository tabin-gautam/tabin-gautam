package com.generic.bank.bankingapi.service;

import com.generic.bank.bankingapi.model.BankAccount;

import java.util.List;

public interface AccountService {

    public BankAccount createAccount(Long bankUserId, double initialDeposit, String accountType);

    public double getBalance(String accountNumber);

    public List<BankAccount> getUserAccounts(Long userId);

}
