package com.agent.langchain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for content refinement using loop pattern.
 */
public class ContentRefinementRequest {

    @NotBlank(message = "Topic cannot be empty")
    @Size(min = 2, max = 200, message = "Topic must be between 2 and 200 characters")
    private String topic;

    @NotBlank(message = "Style cannot be empty")
    @Size(min = 2, max = 100, message = "Style must be between 2 and 100 characters")
    private String style;

    public ContentRefinementRequest() {
    }

    public ContentRefinementRequest(String topic, String style) {
        this.topic = topic;
        this.style = style;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
