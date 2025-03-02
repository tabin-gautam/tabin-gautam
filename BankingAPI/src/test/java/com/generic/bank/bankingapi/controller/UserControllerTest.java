/*
package com.generic.bank.bankingapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generic.bank.bankingapi.bankapienum.Role;
import com.generic.bank.bankingapi.dto.CreateUserRequest;
import com.generic.bank.bankingapi.model.BankUser;
import com.generic.bank.bankingapi.service.UserService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private BankUser user;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        user = new BankUser();
        user.setUserId(1L);
        user.setUsername("Santiago@Bernabou");
        user.setRole(Role.USER);
        user.setPassword("password");
        user.setName("Santiago Bernabou");
    }

    @Test
    void testCreateUser_success() throws Exception {
      //  when(userService.createUser(any())).thenReturn(user);
        CreateUserRequest request = new CreateUserRequest("Santiago Bernabou", "Arish@Bernabou", "pswd" );
        mockMvc.perform(post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("Santiago Bernabou"));
    }

    @Test
    void testCreateUser_bad_request_when_name_is_null_or_blank() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        mockMvc.perform(post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Name cannot be null or empty"));
    }


    @Test
    void testGetAllUsers() throws Exception {
        BankUser user1 = new BankUser();
        user1.setUserId(2L);
        user1.setUsername("Branden Gibson");
        List<BankUser> users = Arrays.asList(user, user1);
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users/retrieveUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[1].userName").value("Branden Gibson"));
    }
}
*/
