package com.agent.langchain.dto;

/**
 * Response DTO for startup pitch generation.
 * Contains the complete pitch document with executive summary, market analysis,
 * and risk assessment.
 */
public class ParallelFlowResponse {

    private String pitch;

    public ParallelFlowResponse() {
    }

    public ParallelFlowResponse(String pitch) {
        this.pitch = pitch;
    }

    public String getPitch() {
        return pitch;
    }

    public void setPitch(String pitch) {
        this.pitch = pitch;
    }
}
