package net.filecode.agent.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import net.filecode.agent.mcp.McpHttpClient;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GitHubMcpTools {
    private final McpHttpClient mcp;

    public GitHubMcpTools(McpHttpClient mcp) {
        this.mcp = mcp;
    }

    @Tool("Create a GitHub issue using MCP. Use when the user asks to create a task.")
    public String createIssue(
            @P("Repository owner (user or org)") String owner,
            @P("Repository name") String repo,
            @P("Issue title") String title,
            @P("Issue body in Markdown") String body
    ) {
        Map result = mcp.callTool("github_create_issue", Map.of(
                "owner", owner,
                "repo", repo,
                "title", title,
                "body", body
        )).block();

        return "Issue created successfully: " + result;
    }
}
