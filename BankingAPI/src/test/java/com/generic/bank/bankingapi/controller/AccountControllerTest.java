/*
package com.generic.bank.bankingapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generic.bank.bankingapi.dto.CreateAccountRequest;
import com.generic.bank.bankingapi.model.BankAccount;
import com.generic.bank.bankingapi.model.BankUser;
import com.generic.bank.bankingapi.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    private ObjectMapper objectMapper;


    private BankAccount account;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        account = new BankAccount();
        account.setAccountId(1L);
        BankUser user = new BankUser();
        user.setUserId(1L);
        user.setUsername("Santiago Bernabou");
        account.setUser(user);
        account.setBalance(1000.0);
    }

    @Test
    void testCreateAccount() throws Exception {
        CreateAccountRequest request = new CreateAccountRequest(1L, 1000.0);
        when(accountService.createAccount(1L, 1000.0)).thenReturn(account);

        mockMvc.perform(post("/accounts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value(1))
                .andExpect(jsonPath("$.user.userId").value(1))
                .andExpect(jsonPath("$.balance").value(1000.0));
    }


    @Test
    void testCreateAccount_NullUserId_ShouldReturnBadRequest() throws Exception {
        CreateAccountRequest request = new CreateAccountRequest(null, 500.0);

        mockMvc.perform(post("/accounts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User ID cannot be null"));
    }

    @Test
    void testCreateAccount_NegativeDeposit_ShouldReturnBadRequest() throws Exception {
        CreateAccountRequest request = new CreateAccountRequest(1L, -100.0);

        mockMvc.perform(post("/accounts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Initial deposit cannot be negative"));
    }

    @Test
    void testCreateAccount_UserNotFound_ShouldReturnNotFound() throws Exception {
        CreateAccountRequest request = new CreateAccountRequest(20L, 1000.0);

        when(accountService.createAccount(20L, 1000.0))
                .thenThrow(new RuntimeException("User not found"));

        mockMvc.perform(post("/accounts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));
    }


    @Test
    void testGetBalance() throws Exception {
        when(accountService.getBalance(1L)).thenReturn(1000.0);

        mockMvc.perform(get("/accounts/1/balance"))
                .andExpect(status().isOk())
                .andExpect(content().string("1000.0"));
    }

    @Test
    void testGetBalance_Invalid_AccountId_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/accounts/-1/balance"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Account ID cannot be null or invalid"));
    }

    @Test
    void testGetBalance_AccountNotFound_ShouldReturnNotFound() throws Exception {

        when(accountService.getBalance(20L))
                .thenThrow(new RuntimeException("Account not found"));

        mockMvc.perform(get("/accounts/20/balance"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Account not found"));
    }

    @Test
    void testGetUserAccounts_Success() throws Exception {
        List<BankAccount> accounts = Arrays.asList(account);
        when(accountService.getUserAccounts(1L)).thenReturn(accounts);

        mockMvc.perform(get("/accounts/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].accountId").value(1))
                .andExpect(jsonPath("$[0].user.userId").value(1))
                .andExpect(jsonPath("$[0].balance").value(1000.0));
    }

    @Test
    void testGetUserAccounts_UserNotFound_ShouldReturnNotFound() throws Exception {

        when(accountService.getUserAccounts(10L)).thenThrow(new RuntimeException("Account doesn't exists for given userId"));

        mockMvc.perform(get("/accounts/user/10"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Account doesn't exists for given userId"));
    }
}
*/
