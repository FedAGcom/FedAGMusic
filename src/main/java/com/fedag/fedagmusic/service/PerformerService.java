package com.fedag.fedagmusic.service;

import com.fedag.fedagmusic.entities.Performer;
import reactor.core.publisher.Mono;

public interface PerformerService {

    Mono<Performer> createPerformer(Performer performer);

    Mono<Performer> getPerformerById(Long performerId);

    Mono<Performer> updatePerformer(Performer performer,Long performerId);

    Mono<Void> deletePerformerById(Long performerId);

}
