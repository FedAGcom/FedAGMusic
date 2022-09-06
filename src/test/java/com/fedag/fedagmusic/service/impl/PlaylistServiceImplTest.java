package com.fedag.fedagmusic.service.impl;

import com.fedag.fedagmusic.entities.Playlist;
import com.fedag.fedagmusic.repository.PlaylistRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class PlaylistServiceImplTest {
    @Mock
    private PlaylistRepository playlistRepository;

    @InjectMocks
    private PlaylistServiceImpl playlistServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPlaylist() {
        Playlist playlistMono = new Playlist(1L, "playlist", LocalDateTime.now());
        when(playlistRepository.save(playlistMono)).thenReturn(Mono.just(playlistMono));
        playlistServiceImpl.createPlaylist(playlistMono);
        verify(playlistRepository, times(1)).save(playlistMono);
    }

    @Test
    void getPlaylistById() {
        Playlist playlistMono = new Playlist(1L, "playlist", LocalDateTime.now());
        Mono<Playlist> playlist1 = Mono.just(playlistMono);
        when(playlistRepository.findById(1L)).thenReturn(playlist1);
        Mono<Playlist> playlistById = playlistServiceImpl.getPlaylistById(1L);
        Assertions.assertEquals(playlist1, playlistById);
        StepVerifier.create(playlistById)
                .expectNext(playlistMono)
                .expectComplete()
                .verify();
        verify(playlistRepository, times(1)).findById(1L);
    }

    @Test
    void updatePlaylist() {
        Playlist playlist = new Playlist(1L, "playlist", LocalDateTime.now());
        when(playlistRepository.findById(1L)).thenReturn(Mono.just(playlist));
        when(playlistRepository.save(playlist)).thenReturn(Mono.just(playlist));
        Mono<Playlist> playlistMono = playlistServiceImpl.updatePlaylist(playlist, playlist.getId());
        StepVerifier.create(playlistMono)
                .expectNext(playlist)
                .expectComplete()
                .verify();
        verify(playlistRepository, times(1)).findById(1L);
    }

    @Test
    void deletePlaylistById() {
        when(playlistRepository.deleteById(1L)).thenReturn(Mono.empty());
        Mono<Void> voidMono = playlistServiceImpl.deletePlaylistById(1L);
        Assertions.assertEquals(Mono.empty(), voidMono);
        verify(playlistRepository, times(1)).deleteById(1L);
    }

    @Test
    void findAll() {
        Playlist playlist = new Playlist(1L, "playlist", LocalDateTime.now());
        Playlist playlist1 = new Playlist(1L, "playlist", LocalDateTime.now());
        when(playlistRepository.findAll()).thenReturn(Flux.just(playlist, playlist1));
        playlistServiceImpl.findAll();
        Flux<Playlist> all = playlistRepository.findAll();
        Assertions.assertNotNull(all);

    }
}