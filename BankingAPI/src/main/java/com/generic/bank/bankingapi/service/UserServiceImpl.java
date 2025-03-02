package com.generic.bank.bankingapi.service;

import com.generic.bank.bankingapi.bankapienum.Role;
import com.generic.bank.bankingapi.model.BankUser;
import com.generic.bank.bankingapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for Users
 * This service provides methods to create  and save new users and retrieve all users from the database.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * Creates a new user with the provided name.
     *
     * @param name The name of the user to create.
     * @return The created User object with a unique ID.
     */
    @Override
    public BankUser createUser(String name, String username, String password, Role role) {
        BankUser user = new BankUser();
        user.setUsername(name);
        user.setName(name);
        user.setUsername(username);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }


    /**
     * Retrieves all users from the database.
     *
     * @return A list of all users.
     */
    @Override
    public List<BankUser> getAllUsers() {
        return userRepository.findAll();
    }
}
