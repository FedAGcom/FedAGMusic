package com.fedag.fedagmusic;

import com.fedag.fedagmusic.entities.Album;
import com.fedag.fedagmusic.repository.AlbumRepository;
import com.fedag.fedagmusic.service.impl.AlbumServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
public class AlbumServiceTest {
    @InjectMocks
    private AlbumServiceImpl albumService;

    @Mock
    private AlbumRepository albumRepository;

    private Album album;

    @BeforeEach
    public void init() {
        album = Album.builder()
                .id(1L).created(LocalDateTime.now()).title("Album")
                .build();
    }

    @Test
    public void getUserByIdTest(){
        Mono<Album> albums = Mono.just(album);
        when(albumRepository.findById(1L)).thenReturn(albums);
        Mono<Album> albumTwo = albumService.getAlbumById(1L);
        Assertions.assertEquals(albums, albumTwo);
        StepVerifier.create(albumTwo)
                        .expectNext(new Album(1L, "Album", album.getCreated()))
                                .expectComplete()
                                        .verify();
        verify(albumRepository, times(1)).findById(1L);
    }

    @Test
    public void deleteUserByIdTest(){
        Mockito.when(albumRepository.deleteById(1L)).thenReturn(Mono.empty());
        Mono<Void> voidMono = albumService.deleteAlbumById(album.getId());
        assertEquals(Mono.empty(), voidMono);
        verify(albumRepository, times(1)).deleteById(album.getId());
    }

    @Test
    public void createUserTest(){
        Mockito.when(albumRepository.save(album)).thenReturn(Mono.just(album));
        albumService.addAlbum(album);
        Mockito.verify(albumRepository, times(1)).save(album);
    }

    @Test
    public void updateUserTest(){
        Album newAlbum = new Album(1L, "333@qwer.ru", LocalDateTime.now());
        Mockito.when(albumRepository.findById(1L)).thenReturn(Mono.just(newAlbum));
        Mockito.when(albumRepository.save(newAlbum)).thenReturn(Mono.just(newAlbum));
        Mono<Album> albumMono = albumService.updateAlbum(newAlbum, 1L);
        StepVerifier.create(albumMono)
                        .expectNext(new Album(1L, "333@qwer.ru", newAlbum.getCreated()))
                         .expectComplete()
                          .verify();

        Mockito.verify(albumRepository, times(1)).findById(album.getId());
    }
}
