package com.fedag.fedagmusic.repository;

import com.fedag.fedagmusic.entities.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends R2dbcRepository<User, Long> {
    // findByEmail equal find by username
    Mono<User> findByEmail(String email);
}
