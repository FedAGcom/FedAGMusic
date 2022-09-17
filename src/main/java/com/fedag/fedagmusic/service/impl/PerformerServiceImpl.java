package com.fedag.fedagmusic.service.impl;

import com.fedag.fedagmusic.entities.Performer;
import com.fedag.fedagmusic.entities.User;
import com.fedag.fedagmusic.repository.PerformerRepository;
import com.fedag.fedagmusic.repository.impl.UserRepoImpl;
import com.fedag.fedagmusic.service.PerformerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PerformerServiceImpl implements PerformerService {

    private final PerformerRepository performerRepository;
    private final UserRepoImpl userRepo;

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

    @Override
    public Mono<Void> subscribeToPerformer(String name) {
        Performer byName = performerRepository.findByName(name).share().block();
        User select = userRepo.selectUserSubscribedToPerformer(
                3L, Objects.requireNonNull(byName)
                        .getId()).share().block();

        if (select == null) {
            performerRepository.findByName(name)
                    .doOnNext(s -> s.setSubscribersCount(s.getSubscribersCount() + 1))
                    .flatMap(performerRepository::save)
                    .share().block();
            return userRepo.subscribeToPerformer(
                    3L, Objects.requireNonNull(byName).getId());
        }
        return Mono.empty();
    }
}
