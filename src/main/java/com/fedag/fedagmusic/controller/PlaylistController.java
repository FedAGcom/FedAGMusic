package com.fedag.fedagmusic.controller;

import com.fedag.fedagmusic.entities.Playlist;
import com.fedag.fedagmusic.service.impl.PlaylistServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@RestController
@RequestMapping("api/v1/playlists")
@RequiredArgsConstructor
public class PlaylistController {
    private final PlaylistServiceImpl playlistServiceImpl;

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Playlist>> getPlaylistById(@PathVariable Long id) {
        return playlistServiceImpl.getPlaylistById(id)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }

    @PostMapping
    public Mono<ResponseEntity<Playlist>> createPlaylist(@RequestBody Playlist playlist) {
        return playlistServiceImpl.createPlaylist(playlist)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Playlist>> updatePlaylist(@RequestBody Playlist playlist,
                                                         @PathVariable Long id) {
        return playlistServiceImpl.updatePlaylist(playlist, id)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deletePlaylist(@PathVariable Long id) {
        return playlistServiceImpl.getPlaylistById(id)
                .flatMap(s -> playlistServiceImpl.deletePlaylistById(s.getId())
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/list/{page}/{pageSize}")
    public Flux<Playlist> findAll(@PathVariable Long page, @PathVariable Long pageSize) {
        return playlistServiceImpl.findAll()
                .sort(Comparator.comparing(Playlist::getCreated).reversed())
                .skip(page * pageSize)
                .take(pageSize);
    }
}
