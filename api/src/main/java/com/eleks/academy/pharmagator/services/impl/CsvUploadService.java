package com.eleks.academy.pharmagator.services.impl;

import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDto;
import com.eleks.academy.pharmagator.entities.Medicine;
import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.entities.Price;
import com.eleks.academy.pharmagator.repositories.MedicineRepository;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
import com.eleks.academy.pharmagator.repositories.PriceRepository;
import com.eleks.academy.pharmagator.util.CsvUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CsvUploadService {

    private final MedicineRepository medicineRepository;
    private final PriceRepository priceRepository;
    private final PharmacyRepository pharmacyRepository;

    public void save(MultipartFile multipartFile) {
        try {
            List<MedicineDto> list = CsvUtil.parseBeans(multipartFile.getInputStream());

            for (MedicineDto dto : list) {
                Pharmacy pharmacy = null;
                Optional<Pharmacy> ifExists = pharmacyRepository.findByName(dto.getPharmacyName());

                if (ifExists.isEmpty()) {
                    pharmacy = new Pharmacy();
                    pharmacy.setName(dto.getPharmacyName());
                    pharmacy.setMedicineLinkTemplate(dto.getExternalId());
                    pharmacy = pharmacyRepository.save(pharmacy);
                } else {
                    pharmacy = ifExists.get();
                }

                Optional<Medicine> ifMedicineExists = medicineRepository.findByTitle(dto.getTitle());
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
                price.setPharmacyId(pharmacy.getId());
                price.setExternalId(dto.getExternalId());
                price.setPrice(dto.getPrice());
                price.setUpdatedAt(Instant.now());
                priceRepository.save(price);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read input", e);
        }
    }


}
