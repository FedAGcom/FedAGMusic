package com.fedag.fedagmusic.service;

import com.fedag.fedagmusic.entities.Song;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface SongService {
    Mono<Song> createSong(Song song);

    Mono<Song> getSongById(Long songId);

    Mono<Song> updateSong(Song song);

    Mono<Void> deleteSongById(Long songId);

    Flux<Song> findAll();
}
