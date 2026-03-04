package com.example.agent.web;

import com.example.agent.service.AgentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AgentControllerIT {

  @Autowired
  WebTestClient web;

  @MockitoBean
  AgentService agentService;

  @Test
  void should_call_endpoint() {
    when(agentService.run(anyString()))
        .thenReturn("ok");

    web.post().uri("/api/run")
        .bodyValue("Create a task to add OpenTelemetry")
        .exchange()
        .expectStatus().isOk();
  }
}