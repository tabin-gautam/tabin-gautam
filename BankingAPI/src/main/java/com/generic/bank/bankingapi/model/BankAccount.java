package com.generic.bank.bankingapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bank_account")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @ManyToOne
    @JoinColumn(name = "bank_user_id", nullable = false)
    private BankUser user;

    @Column(name = "account_number",unique = true, nullable = false)
    private String accountNumber;

    @Column(name = "account_type")
    private String accountType;


    @Column(name = "balance")
    private double balance;


}
