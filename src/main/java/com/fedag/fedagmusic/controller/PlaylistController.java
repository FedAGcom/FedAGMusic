package com.fedag.fedagmusic.controller;

import com.fedag.fedagmusic.entities.Playlist;
import com.fedag.fedagmusic.service.impl.PlaylistServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.fedag.fedagmusic.domain.util.UrlConstants.*;

import java.util.Comparator;

@RestController
@RequestMapping(API + VERSION + PLAYLIST_URL)
@RequiredArgsConstructor
@Tag(name = "Playlist", description = "работа с прейлистом")
public class PlaylistController {
    private final PlaylistServiceImpl playlistServiceImpl;

    @Operation(summary = "получение плейлиста по ID")
    @ApiResponse(responseCode = "200", description = "плейлист найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "400", description = "ошибка клиента",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "204", description = "плейлист не найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @GetMapping(ID)
    public Mono<ResponseEntity<Playlist>> getPlaylistById(@PathVariable Long id) {
        return playlistServiceImpl.getPlaylistById(id)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }

    @Operation(summary = "создание плейлиста")
    @ApiResponse(responseCode = "200", description = "плейлист создан",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "400", description = "ошибка клиента",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "500", description = "ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @PostMapping
    public Mono<ResponseEntity<Playlist>> createPlaylist(@RequestBody Playlist playlist) {
        return playlistServiceImpl.createPlaylist(playlist)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }

    @Operation(summary = "обновление плейлиста")
    @ApiResponse(responseCode = "200", description = "плейлист обновлён",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "400", description = "ошибка клиента",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "404", description = "плейлист не найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @PutMapping(ID)
    public Mono<ResponseEntity<Playlist>> updatePlaylist(@RequestBody Playlist playlist,
                                                         @PathVariable Long id) {
        return playlistServiceImpl.updatePlaylist(playlist, id)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @Operation(summary = "удаление плейлиста по ID")
    @ApiResponse(responseCode = "200", description = "плейлист удалён",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "400", description = "ошибка клиента",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "500", description = "ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "404", description = "плейлист не найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @DeleteMapping(ID)
    public Mono<ResponseEntity<Void>> deletePlaylist(@PathVariable Long id) {
        return playlistServiceImpl.getPlaylistById(id)
                .flatMap(s -> playlistServiceImpl.deletePlaylistById(s.getId())
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "получение страницы с плейлистами")
    @ApiResponse(responseCode = "200", description = "плейлисты получены",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "500", description = "ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "404", description = "страница не найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @GetMapping(LIST_URL + PAGE_URL + PAGE_SIZE_URL)
    public Flux<Playlist> findAll(@PathVariable Long page, @PathVariable Long pageSize) {
        return playlistServiceImpl.findAll()
                .sort(Comparator.comparing(Playlist::getCreated).reversed())
                .skip(page * pageSize)
                .take(pageSize);
    }
}
