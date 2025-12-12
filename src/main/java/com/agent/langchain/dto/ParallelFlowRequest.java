package com.agent.langchain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for startup pitch generation using parallel flow pattern.
 */
public class ParallelFlowRequest {

    @NotBlank(message = "Startup name cannot be empty")
    @Size(min = 2, max = 100, message = "Startup name must be between 2 and 100 characters")
    private String startupName;

    @NotBlank(message = "Idea/Product description cannot be empty")
    @Size(min = 10, max = 500, message = "Idea description must be between 10 and 500 characters")
    private String idea;

    @NotBlank(message = "Target market cannot be empty")
    @Size(min = 5, max = 200, message = "Target market must be between 5 and 200 characters")
    private String targetMarket;

    public ParallelFlowRequest() {
    }

    public ParallelFlowRequest(String startupName, String idea, String targetMarket) {
        this.startupName = startupName;
        this.idea = idea;
        this.targetMarket = targetMarket;
    }

    public String getStartupName() {
        return startupName;
    }

    public void setStartupName(String startupName) {
        this.startupName = startupName;
    }

    public String getIdea() {
        return idea;
    }

    public void setIdea(String idea) {
        this.idea = idea;
    }

    public String getTargetMarket() {
        return targetMarket;
    }

    public void setTargetMarket(String targetMarket) {
        this.targetMarket = targetMarket;
    }
}
