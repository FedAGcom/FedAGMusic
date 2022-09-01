package com.fedag.fedagmusic.service.impl;

import com.fedag.fedagmusic.entities.Song;
import com.fedag.fedagmusic.repository.SongRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class SongServiceImplTest {
    @Mock
    private SongRepository songRepository;

    @InjectMocks
    private SongServiceImpl songServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createSong() {
        Song song = new Song(1L, "qwe", LocalDateTime.now());
        Mockito.when(songRepository.save(song)).thenReturn(Mono.just(song));
        song.setCreated(song.getCreated());
        songServiceImpl.createSong(song);
        verify(songRepository, times(1)).save(song);
    }

    @Test
    void getSongById() {
        Mono<Song> song = Mono.just(new Song(1L, "qwe", LocalDateTime.now()));
        when(songRepository.findById(1L)).thenReturn(song);
        Mono<Song> byId = songServiceImpl.getSongById(1L);
        Assertions.assertEquals(song, byId);
        verify(songRepository, times(1)).findById(1L);
    }

    @Test
    void updateSong() {
        Song song = new Song(1L, "qwe", LocalDateTime.now());
        when(songRepository.findById(1L)).thenReturn(Mono.just(song));
        songServiceImpl.updateSong(song);
        verify(songRepository, times(1)).findById(song.getId());
    }

    @Test
    void deleteSongById() {
        Song song = new Song(1L, "qwe", LocalDateTime.now());
        songServiceImpl.deleteSongById(song.getId());
        verify(songRepository, times(1)).deleteById(song.getId());
    }

    @Test
    void findAll() {
        Song song = new Song(1L, "qwe", LocalDateTime.now());
        Song song1 = new Song(1L, "qwe", LocalDateTime.now());
        songServiceImpl.findAll();
        when(songRepository.findAll()).thenReturn(Flux.just(song, song1));
        Flux<Song> all = songRepository.findAll();
        Assert.assertNotNull(all);

    }
}