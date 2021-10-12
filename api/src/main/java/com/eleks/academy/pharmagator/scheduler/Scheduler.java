package com.eleks.academy.pharmagator.scheduler;

import com.eleks.academy.pharmagator.controllers.MedicineController;
import com.eleks.academy.pharmagator.controllers.PriceController;
import com.eleks.academy.pharmagator.dataproviders.DataProvider;
import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDto;
import com.eleks.academy.pharmagator.mappers.MedicineMapper;
import com.eleks.academy.pharmagator.mappers.PriceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Component
public class Scheduler {
    private final DataProvider dataProvider;
    private final MedicineController medicineController;
    private final PriceController priceController;
    private final MedicineMapper medicineMapper;
    private final PriceMapper priceMapper;

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void schedule() {
        log.info("Scheduler started at {}", Instant.now());
        dataProvider.loadData().forEach(this::storeToDatabase);
    }

    private void storeToDatabase(MedicineDto dto) {
        // TODO: convert DTO to Entity and store to database
        medicineController.updateMedicine(medicineMapper.toMedicineEntity(dto));
        priceController.updatePrice(priceMapper.toPriceEntity(dto));
    }
}
