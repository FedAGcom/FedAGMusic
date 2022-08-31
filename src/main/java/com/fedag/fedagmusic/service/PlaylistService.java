package com.fedag.fedagmusic.service;

import com.fedag.fedagmusic.entities.Playlist;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlaylistService {
    Mono<Playlist> createPlaylist(Playlist playlist);

    Mono<Playlist> getPlaylistById(Long playlistId);

    Mono<Playlist> updatePlaylist(Playlist playlist, Long id);

    Mono<Void> deletePlaylistById(Long playlistId);

    Flux<Playlist> findAll();
}
