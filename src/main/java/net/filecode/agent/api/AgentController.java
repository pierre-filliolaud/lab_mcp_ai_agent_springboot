package net.filecode.agent.api;

import net.filecode.agent.BacklogAgent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AgentController {

    private final BacklogAgent agent;

    public AgentController(BacklogAgent agent) {
        this.agent = agent;
    }

    public record RunIn(String prompt) {}

    @PostMapping("/agent/run")
    public String run(@RequestBody RunIn in) {
        return agent.run(in.prompt());
    }
}
