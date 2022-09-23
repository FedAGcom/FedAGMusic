package com.fedag.fedagmusic.repository.impl;

import com.fedag.fedagmusic.entities.Playlist;
import com.fedag.fedagmusic.entities.User;
import io.r2dbc.spi.Row;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class PlaylistRepoImpl {
    private final DatabaseClient databaseClient;
    private static final String SELECT_PLAYLIST = "select play.*, us.email as us_email, " +
            "us.password as us_password, us.\"firstName\" as us_firstName, " +
            "us.\"lastName\" as us_lastName, us.created as us_created " +
            "from playlist play " +
            "join \"users\" us on us.id = play.user_id";

    public Mono<Playlist> findPlaylistById(Long id) {
        return databaseClient.sql(SELECT_PLAYLIST + " where play.id = :id")
                .bind("id", id)
                .map(PlaylistRepoImpl::convertRow).one();
    }

    public Flux<Playlist> findAllPlaylist() {
        return databaseClient.sql(SELECT_PLAYLIST)
                .map(PlaylistRepoImpl::convertRow).all();
    }

    public static Playlist convertRow(Row row) {
        return Playlist.builder()
                .id(row.get("id", Long.class))
                .title(row.get("title", String.class))
                .created(row.get("created", LocalDateTime.class))
                .user(new User(
                        row.get("user_id", Long.class),
                        row.get("us_email", String.class),
                        row.get("us_password", String.class),
                        row.get("us_firstName", String.class),
                        row.get("us_lastName", String.class),
                        row.get("us_created", LocalDateTime.class)))
                .build();
    }
}
