package com.fedag.fedagmusic.service.impl;

import com.fedag.fedagmusic.entities.Album;
import com.fedag.fedagmusic.repository.AlbumRepository;
import com.fedag.fedagmusic.service.AlbumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository albumRepository;

    @Override
    public Mono<Album> getAlbumById(Long id) {
        log.info("Выполняется метод getAlbumById");
        return albumRepository.findById(id).log("getAlbumById");
    }

    @Override
    public Mono<Album> addAlbum(Album album) {
        log.info("Выполняется метод addAlbum");
        album.setCreated(LocalDateTime.now());
        return albumRepository.save(album).log("addAlbum");
    }

    @Transactional
    @Override
    public Mono<Void> deleteAlbumById(Long id) {
        log.info("Выполняется метод deleteAlbumById");
        return albumRepository.deleteById(id).log("deleteAlbumById");
    }

    @Override
    public Mono<Album> updateAlbum(Album album, Long id) {
        log.info("Выполняется метод updateAlbum");
        return albumRepository.findById(id).
        map((c) -> {
            c.setTitle(album.getTitle());
            return c;
        }).flatMap(albumRepository::save).log("updateAlbum");
    }

}
