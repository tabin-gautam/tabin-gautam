package com.generic.bank.bankingapi.controller;

import com.generic.bank.bankingapi.service.FaqViaChatGptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/askme")
@Tag(name = "AskMe", description = "Response from ChatGpt Open AI via  integration with the BankingApi application")
public class AskMeController {
    @Autowired
    FaqViaChatGptService faqViaChatGptService;

    @PostMapping("/faq")
    @Operation(summary = "Faq regarding banking api", description = "Returns response from chatgpt")
    public String askMe(@RequestBody String input) {
        log.info("Your question : " + input + " will be answered via chat gpt");
        return faqViaChatGptService.askMe(input);
    }

}
