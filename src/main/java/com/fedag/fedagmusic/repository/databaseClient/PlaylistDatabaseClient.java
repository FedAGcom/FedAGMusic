package com.fedag.fedagmusic.repository.databaseClient;

import com.fedag.fedagmusic.entities.Playlist;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlaylistDatabaseClient {
    Mono<Playlist> findPlaylistById(Long id);

    Flux<Playlist> findAllPlaylist();
}
