package com.fedag.fedagmusic.job;

import com.fedag.fedagmusic.repository.PerformerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static com.fedag.fedagmusic.config.SwaggerConfig.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriberCountPerformer {
    private final PerformerRepository performerRepository;

    @Scheduled(cron = EVERY_DAY_AT_1_AM)
    public void subscriberCount() {
        Long count = performerRepository.countOfAllSubscribers().share().block();
        log.debug("total number of subscribers of performers = " + count);
    }
}
