package com.eleks.academy.pharmagator.services.impl;

import com.eleks.academy.pharmagator.dto.MedicineDto;
import com.eleks.academy.pharmagator.dto.mappers.MedicineDtoMapper;
import com.eleks.academy.pharmagator.entities.Medicine;
import com.eleks.academy.pharmagator.repositories.MedicineRepository;
import com.eleks.academy.pharmagator.services.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;

    @Override
    public List<Medicine> findAll() {

        return medicineRepository.findAll();
    }

    @Override
    public Optional<Medicine> findById(Long id) {

        return medicineRepository.findById(id);
    }

    @Override
    public Optional<Medicine> update(Long id, MedicineDto medicineDto) {
        return medicineRepository.findById(id)
                .map(source -> {
                    Medicine medicine = MedicineDtoMapper.toMedicineEntity(medicineDto);
                    medicine.setId(id);
                    return medicineRepository.save(medicine);
                });
    }

    @Override
    public Medicine save(MedicineDto medicineDto) {
        Medicine medicine = MedicineDtoMapper.toMedicineEntity(medicineDto);
        return medicineRepository.save(medicine);
    }

    public void delete(Long id) {

        medicineRepository.deleteById(id);
    }

}
