package com.fedag.fedagmusic.testcontainers;

import com.fedag.fedagmusic.FedagmusicApplication;
import com.fedag.fedagmusic.controller.UserController;
import com.fedag.fedagmusic.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static com.fedag.fedagmusic.entities.UserRole.ROLE_USER;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FedagmusicApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@Testcontainers
public class UserControllerTestContainers {

    @Container
    public static PostgreSQLContainer postgreSQLContainer
            = PostgresTestContainer.getInstance();

    @Autowired
    private UserController userController;

    @Test
    public void WhenAddUserExpectHttpStatus() {
        System.out.println(postgreSQLContainer.getExposedPorts());
        User user = User.builder()
                .created(LocalDateTime.now()).email("testController@test.ru")
                .firstName("Alex").lastName("Dear").password("123")
                .role(ROLE_USER).build();

        Mono<ResponseEntity<User>> responseEntityMono = userController.addUser(user);

        StepVerifier
                .create(responseEntityMono)
                .consumeNextWith(usr -> {
                    Assertions.assertEquals(usr.getStatusCode(), HttpStatus.ACCEPTED);
                })
                .verifyComplete();

    }

}



