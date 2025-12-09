package com.agent.langchain.dto;

/**
 * Response DTO for content refinement.
 * Contains the refined content after iterative quality improvement.
 */
public class ContentRefinementResponse {

    private String content;

    public ContentRefinementResponse() {
    }

    public ContentRefinementResponse(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
