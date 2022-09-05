package com.fedag.fedagmusic.service.impl;

import com.fedag.fedagmusic.entities.Album;
import reactor.core.publisher.Mono;

public interface AlbumService {
    Mono<Album> getAlbumById(Long id);
    Mono<Album> addAlbum(Album album);
    Mono<Void> deleteAlbumById(Long id);
    Mono<Album> updateAlbum(Album album, Long id);
}
