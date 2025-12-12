package com.agent.langchain.services;

import com.agent.langchain.patterns.ConditionalRoutingPattern.ExpertRouterAgent;
import com.agent.langchain.patterns.LoopPattern.ContentRefiner;
import com.agent.langchain.patterns.ParallelFlowPattern.StartupPitcher;
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
 * - Sequential Flow: Processes requests through a pipeline of agents
 * - Loop Pattern: Iteratively refines content through quality scoring and
 * editing
 * - Parallel Flow: Executes multiple independent agents concurrently
 * 
 * Future patterns can be added as additional methods in this service.
 */
@Service
public class AgentPatternService {

    private static final Logger logger = LoggerFactory.getLogger(AgentPatternService.class);

    private final ExpertRouterAgent expertRouterAgent;
    private final RecipeDeveloper recipeDeveloper;
    private final ContentRefiner contentRefiner;
    private final StartupPitcher startupPitcher;

    public AgentPatternService(ExpertRouterAgent expertRouterAgent, RecipeDeveloper recipeDeveloper,
            ContentRefiner contentRefiner, StartupPitcher startupPitcher) {
        this.expertRouterAgent = expertRouterAgent;
        this.recipeDeveloper = recipeDeveloper;
        this.contentRefiner = contentRefiner;
        this.startupPitcher = startupPitcher;
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
     * Executes the Loop Pattern.
     * 
     * Iteratively refines content through a quality scoring and editing loop.
     * 
     * The loop flow:
     * 1. ContentCreator generates initial content based on topic and style
     * 2. QualityScorer evaluates content quality (0.0 to 1.0 scale)
     * 3. ContentEditor improves content based on score
     * 4. Steps 2-3 repeat until score >= 0.8 or max 5 iterations reached
     * 
     * This pattern demonstrates feedback-driven iterative improvement.
     *
     * @param topic the topic to write about
     * @param style the writing style (e.g., "professional", "casual", "technical",
     *              "creative")
     * @return refined content that meets quality standards
     * @throws IllegalArgumentException if any parameter is null or empty
     * @throws RuntimeException         if content refinement fails
     */
    public String executeLoopPattern(String topic, String style) {
        if (topic == null || topic.trim().isEmpty()) {
            logger.warn("Received null or empty topic for loop pattern");
            throw new IllegalArgumentException("Topic cannot be null or empty");
        }
        if (style == null || style.trim().isEmpty()) {
            logger.warn("Received null or empty style for loop pattern");
            throw new IllegalArgumentException("Style cannot be null or empty");
        }

        logger.info("Executing loop pattern for topic: {}, style: {}", topic, style);

        try {
            String result = contentRefiner.refineContent(topic, style);
            logger.info("Successfully executed loop pattern and generated refined content");
            return result;
        } catch (Exception e) {
            logger.error("Error executing loop pattern: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to execute loop pattern: " + e.getMessage(), e);
        }
    }

    /**
     * Executes the Parallel Flow pattern.
     * 
     * Builds a comprehensive startup pitch by executing three agents in parallel:
     * - Executive Summary Generator
     * - Market Analyzer
     * - Risk Assessor
     * 
     * All agents run concurrently, improving performance compared to sequential
     * execution.
     *
     * @param startupName  the name of the startup
     * @param idea         the startup's product/service idea
     * @param targetMarket the target market/audience
     * @return comprehensive startup pitch document
     * @throws IllegalArgumentException if any parameter is null or empty
     * @throws RuntimeException         if pitch generation fails
     */
    public String executeParallelFlow(String startupName, String idea, String targetMarket) {
        if (startupName == null || startupName.trim().isEmpty()) {
            logger.warn("Received null or empty startup name for parallel flow");
            throw new IllegalArgumentException("Startup name cannot be null or empty");
        }
        if (idea == null || idea.trim().isEmpty()) {
            logger.warn("Received null or empty idea for parallel flow");
            throw new IllegalArgumentException("Idea cannot be null or empty");
        }
        if (targetMarket == null || targetMarket.trim().isEmpty()) {
            logger.warn("Received null or empty target market for parallel flow");
            throw new IllegalArgumentException("Target market cannot be null or empty");
        }

        logger.info("Executing parallel flow pattern for startup: {}, idea: {}, market: {}",
                startupName, idea.substring(0, Math.min(idea.length(), 30)), targetMarket);

        try {
            String result = startupPitcher.buildPitch(startupName, idea, targetMarket);
            logger.info("Successfully executed parallel flow and generated startup pitch");
            return result;
        } catch (Exception e) {
            logger.error("Error executing parallel flow: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to execute parallel flow: " + e.getMessage(), e);
        }
    }

    /**
     * Placeholder for future pattern implementations.
     * 
     * Example patterns that could be added:
     * - Hierarchical Routing: Multi-level routing with sub-experts
     * - Tool-using Agents: Agents that can use external tools
     * - Memory-enhanced Agents: Agents with conversation history
     */
    // public String executeHierarchicalRouting(String query) { ... }
}
