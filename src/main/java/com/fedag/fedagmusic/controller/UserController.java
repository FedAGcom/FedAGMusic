package com.fedag.fedagmusic.controller;


import com.fedag.fedagmusic.entities.User;
import com.fedag.fedagmusic.repository.databaseClient.UserDatabaseClient;
import com.fedag.fedagmusic.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.fedag.fedagmusic.domain.util.UrlConstants.*;

@RestController
@RequestMapping(API + VERSION + USER_URL)
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-token-auth")
@Tag(name = "User", description = "Работа с пользователем")
public class UserController {
    private final UserService userService;
    private final UserDatabaseClient userDatabaseClient;

    @Operation(summary = "Получение пользователя по ID")
    @ApiResponse(responseCode = "200", description = "Пользователь найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "400", description = "Ошибка клиента",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "204", description = "Пользователь не найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @GetMapping(ID)
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<User>> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }


    @Operation(summary = "Обновление пользователя по ID")
    @ApiResponse(responseCode = "200", description = "Пользователь обновлён",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "400", description = "Ошибка клиента",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "404", description = "Пользователь не найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @PutMapping(ID)
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<User>> updateUserById(@RequestBody User user, @PathVariable Long id) {
        return userService.updateUser(user, id)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @Operation(summary = "Удаление пользователя по ID")
    @ApiResponse(responseCode = "200", description = "Пользователь удалён",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "400", description = "Ошибка клиента",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "404", description = "Пользователь не найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @DeleteMapping(ID)
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Void>> deleteUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id)
                .flatMap(s ->
                        userService.deleteUserById(s.getId())
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/withPerformer/{id}")
    public Flux<User> findUserByIdWithPerformer(@PathVariable Long id) {
        return userDatabaseClient.findUserByIdWithPerformer(id);
    }

}
