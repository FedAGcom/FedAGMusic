package com.fedag.fedagmusic.service.impl;

import com.fedag.fedagmusic.entities.Performer;
import com.fedag.fedagmusic.repository.PerformerRepository;
import com.fedag.fedagmusic.service.PerformerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class PerformerServiceImpl implements PerformerService {
    Logger logger = LoggerFactory.getLogger("Logger");

    private final PerformerRepository performerRepository;

    @Override
    public Mono<Performer> createPerformer(Performer performer) {
        logger.info("Выполняется метод createPerformer");
        return performerRepository.save(performer);
    }

    @Override
    public Mono<Performer> getPerformerById(Long performerId) {
        logger.info("Выполняется метод getPerformerById");
        return performerRepository.findById(performerId);
    }

    @Override
    public Mono<Performer> updatePerformer(Performer performer, Long performerId) {
        logger.info("Выполняется метод updatePerformer");
        return performerRepository.findById(performerId)
                .map((newPerformer) -> {
                    newPerformer.setName(performer.getName());
                    return newPerformer;
                }).flatMap(performerRepository::save);
    }

    @Override
    public Mono<Void> deletePerformerById(Long performerId) {
        logger.info("Выполняется метод deletePerformerById");
        return performerRepository.deleteById(performerId);
    }
}
