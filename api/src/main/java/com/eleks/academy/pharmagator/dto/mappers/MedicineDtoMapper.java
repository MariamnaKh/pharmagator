package com.eleks.academy.pharmagator.dto.mappers;

import com.eleks.academy.pharmagator.dto.MedicineDto;
import com.eleks.academy.pharmagator.entities.Medicine;

import java.util.List;
import java.util.stream.Collectors;

public class MedicineDtoMapper {

    public static Medicine toMedicineEntity(MedicineDto dto) {

        Medicine medicine = new Medicine();
        medicine.setTitle(dto.getTitle());
        return medicine;

    }

    public static List<Medicine> toMedicineEntity(List<MedicineDto> medicineDtoList) {
        return medicineDtoList.stream().map(MedicineDtoMapper::toMedicineEntity).collect(Collectors.toList());
    }

    public static MedicineDto toMedicineDto(Medicine medicine) {

        MedicineDto medicineDto = new MedicineDto();
        medicineDto.setId(medicine.getId());
        medicineDto.setTitle(medicine.getTitle());
        return medicineDto;

    }

    public static List<MedicineDto> toMedicineDto(List<Medicine> medicineList) {

        return medicineList.stream().map(MedicineDtoMapper::toMedicineDto).collect(Collectors.toList());

    }

}
