package com.fedag.fedagmusic.service.impl;

import com.fedag.fedagmusic.entities.User;
import com.fedag.fedagmusic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.time.LocalDateTime;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    Logger logger = LoggerFactory.getLogger("Logger");
    private final UserRepository userRepository;

    @Override
    public Mono<User> getUserById(Long id) {
        logger.info("Выполняется метод getUserById");
        return userRepository.findById(id);
    }

    @Override
    public Mono<User> addUser(User user) {
        logger.info("Выполняется метод addUser");
        user.setCreated(LocalDateTime.now());
        logger.info("Объект сохранен");
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
                    c.setPassword(user.getPassword());
                    c.setRole(user.getRole());
                    return c;
                }).flatMap(userRepository::save);
    }

}
