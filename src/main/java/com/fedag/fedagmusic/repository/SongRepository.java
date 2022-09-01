package com.fedag.fedagmusic.repository;

import com.fedag.fedagmusic.entities.Song;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends R2dbcRepository<Song, Long> {
}
