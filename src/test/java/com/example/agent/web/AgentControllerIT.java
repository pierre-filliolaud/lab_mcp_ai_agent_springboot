package com.example.agent.web;

import com.example.agent.mcp.McpHttpClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AgentControllerIT {

    @Autowired
    WebTestClient web;

    @MockBean
    McpHttpClient mcp;

    @MockBean
    com.example.agent.agent.BacklogAgent backlogAgent;

    @Test
    void should_call_endpoint() {
        when(backlogAgent.handle(anyString())).thenReturn("mocked agent response");

        web.post().uri("/api/run")
                .bodyValue("Create a task to add OpenTelemetry")
                .exchange()
                .expectStatus().isOk();
    }
}
