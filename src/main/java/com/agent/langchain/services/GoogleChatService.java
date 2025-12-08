package com.agent.langchain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import dev.langchain4j.model.chat.ChatModel;

@Service
public class GoogleChatService {

    @Autowired
    @Qualifier("geminiChatModel")
    private ChatModel chatModel;

    public String generateResponse(String prompt) {
        return chatModel.chat(prompt);
    }
}
