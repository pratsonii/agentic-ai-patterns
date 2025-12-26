# API Testing Guide

Quick reference for testing Agent Pattern endpoints.

## Prerequisites

Start the application: `mvn spring-boot:run`  
Base URL: `http://localhost:8080`

---

## Conditional Routing Pattern

Routes queries to specialized expert agents (Creative, Financial, Wellness, Career).

**Endpoint**: `POST /api/v1/patterns/conditional-routing/route`

### Examples

```bash
# Creative Expert
curl -X POST http://localhost:8080/api/v1/patterns/conditional-routing/route \
  -H "Content-Type: application/json" \
  -d '{"query":"How can I improve my watercolor painting techniques?"}'

# Financial Advisor
curl -X POST http://localhost:8080/api/v1/patterns/conditional-routing/route \
  -H "Content-Type: application/json" \
  -d '{"query":"What is the best strategy for long-term investing in stocks?"}'

# Wellness Coach
curl -X POST http://localhost:8080/api/v1/patterns/conditional-routing/route \
  -H "Content-Type: application/json" \
  -d '{"query":"What is an effective workout routine for beginners?"}'

# Career Mentor
curl -X POST http://localhost:8080/api/v1/patterns/conditional-routing/route \
  -H "Content-Type: application/json" \
  -d '{"query":"What are the best strategies for finding a new job?"}'
```

---

## Sequential Flow Pattern

Develops complete recipes through a 3-stage pipeline: Ingredients → Cooking Instructions → Nutritional Analysis.

**Endpoint**: `POST /api/v1/patterns/sequential-flow/develop-recipe`

### Examples

```bash
# Italian Vegetarian Dinner
curl -X POST http://localhost:8080/api/v1/patterns/sequential-flow/develop-recipe \
  -H "Content-Type: application/json" \
  -d '{"cuisine":"Italian","dietary":"vegetarian","mealType":"dinner"}'

# Thai Vegan Lunch
curl -X POST http://localhost:8080/api/v1/patterns/sequential-flow/develop-recipe \
  -H "Content-Type: application/json" \
  -d '{"cuisine":"Thai","dietary":"vegan","mealType":"lunch"}'

# Mexican Gluten-Free Breakfast
curl -X POST http://localhost:8080/api/v1/patterns/sequential-flow/develop-recipe \
  -H "Content-Type: application/json" \
  -d '{"cuisine":"Mexican","dietary":"gluten-free","mealType":"breakfast"}'

# Japanese Keto Dinner
curl -X POST http://localhost:8080/api/v1/patterns/sequential-flow/develop-recipe \
  -H "Content-Type: application/json" \
  -d '{"cuisine":"Japanese","dietary":"keto","mealType":"dinner"}'

# French Low-Carb Dessert
curl -X POST http://localhost:8080/api/v1/patterns/sequential-flow/develop-recipe \
  -H "Content-Type: application/json" \
  -d '{"cuisine":"French","dietary":"low-carb","mealType":"dessert"}'

# Indian Dairy-Free Lunch
curl -X POST http://localhost:8080/api/v1/patterns/sequential-flow/develop-recipe \
  -H "Content-Type: application/json" \
  -d '{"cuisine":"Indian","dietary":"dairy-free","mealType":"lunch"}'

# Mediterranean Paleo Breakfast
curl -X POST http://localhost:8080/api/v1/patterns/sequential-flow/develop-recipe \
  -H "Content-Type: application/json" \
  -d '{"cuisine":"Mediterranean","dietary":"paleo","mealType":"breakfast"}'
```

---

## Parallel Flow Pattern

Builds comprehensive startup pitches by executing multiple agents in parallel: Executive Summary → Market Analysis → Risk Assessment.

**Endpoint**: `POST /api/v1/patterns/parallel-flow/build-pitch`

### Examples

