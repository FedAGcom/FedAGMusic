package com.fedag.fedagmusic.service;

import com.fedag.fedagmusic.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> getUserById(Long id);
    Mono<User> addUser(User user);
    Mono <Void> deleteUserById(Long id);
    Mono<User> updateUser(User user, Long id);
    Mono<UserDetails> findByUsername(String email);
    Flux<User> findUserByIdWithPerformer(Long id);
}
