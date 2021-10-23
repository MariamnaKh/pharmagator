package com.eleks.academy.pharmagator.dto.mappers;

import com.eleks.academy.pharmagator.dto.MedicineDto;
import com.eleks.academy.pharmagator.entities.Medicine;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MedicineDtoMapper {

    public Medicine toMedicineEntity(MedicineDto dto) {

        Medicine medicine = new Medicine();
        medicine.setTitle(dto.getTitle());
        return medicine;

    }

    public MedicineDto toMedicineDto(Medicine medicine) {

        MedicineDto medicineDto = new MedicineDto();
        medicineDto.setId(medicine.getId());
        medicineDto.setTitle(medicine.getTitle());
        return medicineDto;

    }

    public List<MedicineDto> toMedicineDto(List<Medicine> medicineList) {

        return medicineList.stream().map(this::toMedicineDto).collect(Collectors.toList());

    }

}
