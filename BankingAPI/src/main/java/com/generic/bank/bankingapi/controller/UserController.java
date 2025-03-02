/*
package com.generic.bank.bankingapi.controller;

import com.generic.bank.bankingapi.bankapienum.Role;
import com.generic.bank.bankingapi.dto.CreateUserRequest;
import com.generic.bank.bankingapi.model.BankUser;
import com.generic.bank.bankingapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

*/
/**
 * Controller for managing user-related API endpoints.
 * This controller handles requests for creating new users and retrieving all users.
 *//*

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "APIs for managing banking users")
public class UserController {


    @Autowired
    private UserService userService;

    */
/**
     * Creates a new user in the banking system.
     *
     * @param request The request body containing user details.
     * @return A ResponseEntity 200 ok containing the created BankUser object.
     * or 400 bad request If the provided name is null or empty.
     *//*

    @PostMapping("/create")
    @Operation(
            summary = "Create a new user",
            description = "Creates a new user in the banking system with a given name."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request: Name cannot be null or empty")
    })
  //  @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createUser(
            @Parameter(description = "Request body containing the user's name", required = true)
            @RequestBody CreateUserRequest request) {
        //validation of incoming request
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Name cannot be null or empty");
        }
        //create user
        return ResponseEntity.ok(userService.createUser(request.getName(), request.getUsername(), request.getPassword(), Role.USER));
    }

    */
/**
     * Retrieves all users from the database.
     *
     * @return A list of all users.
     *//*

    @Operation(
            summary = "Retrieve all users",
            description = "Fetches a list of all registered users in the system."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully")
    })
    @GetMapping("/retrieveUser")
    public ResponseEntity<List<BankUser>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


}
*/
