package com.fedag.fedagmusic.repository.impl;

import com.fedag.fedagmusic.entities.Album;
import com.fedag.fedagmusic.entities.Performer;
import com.fedag.fedagmusic.entities.Song;
import io.r2dbc.spi.Row;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class SongRepoImpl {
    private final DatabaseClient databaseClient;
    private static final String SELECT_SONG = "select son.*, per.name as p_name, per.description " +
            "as p_des, alb.title as a_title, alb.created as a_created from song son " +
            "left join performer per on per.id = son.performer_id " +
            "left join album alb on alb.id = son.album_id";

    public Mono<Song> findSongById(Long id) {
        return databaseClient.sql(SELECT_SONG + " where son.id = :id")
                .bind("id", id)
                .map(SongRepoImpl::convertRow).one();
    }

    public Flux<Song> findAllSong() {
        return databaseClient.sql(SELECT_SONG)
                .map(SongRepoImpl::convertRow).all();
    }

    public static Song convertRow(Row row) {
        return Song.builder()
                .id(row.get("id", Long.class))
                .title(row.get("title", String.class))
                .created(row.get("created", LocalDateTime.class))
                .performer(new Performer(
                        row.get("performer_id", Long.class),
                        row.get("p_name", String.class),
                        row.get("p_des", String.class)))
                .album(new Album(
                        row.get("album_id", Long.class),
                        row.get("a_title", String.class),
                        row.get("a_created", LocalDateTime.class)
                )).build();
    }
}
