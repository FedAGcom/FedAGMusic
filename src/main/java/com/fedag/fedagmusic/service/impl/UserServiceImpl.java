package com.fedag.fedagmusic.service.impl;

import com.fedag.fedagmusic.entities.User;
import com.fedag.fedagmusic.repository.UserRepository;
import com.fedag.fedagmusic.repository.impl.UserRepoImpl;
import com.fedag.fedagmusic.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, ReactiveUserDetailsService {
    Logger logger = LoggerFactory.getLogger("Logger");
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepoImpl userRepo;

    @Override
    public Mono<User> getUserById(Long id) {
        logger.info("Выполняется метод getUserById");
        return userRepository.findById(id);
    }

    @Override
    public Mono<User> addUser(User user) {
        logger.info("Выполняется метод addUser");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreated(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public Mono<Void> deleteUserById(Long id) {
        logger.info("Выполняется метод deleteUserById");
        return userRepository.deleteById(id);

    }

    @Override
    public Mono<User> updateUser(User user, Long id) {
        logger.info("Выполняется метод updateUser");
        return userRepository.findById(id).
                map((c) -> {
                    c.setEmail(user.getEmail());
                    c.setLastName(c.getFirstName());
                    c.setFirstName(user.getFirstName());
                    c.setPassword(passwordEncoder.encode(user.getPassword()));
                    c.setRole(user.getRole());
                    return c;
                }).flatMap(userRepository::save);
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByEmail(username)
                .cast(UserDetails.class);
    }

    public Flux<User> findUserByIdWithPerformer(Long id) {
        return userRepo.findUserByIdWithPerformer(id);
    }
}
