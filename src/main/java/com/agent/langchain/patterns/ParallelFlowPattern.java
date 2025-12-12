package com.agent.langchain.patterns;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
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
 * Configuration for the Parallel Flow pattern.
 * 
 * This configuration demonstrates parallel execution of multiple independent
 * agents that run concurrently and their outputs are combined into a final
 * result.
 * 
 * Use Case: Tech Startup Pitch Builder
 * The workflow creates a comprehensive startup pitch by executing three agents
 * in parallel:
 * 1. Executive Summary Generator: Creates a concise overview of the startup idea
 * 2. Market Analyzer: Analyzes market size, trends, and opportunities
 * 3. Risk Assessor: Identifies potential risks and mitigation strategies
 * 
 * This pattern is ideal for scenarios where:
 * - Multiple independent tasks can be executed concurrently
 * - Results from different agents need to be combined
 * - Performance is critical (parallel execution is faster than sequential)
 * - Each agent works on a different aspect of the problem
 */
@Configuration
public class ParallelFlowPattern {

    @Autowired
    @Qualifier("geminiChatModel")
    private ChatModel chatModel;

    /**
     * Logger for agent invocations during parallel flow.
     */
    Consumer<AgentRequest> agentRequestLogger = (ag) -> {
        System.out.println("Parallel Flow - Processing with Agent: " + ag.agentId());
    };

    /**
     * Executive Summary Agent Interface.
     * Generates a concise, compelling overview of the startup idea.
     */
    public interface ExecutiveSummaryGenerator {
        @UserMessage("""
                You are an expert business strategist and pitch coach.
                Create a compelling executive summary for a startup.

                Startup Name: {{startupName}}
                Idea/Product: {{idea}}
                Target Market: {{targetMarket}}

                The executive summary should:
                - Hook the reader with the problem being solved
                - Clearly state the unique value proposition
                - Highlight why this startup will succeed
                - Keep it to 3-4 sentences maximum
                
                Return only the executive summary and nothing else.
                """)
        @Agent("Generates compelling executive summary for startup pitch")
        String generateExecutiveSummary(
                @V("startupName") String startupName,
                @V("idea") String idea,
                @V("targetMarket") String targetMarket);
    }

    /**
     * Market Analyzer Agent Interface.
     * Analyzes market size, trends, and competitive opportunities.
     */
    public interface MarketAnalyzer {
        @UserMessage("""
                You are a venture capital analyst and market research expert.
                Analyze the market opportunity for a startup.

                Startup Name: {{startupName}}
                Product/Service: {{idea}}
                Target Market: {{targetMarket}}

                Provide market analysis including:
                - Estimated market size (TAM - Total Addressable Market)
                - Key market trends supporting this opportunity
                - Target customer segments
                - Competitive landscape overview
                - Growth potential and timeline
                
                Return only the market analysis and nothing else.
                """)
        @Agent("Analyzes market opportunity and trends")
        String analyzeMarket(
                @V("startupName") String startupName,
                @V("idea") String idea,
                @V("targetMarket") String targetMarket);
    }

    /**
     * Risk Assessor Agent Interface.
     * Identifies risks and proposes mitigation strategies.
     */
    public interface RiskAssessor {
        @UserMessage("""
                You are a risk management consultant and due diligence expert.
                Assess risks for a startup and propose mitigation strategies.

                Startup Name: {{startupName}}
                Product/Service: {{idea}}
                Target Market: {{targetMarket}}

                Identify and analyze:
                - Key business risks (market, competition, execution)
                - Technology and operational risks
                - Regulatory and compliance risks
                - Financial risks
                - Mitigation strategies for each identified risk
                
                Be realistic but constructive in your assessment.
                Return only the risk assessment and nothing else.
                """)
        @Agent("Assesses risks and proposes mitigation strategies")
        String assessRisks(
                @V("startupName") String startupName,
                @V("idea") String idea,
                @V("targetMarket") String targetMarket);
    }

    /**
     * Startup Pitcher Agent Interface.
     * Main orchestrator that combines results from all parallel agents.
     */
    public interface StartupPitcher {
        String buildPitch(String startupName, String idea, String targetMarket);
    }

    /**
     * Bean definition for the Startup Pitcher Agent.
     * 
     * This bean creates a parallel workflow where:
     * 1. ExecutiveSummaryGenerator generates a concise pitch overview
     * 2. MarketAnalyzer analyzes market opportunity
     * 3. RiskAssessor identifies risks and mitigation strategies
     * 
     * All three agents execute in parallel using a thread pool (2 threads).
     * Their outputs are combined into a comprehensive startup pitch document.
     * 
     * @return configured StartupPitcher bean
     */
    @Bean
    public StartupPitcher startupPitcher() {
        // Stage 1: Build the executive summary generator agent
        ExecutiveSummaryGenerator executiveSummaryGenerator = AgenticServices
                .agentBuilder(ExecutiveSummaryGenerator.class)
                .chatModel(chatModel)
                .beforeAgentInvocation(agentRequestLogger)
                .outputKey("executiveSummary")
                .build();

        // Stage 2: Build the market analyzer agent
        MarketAnalyzer marketAnalyzer = AgenticServices
                .agentBuilder(MarketAnalyzer.class)
                .chatModel(chatModel)
                .beforeAgentInvocation(agentRequestLogger)
                .outputKey("marketAnalysis")
                .build();

        // Stage 3: Build the risk assessor agent
        RiskAssessor riskAssessor = AgenticServices
                .agentBuilder(RiskAssessor.class)
                .chatModel(chatModel)
                .beforeAgentInvocation(agentRequestLogger)
                .outputKey("riskAssessment")
                .build();

        // Build the parallel workflow
        return AgenticServices
                .parallelBuilder(StartupPitcher.class)
                .subAgents(executiveSummaryGenerator, marketAnalyzer, riskAssessor)
                .executor(Executors.newFixedThreadPool(3))
                .outputKey("pitch")
                .output(agenticScope -> {
                    String executiveSummary = agenticScope.readState("executiveSummary", "");
                    String marketAnalysis = agenticScope.readState("marketAnalysis", "");
                    String riskAssessment = agenticScope.readState("riskAssessment", "");

                    // Combine all outputs into a comprehensive pitch document
                    StringBuilder pitchDocument = new StringBuilder();
                    pitchDocument.append("═══════════════════════════════════════════════════════════\n");
                    pitchDocument.append("                    STARTUP PITCH DOCUMENT\n");
                    pitchDocument.append("═══════════════════════════════════════════════════════════\n\n");

                    pitchDocument.append("EXECUTIVE SUMMARY\n");
                    pitchDocument.append("───────────────────────────────────────────────────────────\n");
                    pitchDocument.append(executiveSummary).append("\n\n");

                    pitchDocument.append("MARKET ANALYSIS\n");
                    pitchDocument.append("───────────────────────────────────────────────────────────\n");
                    pitchDocument.append(marketAnalysis).append("\n\n");

                    pitchDocument.append("RISK ASSESSMENT & MITIGATION\n");
                    pitchDocument.append("───────────────────────────────────────────────────────────\n");
                    pitchDocument.append(riskAssessment).append("\n");

                    return pitchDocument.toString();
                })
                .build();
    }
}
