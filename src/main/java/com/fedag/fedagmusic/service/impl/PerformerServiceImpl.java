package com.fedag.fedagmusic.service.impl;

import com.fedag.fedagmusic.entities.Performer;
import com.fedag.fedagmusic.repository.PerformerRepository;
import com.fedag.fedagmusic.service.PerformerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class PerformerServiceImpl implements PerformerService {

    private final PerformerRepository performerRepository;

    @Override
    public Mono<Performer> createPerformer(Performer performer) {
        log.info("Выполняется метод createPerformer");
        return performerRepository.save(performer).log("createPerformer");
    }

    @Override
    public Mono<Performer> getPerformerById(Long performerId) {
        log.info("Выполняется метод getPerformerById");
        return performerRepository.findById(performerId).log("getPerformerById");
    }

    @Override
    public Mono<Performer> updatePerformer(Performer performer, Long performerId) {
        log.info("Выполняется метод updatePerformer");
        return performerRepository.findById(performerId)
                .map((newPerformer) -> {
                    newPerformer.setName(performer.getName());
                    return newPerformer;
                }).flatMap(performerRepository::save).log("updatePerformer");
    }

    @Override
    public Mono<Void> deletePerformerById(Long performerId) {
        log.info("Выполняется метод deletePerformerById");
        return performerRepository.deleteById(performerId).log("deletePerformerById");
    }
}
