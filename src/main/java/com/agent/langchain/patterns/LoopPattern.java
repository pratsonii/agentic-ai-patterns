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
 * Configuration for the Loop Pattern.
 * 
 * This configuration demonstrates an iterative refinement workflow where
 * agents work in a loop to continuously improve content until it meets
 * quality standards.
 * 
 * Use Case: Content Quality Refinement System
 * The workflow creates high-quality content through iterative improvement:
 * 1. Content Creator: Generates initial content based on topic and style
 * 2. Quality Scorer: Evaluates content quality (0.0 to 1.0 scale)
 * 3. Content Editor: Refines content based on quality feedback
 * 4. Loop continues until quality score >= 0.8 or max iterations reached
 * 
 * This pattern is ideal for scenarios where:
 * - Quality improvement requires multiple iterations
 * - Feedback-driven refinement is needed
 * - Exit conditions are based on measurable criteria
 * - Automated quality assurance is required
 */
@Configuration
public class LoopPattern {

    @Autowired
    @Qualifier("geminiChatModel")
    private ChatModel chatModel;

    /**
     * Logger for agent invocations during the loop process.
     */
    Consumer<AgentRequest> agentRequestLogger = (ag) -> {
        System.out.println("Loop Pattern - Processing with Agent: " + ag.agentId());
    };

    /**
     * Content Creator Agent Interface.
     * Generates initial content based on topic and desired style.
     */
    public interface ContentCreator {
        @UserMessage("""
                You are a creative content writer.
                Create engaging content about the following topic in the specified style.

                Topic: {{topic}}
                Style: {{style}}

                Write a compelling piece that is informative, well-structured, and matches the requested style.
                Keep it concise (2-3 paragraphs).
                Return only the content and nothing else.
                """)
        @Agent("Creates initial content based on topic and style")
        String createContent(@V("topic") String topic, @V("style") String style);
    }

    /**
     * Quality Scorer Agent Interface.
     * Evaluates content quality on a scale from 0.0 to 1.0.
     */
    public interface QualityScorer {
        @UserMessage("""
                You are a content quality analyst.
                Evaluate the following content and provide a quality score between 0.0 and 1.0.

                Evaluation Criteria:
                - Clarity and coherence (0.3 weight)
                - Engagement and style (0.3 weight)
                - Grammar and structure (0.2 weight)
                - Relevance to topic (0.2 weight)

                Content to evaluate:
                {{content}}

                Respond with ONLY a decimal number between 0.0 and 1.0 (e.g., 0.75).
                Do not include any explanation, just the number.
                """)
        @Agent("Evaluates content quality and assigns a score")
        Double scoreContent(@V("content") String content);
    }

    /**
     * Content Editor Agent Interface.
     * Refines and improves content based on quality feedback.
     */
    public interface ContentEditor {
        @UserMessage("""
                You are an expert content editor.
                Improve the following content to enhance its quality.

                Current Quality Score: {{score}}

                Focus on:
                - Enhancing clarity and flow
                - Improving engagement and style
                - Fixing any grammatical issues
                - Strengthening the narrative

                Content to improve:
                {{content}}

                Provide the improved version. Return only the improved content and nothing else.
                """)
        @Agent("Refines and improves content quality")
        String editContent(@V("content") String content, @V("score") Double score);
    }

    /**
     * Content Refiner Agent Interface.
     * Main orchestrator that coordinates the iterative refinement workflow.
     */
    public interface ContentRefiner {
        String refineContent(String topic, String style);
    }

    /**
     * Bean definition for the Content Refiner Agent.
     * 
     * This bean creates an iterative loop workflow that:
     * 1. ContentCreator generates initial content
     * 2. QualityScorer evaluates the content (loop starts)
     * 3. ContentEditor improves the content based on score
     * 4. Loop repeats until score >= 0.8 or max 5 iterations
     * 
     * The loop pattern enables continuous improvement until quality standards are
     * met.
     * 
     * @return configured ContentRefiner bean
     */
    @Bean
    public ContentRefiner contentRefiner() {
        // Stage 1: Build the content creator agent
        ContentCreator contentCreator = AgenticServices
                .agentBuilder(ContentCreator.class)
                .chatModel(chatModel)
                .beforeAgentInvocation(agentRequestLogger)
                .outputKey("content")
                .build();

        // Stage 2: Build the quality scorer agent
        QualityScorer qualityScorer = AgenticServices
                .agentBuilder(QualityScorer.class)
                .chatModel(chatModel)
                .beforeAgentInvocation(agentRequestLogger)
                .outputKey("score")
                .build();

        // Stage 3: Build the content editor agent
        ContentEditor contentEditor = AgenticServices
                .agentBuilder(ContentEditor.class)
                .chatModel(chatModel)
                .beforeAgentInvocation(agentRequestLogger)
                .outputKey("content")
                .build();

        // Stage 4: Build the refinement loop (scorer -> editor)
        UntypedAgent refinementLoop = AgenticServices
                .loopBuilder()
                .subAgents(qualityScorer, contentEditor)
                .maxIterations(5)
                .exitCondition(agenticScope -> {
                    System.out.println("Quality Scorer - Score: " + agenticScope.readState("score", 0.0));
                    return agenticScope.readState("score", 0.0) >= 0.9;
                })
                .build();

        // Stage 5: Chain creator and refinement loop in sequence
        return AgenticServices
                .sequenceBuilder(ContentRefiner.class)
                .subAgents(contentCreator, refinementLoop)
                .outputKey("content")
                .build();
    }
}
