package com.generic.bank.bankingapi.dto;


import com.generic.bank.bankingapi.bankapienum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    private String name;
    private String username;
    private String password;
    private Role role;
}