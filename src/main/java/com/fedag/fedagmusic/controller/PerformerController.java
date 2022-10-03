package com.fedag.fedagmusic.controller;

import com.fedag.fedagmusic.entities.Performer;
import com.fedag.fedagmusic.service.PerformerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static com.fedag.fedagmusic.domain.util.UrlConstants.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(API + VERSION + PERFORMER_URL)
@Tag(name = "Performer", description = "Работа с исполнителем")
public class PerformerController {

    private final PerformerService performerService;

    @Operation(summary = "Создание исполнителя")
    @ApiResponse(responseCode = "200", description = "Исполнитель создан",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "400", description = "Ошибка клиента",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "500", description = "Ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @PostMapping()
//    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Performer>> createPerformer(@RequestBody Performer performer) {
        return performerService.createPerformer(performer)
                .map(ResponseEntity.status(HttpStatus.NO_CONTENT)::body)
                .switchIfEmpty(Mono.just(ResponseEntity.ok().build()));
    }


    @Operation(summary = "Получение исполнителя по ID")
    @ApiResponse(responseCode = "200", description = "Исполнитель найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "400", description = "Ошибка клиента",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "204", description = "Исполнитель не найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @GetMapping(ID)
   // @PreAuthorize("hasRole('USER')")
    public Mono<ResponseEntity<Performer>> getPerformerById(@PathVariable Long id) {
        return performerService.getPerformerById(id)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }


    @Operation(summary = "Обновление исполнителя по ID")
    @ApiResponse(responseCode = "200", description = "Исполнитель обновлён",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "400", description = "Ошибка клиента",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "404", description = "Исполнитель не найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @PutMapping(ID)
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Performer>> updatePerformer(@RequestBody Performer performer, @PathVariable Long id) {
        return performerService.updatePerformer(performer, id)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @Operation(summary = "Удаление исполнителя по ID")
    @ApiResponse(responseCode = "200", description = "Исполнитель найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "400", description = "Ошибка клиента",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "404", description = "Исполнитель не найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @DeleteMapping(ID)
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Void>> deletePerformerById(@PathVariable Long id) {
        return performerService.getPerformerById(id)
                .flatMap(s ->
                        performerService.deletePerformerById(s.getId())
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("subscribe/{name}")
    public Mono<ResponseEntity<Void>> subscribeToPerformer(@PathVariable String name) {
        return performerService.subscribeToPerformer(name)
                .map(ResponseEntity.ok()::body);
    }
}
