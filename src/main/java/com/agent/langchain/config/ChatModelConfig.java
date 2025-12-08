package com.agent.langchain.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for chat model beans.
 * Configures Google AI Gemini chat model with externalized API key.
 */
@Configuration
public class ChatModelConfig {

    @Value("${google.ai.api-key}")
    private String apiKey;

    @Value("${google.ai.model-name:gemini-2.5-flash-lite}")
    private String modelName;

    @Bean("geminiChatModel")
    public ChatModel geminiChatModel() {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalStateException(
                    "Google AI API key is not configured. Please set 'google.ai.api-key' in application.properties");
        }

        return GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .build();
    }
}
