package com.generic.bank.bankingapi.service;

import com.generic.bank.bankingapi.model.ChatGptRequest;
import com.generic.bank.bankingapi.model.ChatGptResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@Slf4j
public class FaqViaChatGptServiceImpl implements FaqViaChatGptService {


    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.model}")
    private String model;

    @Override
    public String askMe(String messageText) {
        ChatGptRequest request = new ChatGptRequest(
                model,
                Collections.singletonList(new com.generic.bank.bankingapi.model.Message("user", messageText)),
                200,
                0.7
        );
        HttpEntity<ChatGptRequest> requestEntity = new HttpEntity<>(request);

        ChatGptResponse response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                ChatGptResponse.class
        ).getBody();

        if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
            log.info("Successfully completed your request via chatgpt api");
            return response.getChoices().get(0).getMessage().getContent();
        } else {
            return "Error: Unable to retrieve response from ChatGPT.";
        }
    }
}



