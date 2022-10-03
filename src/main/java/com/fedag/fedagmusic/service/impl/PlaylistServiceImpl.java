package com.fedag.fedagmusic.service.impl;

import com.fedag.fedagmusic.entities.Playlist;
import com.fedag.fedagmusic.repository.PlaylistRepository;
import com.fedag.fedagmusic.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {
    private final PlaylistRepository playlistRepository;

    @Override
    @Transactional
    public Mono<Playlist> createPlaylist(Playlist playlist) {
        log.info("Выполняется метод createPlaylist");
        playlist.setCreated(LocalDateTime.now());
        return playlistRepository.save(playlist).log("createPlaylist");
    }

    @Override
    @Transactional
    public Mono<Playlist> getPlaylistById(Long playlistId) {
        log.info("Выполняется метод getPlaylistById");
        return playlistRepository.findById(playlistId).log("getPlaylistById");
    }

    @Override
    @Transactional
    public Mono<Playlist> updatePlaylist(Playlist playlist, Long id) {
        log.info("Выполняется метод updatePlaylist");
        return playlistRepository.findById(id)
                .doOnNext(s -> s.setTitle(playlist.getTitle()))
                .flatMap(playlistRepository::save).log("updatePlaylist");
    }

    @Override
    @Transactional
    public Mono<Void> deletePlaylistById(Long playlistId) {
        log.info("Выполняется метод deletePlaylistById");
        return playlistRepository.deleteById(playlistId).log("deletePlaylistById");
    }

    @Override
    public Flux<Playlist> findAll() {
        log.info("Выполняется метод findAll");
        return playlistRepository.findAll().log("findAll");
    }
}
