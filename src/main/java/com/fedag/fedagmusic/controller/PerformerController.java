package com.fedag.fedagmusic.controller;

import com.fedag.fedagmusic.entities.Performer;
import com.fedag.fedagmusic.service.PerformerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/performer")
public class PerformerController {

    private final PerformerService performerService;

    @PostMapping()
    public Mono<ResponseEntity<Performer>> createPerformer(@RequestBody Performer performer) {
        return performerService.createPerformer(performer)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Performer>> getPerformerById(@PathVariable Long id) {
        return performerService.getPerformerById(id)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Performer>> updatePerformer(@RequestBody Performer performer, @PathVariable Long id) {
        return performerService.updatePerformer(performer,id)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deletePerformerById(@PathVariable Long id) {
        return performerService.getPerformerById(id)
                .flatMap(s ->
                        performerService.deletePerformerById(s.getId())
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public Mono<ResponseEntity<Void>> subscribeToPerformer(@RequestParam String name) {
        return performerService.subscribeToPerformer(name)
                .map(ResponseEntity.ok()::body);
    }
}
