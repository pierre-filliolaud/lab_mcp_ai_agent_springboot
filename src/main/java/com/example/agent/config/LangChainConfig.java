import dev.langchain4j.model.google.gemini.GoogleGeminiChatModel;
import dev.langchain4j.service.AiServices;
import com.example.agent.BacklogAgent;
import com.example.agent.tools.AgentTool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

@Configuration
public class LangChainConfig {
  @Bean
  public GoogleGeminiChatModel googleGeminiChatModel(
          @Value("${gemini.api-key}") String apiKey,
          @Value("${gemini.model}") String model,
          @Value("${gemini.timeout-seconds:60}") Integer timeoutSeconds
  ) {
    return GoogleGeminiChatModel.builder()
            .apiKey(apiKey)
            .modelName(model)
            .timeout(Duration.ofSeconds(timeoutSeconds))
            .build();
  }

  @Bean
  public BacklogAgent backlogAgent(GoogleGeminiChatModel model, List<AgentTool> tools) {

    System.out.println("=== Agent tools loaded: " + tools.size() + " ===");
    tools.forEach(t -> System.out.println(" - " + t.getClass().getName()));

    return AiServices.builder(BacklogAgent.class)
            .chatModel(model)
            .tools(tools.toArray())
            .build();
  }
}