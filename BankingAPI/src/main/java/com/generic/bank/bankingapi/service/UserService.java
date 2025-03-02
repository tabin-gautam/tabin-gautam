package com.generic.bank.bankingapi.service;

import com.generic.bank.bankingapi.bankapienum.Role;
import com.generic.bank.bankingapi.model.BankUser;

import java.util.List;

public interface UserService {
    public BankUser createUser(String name, String username, String password, Role role);
    public List<BankUser> getAllUsers();

}
