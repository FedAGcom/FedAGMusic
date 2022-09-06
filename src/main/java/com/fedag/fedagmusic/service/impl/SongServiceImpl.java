package com.fedag.fedagmusic.service.impl;

import com.fedag.fedagmusic.entities.Song;
import com.fedag.fedagmusic.repository.SongRepository;
import com.fedag.fedagmusic.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;

    @Override
    @Transactional
    public Mono<Song> createSong(Song song) {
        song.setCreated(LocalDateTime.now());
        return songRepository.save(song);
    }

    @Override
    @Transactional
    public Mono<Song> getSongById(Long songId) {
        return songRepository.findById(songId);
    }

    @Override
    @Transactional
    public Mono<Song> updateSong(Song song, Long id) {
        return songRepository.findById(id)
                .doOnNext(e -> e.setTitle(song.getTitle()))
                .flatMap(songRepository::save);
    }

    @Override
    @Transactional
    public Mono<Void> deleteSongById(Long songId) {
        return songRepository.deleteById(songId);
    }

    @Override
    public Flux<Song> findAll() {
        return songRepository.findAll();
    }

}
