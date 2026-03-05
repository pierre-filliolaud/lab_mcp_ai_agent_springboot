package com.example.agent.tools;

import com.example.agent.mcp.McpHttpClient;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GitHubMcpTools implements AgentTool {

  private final McpHttpClient mcp;

  public GitHubMcpTools(McpHttpClient mcp) {
    this.mcp = mcp;
  }

  @Tool("""
          Create a new GitHub issue in the preconfigured repository.
          The body MUST follow the technical structure:
          - Context
          - Goal
          - Acceptance Criteria
          """)
  public String createGitHubIssue(
          @P("The title of the issue") String title,
          @P("The detailed body of the issue") String body
  ) {
    System.out.println("=== Calling MCP tool: create_issue ===");
    
    // Bridge to MCP JSON-RPC
    return mcp.callTool("create_issue", Map.of(
            "title", title,
            "body", body
    ))
    .map(Object::toString)
    .block(); // LangChain4j tools are usually synchronous
  }
}
