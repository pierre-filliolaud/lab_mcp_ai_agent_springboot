package com.example.agent.mcp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class McpHttpClientTest {

  private WebClient webClient;
  private WebClient.RequestBodyUriSpec requestBodyUriSpec;
  private WebClient.RequestBodySpec requestBodySpec;
  private WebClient.RequestHeadersSpec requestHeadersSpec;
  private WebClient.ResponseSpec responseSpec;

  private McpHttpClient mcpHttpClient;

  @BeforeEach
  void setUp() {
    webClient = Mockito.mock(WebClient.class);
    WebClient.Builder builder = Mockito.mock(WebClient.Builder.class);
    when(builder.baseUrl(any())).thenReturn(builder);
    when(builder.build()).thenReturn(webClient);

    requestBodyUriSpec = Mockito.mock(WebClient.RequestBodyUriSpec.class);
    requestBodySpec = Mockito.mock(WebClient.RequestBodySpec.class);
    requestHeadersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
    responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

    mcpHttpClient = new McpHttpClient(builder, "http://localhost", "/mcp");
  }

  @Test
  void testListToolsSuccess() {
    Map<String, Object> mockResponse = Map.of(
        "jsonrpc", "2.0",
        "id", "123",
        "result", Map.of("tools", "[]"));

    when(webClient.post()).thenReturn(requestBodyUriSpec);
    when(requestBodyUriSpec.uri(any(String.class))).thenReturn(requestBodySpec);
    when(requestBodySpec.contentType(any())).thenReturn(requestBodySpec);
    when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
    when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
    when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
    when(responseSpec.bodyToMono(Map.class)).thenReturn(Mono.just(mockResponse));

    Mono<Object> result = mcpHttpClient.listTools();

    StepVerifier.create(result)
        .expectNextMatches(res -> ((Map) res).containsKey("tools"))
        .verifyComplete();
  }
}
