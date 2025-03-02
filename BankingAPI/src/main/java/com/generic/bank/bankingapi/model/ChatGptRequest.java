package com.generic.bank.bankingapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGptRequest {
    private String model;
    private List<Message> messages;
    private int max_tokens;
    private double temperature;
}




