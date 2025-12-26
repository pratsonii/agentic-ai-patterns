package com.agent.langchain.dto;

/**
 * Response DTO for interview coaching - returns complete assessment in single response.
 */
public class HumanInLoopResponse {

    private String candidateName;
    private String position;
    private String coachingFeedback;
    private String humanFeedback;
    private String finalAssessment;

    public HumanInLoopResponse() {
    }

    public HumanInLoopResponse(String candidateName, String position, String coachingFeedback, 
            String humanFeedback, String finalAssessment) {
        this.candidateName = candidateName;
        this.position = position;
        this.coachingFeedback = coachingFeedback;
        this.humanFeedback = humanFeedback;
        this.finalAssessment = finalAssessment;
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

    public String getCoachingFeedback() {
        return coachingFeedback;
    }

    public void setCoachingFeedback(String coachingFeedback) {
        this.coachingFeedback = coachingFeedback;
    }

    public String getHumanFeedback() {
        return humanFeedback;
    }

    public void setHumanFeedback(String humanFeedback) {
        this.humanFeedback = humanFeedback;
    }

    public String getFinalAssessment() {
        return finalAssessment;
    }

    public void setFinalAssessment(String finalAssessment) {
        this.finalAssessment = finalAssessment;
    }
}
