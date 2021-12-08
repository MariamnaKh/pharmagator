package com.eleks.academy.pharmagator.services.impl;

import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDto;
import com.eleks.academy.pharmagator.entities.Medicine;
import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.entities.Price;
import com.eleks.academy.pharmagator.repositories.MedicineRepository;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
import com.eleks.academy.pharmagator.repositories.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PersistenceService {

    private final MedicineRepository medicineRepository;
    private final PriceRepository priceRepository;
    private final PharmacyRepository pharmacyRepository;

    public void storeToDB(MedicineDto dto) {
        Pharmacy pharmacy = pharmacyRepository.findByName(dto.getPharmacyName())
                .orElseGet(() -> pharmacyRepository.save(Pharmacy.builder()
                        .name(dto.getPharmacyName())
                        .medicineLinkTemplate(dto.getExternalId()).build()));

        Medicine medicine = medicineRepository.findByTitle(dto.getTitle())
                .orElseGet(() -> medicineRepository.save(Medicine.builder().title(dto.getTitle()).build()));

        priceRepository.save(Price.builder()
                .price(dto.getPrice())
                .medicineId(medicine.getId())
                .pharmacyId(pharmacy.getId())
                .externalId(pharmacy.getMedicineLinkTemplate())
                .updatedAt(Instant.now())
                .build());
    }

}
