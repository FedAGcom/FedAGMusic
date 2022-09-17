package com.fedag.fedagmusic.service.impl;

import com.fedag.fedagmusic.entities.Album;
import com.fedag.fedagmusic.repository.AlbumRepository;
import com.fedag.fedagmusic.service.AlbumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {
    private static final Logger logger = LoggerFactory.getLogger("Logger");
    private final AlbumRepository albumRepository;

    @Override
    public Mono<Album> getAlbumById(Long id) {
        logger.info("Выполняется метод getAlbumById");
        return albumRepository.findById(id);
    }

    @Override
    public Mono<Album> addAlbum(Album album) {
        logger.info("Выполняется метод addAlbum");
        album.setCreated(LocalDateTime.now());
        return albumRepository.save(album);
    }

    @Transactional
    @Override
    public Mono<Void> deleteAlbumById(Long id) {
        logger.info("Выполняется метод deleteAlbumById");
        return albumRepository.deleteById(id);
    }

    @Override
    public Mono<Album> updateAlbum(Album album, Long id) {
        logger.info("Выполняется метод updateAlbum");
        return albumRepository.findById(id).
        map((c) -> {
            c.setTitle(album.getTitle());
            return c;
        }).flatMap(albumRepository::save);
    }

}
