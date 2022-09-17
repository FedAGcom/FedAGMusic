package com.fedag.fedagmusic.job;

import com.fedag.fedagmusic.repository.PerformerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriberCountPerformer {
    private final PerformerRepository performerRepository;

    @Scheduled(cron = "0 0 01 * * *")
    public void SubscriberCount() {
        Long count = performerRepository.countOfAllSubscribers().share().block();
        System.out.println("total number of subscribers of performers = " + count);
    }
}
