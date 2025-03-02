package com.generic.bank.bankingapi.service;

import com.generic.bank.bankingapi.model.ChatGptChoice;
import com.generic.bank.bankingapi.model.ChatGptRequest;
import com.generic.bank.bankingapi.model.ChatGptResponse;
import com.generic.bank.bankingapi.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FaqViaChatGptServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private FaqViaChatGptServiceImpl faqViaChatGptService;




    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(faqViaChatGptService, "apiUrl",
                "http://api.openai.com/v1/engines/gpt-3.5-turbo/completions");
        ReflectionTestUtils.setField(faqViaChatGptService, "model",
                "turbo 3.5");
    }

    @Test
    void testAskMe_Success() {
        String question = "What is the capital of France?";
        String expectedResponse = "Paris";
        ChatGptResponse chatGptResponse = new ChatGptResponse();
        ChatGptChoice chatGptChoice = new ChatGptChoice();
        chatGptChoice.setMessage(new Message("assistant", expectedResponse));
        chatGptResponse.setChoices(Collections.singletonList(chatGptChoice));

        HttpEntity<ChatGptRequest> requestEntity = new HttpEntity<>(new ChatGptRequest(
                "turbo 3.5",
                Collections.singletonList(new Message("user", question)),
                200,
                0.7
        ));

        lenient().when(restTemplate.exchange(
                eq("http://api.openai.com/v1/engines/gpt-3.5-turbo/completions"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(ChatGptResponse.class)
        )).thenReturn(new ResponseEntity<>(chatGptResponse, HttpStatus.OK));


        String result = faqViaChatGptService.askMe(question);


        assertEquals(expectedResponse, result);
        verify(restTemplate, times(1)).exchange(eq("http://api.openai.com/v1/engines/gpt-3.5-turbo/completions"), eq(HttpMethod.POST), eq(requestEntity), eq(ChatGptResponse.class));
    }

    @Test
    void testAskMe_ErrorResponse() {
        String question = "What is the capital of France?";

        ChatGptResponse chatGptResponse = new ChatGptResponse();
        HttpEntity<ChatGptRequest> requestEntity = new HttpEntity<>(new ChatGptRequest(
                "turbo 3.5",
                Collections.singletonList(new Message("user", question)),
                200,
                0.7
        ));

        lenient().when(restTemplate.exchange(
                eq("http://api.openai.com/v1/engines/gpt-3.5-turbo/completions"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(ChatGptResponse.class)
        )).thenReturn(new ResponseEntity<>(chatGptResponse, HttpStatus.OK));


        String result = faqViaChatGptService.askMe(question);

        assertEquals("Error: Unable to retrieve response from ChatGPT.", result);
        verify(restTemplate, times(1)).exchange(eq("http://api.openai.com/v1/engines/gpt-3.5-turbo/completions"), eq(HttpMethod.POST), any(HttpEntity.class), eq(ChatGptResponse.class));
    }

    @Test
    void testAskMe_ExceptionHandling() {
        String question = "What is the capital of France?";

        when(restTemplate.exchange(eq("http://api.openai.com/v1/engines/gpt-3.5-turbo/completions"), eq(HttpMethod.POST), any(HttpEntity.class), eq(ChatGptResponse.class)))
                .thenThrow(new RuntimeException("ChatGPT Api Call Failed"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            faqViaChatGptService.askMe(question);
        });

        assertEquals("ChatGPT Api Call Failed", exception.getMessage());
    }



}
