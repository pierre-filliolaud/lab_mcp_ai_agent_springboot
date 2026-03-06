package com.example.agent;

import dev.langchain4j.model.anthropic.AnthropicChatModel;
import com.example.agent.agent.BacklogAgent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class AgentIntegrationTest {

    @MockBean
    private AnthropicChatModel anthropicChatModel;

    @Autowired
    private BacklogAgent backlogAgent;

    @Test
    void contextLoads() {
        assertThat(backlogAgent).isNotNull();
    }
}
