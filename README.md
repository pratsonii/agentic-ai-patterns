# Agentic AI Patterns

A Spring Boot application demonstrating production-ready **Agentic AI patterns** using LangChain4j and Google Gemini. This project showcases how intelligent agents can work together through different orchestration patterns to solve complex problems.

## 🎯 Overview

This project implements multiple agentic AI patterns, each demonstrating different ways agents can collaborate and process information. The patterns are exposed through a unified REST API, making it easy to explore and compare different agentic workflows.

**Current Patterns:**
- **Conditional Routing** - Intelligent query classification and expert delegation
- **Sequential Flow** - Multi-stage pipeline processing with cumulative enhancement

**Coming Soon:** Parallel execution, hierarchical routing, tool-using agents, and more.

## 🏗️ Architecture

The application follows a clean, modular architecture:

```
REST API → Service Layer → Pattern Implementations → AI Agents
```

Each pattern is self-contained and independently configurable, allowing for easy extension and experimentation with new agentic workflows.

## 🚀 Quick Start

### Prerequisites
- Java 21+
- Maven 3.6+
- Google AI API Key ([Get one here](https://ai.google.dev/))

### Setup

1. **Clone and navigate to the project**
   ```bash
   git clone <repository-url>
   cd agentic-ai-patterns
   ```

2. **Configure your API key**
   
   Set the environment variable:
   ```bash
   # Windows (PowerShell)
   $env:GOOGLE_AI_API_KEY="your-api-key"
   
   # Linux/Mac
   export GOOGLE_AI_API_KEY="your-api-key"
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

The API will be available at `http://localhost:8080`

## 📡 API Endpoints
> See `API-TESTING.md` for comprehensive examples

## 🛠️ Technology Stack

- **Spring Boot 4.0.0** - Application framework
- **LangChain4j 1.9.1** - AI orchestration
- **Google Gemini** - Language model
- **Jakarta Validation** - Input validation
- **Maven** - Build management

## 📂 Project Structure

```
src/main/java/com/agent/langchain/
├── patterns/              # Pattern implementations
│   ├── ConditionalRoutingPattern.java
│   └── SequentialFlowPattern.java
├── controller/            # REST endpoints
├── services/              # Business logic
├── dto/                   # Data transfer objects
├── config/                # Configuration
└── exception/             # Error handling
```

## 🎓 Learning Outcomes

This project demonstrates:
- Implementing agentic AI patterns with LangChain4j
- Building production-grade AI applications with Spring Boot
- Designing modular, extensible agent architectures
- Applying software engineering best practices to AI systems

## 🤝 Contributing

This is an evolving demonstration project. New patterns and features will be added regularly to showcase different agentic AI capabilities.

## 📄 License

Educational demonstration project for exploring Agentic AI patterns.

---

**Built to explore the future of Agentic AI** 🚀
