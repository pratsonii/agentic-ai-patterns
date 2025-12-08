package com.agent.langchain.services;

import com.agent.langchain.patterns.ConditionalRoutingPattern.ExpertRouterAgent;
import com.agent.langchain.patterns.SequentialFlowPattern.RecipeDeveloper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service layer for AI Agent Patterns.
 * 
 * This service acts as a central orchestrator for various agentic AI patterns.
 * It provides methods to execute different agent patterns and route requests
 * to the appropriate pattern implementation.
 * 
 * Currently Supported Patterns:
 * - Conditional Routing: Routes queries to specialized expert agents based on
 * classification
 * 
 * Future patterns can be added as additional methods in this service.
 */
@Service
public class AgentPatternService {

    private static final Logger logger = LoggerFactory.getLogger(AgentPatternService.class);

    private final ExpertRouterAgent expertRouterAgent;
    private final RecipeDeveloper recipeDeveloper;

    public AgentPatternService(ExpertRouterAgent expertRouterAgent, RecipeDeveloper recipeDeveloper) {
        this.expertRouterAgent = expertRouterAgent;
        this.recipeDeveloper = recipeDeveloper;
    }

    /**
     * Executes the Conditional Routing pattern.
     * 
     * Routes a user query to the appropriate expert based on its category.
     * 
     * The routing process:
     * 1. Classifies the query into: CREATIVE, FINANCIAL, WELLNESS, CAREER, or
     * UNKNOWN
     * 2. Routes to the corresponding expert agent
     * 3. Returns the expert's response
     *
     * @param query the user's question or request
     * @return the expert's response based on the query category
     * @throws IllegalArgumentException if query is null or empty
     * @throws RuntimeException         if routing or response generation fails
     */
    public String executeConditionalRouting(String query) {
        if (query == null || query.trim().isEmpty()) {
            logger.warn("Received null or empty query for conditional routing");
            throw new IllegalArgumentException("Query cannot be null or empty");
        }

        logger.info("Executing conditional routing pattern for query: {}",
                query.substring(0, Math.min(query.length(), 50)) + "...");

        try {
            String response = expertRouterAgent.ask(query);
            logger.info("Successfully executed conditional routing and received expert response");
            return response;
        } catch (Exception e) {
            logger.error("Error executing conditional routing: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to execute conditional routing: " + e.getMessage(), e);
        }
    }

    /**
     * Executes the Sequential Flow pattern.
     * 
     * Processes a recipe development request through a sequential pipeline of
     * agents.
     * 
     * The sequential flow:
     * 1. IngredientCurator suggests ingredients based on cuisine, dietary
     * preferences, and meal type
     * 2. CookingMethodDesigner creates detailed cooking instructions using those
     * ingredients
     * 3. NutritionalAnalyst adds comprehensive nutritional information
     * 
     * Each agent builds upon the output of the previous agent.
     *
     * @param cuisine  the type of cuisine (e.g., "Italian", "Thai", "Mexican")
     * @param dietary  dietary preferences or restrictions (e.g., "vegetarian",
     *                 "gluten-free", "keto")
     * @param mealType the type of meal (e.g., "breakfast", "lunch", "dinner",
     *                 "dessert")
     * @return complete recipe with ingredients, cooking instructions, and
     *         nutritional information
     * @throws IllegalArgumentException if any parameter is null or empty
     * @throws RuntimeException         if recipe development fails
     */
    public String executeSequentialFlow(String cuisine, String dietary, String mealType) {
        if (cuisine == null || cuisine.trim().isEmpty()) {
            logger.warn("Received null or empty cuisine for sequential flow");
            throw new IllegalArgumentException("Cuisine cannot be null or empty");
        }
        if (dietary == null || dietary.trim().isEmpty()) {
            logger.warn("Received null or empty dietary preferences for sequential flow");
            throw new IllegalArgumentException("Dietary preferences cannot be null or empty");
        }
        if (mealType == null || mealType.trim().isEmpty()) {
            logger.warn("Received null or empty meal type for sequential flow");
            throw new IllegalArgumentException("Meal type cannot be null or empty");
        }

        logger.info("Executing sequential flow pattern for cuisine: {}, dietary: {}, mealType: {}",
                cuisine, dietary, mealType);

        try {
            String result = recipeDeveloper.developRecipe(cuisine, dietary, mealType);
            logger.info("Successfully executed sequential flow and generated complete recipe");
            return result;
        } catch (Exception e) {
            logger.error("Error executing sequential flow: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to execute sequential flow: " + e.getMessage(), e);
        }
    }

    /**
     * Placeholder for future pattern implementations.
     * 
     * Example patterns that could be added:
     * - Parallel Execution: Run multiple agents concurrently
     * - Hierarchical Routing: Multi-level routing with sub-experts
     * - Tool-using Agents: Agents that can use external tools
     * - Memory-enhanced Agents: Agents with conversation history
     */
    // public String executeParallelPattern(String query) { ... }
    // public String executeHierarchicalRouting(String query) { ... }
}
