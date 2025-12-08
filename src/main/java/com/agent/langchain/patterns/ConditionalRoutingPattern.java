package com.agent.langchain.patterns;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.agentic.agent.AgentRequest;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * Configuration for the Conditional Routing pattern.
 * 
 * This configuration sets up a multi-expert agentic system that:
 * 1. Routes incoming queries to a category router
 * 2. The router classifies queries into specialized domains
 * 3. Dispatches to the appropriate expert agent based on category
 * 
 * Supported Categories:
 * - CREATIVE: Art, design, writing, music, content creation
 * - FINANCIAL: Money management, investing, budgeting, business finance
 * - WELLNESS: Health, fitness, mental wellbeing, nutrition, lifestyle
 * - CAREER: Job search, professional growth, workplace issues, career
 * transitions
 * - UNKNOWN: Queries that don't fit any category
 */
@Configuration
public class ConditionalRoutingPattern {

        @Autowired
        @Qualifier("geminiChatModel")
        private ChatModel chatModel;

        /**
         * Enum representing query categories for routing.
         */
        public enum RequestCategory {
                CREATIVE, FINANCIAL, WELLNESS, CAREER, UNKNOWN
        }

        /**
         * Logger for agent invocations during the routing process.
         */
        Consumer<AgentRequest> agentRequestLogger = (ag) -> {
                System.out.println("Received Request for Agent: " + ag.agentId());
        };

        /**
         * Category Router Agent Interface.
         * Analyzes incoming queries and classifies them into one of the predefined
         * categories.
         */
        public interface CategoryRouter {
                @UserMessage("""
                                Analyze the following user request and categorize it as 'creative', 'financial', 'wellness', or 'career'.
                                - 'creative': Questions about art, design, writing, music, content creation, or creative problem-solving
                                - 'financial': Questions about money management, investing, budgeting, business finance, or economic advice
                                - 'wellness': Questions about health, fitness, mental wellbeing, nutrition, lifestyle, or personal development
                                - 'career': Questions about job search, professional growth, workplace issues, skills development, or career transitions
                                In case the request doesn't belong to any of those categories, categorize it as 'unknown'.
                                Reply with only one of those words and nothing else.
                                The user request is: '{{request}}'.
                                """)
                @Agent("Router agent that classifies queries into expert domains")
                RequestCategory classify(@V("request") String request);
        }

        /**
         * Creative Expert Agent Interface.
         * Provides expert guidance on art, design, writing, music, and creative
         * problem-solving.
         */
        public interface CreativeExpert {
                @UserMessage("{{request}}")
                @Agent("Creative expert specializing in art, design, writing, music, and innovative problem-solving")
                String answer(@V("request") String request);
        }

        /**
         * Financial Advisor Agent Interface.
         * Provides expert guidance on money management, investing, budgeting, and
         * business finance.
         */
        public interface FinancialAdvisor {
                @UserMessage("{{request}}")
                @Agent("Financial advisor providing expert guidance on money management, investing, budgeting, and business finance")
                String answer(@V("request") String request);
        }

        /**
         * Wellness Coach Agent Interface.
         * Provides holistic advice on health, fitness, mental wellbeing, nutrition, and
         * lifestyle optimization.
         */
        public interface WellnessCoach {
                @UserMessage("{{request}}")
                @Agent("Wellness coach offering holistic advice on health, fitness, mental wellbeing, nutrition, and lifestyle optimization")
                String answer(@V("request") String request);
        }

        /**
         * Career Mentor Agent Interface.
         * Guides professionals through job search, skill development, workplace
         * challenges, and career transitions.
         */
        public interface CareerMentor {
                @UserMessage("{{request}}")
                @Agent("Career mentor guiding professionals through job search, skill development, workplace challenges, and career transitions")
                String answer(@V("request") String request);
        }

        /**
         * Expert Router Agent Interface.
         * Main entry point that orchestrates the routing workflow.
         */
        public interface ExpertRouterAgent {
                String ask(String request);
        }

        /**
         * Bean definition for the Expert Router Agent.
         * 
         * This bean creates a multi-step agentic workflow:
         * 1. CategoryRouter agent classifies the incoming query
         * 2. Based on classification, one of four expert agents is invoked
         * 3. The selected expert returns their specialized response
         * 
         * @return configured ExpertRouterAgent bean
         */
        @Bean
        public ExpertRouterAgent expertRouterAgent() {
                // Step 1: Build the category router agent
                CategoryRouter routerAgent = AgenticServices
                                .agentBuilder(CategoryRouter.class)
                                .chatModel(chatModel)
                                .beforeAgentInvocation(agentRequestLogger)
                                .outputKey("category")
                                .build();

                // Step 2: Build specialized expert agents
                CreativeExpert creativeExpert = AgenticServices
                                .agentBuilder(CreativeExpert.class)
                                .chatModel(chatModel)
                                .beforeAgentInvocation(agentRequestLogger)
                                .outputKey("response")
                                .build();

                FinancialAdvisor financialAdvisor = AgenticServices
                                .agentBuilder(FinancialAdvisor.class)
                                .chatModel(chatModel)
                                .beforeAgentInvocation(agentRequestLogger)
                                .outputKey("response")
                                .build();

                WellnessCoach wellnessCoach = AgenticServices
                                .agentBuilder(WellnessCoach.class)
                                .chatModel(chatModel)
                                .outputKey("response")
                                .build();

                CareerMentor careerMentor = AgenticServices
                                .agentBuilder(CareerMentor.class)
                                .chatModel(chatModel)
                                .beforeAgentInvocation(agentRequestLogger)
                                .outputKey("response")
                                .build();

                // Step 3: Build conditional routing based on category
                UntypedAgent expertsAgent = AgenticServices.conditionalBuilder()
                                .subAgents(agenticScope -> agenticScope.readState("category",
                                                RequestCategory.UNKNOWN) == RequestCategory.CREATIVE, creativeExpert)
                                .subAgents(agenticScope -> agenticScope.readState("category",
                                                RequestCategory.UNKNOWN) == RequestCategory.FINANCIAL, financialAdvisor)
                                .subAgents(agenticScope -> agenticScope.readState("category",
                                                RequestCategory.UNKNOWN) == RequestCategory.WELLNESS, wellnessCoach)
                                .subAgents(agenticScope -> agenticScope.readState("category",
                                                RequestCategory.UNKNOWN) == RequestCategory.CAREER, careerMentor)
                                .build();

                // Step 4: Chain router and expert agents in sequence
                return AgenticServices
                                .sequenceBuilder(ExpertRouterAgent.class)
                                .subAgents(routerAgent, expertsAgent)
                                .outputKey("response")
                                .build();
        }
}
