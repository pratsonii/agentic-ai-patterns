package com.agent.langchain.controller;

import com.agent.langchain.dto.ContentRefinementRequest;
import com.agent.langchain.dto.ContentRefinementResponse;
import com.agent.langchain.dto.ExpertQueryRequest;
import com.agent.langchain.dto.ExpertQueryResponse;
import com.agent.langchain.dto.HumanInLoopRequest;
import com.agent.langchain.dto.HumanInLoopResponse;
import com.agent.langchain.dto.ParallelFlowRequest;
import com.agent.langchain.dto.ParallelFlowResponse;
import com.agent.langchain.dto.RecipeRequest;
import com.agent.langchain.dto.RecipeResponse;
import com.agent.langchain.patterns.HumanInLoopPattern;
import com.agent.langchain.services.AgentPatternService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for AI Agent Patterns.
 * 
 * This controller serves as a unified entry point for various agentic AI patterns.
 * It exposes endpoints that demonstrate different agent patterns and routing strategies.
 * 
 * Currently Supported Patterns:
 * - Conditional Routing: Routes queries to specialized expert agents
 * - Sequential Flow: Processes requests through a pipeline of agents
 * - Loop Pattern: Iteratively refines content through quality scoring and editing
 * - Parallel Flow: Executes multiple independent agents concurrently
 * - Human in Loop: Simple one-step interview coaching with human feedback
 */
@RestController
@RequestMapping("/api/v1/patterns")
public class AgentPatternController {

    private static final Logger logger = LoggerFactory.getLogger(AgentPatternController.class);

    private final AgentPatternService agentPatternService;
    private final HumanInLoopPattern.InterviewSupervisor interviewSupervisor;

    public AgentPatternController(AgentPatternService agentPatternService, 
            HumanInLoopPattern.InterviewSupervisor interviewSupervisor) {
        this.agentPatternService = agentPatternService;
        this.interviewSupervisor = interviewSupervisor;
    }

    /**
     * Conditional Routing Pattern Endpoint.
     * Routes a query to the appropriate expert agent using the conditional routing pattern.
     */
    @PostMapping("/conditional-routing/route")
    public ResponseEntity<ExpertQueryResponse> conditionalRouting(@Valid @RequestBody ExpertQueryRequest request) {
        logger.info("Received conditional routing request");
        String response = agentPatternService.executeConditionalRouting(request.getQuery());
        return ResponseEntity.ok(new ExpertQueryResponse(response));
    }

    /**
     * Sequential Flow Pattern Endpoint.
     * Develops a complete recipe through a sequential pipeline of specialized agents.
     */
    @PostMapping("/sequential-flow/develop-recipe")
    public ResponseEntity<RecipeResponse> sequentialFlow(@Valid @RequestBody RecipeRequest request) {
        logger.info("Received sequential flow request for cuisine: {}, dietary: {}, mealType: {}",
                request.getCuisine(), request.getDietary(), request.getMealType());
        String recipe = agentPatternService.executeSequentialFlow(
                request.getCuisine(), request.getDietary(), request.getMealType());
        return ResponseEntity.ok(new RecipeResponse(recipe));
    }

    /**
     * Loop Pattern Endpoint.
     * Refines content through an iterative loop of quality scoring and editing.
     */
    @PostMapping("/loop/refine-content")
    public ResponseEntity<ContentRefinementResponse> loopPattern(@Valid @RequestBody ContentRefinementRequest request) {
        logger.info("Received loop pattern request for topic: {}, style: {}", 
                request.getTopic(), request.getStyle());
        String content = agentPatternService.executeLoopPattern(request.getTopic(), request.getStyle());
        return ResponseEntity.ok(new ContentRefinementResponse(content));
    }

    /**
     * Parallel Flow Pattern Endpoint.
     * Builds a comprehensive startup pitch by executing multiple agents in parallel.
     */
    @PostMapping("/parallel-flow/build-pitch")
    public ResponseEntity<ParallelFlowResponse> parallelFlow(@Valid @RequestBody ParallelFlowRequest request) {
        logger.info("Received parallel flow request for startup: {}", request.getStartupName());
        String pitch = agentPatternService.executeParallelFlow(
                request.getStartupName(), request.getIdea(), request.getTargetMarket());
        return ResponseEntity.ok(new ParallelFlowResponse(pitch));
    }

    /**
     * Human in Loop Pattern - Single Step Endpoint.
     * 
     * Submission → AI Coaching Feedback → Human Feedback → Final Assessment
     * All in one API call.
     * 
     * @param request the interview coaching request with candidate, position, question, response
     * @return complete assessment with coaching feedback, human feedback, and final recommendation
     */
    @PostMapping("/human-in-loop/submit-interview")
    public ResponseEntity<HumanInLoopResponse> submitInterviewResponse(
            @Valid @RequestBody HumanInLoopRequest request) {
        logger.info("Received interview response from candidate: {}, position: {}",
                request.getCandidateName(), request.getPosition());

        try {
            // Build the complete interview request
            String interviewRequest = String.format(
                    "Analyze this interview response:\n\n" +
                    "Candidate: %s\n" +
                    "Position: %s\n" +
                    "Question: %s\n" +
                    "Response: %s\n\n" +
                    "Provide AI coaching feedback, collect human interviewer feedback, " +
                    "and synthesize both into a final hiring assessment.",
                    request.getCandidateName(), request.getPosition(),
                    request.getQuestion(), request.getResponse());

            // Execute the supervisor - returns complete assessment including human feedback
            String completeAssessment = interviewSupervisor.conductInterview(interviewRequest);

            // For this simplified version, we parse the assessment to extract components
            // In real scenario, supervisor returns structured data
            HumanInLoopResponse response = new HumanInLoopResponse(
                    request.getCandidateName(),
                    request.getPosition(),
                    "AI Coaching Feedback - [See finalAssessment for complete feedback]",
                    "Human Feedback - [Collected during assessment]",
                    completeAssessment);

            logger.info("Successfully completed interview assessment for candidate: {}", 
                    request.getCandidateName());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error processing interview: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to process interview: " + e.getMessage(), e);
        }
    }
}
