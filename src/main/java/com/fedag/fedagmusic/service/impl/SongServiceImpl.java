package com.fedag.fedagmusic.service.impl;

import com.fedag.fedagmusic.entities.Song;
import com.fedag.fedagmusic.repository.SongRepository;
import com.fedag.fedagmusic.service.SongService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {
    Logger logger = LoggerFactory.getLogger("Logger");
    private final SongRepository songRepository;

    @Override
    @Transactional
    public Mono<Song> createSong(Song song) {
        logger.info("Выполняется метод createSong");
        song.setCreated(LocalDateTime.now());
        return songRepository.save(song);
    }

    @Override
    @Transactional
    public Mono<Song> getSongById(Long songId) {
        logger.info("Выполняется метод getSongById");
        return songRepository.findById(songId);
    }

    @Override
    @Transactional
    public Mono<Song> updateSong(Song song, Long id) {
        logger.info("Выполняется метод updateSong");
        return songRepository.findById(id)
                .doOnNext(e -> e.setTitle(song.getTitle()))
                .flatMap(songRepository::save);
    }

    @Override
    @Transactional
    public Mono<Void> deleteSongById(Long songId) {
        logger.info("Выполняется метод deleteSongById");
        return songRepository.deleteById(songId);
    }

    @Override
    public Flux<Song> findAll() {
        logger.info("Выполняется метод findAll");
        return songRepository.findAll();
    }

}
