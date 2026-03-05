package com.example.agent.web;

import com.example.agent.agent.BacklogAgent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/agent")
public class AgentController {

  private final BacklogAgent agent;

  public AgentController(BacklogAgent agent) {
    this.agent = agent;
  }

  @PostMapping("/work")
  public Map<String, String> work(@RequestBody Map<String, String> request) {
    String prompt = request.get("prompt");
    if (prompt == null || prompt.isBlank()) {
      throw new IllegalArgumentException("Prompt is required");
    }

    String response = agent.handle(prompt);
    return Map.of("response", response);
  }
}
