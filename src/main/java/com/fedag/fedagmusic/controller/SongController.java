package com.fedag.fedagmusic.controller;

import com.fedag.fedagmusic.entities.Song;
import com.fedag.fedagmusic.service.impl.SongServiceImpl;
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
@RequestMapping(API + VERSION + SONG_URL)
@RequiredArgsConstructor
@Tag(name = "Song", description = "Работа с песней")
public class SongController {
    private final SongServiceImpl songServiceImpl;

    @Operation(summary = "Получение песни по ID")
    @ApiResponse(responseCode = "200", description = "Песня найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "400", description = "Ошибка клиента",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "204", description = "Песня не найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @GetMapping(ID)
    public Mono<ResponseEntity<Song>> getSongById(@PathVariable Long id) {
        return songServiceImpl.getSongById(id)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }

    @Operation(summary = "Создание песни по ID")
    @ApiResponse(responseCode = "200", description = "Песня создана",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "400", description = "Ошибка клиента",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "204", description = "Песня не найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @PostMapping
    public Mono<ResponseEntity<Song>> createSong(@RequestBody Song song) {
        return songServiceImpl.createSong(song)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }

    @Operation(summary = "Обновление песни по ID")
    @ApiResponse(responseCode = "200", description = "Песня обновлена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "400", description = "Ошибка клиента",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "404", description = "Песня не найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @PutMapping(ID)
    public Mono<ResponseEntity<Song>> updateSong(@RequestBody Song song, @PathVariable Long id) {
        return songServiceImpl.updateSong(song, id)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @Operation(summary = "Удаление песни по ID")
    @ApiResponse(responseCode = "200", description = "Песня удалена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "400", description = "Ошибка клиента",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "204", description = "Песня не найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @DeleteMapping(ID)
    public Mono<ResponseEntity<Void>> deleteSongById(@PathVariable Long id) {
        return songServiceImpl.getSongById(id)
                .flatMap(s -> songServiceImpl.deleteSongById(s.getId())
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Получение страницы с песнями")
    @ApiResponse(responseCode = "200", description = "Песни получены",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "500", description = "Ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "404", description = "Страница не найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @GetMapping(LIST_URL + PAGE_URL + PAGE_SIZE_URL)
    public Flux<Song> findAllSong(@PathVariable Long page, @PathVariable Long pageSize) {
        return songServiceImpl.findAll()
                .sort(Comparator.comparing(Song::getCreated).reversed())
                .skip(page * pageSize)
                .take(pageSize);
    }
}
