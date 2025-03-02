package com.generic.bank.bankingapi.repository;

import com.generic.bank.bankingapi.model.BankUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<BankUser,Long> {
    Optional<BankUser> findByUsername(String username);

}
