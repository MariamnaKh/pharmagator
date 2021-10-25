package com.eleks.academy.pharmagator.services.impl;

import com.eleks.academy.pharmagator.dto.MedicineDto;
import com.eleks.academy.pharmagator.dto.mappers.MedicineDtoMapper;
import com.eleks.academy.pharmagator.entities.Medicine;
import com.eleks.academy.pharmagator.repositories.MedicineRepository;
import com.eleks.academy.pharmagator.services.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;

    @Override
    public List<MedicineDto> findAll() {

        return MedicineDtoMapper.toMedicineDto(medicineRepository.findAll());

    }

    @Override
    public MedicineDto getById(Long id) {

        Optional<Medicine> medicine = medicineRepository.findById(id);
        if(medicine.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return MedicineDtoMapper.toMedicineDto(medicine.get());

    }

    @Override
    public void deleteMedicine(Long id) {

        Optional<Medicine> medicine = medicineRepository.findById(id);
        if(medicine.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        medicineRepository.deleteById(id);

    }

    @Override
    public MedicineDto createMedicine(MedicineDto medicine) {

        return MedicineDtoMapper.toMedicineDto(medicineRepository.save(MedicineDtoMapper.toMedicineEntity(medicine)));

    }

    @Override
    public MedicineDto updateMedicine(Long id, MedicineDto medicineDto) {

        Optional<Medicine> medicine = medicineRepository.findById(id);
        if(medicine.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Medicine tmp = medicine.get();
        tmp.setTitle(medicineDto.getTitle());
        return MedicineDtoMapper.toMedicineDto(medicineRepository.save(tmp));

    }

}
