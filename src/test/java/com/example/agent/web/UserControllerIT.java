package com.example.agent.web;

import com.example.agent.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIT {

        @LocalServerPort
        private int port;

        @Autowired
        private ApplicationContext applicationContext;

        private WebTestClient webTestClient;

        @BeforeEach
        void setUp() {
                this.webTestClient = WebTestClient
                                .bindToApplicationContext(applicationContext)
                                .configureClient()
                                .baseUrl("http://localhost:" + port)
                                .build();
        }

        @Test
        void shouldCreateAndRetrieveUser() {
                // Create a user
                User created = webTestClient.post()
                                .uri(uriBuilder -> uriBuilder
                                                .path("/api/users")
                                                .queryParam("name", "Alice")
                                                .queryParam("email", "alice@example.com")
                                                .build())
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(User.class)
                                .returnResult()
                                .getResponseBody();

                assertThat(created).isNotNull();
                assertThat(created.id()).isNotBlank();
                assertThat(created.name()).isEqualTo("Alice");
                assertThat(created.email()).isEqualTo("alice@example.com");

                // Retrieve the user by ID
                User retrieved = webTestClient.get()
                                .uri("/api/users/{id}", created.id())
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(User.class)
                                .returnResult()
                                .getResponseBody();

                assertThat(retrieved).isEqualTo(created);
        }
}
