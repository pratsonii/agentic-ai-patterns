package com.agent.langchain.patterns;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.agent.AgentRequest;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * Configuration for the Sequential Flow pattern.
 * 
 * This configuration demonstrates a multi-stage sequential workflow where
 * each agent processes and enhances the output from the previous agent.
 * 
 * Use Case: Recipe Development Pipeline
 * The workflow creates a complete recipe through three sequential stages:
 * 1. Ingredient Curator: Suggests ingredients based on cuisine and dietary
 * preferences
 * 2. Cooking Method Designer: Develops cooking techniques and step-by-step
 * instructions
 * 3. Nutritional Analyst: Adds nutritional information and health insights
 * 
 * This pattern is ideal for scenarios where:
 * - Each step builds upon the previous step's output
 * - The workflow has a clear, linear progression
 * - Each agent has a specialized role in the pipeline
 */
@Configuration
public class SequentialFlowPattern {

    @Autowired
    @Qualifier("geminiChatModel")
    private ChatModel chatModel;

    /**
     * Logger for agent invocations during the sequential flow.
     */
    Consumer<AgentRequest> agentRequestLogger = (ag) -> {
        System.out.println("Sequential Flow - Processing with Agent: " + ag.agentId());
    };

    /**
     * Ingredient Curator Agent Interface.
     * First stage: Analyzes cuisine type and dietary preferences to suggest
     * appropriate ingredients.
     */
    public interface IngredientCurator {
        @UserMessage("""
                You are an expert ingredient curator and food scientist.
                Based on the cuisine type and dietary preferences provided, suggest a list of
                fresh, high-quality ingredients for a delicious recipe.

                Consider:
                - Seasonal availability
                - Flavor combinations
                - Dietary restrictions
                - Authenticity to the cuisine

                Cuisine: {{cuisine}}
                Dietary Preferences: {{dietary}}
                Meal Type: {{mealType}}

                Provide a well-organized list of ingredients with approximate quantities.
                Return only the ingredient list and nothing else.
                """)
        @Agent("Curates ingredients based on cuisine type and dietary preferences")
        String suggestIngredients(@V("cuisine") String cuisine,
                @V("dietary") String dietary,
                @V("mealType") String mealType);
    }

    /**
     * Cooking Method Designer Agent Interface.
     * Second stage: Takes the ingredient list and creates detailed cooking
     * instructions with techniques.
     */
    public interface CookingMethodDesigner {
        @UserMessage("""
                You are a professional chef and culinary instructor.
                Using the following ingredients, create detailed step-by-step cooking instructions.

                Include:
                - Preparation steps
                - Cooking techniques and methods
                - Timing for each step
                - Temperature settings
                - Plating suggestions

                Ingredients:
                {{ingredients}}

                Create clear, easy-to-follow instructions that will result in a restaurant-quality dish.
                Return only the cooking instructions and nothing else.
                """)
        @Agent("Designs cooking methods and step-by-step instructions")
        String designCookingMethod(@V("ingredients") String ingredients);
    }

    /**
     * Nutritional Analyst Agent Interface.
     * Third stage: Analyzes the complete recipe and adds nutritional information
     * and health insights.
     */
    public interface NutritionalAnalyst {
        @UserMessage("""
                You are a certified nutritionist and dietitian.
                Analyze the following recipe and provide comprehensive nutritional information.

                Include:
                - Estimated calories per serving
                - Macronutrient breakdown (protein, carbs, fats)
                - Key vitamins and minerals
                - Health benefits
                - Dietary considerations
                - Serving size recommendations

                Recipe:
                {{recipe}}

                Provide a professional nutritional analysis.
                Return only the nutritional information and nothing else.
                """)
        @Agent("Analyzes nutritional content and provides health insights")
        String analyzeNutrition(@V("recipe") String recipe);
    }

    /**
     * Recipe Developer Agent Interface.
     * Main orchestrator that coordinates the sequential workflow.
     */
    public interface RecipeDeveloper {
        String developRecipe(String cuisine, String dietary, String mealType);
    }

    /**
     * Bean definition for the Recipe Developer Agent.
     * 
     * This bean creates a sequential workflow that:
     * 1. IngredientCurator suggests ingredients based on inputs
     * 2. CookingMethodDesigner creates cooking instructions using those ingredients
     * 3. NutritionalAnalyst adds nutritional information to the complete recipe
     * 
     * Each agent's output becomes input for the next agent in the sequence.
     * 
     * @return configured RecipeDeveloper bean
     */
    @Bean
    public RecipeDeveloper recipeDeveloper() {
        // Stage 1: Build the ingredient curator agent
        IngredientCurator ingredientCurator = AgenticServices
                .agentBuilder(IngredientCurator.class)
                .chatModel(chatModel)
                .beforeAgentInvocation(agentRequestLogger)
                .outputKey("ingredients")
                .build();

        // Stage 2: Build the cooking method designer agent
        CookingMethodDesigner cookingMethodDesigner = AgenticServices
                .agentBuilder(CookingMethodDesigner.class)
                .chatModel(chatModel)
                .beforeAgentInvocation(agentRequestLogger)
                .outputKey("recipe")
                .build();

        // Stage 3: Build the nutritional analyst agent
        NutritionalAnalyst nutritionalAnalyst = AgenticServices
                .agentBuilder(NutritionalAnalyst.class)
                .chatModel(chatModel)
                .beforeAgentInvocation(agentRequestLogger)
                .outputKey("nutritionalInfo")
                .build();

        // Build the sequential workflow
        return AgenticServices
                .sequenceBuilder(RecipeDeveloper.class)
                .subAgents(ingredientCurator, cookingMethodDesigner, nutritionalAnalyst)
                .outputKey("nutritionalInfo")
                .build();
    }
}
