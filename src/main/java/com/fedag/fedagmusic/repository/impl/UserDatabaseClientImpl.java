package com.fedag.fedagmusic.repository.impl;

import com.fedag.fedagmusic.entities.Performer;
import com.fedag.fedagmusic.entities.User;
import com.fedag.fedagmusic.repository.databaseClient.UserDatabaseClient;
import io.r2dbc.spi.Row;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserDatabaseClientImpl implements UserDatabaseClient {
    private final DatabaseClient databaseClient;

    @Override
    public Flux<User> findUserByIdWithPerformer(Long id) {
        return databaseClient.sql("select *, p.id as p_id, u.id as u_id " +
                        "from users_performers " +
                        "join performer p on p.id = users_performers.performers_id " +
                        "join \"users\" u on u.id = users_performers.user_id " +
                        "where u.id = :id")
                .bind("id", id)
                .fetch().all()
                .bufferUntilChanged(s -> s.get("u_id"))
                .map(list -> User.builder()
                        .id((Long) list.get(0).get("u_id"))
                        .email(String.valueOf(list.get(0).get("email")))
                        .password(String.valueOf(list.get(0).get("password")))
                        .firstName(String.valueOf(list.get(0).get("firstName")))
                        .lastName(String.valueOf(list.get(0).get("lastName")))
                        .created((LocalDateTime) list.get(0).get("created"))
                        .performer(list.stream()
                                .map(map -> Performer.builder()
                                        .id((Long) map.get("p_id"))
                                        .name(String.valueOf(map.get("name")))
                                        .description(String.valueOf(map.get("description")))
                                        .subscribersCount((Integer) map.get("subscribers_count"))
                                        .build())
                                .collect(Collectors.toList())).build());
    }

    @Override
    public Mono<User> selectUserSubscribedToPerformer(Long user_id, Long performer_id) {
        return databaseClient.sql("select *, p.id as p_id, u.id as u_id " +
                        "from users_performers " +
                        "join performer p on p.id = users_performers.performers_id " +
                        "join \"users\" u on u.id = users_performers.user_id " +
                        "where u.id = :user_id and p.id = :performer_id")
                .bind("user_id", user_id)
                .bind("performer_id", performer_id)
                .map(UserDatabaseClientImpl::convertRow).one();
    }

    public static User convertRow(Row row) {
        return User.builder()
                .id(row.get("u_id", Long.class))
                .performer(Collections.singletonList(new Performer(
                        row.get("p_id", Long.class)
                )))
                .build();
    }

    @Override
    public Mono<Void> subscribeToPerformer(Long user_id, Long performer_id) {
        return databaseClient.sql("insert into users_performers VALUES (:user_id, :performer_id)")
                .bind("user_id", user_id)
                .bind("performer_id", performer_id)
                .then();
    }
}
