package com.fedag.fedagmusic.service.impl;

import com.fedag.fedagmusic.entities.Performer;
import com.fedag.fedagmusic.repository.PerformerRepository;
import com.fedag.fedagmusic.service.impl.PerformerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
public class PerformerServiceTest {

    @InjectMocks
    private PerformerServiceImpl performerService;

    @Mock
    private PerformerRepository performerRepository;

    private Performer performer;

    @BeforeEach
    public void init() {
        performer = Performer.builder()
                .id(24L)
                .name("Michael Jackson")
                .description("The King of Pop and an international superstar")
                .build();
    }

    @Test
    public void createPerformerTest() {

        Mockito.when(performerRepository.save(performer)).thenReturn(Mono.just(performer));
        performerService.createPerformer(performer);
        Mockito.verify(performerRepository, times(1)).save(performer);

    }

    @Test
    public void getPerformerByIdTest() {

        Mockito.when(performerRepository.findById(24L)).thenReturn(Mono.just(performer));
        Mono<Performer> result = performerService.getPerformerById(performer.getId());

        StepVerifier
                .create(result)
                .consumeNextWith(newPerformer -> {
                    assertEquals(newPerformer.getName(), "Michael Jackson");
                })
                .verifyComplete();
    }

    @Test
    public void updatePerformerTest() {

        Performer updatePerformer = new Performer(
                24L, "M.Jackson", "The King of Pop and an international SUPERSTAR");
        Mockito.when(performerRepository.findById(24L)).thenReturn(Mono.just(updatePerformer));
        Mockito.when(performerRepository.save(updatePerformer)).thenReturn(Mono.just(updatePerformer));

        Mono<Performer> result = performerService.updatePerformer(updatePerformer,24L);

        StepVerifier.create(result)
                .expectNext(new Performer(
                        24L, "M.Jackson", "The King of Pop and an international SUPERSTAR"))
                .expectComplete()
                .verify();

        Mockito.verify(performerRepository, times(1)).findById(performer.getId());

    }

    @Test
    public void deletePerformerByIdTest() {
        Mockito.when(performerRepository.deleteById(24L)).thenReturn(Mono.empty());
        Mono<Void> voidMono = performerService.deletePerformerById(performer.getId());
        assertEquals(Mono.empty(), voidMono);
        Mockito.verify(performerRepository, times(1)).deleteById(performer.getId());
    }

}
