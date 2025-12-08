package com.agent.langchain.dto;

import java.time.LocalDateTime;

/**
 * Response DTO for expert agent queries.
 */
public class ExpertQueryResponse {

    private String response;
    private LocalDateTime timestamp;

    public ExpertQueryResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ExpertQueryResponse(String response) {
        this.response = response;
        this.timestamp = LocalDateTime.now();
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
