package com.example.agent.mcp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@Component
public class McpHttpClient {

    private final WebClient webClient;
    private final String mcpPath;

    public McpHttpClient(WebClient.Builder webClientBuilder,
            @Value("${mcp.base-url}") String baseUrl,
            @Value("${mcp.path}") String path) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.mcpPath = path;
    }

    public Mono<Object> listTools() {
        Map<String, Object> request = Map.of(
                "jsonrpc", "2.0",
                "method", "tools/list",
                "id", UUID.randomUUID().toString(),
                "params", Map.of());

        return webClient.post()
                .uri(mcpPath)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    if (response.containsKey("error")) {
                        throw new RuntimeException("MCP Error: " + response.get("error"));
                    }
                    return response.get("result");
                });
    }

    public Mono<Object> callTool(String toolName, Map<String, Object> arguments) {
        Map<String, Object> request = Map.of(
                "jsonrpc", "2.0",
                "method", "tools/call",
                "id", UUID.randomUUID().toString(),
                "params", Map.of(
                        "name", toolName,
                        "arguments", arguments));

        return webClient.post()
                .uri(mcpPath)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    if (response.containsKey("error")) {
                        throw new RuntimeException("MCP Tool Error: " + response.get("error"));
                    }
                    return response.get("result");
                });
    }
}
