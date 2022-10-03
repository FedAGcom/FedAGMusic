package com.fedag.fedagmusic.service.impl;

import com.fedag.fedagmusic.entities.Song;
import com.fedag.fedagmusic.repository.SongRepository;
import com.fedag.fedagmusic.service.SongService;
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
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;

    @Override
    @Transactional
    public Mono<Song> createSong(Song song) {
        log.info("Выполняется метод createSong");
        song.setCreated(LocalDateTime.now());
        return songRepository.save(song).log("createSong");
    }

    @Override
    @Transactional
    public Mono<Song> getSongById(Long songId) {
        log.info("Выполняется метод getSongById");
        return songRepository.findById(songId).log("getSongById");
    }

    @Override
    @Transactional
    public Mono<Song> updateSong(Song song, Long id) {
        log.info("Выполняется метод updateSong");
        return songRepository.findById(id)
                .doOnNext(e -> e.setTitle(song.getTitle()))
                .flatMap(songRepository::save).log("updateSong");
    }

    @Override
    @Transactional
    public Mono<Void> deleteSongById(Long songId) {
        log.info("Выполняется метод deleteSongById");
        return songRepository.deleteById(songId).log("deleteSongById");
    }

    @Override
    public Flux<Song> findAll() {
        log.info("Выполняется метод findAll");
        return songRepository.findAll().log("findAll");
    }

}
