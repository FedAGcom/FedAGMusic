package com.fedag.fedagmusic.repository;

import com.fedag.fedagmusic.entities.Performer;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PerformerRepository extends R2dbcRepository<Performer, Long> {
    @Query("select * from performer per where per.name = :name")
    Mono<Performer> findByName(String name);
}
