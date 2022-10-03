package com.fedag.fedagmusic.service.impl;

import com.fedag.fedagmusic.entities.Performer;
import com.fedag.fedagmusic.entities.User;
import com.fedag.fedagmusic.repository.PerformerRepository;
import com.fedag.fedagmusic.repository.databaseClient.UserDatabaseClient;
import com.fedag.fedagmusic.security.JwtUtil;
import com.fedag.fedagmusic.service.PerformerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PerformerServiceImpl implements PerformerService {

    private final PerformerRepository performerRepository;
    private final UserDatabaseClient userDatabaseClient;
    private final JwtUtil jwtUtil;

    @Override
    public Mono<Performer> createPerformer(Performer performer) {
        return performerRepository.findByName(performer.getName())
                .switchIfEmpty(Mono.defer(() -> performerRepository.save(performer)
                        .then(Mono.empty())));
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
        User authUser = jwtUtil.getAuthUser();
        Performer byName = performerRepository.findByName(name).share().block();
        User select = userDatabaseClient.selectUserSubscribedToPerformer(
                authUser.getId(), Objects.requireNonNull(byName)
                        .getId()).share().block();

        if (select == null) {
            performerRepository.findByName(name)
                    .doOnNext(s -> s.setSubscribersCount(s.getSubscribersCount() + 1))
                    .flatMap(performerRepository::save)
                    .share().block();
            return userDatabaseClient.subscribeToPerformer(
                    authUser.getId(), Objects.requireNonNull(byName).getId());
        }
        return Mono.empty();
    }
}
