package com.fedag.fedagmusic.controller;

import com.fedag.fedagmusic.entities.Playlist;
import com.fedag.fedagmusic.repository.databaseClient.PlaylistDatabaseClient;
import com.fedag.fedagmusic.service.PlaylistService;
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

@RestController
@RequestMapping(API + VERSION + PLAYLIST_URL)
@RequiredArgsConstructor
@Tag(name = "Playlist", description = "Работа с плейлистом")
public class PlaylistController {
    private final PlaylistService playlistService;
    private final PlaylistDatabaseClient playlistDatabaseClient;

    @Operation(summary = "Получение плейлиста по ID")
    @ApiResponse(responseCode = "200", description = "Плейлист найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "400", description = "Ошибка клиента",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "204", description = "Плейлист не найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @GetMapping(ID)
    public Mono<ResponseEntity<Playlist>> getPlaylistById(@PathVariable Long id) {
        return playlistDatabaseClient.findPlaylistById(id)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }

    @Operation(summary = "Создание плейлиста")
    @ApiResponse(responseCode = "200", description = "Плейлист создан",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "400", description = "Ошибка клиента",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "500", description = "Ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @PostMapping
    public Mono<ResponseEntity<Playlist>> createPlaylist(@RequestBody Playlist playlist) {
        return playlistService.createPlaylist(playlist)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }

    @Operation(summary = "Обновление плейлиста по ID")
    @ApiResponse(responseCode = "200", description = "Плейлист обновлён",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "400", description = "Ошибка клиента",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "404", description = "Плейлист не найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @PutMapping(ID)
    public Mono<ResponseEntity<Playlist>> updatePlaylist(@RequestBody Playlist playlist,
                                                         @PathVariable Long id) {
        return playlistService.updatePlaylist(playlist, id)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @Operation(summary = "Удаление плейлиста по ID")
    @ApiResponse(responseCode = "200", description = "Плейлист удалён",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "400", description = "Ошибка клиента",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "500", description = "Ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "404", description = "Плейлист не найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @DeleteMapping(ID)
    public Mono<ResponseEntity<Void>> deletePlaylist(@PathVariable Long id) {
        return playlistService.getPlaylistById(id)
                .flatMap(s -> playlistService.deletePlaylistById(s.getId())
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Получение страницы с плейлистами")
    @ApiResponse(responseCode = "200", description = "Плейлисты получены",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "500", description = "Ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "404", description = "Страница не найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @GetMapping(LIST_URL + PAGE_URL + PAGE_SIZE_URL)
    public Flux<Playlist> findAll(@PathVariable Long page, @PathVariable Long pageSize) {
        return playlistDatabaseClient.findAllPlaylist()
                .skip(page * pageSize)
                .take(pageSize);
    }
}
