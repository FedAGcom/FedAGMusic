package com.fedag.fedagmusic.repository;

import com.fedag.fedagmusic.entities.Song;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SongRepository extends ReactiveCrudRepository<Song, Long> {
}
