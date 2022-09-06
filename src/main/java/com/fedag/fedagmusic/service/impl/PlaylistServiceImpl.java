package com.fedag.fedagmusic.service.impl;

import com.fedag.fedagmusic.entities.Playlist;
import com.fedag.fedagmusic.repository.PlaylistRepository;
import com.fedag.fedagmusic.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {
    private final PlaylistRepository playlistRepository;

    @Override
    @Transactional
    public Mono<Playlist> createPlaylist(Playlist playlist) {
        playlist.setCreated(LocalDateTime.now());
        return playlistRepository.save(playlist);
    }

    @Override
    @Transactional
    public Mono<Playlist> getPlaylistById(Long playlistId) {
        return playlistRepository.findById(playlistId);
    }

    @Override
    @Transactional
    public Mono<Playlist> updatePlaylist(Playlist playlist, Long id) {
        return playlistRepository.findById(id)
                .doOnNext(s -> s.setTitle(playlist.getTitle()))
                .flatMap(playlistRepository::save);
    }

    @Override
    @Transactional
    public Mono<Void> deletePlaylistById(Long playlistId) {
        return playlistRepository.deleteById(playlistId);
    }

    @Override
    public Flux<Playlist> findAll() {
        return playlistRepository.findAll();
    }
}