```bash
# Tech SaaS Startup
curl -X POST http://localhost:8080/api/v1/patterns/parallel-flow/build-pitch \
  -H "Content-Type: application/json" \
  -d '{"startupName":"CloudSync AI","idea":"AI-powered cloud data synchronization and backup platform with real-time collaboration features","targetMarket":"Enterprise IT departments and remote teams"}'

# HealthTech Startup
curl -X POST http://localhost:8080/api/v1/patterns/parallel-flow/build-pitch \
  -H "Content-Type: application/json" \
  -d '{"startupName":"WellnessFlow","idea":"Mobile app for personalized mental health and stress management using AI and behavioral science","targetMarket":"Millennials and Gen Z health-conscious consumers"}'

# FinTech Startup
curl -X POST http://localhost:8080/api/v1/patterns/parallel-flow/build-pitch \
  -H "Content-Type: application/json" \
  -d '{"startupName":"MoneyMind","idea":"Intelligent personal finance platform for cryptocurrency management and investment portfolio optimization","targetMarket":"Crypto investors and young professionals"}'

# EdTech Startup
curl -X POST http://localhost:8080/api/v1/patterns/parallel-flow/build-pitch \
  -H "Content-Type: application/json" \
  -d '{"startupName":"SkillBridge","idea":"Platform connecting students with mentors for skill development through AI-matched pair learning","targetMarket":"College students and career changers"}'

# E-commerce Startup
curl -X POST http://localhost:8080/api/v1/patterns/parallel-flow/build-pitch \
  -H "Content-Type: application/json" \
  -d '{"startupName":"VintageHub","idea":"Curated online marketplace for sustainable vintage and second-hand fashion with AR try-on","targetMarket":"Eco-conscious fashion enthusiasts aged 18-35"}'

# IoT Startup
curl -X POST http://localhost:8080/api/v1/patterns/parallel-flow/build-pitch \
  -H "Content-Type: application/json" \
  -d '{"startupName":"SmartHome Plus","idea":"Integrated IoT ecosystem for smart home automation with voice control and energy optimization","targetMarket":"Homeowners and smart building facility managers"}'

# Green Energy Startup
curl -X POST http://localhost:8080/api/v1/patterns/parallel-flow/build-pitch \
  -H "Content-Type: application/json" \
  -d '{"startupName":"SolarIQ","idea":"AI-powered solar panel monitoring and maintenance platform for residential and commercial installations","targetMarket":"Solar companies, installers, and homeowners"}'
```

---

## Loop Pattern

Iteratively refines content through quality scoring and editing until it meets quality standards (score >= 0.8) or reaches max iterations (5).

**Endpoint**: `POST /api/v1/patterns/loop/refine-content`

### Examples

```bash
# Professional Technical Article
curl -X POST http://localhost:8080/api/v1/patterns/loop/refine-content \
  -H "Content-Type: application/json" \
  -d '{"topic":"artificial intelligence in healthcare","style":"professional"}'

# Casual Blog Post
curl -X POST http://localhost:8080/api/v1/patterns/loop/refine-content \
  -H "Content-Type: application/json" \
  -d '{"topic":"sustainable living tips","style":"casual"}'

# Technical Documentation
curl -X POST http://localhost:8080/api/v1/patterns/loop/refine-content \
  -H "Content-Type: application/json" \
  -d '{"topic":"microservices architecture patterns","style":"technical"}'

# Creative Storytelling
curl -X POST http://localhost:8080/api/v1/patterns/loop/refine-content \
  -H "Content-Type: application/json" \
  -d '{"topic":"time travel paradoxes","style":"creative"}'

# Academic Writing
curl -X POST http://localhost:8080/api/v1/patterns/loop/refine-content \
  -H "Content-Type: application/json" \
  -d '{"topic":"climate change mitigation strategies","style":"academic"}'

# Marketing Copy
curl -X POST http://localhost:8080/api/v1/patterns/loop/refine-content \
  -H "Content-Type: application/json" \
  -d '{"topic":"eco-friendly products","style":"persuasive"}'

# Educational Content
curl -X POST http://localhost:8080/api/v1/patterns/loop/refine-content \
  -H "Content-Type: application/json" \
  -d '{"topic":"quantum computing basics","style":"educational"}'
```

---

## Human in Loop Pattern

Real-time interview coaching with human feedback integration. Demonstrates synchronous human-in-the-loop feedback workflow in a single API call for interview assessment and candidate coaching.

**Workflow**: Submit Interview Response → (AI Coaching + Human Feedback + Final Assessment) → Single Response

### Endpoints

```
POST   /api/v1/patterns/human-in-loop/submit-interview  - Submit interview response & get complete assessment
GET    /api/v1/patterns/human-in-loop/health            - Health check
```

### Single-Step Example

#### Submit Interview Response & Get Complete Assessment

```bash
curl -X POST http://localhost:8080/api/v1/patterns/human-in-loop/submit-interview \
  -H "Content-Type: application/json" \
  -d '{
    "candidateName":"Alice Johnson",
    "position":"Senior Software Engineer",
    "question":"Tell us about a time you had to refactor legacy code. What was your approach and what challenges did you face?",
    "response":"In my previous role at Tech Corp, I inherited a 50,000-line monolithic Python codebase that was difficult to test and maintain. First, I analyzed the code structure and identified core modules. Then I created comprehensive unit tests to establish a safety net. I refactored incrementally, breaking the monolith into microservices over 3 months. This improved test coverage from 15% to 85% and reduced deployment time by 60%. The key was communication with the team and gradual migration to avoid disrupting ongoing features."
  }'
```

