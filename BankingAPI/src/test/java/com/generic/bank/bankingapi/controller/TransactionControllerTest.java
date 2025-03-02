/*
package com.generic.bank.bankingapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generic.bank.bankingapi.dto.TransferRequest;
import com.generic.bank.bankingapi.model.BankTransaction;
import com.generic.bank.bankingapi.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private BankTransaction transaction;

    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        transaction = new BankTransaction();
        transaction.setTransactionId(1L);
        transaction.setFromAccountId(1L);
        transaction.setToAccountId(2L);
        transaction.setAmount(300.0);
    }

    @Test
    void testTransfer_success() throws Exception {
        TransferRequest request = new TransferRequest(1L, 2L, 300.0);
        doNothing().when(transactionService).transfer(1L, 2L, 300.0);
        mockMvc.perform(post("/transactions/transfer")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Transfer successful"));
    }

    @Test
    void testTransfer_invalidAmount() throws Exception {
        TransferRequest request = new TransferRequest(1L, 2L, -1000.0);

        mockMvc.perform(post("/transactions/transfer")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Transfer Amount must be positive"));
    }

    @Test
    void testTransfer_fromAccountNotFound() throws Exception {
        TransferRequest request = new TransferRequest(20L, 30L, 100.0);

        doThrow(new RuntimeException("From account not found")).when(transactionService).transfer(anyLong(), anyLong(), anyDouble());

        mockMvc.perform(post("/transactions/transfer")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Transfer Failed From account not found"));
    }

    @Test
    void testTransfer_toAccountNotFound() throws Exception {
        TransferRequest request = new TransferRequest(20L, 30L, 100.0);

        doThrow(new RuntimeException("To account not found")).when(transactionService).transfer(anyLong(), anyLong(), anyDouble());

        mockMvc.perform(post("/transactions/transfer")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Transfer Failed To account not found"));
    }

    @Test
    void testTransfer_insufficientFunds() throws Exception {
        TransferRequest request = new TransferRequest(1L, 2L, 5000.0);  // Assuming account 1 has insufficient funds

        doThrow(new RuntimeException("Insufficient funds")).when(transactionService).transfer(anyLong(), anyLong(), anyDouble());

        mockMvc.perform(post("/transactions/transfer")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Transfer Failed Insufficient funds"));
    }

    @Test
    void testGetTransactionHistory_success() throws Exception {
        List<BankTransaction> transactions = Arrays.asList(transaction);
        when(transactionService.getTransactionHistory(1L)).thenReturn(transactions);

        mockMvc.perform(get("/transactions/1/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].transactionId").value(1))
                .andExpect(jsonPath("$[0].fromAccountId").value(1))
                .andExpect(jsonPath("$[0].toAccountId").value(2))
                .andExpect(jsonPath("$[0].amount").value(300.0));
    }

    @Test
    void testGetTransactionHistory_invalidAccountIdNegative() throws Exception {

        mockMvc.perform(get("/transactions/-1/history"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid Account Id"));
    }


    @Test
    void testGetTransactionHistory_NoTransactions() throws Exception {
        when(transactionService.getTransactionHistory(9L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/transactions/9/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }
}

*/
