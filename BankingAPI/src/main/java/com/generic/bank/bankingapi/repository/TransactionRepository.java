package com.generic.bank.bankingapi.repository;

import com.generic.bank.bankingapi.model.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<BankTransaction, Long> {
    @Query("SELECT t FROM BankTransaction  t WHERE t.fromAccountNumber = :accountNumber OR t.toAccountNumber = :accountNumber ORDER BY t.timestamp DESC")
    List<BankTransaction> findByAccountNumber(@Param("accountNumber") String accountNumber);
}
