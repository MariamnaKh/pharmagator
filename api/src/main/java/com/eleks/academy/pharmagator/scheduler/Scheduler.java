package com.eleks.academy.pharmagator.scheduler;

import com.eleks.academy.pharmagator.dataproviders.DataProvider;
import com.eleks.academy.pharmagator.services.impl.PersistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@Profile("!test")
@RequiredArgsConstructor
public class Scheduler {

    private final List<DataProvider> dataProviderList;
    private final PersistenceService persistenceService;

    @Scheduled(cron = "0 0 12 * * ?")
    public void schedule() {
        log.info("Scheduler started at {}", Instant.now());
        dataProviderList.stream()
                .flatMap(DataProvider::loadData)
                .forEach(persistenceService::storeToDB);
        log.info("Scheduler finished at {}", Instant.now());
        dataProviderList.stream().flatMap(DataProvider::loadData).forEach(this::storeToDatabase);
    }

}