**Response** (Complete assessment in one call):
```json
{
  "candidateName": "Alice Johnson",
  "position": "Senior Software Engineer",
  "coachingFeedback": "AI COACHING FEEDBACK\n═══════════════════════════════════\nStrengths:\n- Excellent quantitative thinking (15% → 85% coverage, 60% deployment improvement)\n- Strong problem-solving approach with phased implementation\n- Clear communication and team awareness\n\nDevelopment Areas:\n- Could mention specific tools/frameworks used\n- Could discuss testing strategy in more detail\n\nOverall: Strong technical response demonstrating senior-level competencies.",
  "humanFeedback": "HUMAN INTERVIEWER FEEDBACK\n═══════════════════════════════════\nCommunication: Clear, structured narrative with specific metrics\nTechnical Depth: Strong understanding of refactoring, testing, and system design\nLeadership: Shows collaborative approach and change management skills\nRating: 9/10",
  "finalAssessment": "FINAL HIRING ASSESSMENT\n═══════════════════════════════════\nCandidate: Alice Johnson | Position: Senior Software Engineer\nRecommendation: STRONG HIRE\n\nSynthesis:\nCandidate demonstrates exceptional technical leadership with proven ability to manage complex refactoring initiatives. Strong communication and collaborative approach. Clear evidence of system design thinking and risk management. Excellent fit for senior engineering role. Recommend immediate offer."
}
```

### Practical Interview Scenarios

```bash
# Scenario 1: Behavioral question about teamwork
curl -X POST http://localhost:8080/api/v1/patterns/human-in-loop/submit-interview \
  -H "Content-Type: application/json" \
  -d '{
    "candidateName":"Bob Martinez",
    "position":"Product Manager",
    "question":"Describe a situation where you had to work with a difficult team member. How did you handle it?",
    "response":"I worked with an engineer who was resistant to a new product direction. Instead of mandating the change, I scheduled a 1-on-1 to understand his concerns. He was worried about technical debt accumulation. I acknowledged his concerns and we collaborated on a hybrid approach that balanced new features with technical improvements. This built trust and he became one of the strongest advocates for the new direction. The key lesson was that understanding the root cause of resistance and involving stakeholders in solutions leads to better outcomes."
  }'

# Scenario 2: Technical deep-dive question
curl -X POST http://localhost:8080/api/v1/patterns/human-in-loop/submit-interview \
  -H "Content-Type: application/json" \
  -d '{
    "candidateName":"Carol Chen",
    "position":"Data Engineer",
    "question":"Design a data pipeline to handle real-time streaming data from 1000 IoT devices with fault tolerance requirements.",
    "response":"I would use Apache Kafka for ingestion, Apache Spark Streaming for real-time processing, and Delta Lake for reliable storage. Kafka provides exactly-once semantics and partitioning by device ID. Spark Streaming with micro-batch processing allows complex aggregations and transformations. Delta Lake ensures ACID transactions and time-travel capabilities. For fault tolerance, I would implement checkpointing in Spark, replication in Kafka, and multi-zone deployment. Monitoring with Prometheus and alerting for lag detection ensures early problem identification."
  }'

# Scenario 3: Leadership question
curl -X POST http://localhost:8080/api/v1/patterns/human-in-loop/submit-interview \
  -H "Content-Type: application/json" \
  -d '{
    "candidateName":"David Kumar",
    "position":"Engineering Manager",
    "question":"How do you build psychological safety and trust in a new team you are managing?",
    "response":"First, I establish regular 1-on-1s to understand each team member's strengths, career goals, and concerns. I model vulnerability by admitting my own mistakes and learning publicly. I create a blameless post-mortem culture where failures are learning opportunities, not punishment. I actively solicit input on decisions before announcing them, showing I value diverse perspectives. I celebrate both individual and team wins. I protect my team from unnecessary organizational changes while being transparent about constraints. Trust is built through consistency, follow-through on commitments, and genuine investment in their growth."
  }'

# Scenario 4: Coding challenge response
curl -X POST http://localhost:8080/api/v1/patterns/human-in-loop/submit-interview \
  -H "Content-Type: application/json" \
  -d '{
    "candidateName":"Eve Thompson",
    "position":"Backend Engineer",
    "question":"Write a solution to find the longest palindromic substring in a string",
    "response":"I would use dynamic programming with O(n²) time and O(n²) space complexity. Create a 2D table where dp[i][j] represents whether substring from index i to j is a palindrome. Base case: single characters are palindromes. For length > 1, a substring is palindromic if s[i] == s[j] and dp[i+1][j-1] is true. Alternatively, I could use Manacher's algorithm for O(n) time complexity by exploiting palindrome properties and maintaining a center and right boundary. For production use, I would consider the trade-off between code clarity and optimization based on typical input sizes."
  }'
```

