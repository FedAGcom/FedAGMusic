package com.fedag.fedagmusic.controller;

import com.fedag.fedagmusic.entities.User;
import com.fedag.fedagmusic.security.JwtUtil;
import com.fedag.fedagmusic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@RestController
@RequiredArgsConstructor

public class AuthController {
    private static final ResponseEntity<Object> UNAUTHORIZED =
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public Mono<ResponseEntity> login(ServerWebExchange serverWebExchange) {
        return serverWebExchange.getFormData().flatMap(credentials ->
                userService.findByUsername(credentials.getFirst("username"))
                        .cast(User.class)
                        .map(userDetails ->
                                isEquals(credentials, userDetails)
                                        ? ResponseEntity.ok(jwtUtil.generateToken(userDetails))
                                        : UNAUTHORIZED
                        )
                        .defaultIfEmpty(UNAUTHORIZED)
        );
    }

    private boolean isEquals(MultiValueMap<String, String> credentials, User userDetails) {
        return passwordEncoder.matches(credentials.getFirst("password"), userDetails.getPassword());
    }


}