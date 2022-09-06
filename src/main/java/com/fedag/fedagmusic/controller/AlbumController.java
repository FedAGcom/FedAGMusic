package com.fedag.fedagmusic.controller;

import com.fedag.fedagmusic.entities.Album;
import com.fedag.fedagmusic.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping(value = "/{id}")
    public Mono<ResponseEntity<Album>> getAlbumById(@PathVariable Long id) {
        return albumService.getAlbumById(id)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }
    @PostMapping
    public Mono<ResponseEntity<Album>> addAlbum(@RequestBody Album album) {
            return albumService.addAlbum(album)
                    .map(ResponseEntity.accepted()::body)
                    .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
        }

   @PutMapping(value = "/{id}")
    public Mono<ResponseEntity<Album>> updateAlbumById(@PathVariable Long id,
                                                       @RequestBody Album album) {
     return albumService.updateAlbum(album, id)
              .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
                }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteAlbumById(@PathVariable Long id) {
        return albumService.getAlbumById(id)
                .flatMap(s ->
                        albumService.deleteAlbumById(s.getId())
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
