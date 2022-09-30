package com.fedag.fedagmusic.repository.databaseClient;

import com.fedag.fedagmusic.entities.Song;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SongDatabaseClient {
    Mono<Song> findSongById(Long id);

    Flux<Song> findAllSong();
}
