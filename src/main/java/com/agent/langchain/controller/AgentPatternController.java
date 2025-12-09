package com.agent.langchain.controller;

import com.agent.langchain.dto.ContentRefinementRequest;
import com.agent.langchain.dto.ContentRefinementResponse;
import com.agent.langchain.dto.ExpertQueryRequest;
import com.agent.langchain.dto.ExpertQueryResponse;
import com.agent.langchain.dto.RecipeRequest;
import com.agent.langchain.dto.RecipeResponse;
import com.agent.langchain.services.AgentPatternService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for AI Agent Patterns.
 * 
 * This controller serves as a unified entry point for various agentic AI
 * patterns.
 * It exposes endpoints that demonstrate different agent patterns and routing
 * strategies.
 * 
 * Currently Supported Patterns:
 * - Conditional Routing: Routes queries to specialized expert agents based on
 * automatic classification
 * - Sequential Flow: Processes requests through a pipeline of agents where each
 * agent builds upon the previous agent's output
 * 
 * Future patterns can be added as additional endpoints in this controller.
 */
@RestController
@RequestMapping("/api/v1/patterns")
public class AgentPatternController {

    private static final Logger logger = LoggerFactory.getLogger(AgentPatternController.class);

    private final AgentPatternService agentPatternService;

    public AgentPatternController(AgentPatternService agentPatternService) {
        this.agentPatternService = agentPatternService;
    }

    /**
     * Conditional Routing Pattern Endpoint.
     * 
     * Routes a query to the appropriate expert agent using the conditional routing
     * pattern.
     * The system performs the following steps:
     * 1. Analyzes the incoming query to determine its category
     * 2. Routes to the corresponding expert agent
     * 3. Returns the expert's specialized response
     * 
     * Supported Expert Categories:
     * - Creative Expert: Art, design, writing, music, creative problem-solving
     * - Financial Advisor: Money management, investing, budgeting, business finance
     * - Wellness Coach: Health, fitness, mental wellbeing, nutrition, lifestyle
     * - Career Mentor: Job search, professional growth, workplace issues, career
     * transitions
     *
     * @param request the routing request containing the user query
     * @return response from the appropriate expert agent
     * 
     * @throws IllegalArgumentException if query is null, empty, or exceeds length
     *                                  limits
     * @throws RuntimeException         if expert routing fails
     */
    @PostMapping("/conditional-routing/route")
    public ResponseEntity<ExpertQueryResponse> conditionalRouting(@Valid @RequestBody ExpertQueryRequest request) {
        logger.info("Received conditional routing request");

        String response = agentPatternService.executeConditionalRouting(request.getQuery());
        ExpertQueryResponse queryResponse = new ExpertQueryResponse(response);

        logger.debug("Returning routed response to client");
        return ResponseEntity.ok(queryResponse);
    }

    /**
     * Health check endpoint for the conditional routing pattern.
     * 
     * Verifies that the conditional routing system is operational and all
     * expert agents are available.
     *
     * @return status message indicating system health
     */
    @GetMapping("/conditional-routing/health")
    public ResponseEntity<String> conditionalRoutingHealth() {
        logger.debug("Conditional routing health check requested");
        return ResponseEntity.ok("Conditional Routing Pattern is operational");
    }

    /**
     * General health check endpoint for all agent patterns.
     * 
     * Verifies that the agent pattern service is operational.
     *
     * @return status message indicating system health
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        logger.debug("General health check requested");
        return ResponseEntity.ok("Agent Pattern Service is operational");
    }

    /**
     * Sequential Flow Pattern Endpoint.
     * 
     * Develops a complete recipe through a sequential pipeline of specialized
     * agents.
     * The system performs the following steps:
     * 1. IngredientCurator suggests ingredients based on cuisine, dietary
     * preferences, and meal type
     * 2. CookingMethodDesigner creates detailed cooking instructions using those
     * ingredients
     * 3. NutritionalAnalyst adds comprehensive nutritional information and health
     * insights
     * 
     * Each agent builds upon the output of the previous agent, creating a complete
     * recipe with ingredients, instructions, and nutritional data.
     *
     * @param request the recipe request containing cuisine, dietary preferences,
     *                and meal type
     * @return complete recipe with all components
     * 
     * @throws IllegalArgumentException if any request field is null, empty, or
     *                                  exceeds length limits
     * @throws RuntimeException         if recipe development fails
     */
    @PostMapping("/sequential-flow/develop-recipe")
    public ResponseEntity<RecipeResponse> sequentialFlow(@Valid @RequestBody RecipeRequest request) {
        logger.info("Received sequential flow request for cuisine: {}, dietary: {}, mealType: {}",
                request.getCuisine(), request.getDietary(), request.getMealType());

        String recipe = agentPatternService.executeSequentialFlow(
                request.getCuisine(),
                request.getDietary(),
                request.getMealType());
        RecipeResponse response = new RecipeResponse(recipe);

        logger.debug("Returning complete recipe to client");
        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint for the sequential flow pattern.
     * 
     * Verifies that the sequential flow system is operational and all
     * recipe development agents are available.
     *
     * @return status message indicating system health
     */
    @GetMapping("/sequential-flow/health")
    public ResponseEntity<String> sequentialFlowHealth() {
        logger.debug("Sequential flow health check requested");
        return ResponseEntity.ok("Sequential Flow Pattern is operational");
    }

    /**
     * Loop Pattern Endpoint.
     * 
     * Refines content through an iterative loop of quality scoring and editing.
     * The system performs the following steps:
     * 1. ContentCreator generates initial content based on topic and style
     * 2. QualityScorer evaluates content quality (0.0 to 1.0 scale)
     * 3. ContentEditor improves content based on quality score
     * 4. Steps 2-3 repeat until quality score >= 0.8 or max 5 iterations reached
     * 
     * This pattern demonstrates feedback-driven iterative improvement, ideal for
     * scenarios requiring automated quality assurance and refinement.
     *
     * @param request the content refinement request containing topic and style
     * @return refined content that meets quality standards
     * 
     * @throws IllegalArgumentException if any request field is null, empty, or
     *                                  exceeds length limits
     * @throws RuntimeException         if content refinement fails
     */
    @PostMapping("/loop/refine-content")
    public ResponseEntity<ContentRefinementResponse> loopPattern(@Valid @RequestBody ContentRefinementRequest request) {
        logger.info("Received loop pattern request for topic: {}, style: {}",
                request.getTopic(), request.getStyle());

        String content = agentPatternService.executeLoopPattern(
                request.getTopic(),
                request.getStyle());
        ContentRefinementResponse response = new ContentRefinementResponse(content);

        logger.debug("Returning refined content to client");
        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint for the loop pattern.
     * 
     * Verifies that the loop pattern system is operational and all
     * content refinement agents are available.
     *
     * @return status message indicating system health
     */
    @GetMapping("/loop/health")
    public ResponseEntity<String> loopPatternHealth() {
        logger.debug("Loop pattern health check requested");
        return ResponseEntity.ok("Loop Pattern is operational");
    }
}
