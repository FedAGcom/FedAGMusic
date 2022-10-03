package com.fedag.fedagmusic.service.impl;

import com.fedag.fedagmusic.entities.User;
import com.fedag.fedagmusic.repository.UserRepository;
import com.fedag.fedagmusic.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;



@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, ReactiveUserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<User> getUserById(Long id) {
        log.info("Выполняется метод getUserById");
        return userRepository.findById(id).log("getUserById");
    }

    @Override
    public Mono<User> addUser(User user) {
        log.info("Выполняется метод addUser");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreated(LocalDateTime.now());
        return userRepository.save(user).log("addUser");
    }

    @Transactional
    @Override
    public Mono<Void> deleteUserById(Long id) {
        log.info("Выполняется метод deleteUserById");
        return userRepository.deleteById(id).log("deleteUserById");

    }

    @Override
    public Mono<User> updateUser(User user, Long id) {
        log.info("Выполняется метод updateUser");
        return userRepository.findById(id).
                map((c) -> {
                    c.setEmail(user.getEmail());
                    c.setLastName(c.getFirstName());
                    c.setFirstName(user.getFirstName());
                    c.setPassword(passwordEncoder.encode(user.getPassword()));
                    c.setRole(user.getRole());
                    return c;
                }).flatMap(userRepository::save).log("updateUser");
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        log.info("Выполняется метод findByUsername");
        return userRepository.findByEmail(username)
                .cast(UserDetails.class).log("findByUsername");
    }

}
