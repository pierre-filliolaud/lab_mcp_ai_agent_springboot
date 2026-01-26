package net.filecode.agent.mcp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class McpHttpClient {
    private final WebClient web;
    private final String path;

    public McpHttpClient(WebClient.Builder builder,
                         @Value("${mcp.base-url}") String baseUrl,
                         @Value("${mcp.path:/mcp}") String path) {
        this.web = builder.baseUrl(baseUrl).build();
        this.path = path;
    }

    public Mono<Map> callTool(String toolName, Map<String, Object> arguments) {
        Map<String, Object> payload = Map.of(
                "jsonrpc", "2.0",
                "id", "1",
                "method", "tools/call",
                "params", Map.of(
                        "name", toolName,
                        "arguments", arguments
                )
        );

        return web.post()
                .uri(path)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .map(resp -> {
                    if (resp.containsKey("error")) {
                        throw new RuntimeException("MCP error: " + resp.get("error"));
                    }
                    return (Map) resp.get("result");
                });
    }

    public Mono<Map> listTools() {
        Map<String, Object> payload = Map.of(
                "jsonrpc", "2.0",
                "id", "1",
                "method", "tools/list",
                "params", Map.of()
        );

        return web.post()
                .uri(path)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .map(resp -> (Map) resp.get("result"));
    }
}
