package com.eleks.academy.pharmagator.scheduler;

import com.eleks.academy.pharmagator.dataproviders.DataProvider;
import com.eleks.academy.pharmagator.dataproviders.PharmacyRozetkaDataProvider;
import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDto;
import com.eleks.academy.pharmagator.entities.Medicine;
import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.entities.Price;
import com.eleks.academy.pharmagator.mappers.MedicineMapper;
import com.eleks.academy.pharmagator.mappers.PriceMapper;
import com.eleks.academy.pharmagator.repositories.MedicineRepository;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
import com.eleks.academy.pharmagator.repositories.PriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

import java.util.Optional;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@Profile("!test")
@RequiredArgsConstructor
public class Scheduler {

    private final List<DataProvider> dataProviderList;
    private final MedicineRepository medicineRepository;
    private final PriceRepository priceRepository;
    private static final Long ROZETKA = 2L;

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)

    public void schedule() {

        log.info("Scheduler started at {}", Instant.now());
        dataProviderList.stream().flatMap(DataProvider::loadData).forEach(this::storeToDatabase);

    }

    private void storeToDatabase(MedicineDto dto) {
        // TODO: convert DTO to Entity and store to database
        log.info(dto.getTitle() + " - " + dto.getPrice());
        /*Optional<Medicine> ifMedicineExists = medicineRepository.findByTitle(dto.getTitle());
        Medicine medicine = null;
        if (ifMedicineExists.isPresent()) {
            medicine = ifMedicineExists.get();
        } else {
            Medicine newMedicine = new Medicine();
            newMedicine.setTitle(dto.getTitle());
            medicine = medicineRepository.save(newMedicine);
        }
        Price price = new Price();
        price.setMedicineId(medicine.getId());
        price.setPharmacyId(ROZETKA);
        price.setExternalId(dto.getExternalId());
        price.setPrice(dto.getPrice());
        price.setUpdatedAt(Instant.now());
        priceRepository.save(price);*/

        Medicine medicine = MedicineMapper.toMedicineEntity(dto);
        Price price = PriceMapper.toPriceEntity(dto);

    }

}
