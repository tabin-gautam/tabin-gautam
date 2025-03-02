package com.generic.bank.bankingapi.service;

import com.generic.bank.bankingapi.bankapienum.AccountType;
import com.generic.bank.bankingapi.model.BankAccount;
import com.generic.bank.bankingapi.model.BankUser;
import com.generic.bank.bankingapi.repository.AccountRepository;
import com.generic.bank.bankingapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * Service implementation for managing bank accounts.
 * This service provides methods for creating new bank accounts, retrieving balances,
 * and get account details for given userId.
 */
@Service
public class AccountServiceImpl implements AccountService {


    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new bank account for a given user with the initial balance.
     *
     * @param bankUserId     The ID of the user to whom the account belongs.
     * @param initialDeposit The initial balance of the account.
     * @return The created BankAccount object.
     * @throws RuntimeException If the user does not exist.
     */
    @Override
    public BankAccount createAccount(Long bankUserId, double initialDeposit, String accountType) {
        BankUser user = userRepository.findById(bankUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        BankAccount bankAccount = new BankAccount();
        bankAccount.setUser(user);
        bankAccount.setBalance(initialDeposit);
        bankAccount.setAccountType(accountType);
        bankAccount.setAccountNumber(generateUniqueAccountNumber());
        return accountRepository.save(bankAccount);
    }


    /**
     * Retrieves the balance of a given account.
     *
     * @param accountNumber The ID of the account to retrieve the balance for.
     * @return The balance of the specified account.
     * @throws RuntimeException If the account does not exist.
     */
    @Override
    public double getBalance(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"))
                .getBalance();
    }

    /**
     * Retrieves the balance of a given userId.
     *
     * @param userId The ID of the account to retrieve the balance for.
     * @return The Bank Account Object for given user.
     * @throws RuntimeException If the account does not exist for given userId.
     */
    @Override
    public List<BankAccount> getUserAccounts(Long userId) {
        return accountRepository.findByBankUserId(userId)
                .orElseThrow(() -> new RuntimeException("Account doesn't exists for given userId"));
    }

    private String generateUniqueAccountNumber() {
        String accountNumber;
        do {
            accountNumber = String.valueOf(1000000000L + new Random().nextLong(9000000000L));
        } while (accountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }
}
