
package com.fedag.fedagmusic.testcontainers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FedagmusicAppTestsContainers {

    private final static int PORT = 8080;
    @Autowired
    private WebTestClient webClient;

    @Container
    public static PostgreSQLContainer postgreSQLContainer
            = PostgresTestContainer.getInstance();

    @Container
    public static GenericContainer<?> fedagmusic = new GenericContainer<>("fedagmusic:latest")
            .withExposedPorts(PORT).dependsOn(postgreSQLContainer);

    @Test
    void userCreateTest() {
        String getURI = fedagmusic.getHost() + ":" + PORT;
        System.out.println(getURI);

        String body = "{\n" +
                "\"email\": \"FedagmusicAppTestsContainers@test.ru\",\n" +
                "\"lastName\": \"Dear\",\n" +
                "\"password\": \"test\",\n" +
                "\"firstName\": \"Alex\",\n" +
                "\"role\": \"ROLE_USER\"\n" +
                "}";

        webClient.post().uri(getURI + "/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body))
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    void swaggerTest() {
        String getURI = fedagmusic.getHost() + ":" + PORT;
        System.out.println(getURI);

        webClient.get()
                .uri(getURI + "/swagger-ui.html")
                .exchange()
                .expectStatus().is4xxClientError();
    }
}