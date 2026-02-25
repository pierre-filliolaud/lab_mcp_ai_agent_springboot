package com.example.agent.config;

//import dev.langchain4j.model.anthropic.AnthropicChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import com.example.agent.BacklogAgent;
import com.example.agent.tools.AgentTool;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

@Configuration
public class LangChainConfig {
  @Bean
  public OpenAiChatModel openAiChatModel(
          @Value("${openai.api-key}") String apiKey,
          @Value("${openai.model}") String model,
          @Value("${openai.timeout-seconds:60}") Integer timeoutSeconds
  ) {
    return OpenAiChatModel.builder()
            .apiKey(apiKey)
            .modelName(model) // gpt-4o-mini
            .timeout(Duration.ofSeconds(timeoutSeconds))
            .build();
  }

  @Bean
  public BacklogAgent backlogAgent(OpenAiChatModel model, List<AgentTool> tools) {

    System.out.println("=== Agent tools loaded: " + tools.size() + " ===");
    tools.forEach(t -> System.out.println(" - " + t.getClass().getName()));

    return AiServices.builder(BacklogAgent.class)
            .chatModel(model)
            .tools(tools.toArray())
            .build();
  }
}