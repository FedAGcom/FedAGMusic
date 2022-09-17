package com.fedag.fedagmusic.controller;

import com.fedag.fedagmusic.entities.Song;
import com.fedag.fedagmusic.repository.impl.SongRepoImpl;
import com.fedag.fedagmusic.service.impl.SongServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@RestController
@RequestMapping("api/v1/songs")
@RequiredArgsConstructor
public class SongController {
    private final SongServiceImpl songServiceImpl;
    private final SongRepoImpl songRepo;

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Song>> getSongById(@PathVariable Long id) {
        return songRepo.findSongById(id)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }

    @PostMapping
    public Mono<ResponseEntity<Song>> createSong(@RequestBody Song song) {
        return songServiceImpl.createSong(song)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Song>> updateSong(@RequestBody Song song, @PathVariable Long id) {
        return songServiceImpl.updateSong(song, id)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteSongById(@PathVariable Long id) {
        return songServiceImpl.getSongById(id)
                .flatMap(s -> songServiceImpl.deleteSongById(s.getId())
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/list/{page}/{pageSize}")
    public Flux<Song> findAllSong(@PathVariable Long page, @PathVariable Long pageSize) {
        return songRepo.findAllSong()
                .sort(Comparator.comparing(Song::getCreated).reversed())
                .skip(page * pageSize)
                .take(pageSize);
    }
}
