package com.fedag.fedagmusic.repository;

import com.fedag.fedagmusic.entities.Performer;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformerRepository extends R2dbcRepository<Performer, Long> {

}
