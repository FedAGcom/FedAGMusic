package com.fedag.fedagmusic.service.impl;

import com.fedag.fedagmusic.entities.User;
import com.fedag.fedagmusic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.time.LocalDateTime;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Mono<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Mono<User> addUser(User user) {
        user.setCreated(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public Mono<Void> deleteUserById(Long id) {
        return userRepository.deleteById(id);

    }

    @Override
    public Mono<User> updateUser(User user, Long id) {
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
