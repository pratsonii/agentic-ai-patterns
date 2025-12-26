package com.agent.langchain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for interview coaching - single step submission.
 */
public class HumanInLoopRequest {

    @NotBlank(message = "Candidate name cannot be empty")
    @Size(min = 2, max = 100, message = "Candidate name must be between 2 and 100 characters")
    private String candidateName;

    @NotBlank(message = "Position cannot be empty")
    @Size(min = 2, max = 100, message = "Position must be between 2 and 100 characters")
    private String position;

    @NotBlank(message = "Interview question cannot be empty")
    @Size(min = 10, max = 500, message = "Interview question must be between 10 and 500 characters")
    private String question;

    @NotBlank(message = "Candidate response cannot be empty")
    @Size(min = 10, max = 2000, message = "Candidate response must be between 10 and 2000 characters")
    private String response;

    public HumanInLoopRequest() {
    }

    public HumanInLoopRequest(String candidateName, String position, String question, String response) {
        this.candidateName = candidateName;
        this.position = position;
        this.question = question;
        this.response = response;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
