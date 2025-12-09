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
