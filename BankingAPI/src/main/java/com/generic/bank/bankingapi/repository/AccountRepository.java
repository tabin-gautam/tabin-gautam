package com.generic.bank.bankingapi.repository;

import com.generic.bank.bankingapi.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<BankAccount, Long> {
    @Query("SELECT a FROM BankAccount a WHERE a.user.userId = :bankUserId")
    Optional<List<BankAccount>> findByBankUserId(@Param("bankUserId") Long bankUserId);
    Optional<BankAccount> findByAccountNumber(String accountNumber);
    boolean existsByAccountNumber(String accountNumber);


}
