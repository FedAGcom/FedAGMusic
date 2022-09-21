package com.fedag.fedagmusic.controller;


import com.fedag.fedagmusic.entities.User;
import com.fedag.fedagmusic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<User>> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }

    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<User>> addUser(@RequestBody User user) {
        return userService.addUser(user)
                .map(ResponseEntity.accepted()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<User>> updateUserById(@RequestBody User user, @PathVariable Long id) {
        return userService.updateUser(user, id)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Void>> deleteUserById(@PathVariable("id") Long id) {
               return userService.getUserById(id)
                .flatMap(s ->
                        userService.deleteUserById(s.getId())
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));


    }

}
