package com.example.agent.web;

import com.example.agent.domain.User;
import com.example.agent.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User create(@RequestParam String name, @RequestParam String email) {
        return userService.create(name, email);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable String id) {
        return userService.getById(id);
    }
}
