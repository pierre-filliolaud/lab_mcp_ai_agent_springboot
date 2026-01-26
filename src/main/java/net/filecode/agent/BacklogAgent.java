package net.filecode.agent;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public class BacklogAgent {

    @SystemMessage("""
            You are a GitHub backlog agent.
            When the user asks for a task, you MUST create a GitHub issue using the available tools.
            The issue body must include:
            - Context
            - Goal
            - Acceptance Criteria
            Never expose secrets.
            """)

    @UserMessage("User request: {{prompt}}")
    public String run(@V("prompt") String prompt) {
        return null;
    }

}
