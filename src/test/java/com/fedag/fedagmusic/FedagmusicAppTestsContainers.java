
package com.fedag.fedagmusic;

import com.fedag.fedagmusic.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static com.fedag.fedagmusic.entities.UserRole.USER;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FedagmusicAppTestsContainers {

    private final static int PORT = 8080;

    @Autowired
    TestRestTemplate restTemplate;

    @Container
    public static GenericContainer<?> fedagmusic = new GenericContainer<>("fedagmusic:latest")
            .withExposedPorts(PORT);

    @Test
    void userAddTest() {
        User user = User.builder()
                .id(1L).created(LocalDateTime.now()).email("test@test.ru")
                .firstName("Alex").lastName("Dear").password("test")
                .role(USER).build();

        Assertions.assertTrue(fedagmusic.isRunning());


        ResponseEntity<String> forEntity = restTemplate.postForEntity(
                "http://localhost:" + fedagmusic.getMappedPort(PORT) + "/api/v1/users", user, String.class);
        System.out.println(forEntity.getBody());
        String expected = "{\"operationId\":" + "\"1\"}";
       String actual = forEntity.getBody();
      //  Assertions.assertEquals(expected, actual);

        Assertions.assertEquals(HttpStatus.OK, forEntity.getStatusCode());
    }
/*
    @Test
    void confirmOperationTest() {
        ConfirmOperation request = new ConfirmOperation("1","0000");

        ResponseEntity<String> forEntity = restTemplate.postForEntity(
                "http://localhost:" + myapp.getMappedPort(PORT) + "/confirmOperation", request, String.class);
        System.out.println(forEntity.getBody());
        String expected = "{\"operationId\":" + "\"1\"}";
        String actual = forEntity.getBody();
        Assertions.assertEquals(expected, actual);
    }*/
}

