package com.fedag.fedagmusic.controller;

import com.fedag.fedagmusic.entities.Album;
import com.fedag.fedagmusic.service.AlbumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static com.fedag.fedagmusic.domain.util.UrlConstants.*;

@RestController
@RequestMapping(API + VERSION + ALBUM_URL)
@RequiredArgsConstructor
@Tag(name = "Album", description = "Работа с альбомом")
public class AlbumController {

    private final AlbumService albumService;

    @Operation(summary = "Получение альбома по ID")
    @ApiResponse(responseCode = "200", description = "Альбом найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "400", description = "Ошибка клиента",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "500", description = "Ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "204", description = "Альбом не найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @GetMapping(ID)
    public Mono<ResponseEntity<Album>> getAlbumById(@PathVariable Long id) {
        return albumService.getAlbumById(id)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }

    @Operation(summary = "Создание альбома")
    @ApiResponse(responseCode = "200", description = "Альбом создан",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "400", description = "Ошибка клиента",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "500", description = "Ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @PostMapping
    public Mono<ResponseEntity<Album>> addAlbum(@RequestBody Album album) {
        return albumService.addAlbum(album)
                .map(ResponseEntity.accepted()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }

    @Operation(summary = "Обновление альбома по ID")
    @ApiResponse(responseCode = "200", description = "Альбом обновлён",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "400", description = "Ошибка клиента",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "500", description = "Ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "404", description = "Альбом не найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @PutMapping(ID)
    public Mono<ResponseEntity<Album>> updateAlbumById(@PathVariable Long id,
                                                       @RequestBody Album album) {
        return albumService.updateAlbum(album, id)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @Operation(summary = "Удаление альбома")
    @ApiResponse(responseCode = "200", description = "Альбом удалён",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "400", description = "Ошибка клиента",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "404", description = "Альбом не найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @DeleteMapping(ID)
    public Mono<ResponseEntity<Void>> deleteAlbumById(@PathVariable Long id) {
        return albumService.getAlbumById(id)
                .flatMap(s ->
                        albumService.deleteAlbumById(s.getId())
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
