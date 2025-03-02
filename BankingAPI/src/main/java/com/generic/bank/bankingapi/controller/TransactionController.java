package com.generic.bank.bankingapi.controller;

import com.generic.bank.bankingapi.dto.TransferRequest;
import com.generic.bank.bankingapi.model.BankTransaction;
import com.generic.bank.bankingapi.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing transaction-related API endpoints.
 * This controller handles requests for transferring funds between accounts and retrieving transaction history.
 */
@RestController
@RequestMapping("/transactions")
@Tag(name = "Transfers", description = "APIs for transferring funds between accounts")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;


    /**
     * Creates a new bank account for a given user with an initial balance.
     *
     * @param transferRequest The request body containing fromAccountId and toAccountId and  amount details.
     * @return ResponseEntity 200 ok with message of transfer success .
     * or 400 bad request for failed transfer.
     */
    @PostMapping("/transfer")
    @Operation(summary = "Transfer funds", description = "Transfers an amount from one account to another")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transfer successful"),
            @ApiResponse(responseCode = "400", description = "Invalid transfer request: Insufficient balance or negative amount"),
            @ApiResponse(responseCode = "404", description = "One or both accounts not found")
    })
    public ResponseEntity<String> transfer(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Details of the transfer, including source and destination accounts and amount.",
            required = true,
            content = @Content(schema = @Schema(implementation = TransferRequest.class))
    )
            @RequestBody TransferRequest transferRequest) {
        try {
            if (transferRequest.getAmount() <= 0) {
                return ResponseEntity.badRequest().body("Transfer Amount must be positive");
            }
            transactionService.transfer(transferRequest.getFromAccountNumber(), transferRequest.getToAccountNumber(), transferRequest.getAmount());
        } catch (Exception e) {
            return new ResponseEntity<>("Transfer Failed " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok("Transfer successful");
    }


    @PostMapping("/deposit")
    public ResponseEntity<BankTransaction> deposit(@RequestParam String accountNumber, @RequestParam double amount) {
        BankTransaction transaction = transactionService.deposit(accountNumber, amount);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<BankTransaction> withdraw(@RequestParam String accountNumber, @RequestParam double amount) {
        BankTransaction transaction = transactionService.withdraw(accountNumber, amount);
        return ResponseEntity.ok(transaction);
    }

    /**
     * Retrieves the transaction history for a given account.
     *
     * @param accountNumber The  to retrieve the transaction history for given accountId.
     * @return A list of BankTransaction objects associated with the account.
     */
    @Operation(summary = "Get account transaction history", description = "Retrieves the transaction history for a given account.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transaction history retrieved successfully",
                    content = @Content(schema = @Schema(implementation = BankTransaction.class))),
            @ApiResponse(responseCode = "400", description = "Invalid Account Id"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @GetMapping("/{accountNumber}/history")
    public ResponseEntity<?> getTransactionHistory(
            @Parameter(description = "ID of the account to retrieve transaction history for", example = "1")
            @PathVariable String accountNumber) {
        if (accountNumber.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid Account Number");
        }
        List<BankTransaction> bankTransactions =transactionService.getTransactionHistory(accountNumber);
        if(bankTransactions == null || bankTransactions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bankTransactions);
    }

}



