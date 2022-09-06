package com.fedag.fedagmusic.service.impl;

import com.fedag.fedagmusic.entities.Performer;
import com.fedag.fedagmusic.repository.PerformerRepository;
import com.fedag.fedagmusic.service.PerformerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PerformerServiceImpl implements PerformerService {

    private final PerformerRepository performerRepository;

    @Override
    public Mono<Performer> createPerformer(Performer performer) {
        return performerRepository.save(performer);
    }

    @Override
    public Mono<Performer> getPerformerById(Long performerId) {
        return performerRepository.findById(performerId);
    }

    @Override
    public Mono<Performer> updatePerformer(Performer performer, Long performerId) {
        return performerRepository.findById(performerId)
                .map((newPerformer) -> {
                    newPerformer.setName(performer.getName());
                    return newPerformer;
                }).flatMap(performerRepository::save);
    }

    @Override
    public Mono<Void> deletePerformerById(Long performerId) {
        return performerRepository.deleteById(performerId);
    }
}
