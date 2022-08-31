package com.fedag.fedagmusic.service.impl;

import com.fedag.fedagmusic.entities.User;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> getUserById(Long id);
    Mono<User> addUser(User user);
    Mono <Void> deleteUserById(Long id);
    Mono<User> updateUser(User user, Long id);

}
