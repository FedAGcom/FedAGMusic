package com.fedag.fedagmusic.repository.databaseClient;

import com.fedag.fedagmusic.entities.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserDatabaseClient {
    Flux<User> findUserByIdWithPerformer(Long id);

    Mono<User> selectUserSubscribedToPerformer(Long user_id, Long performer_id);

    Mono<Void> subscribeToPerformer(Long user_id, Long performer_id);
}
