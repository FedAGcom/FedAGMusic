package com.fedag.fedagmusic.repository;

import com.fedag.fedagmusic.entities.Album;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends R2dbcRepository<Album, Long> {
}
