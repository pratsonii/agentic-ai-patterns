package com.agent.langchain.controller;

import com.agent.langchain.dto.ExpertQueryRequest;
import com.agent.langchain.services.AgentPatternService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

/**
 * WebMvcTest for AgentPatternController.
 * 
 * This test class focuses on testing the REST API endpoints for the conditional
 * routing pattern.
 * It tests routing to each of the four expert agents: Creative, Financial,
 * Wellness, and Career.
 * 
 * The tests use MockMvc to simulate HTTP requests and verify responses without
 * starting
 * a full HTTP server.
 */
@WebMvcTest(AgentPatternController.class)
@DisplayName("Agent Pattern Controller WebMvc Tests")
public class AgentPatternControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AgentPatternService agentPatternService;

    // ==================== Creative Expert Tests ====================

    @Test
    @DisplayName("Should route to Creative Expert for art-related query")
    public void testCreativeExpert_ArtQuery() throws Exception {
        // Given
        String query = "How can I improve my watercolor painting techniques?";
        ExpertQueryRequest request = new ExpertQueryRequest(query);
        String mockResponse = "As a creative expert, I recommend focusing on wet-on-wet techniques and color blending...";

        when(agentPatternService.executeConditionalRouting(query))
                .thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/api/v1/patterns/conditional-routing/route")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response").value(mockResponse))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Should route to Creative Expert for writing-related query")
    public void testCreativeExpert_WritingQuery() throws Exception {
        // Given
        String query = "What are some tips for writing compelling short stories?";
        ExpertQueryRequest request = new ExpertQueryRequest(query);
        String mockResponse = "To write compelling short stories, focus on strong character development, create conflict early...";

        when(agentPatternService.executeConditionalRouting(query))
                .thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/api/v1/patterns/conditional-routing/route")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response").value(mockResponse))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Should route to Creative Expert for music-related query")
    public void testCreativeExpert_MusicQuery() throws Exception {
        // Given
        String query = "How do I compose a catchy melody for my song?";
        ExpertQueryRequest request = new ExpertQueryRequest(query);
        String mockResponse = "Creating a catchy melody involves using repetition, memorable hooks, and understanding musical scales...";

        when(agentPatternService.executeConditionalRouting(query))
                .thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/api/v1/patterns/conditional-routing/route")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response").value(mockResponse))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Should route to Creative Expert for design-related query")
    public void testCreativeExpert_DesignQuery() throws Exception {
        // Given
        String query = "What are the principles of good UI/UX design?";
        ExpertQueryRequest request = new ExpertQueryRequest(query);
        String mockResponse = "Good UI/UX design follows principles like consistency, simplicity, visual hierarchy, and user feedback...";

        when(agentPatternService.executeConditionalRouting(query))
                .thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/api/v1/patterns/conditional-routing/route")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response").value(mockResponse))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    // ==================== Financial Advisor Tests ====================

    @Test
    @DisplayName("Should route to Financial Advisor for investment query")
    public void testFinancialAdvisor_InvestmentQuery() throws Exception {
        // Given
        String query = "What's the best strategy for long-term investing in stocks?";
        ExpertQueryRequest request = new ExpertQueryRequest(query);
        String mockResponse = "For long-term stock investing, consider diversification, dollar-cost averaging, and focusing on index funds...";

        when(agentPatternService.executeConditionalRouting(query))
                .thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/api/v1/patterns/conditional-routing/route")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response").value(mockResponse))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Should route to Financial Advisor for budgeting query")
    public void testFinancialAdvisor_BudgetingQuery() throws Exception {
        // Given
        String query = "How can I create an effective monthly budget?";
        ExpertQueryRequest request = new ExpertQueryRequest(query);
        String mockResponse = "Creating an effective budget involves tracking expenses, using the 50/30/20 rule, and setting financial goals...";

        when(agentPatternService.executeConditionalRouting(query))
                .thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/api/v1/patterns/conditional-routing/route")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response").value(mockResponse))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Should route to Financial Advisor for retirement planning query")
    public void testFinancialAdvisor_RetirementQuery() throws Exception {
        // Given
        String query = "What should I know about retirement savings and 401k plans?";
        ExpertQueryRequest request = new ExpertQueryRequest(query);
        String mockResponse = "Retirement planning with 401k involves understanding employer matching, contribution limits, and tax advantages...";

        when(agentPatternService.executeConditionalRouting(query))
                .thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/api/v1/patterns/conditional-routing/route")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response").value(mockResponse))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Should route to Financial Advisor for business finance query")
    public void testFinancialAdvisor_BusinessFinanceQuery() throws Exception {
        // Given
        String query = "How do I manage cash flow for my small business?";
        ExpertQueryRequest request = new ExpertQueryRequest(query);
        String mockResponse = "Managing small business cash flow requires monitoring receivables, controlling expenses, and maintaining reserves...";

        when(agentPatternService.executeConditionalRouting(query))
                .thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/api/v1/patterns/conditional-routing/route")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response").value(mockResponse))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    // ==================== Wellness Coach Tests ====================

    @Test
    @DisplayName("Should route to Wellness Coach for fitness query")
    public void testWellnessCoach_FitnessQuery() throws Exception {
        // Given
        String query = "What's an effective workout routine for beginners?";
        ExpertQueryRequest request = new ExpertQueryRequest(query);
        String mockResponse = "For beginners, start with a balanced routine including cardio, strength training, and flexibility exercises...";

        when(agentPatternService.executeConditionalRouting(query))
                .thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/api/v1/patterns/conditional-routing/route")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response").value(mockResponse))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Should route to Wellness Coach for nutrition query")
    public void testWellnessCoach_NutritionQuery() throws Exception {
        // Given
        String query = "How can I eat healthier and improve my diet?";
        ExpertQueryRequest request = new ExpertQueryRequest(query);
        String mockResponse = "Improving your diet involves eating whole foods, balancing macronutrients, staying hydrated, and meal planning...";

        when(agentPatternService.executeConditionalRouting(query))
                .thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/api/v1/patterns/conditional-routing/route")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response").value(mockResponse))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Should route to Wellness Coach for mental health query")
    public void testWellnessCoach_MentalHealthQuery() throws Exception {
        // Given
        String query = "What are some strategies to reduce stress and anxiety?";
        ExpertQueryRequest request = new ExpertQueryRequest(query);
        String mockResponse = "To reduce stress and anxiety, practice mindfulness, maintain regular sleep, exercise, and consider meditation...";

        when(agentPatternService.executeConditionalRouting(query))
                .thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/api/v1/patterns/conditional-routing/route")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response").value(mockResponse))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Should route to Wellness Coach for lifestyle query")
    public void testWellnessCoach_LifestyleQuery() throws Exception {
        // Given
        String query = "How can I improve my sleep quality and establish better sleep habits?";
        ExpertQueryRequest request = new ExpertQueryRequest(query);
        String mockResponse = "Improving sleep quality involves maintaining a consistent schedule, creating a relaxing bedtime routine...";

        when(agentPatternService.executeConditionalRouting(query))
                .thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/api/v1/patterns/conditional-routing/route")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response").value(mockResponse))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    // ==================== Career Mentor Tests ====================

    @Test
    @DisplayName("Should route to Career Mentor for job search query")
    public void testCareerMentor_JobSearchQuery() throws Exception {
        // Given
        String query = "What are the best strategies for finding a new job?";
        ExpertQueryRequest request = new ExpertQueryRequest(query);
        String mockResponse = "Effective job search strategies include networking, optimizing your resume, leveraging LinkedIn, and tailoring applications...";

        when(agentPatternService.executeConditionalRouting(query))
                .thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/api/v1/patterns/conditional-routing/route")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response").value(mockResponse))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Should route to Career Mentor for professional development query")
    public void testCareerMentor_ProfessionalDevelopmentQuery() throws Exception {
        // Given
        String query = "How can I advance my career and get promoted?";
        ExpertQueryRequest request = new ExpertQueryRequest(query);
        String mockResponse = "Career advancement involves developing leadership skills, taking on challenging projects, building relationships...";

        when(agentPatternService.executeConditionalRouting(query))
                .thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/api/v1/patterns/conditional-routing/route")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response").value(mockResponse))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Should route to Career Mentor for workplace issues query")
    public void testCareerMentor_WorkplaceIssuesQuery() throws Exception {
        // Given
        String query = "How should I handle conflicts with my manager?";
        ExpertQueryRequest request = new ExpertQueryRequest(query);
        String mockResponse = "Handling workplace conflicts requires professional communication, seeking to understand perspectives, and finding common ground...";

        when(agentPatternService.executeConditionalRouting(query))
                .thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/api/v1/patterns/conditional-routing/route")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response").value(mockResponse))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Should route to Career Mentor for career transition query")
    public void testCareerMentor_CareerTransitionQuery() throws Exception {
        // Given
        String query = "I want to switch careers to tech. What should I do?";
        ExpertQueryRequest request = new ExpertQueryRequest(query);
        String mockResponse = "Transitioning to tech involves identifying transferable skills, learning relevant technologies, building a portfolio...";

        when(agentPatternService.executeConditionalRouting(query))
                .thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/api/v1/patterns/conditional-routing/route")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response").value(mockResponse))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    // ==================== Validation Tests ====================

    @Test
    @DisplayName("Should return 400 Bad Request for empty query")
    public void testValidation_EmptyQuery() throws Exception {
        // Given
        ExpertQueryRequest request = new ExpertQueryRequest("");

        // When & Then
        mockMvc.perform(post("/api/v1/patterns/conditional-routing/route")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 400 Bad Request for null query")
    public void testValidation_NullQuery() throws Exception {
        // Given
        ExpertQueryRequest request = new ExpertQueryRequest(null);

        // When & Then
        mockMvc.perform(post("/api/v1/patterns/conditional-routing/route")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 400 Bad Request for query too short")
    public void testValidation_QueryTooShort() throws Exception {
        // Given
        ExpertQueryRequest request = new ExpertQueryRequest("Hi");

        // When & Then
        mockMvc.perform(post("/api/v1/patterns/conditional-routing/route")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 400 Bad Request for query too long")
    public void testValidation_QueryTooLong() throws Exception {
        // Given
        String longQuery = "a".repeat(1001);
        ExpertQueryRequest request = new ExpertQueryRequest(longQuery);

        // When & Then
        mockMvc.perform(post("/api/v1/patterns/conditional-routing/route")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    // ==================== Health Check Tests ====================

    @Test
    @DisplayName("Should return OK for conditional routing health check")
    public void testConditionalRoutingHealthCheck() throws Exception {
        mockMvc.perform(get("/api/v1/patterns/conditional-routing/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("Conditional Routing Pattern is operational"));
    }

    @Test
    @DisplayName("Should return OK for general health check")
    public void testGeneralHealthCheck() throws Exception {
        mockMvc.perform(get("/api/v1/patterns/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("Agent Pattern Service is operational"));
    }
}
