package com.generic.bank.bankingapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bank_transaction")
public class BankTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;
    @Column(name = "from_account_id")
    private String fromAccountNumber;
    @Column(name = "to_account_id")
    private String toAccountNumber;
    @Column(name = "amount")
    private double amount;
    @Column(name = "transactionType")
    private String transactionType;
    @Column(name = "date")
    private LocalDateTime timestamp;
}
