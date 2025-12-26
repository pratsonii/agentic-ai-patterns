package com.agent.langchain.patterns;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * Configuration for the Human in Loop pattern.
 * 
 * Simplified implementation demonstrating human-in-the-loop feedback integration.
 * 
 * Use Case: AI Interview Coach with Real-Time Human Feedback
 * Single-step workflow:
 * - Candidate submits interview response
 * - AI provides coaching feedback
 * - Human interviewer provides feedback via console
 * - Final assessment synthesizes both perspectives
 * 
 * This pattern demonstrates:
 * - Minimal service layer - direct agent invocation
 * - Consumer/Supplier for human feedback collection
 * - Direct supervisor orchestration without intermediate storage
 */
@Configuration
public class HumanInLoopPattern {

        private static final Logger logger = LoggerFactory.getLogger(HumanInLoopPattern.class);

        @Autowired
        @Qualifier("geminiChatModel")
        private ChatModel chatModel;

        /**
         * Record representing the Human-in-the-Loop feedback mechanism.
         * Encapsulates the request writer (prompt to human) and response reader
         * (receive human feedback).
         */
        public record HumanFeedbackLoop(Consumer<String> requestWriter, Supplier<String> responseReader) {

                /**
                 * Requests feedback from the human interviewer.
                 * 
                 * @param feedbackRequest the question/request for human feedback
                 * @return the human's feedback response
                 */
                @Agent("An agent that collects real-time feedback from human interviewers")
                public String collectFeedback(String feedbackRequest) {
                        requestWriter.accept(feedbackRequest);
                        return responseReader.get();
                }
        }

        /**
         * Interview Coach Agent Interface.
         * Analyzes candidate responses and provides constructive feedback.
         */
        public interface InterviewCoach {
                @SystemMessage("""
                                You are an expert interview coach. Provide constructive, actionable feedback.
                                """)
                @UserMessage("""
                                Position: {{position}}
                                Question: {{question}}
                                Response: {{response}}
                                
                                Provide concise feedback on:
                                1. Communication effectiveness
                                2. Technical depth (if applicable)
                                3. Soft skills demonstrated
                                4. Key strengths
                                5. Areas for improvement
                                """)
                @Agent("Interview coach providing constructive feedback")
                String provideFeedback(@V("position") String position,
                                @V("question") String question,
                                @V("response") String response);
        }

        /**
         * Interview Assessor Agent Interface.
         * Synthesizes AI coaching and human feedback into final assessment.
         */
        public interface InterviewAssessor {
                @SystemMessage("""
                                You are a senior hiring manager synthesizing feedback into a hiring recommendation.
                                """)
                @UserMessage("""
                                Candidate: {{candidateName}}
                                Position: {{position}}
                                
                                AI Coaching Feedback:
                                {{coachFeedback}}
                                
                                Human Interviewer Feedback:
                                {{humanFeedback}}
                                
                                Provide a final assessment with:
                                1. Performance Rating (1-10)
                                2. Key Strengths
                                3. Areas for Development
                                4. Hiring Recommendation (Strong Hire / Hire / Consider / No Hire)
                                """)
                @Agent("Assessment agent synthesizing feedback")
                String assessInterview(@V("candidateName") String candidateName,
                                @V("position") String position,
                                @V("coachFeedback") String coachFeedback,
                                @V("humanFeedback") String humanFeedback);
        }

        /**
         * Interview Supervisor - orchestrates the complete workflow.
         */
        public interface InterviewSupervisor {
                @Agent("Supervisor orchestrating interview workflow")
                String conductInterview(@V("request") String request);
        }

        /**
         * Interview Coach Agent Bean.
         */
        @Bean
        public InterviewCoach interviewCoach() {
                logger.info("Creating InterviewCoach agent");
                return AgenticServices
                                .agentBuilder(InterviewCoach.class)
                                .chatModel(chatModel)
                                .build();
        }

        /**
         * Interview Assessor Agent Bean.
         */
        @Bean
        public InterviewAssessor interviewAssessor() {
                logger.info("Creating InterviewAssessor agent");
                return AgenticServices
                                .agentBuilder(InterviewAssessor.class)
                                .chatModel(chatModel)
                                .build();
        }

        /**
         * Human Feedback Loop Bean - provides console I/O for human feedback.
         */
        @Bean
        public HumanFeedbackLoop humanFeedbackLoop() {
                logger.info("Creating HumanFeedbackLoop");
                return new HumanFeedbackLoop(
                                request -> {
                                        System.out.println("\n🔹 REQUEST FOR HUMAN INTERVIEWER:");
                                        System.out.println(request);
                                        System.out.print("\n> Your feedback: ");
                                },
                                () -> {
                                        try {
                                                return System.console() != null 
                                                        ? System.console().readLine() 
                                                        : "Good response with clear communication";
                                        } catch (Exception e) {
                                                logger.error("Error reading human feedback", e);
                                                return "Unable to collect feedback";
                                        }
                                }
                );
        }

        /**
         * Interview Supervisor Bean - orchestrates the complete interview workflow.
         * Coordinates InterviewCoach, HumanFeedbackLoop, and InterviewAssessor.
         */
        @Bean
        public InterviewSupervisor interviewSupervisor() {
                logger.info("Creating InterviewSupervisor");
                return AgenticServices
                                .supervisorBuilder(InterviewSupervisor.class)
                                .chatModel(chatModel)
                                .subAgents(interviewCoach(), humanFeedbackLoop(), interviewAssessor())
                                .build();
        }
}
