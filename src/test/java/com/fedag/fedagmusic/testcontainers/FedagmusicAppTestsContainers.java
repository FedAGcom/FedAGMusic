
package com.fedag.fedagmusic.testcontainers;

import com.fedag.fedagmusic.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.fedag.fedagmusic.entities.UserRole.USER;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FedagmusicAppTestsContainers {

    private final static int PORT = 8080;

    @Autowired
    TestRestTemplate restTemplate;

    @Container
    public static PostgreSQLContainer postgreSQLContainer
            = PostgresTestContainer.getInstance();

    @Container
    public static GenericContainer<?> fedagmusic = new GenericContainer<>("fedagmusic:latest")
            .withExposedPorts(PORT);

    @Test
    void userAddTest() {
     /*   User user = User.builder()
                .email("FedagmusicAppTestsContainers@test.ru")
                .firstName("Alex").lastName("Dear").password("test")
                .role(USER).build();*/

        Assertions.assertTrue(fedagmusic.isRunning());

/*
        ResponseEntity<String> forEntity = restTemplate.getForEntity(
                "http://localhost:" + fedagmusic.getMappedPort(PORT) + "/api/v1/users/1", String.class);
        System.out.println(forEntity.getBody());
    *//*    String expected = "{\"operationId\":" + "\"1\"}";
        String actual = forEntity.getBody();*//*
        //  Assertions.assertEquals(expected, actual);

        Assertions.assertEquals(HttpStatus.ACCEPTED, forEntity.getStatusCode());*/
    }

    @Test
    void swaggerTest() {

        ResponseEntity<String> swagger = restTemplate.getForEntity(
                "http://localhost:" + fedagmusic.getMappedPort(PORT) + "/swagger-ui.html", String.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, swagger.getStatusCode());

//        System.out.println(swagger.getBody());
//        String expected = "{\"operationId\":" + "\"1\"}";
//        String actual = swagger.getBody();
//        Assertions.assertEquals(expected, actual);
    }
}

