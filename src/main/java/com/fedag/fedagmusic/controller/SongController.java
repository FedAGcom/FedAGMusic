package com.fedag.fedagmusic.controller;

import com.fedag.fedagmusic.entities.Song;
import com.fedag.fedagmusic.service.impl.SongServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/songs")
@RequiredArgsConstructor
public class SongController {
    private final SongServiceImpl songServiceImpl;

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Song>> getSongById(@PathVariable Long id) {
        return songServiceImpl.getSongById(id)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }

    @PostMapping
    public Mono<ResponseEntity<Song>> createSong(@RequestBody Song song) {
        return songServiceImpl.createSong(song)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }

    @PutMapping
    public Mono<ResponseEntity<Song>> updateSong(@RequestBody Song song) {
        return songServiceImpl.updateSong(song)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteSongById(@PathVariable Long id) {
        return songServiceImpl.deleteSongById(id)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }

    @GetMapping("/list")
    public Flux<Song> findAllSong() {
        return songServiceImpl.findAll();
    }
}
