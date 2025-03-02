package com.generic.bank.bankingapi.controller;

import com.generic.bank.bankingapi.dto.CreateAccountRequest;
import com.generic.bank.bankingapi.model.BankAccount;
import com.generic.bank.bankingapi.service.AccountService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Controller for managing account-related API endpoints.
 * This controller handles requests for creating bank accounts, retrieving balances, and  retrieving account
 * for given userId
 */
@RestController
@RequestMapping("/accounts")
@Tag(name = "Accounts", description = "APIs for managing bank accounts")
public class AccountController {


    @Autowired
    private AccountService accountService;


    /**
     * Creates a new bank account for a given user with an initial balance.
     *
     * @param request The request body containing userId and Initial amount details.
     * @return ResponseEntity 200 ok with created BankAccount object.
     * or 400 bad request when the userId is null or deposit is negative.
     * or 404 not found when user doesn't exist
     */
    @PostMapping("/create")
    @Operation(summary = "Create a new bank account", description = "Creates an account for a customer with an initial deposit")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account created successfully",
                    content = @Content(schema = @Schema(implementation = BankAccount.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request: Initial deposit must be positive or User ID cannot be null"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    public ResponseEntity<?> createAccount(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing user ID and initial deposit",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CreateAccountRequest.class))
            )
            @RequestBody CreateAccountRequest request) {
        if (request.getUserId() == null) {
            return ResponseEntity.badRequest().body("User ID cannot be null");
        }
        if (request.getInitialDeposit() < 0) {
            return ResponseEntity.badRequest().body("Initial deposit cannot be negative");
        }
        try {
            BankAccount account = accountService.createAccount(request.getUserId(), request.getInitialDeposit(), request.getAccountType());
            return ResponseEntity.ok(account);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    /**
     * Retrieves the balance of the specified bank account.
     *
     * @param accountNumber The ID of the account whose balance is to be retrieved.
     * @return 200 ok with The balance of the specified account.
     * or 400 if accountId is null or 404 when account doesn't exist.
     */
    @GetMapping("/{accountNumber}/balance")
    @Operation(summary = "Get account balance", description = "Retrieves the balance of a given account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Balance retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    public ResponseEntity<?> getBalance(
            @Parameter(description = "ID of the account to retrieve balance for", example = "1")
            @PathVariable String accountNumber) {
        if (accountNumber.isEmpty()) {
            return ResponseEntity.badRequest().body("Account Number cannot be null or invalid");
        }
        try {
            double balance = accountService.getBalance(accountNumber);
            return ResponseEntity.ok(balance);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Retrieves the balance of a given userId.
     *
     * @param userId The ID of the account to retrieve the balance for.
     * @return The Bank Account Object for given user.
     * or 404 If the account does not exist for given userId.
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "List all accounts for given users", description = "Returns all bank accounts associated to user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Accounts retrieved successfully")
    })
    public ResponseEntity<?> getUserAccounts(
            @Parameter(description = "UserId to retrieve associated account", example = "1")
            @PathVariable Long userId) {
        try {
            List<BankAccount> accounts = accountService.getUserAccounts(userId);
            return ResponseEntity.ok(accounts);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account doesn't exists for given userId");
        }
    }
}
