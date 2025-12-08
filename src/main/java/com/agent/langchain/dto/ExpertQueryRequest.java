package com.agent.langchain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for expert agent queries.
 */
public class ExpertQueryRequest {

    @NotBlank(message = "Query cannot be empty")
    @Size(min = 3, max = 1000, message = "Query must be between 3 and 1000 characters")
    private String query;

    public ExpertQueryRequest() {
    }

    public ExpertQueryRequest(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
