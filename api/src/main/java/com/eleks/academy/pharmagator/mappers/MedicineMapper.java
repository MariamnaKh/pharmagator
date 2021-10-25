package com.eleks.academy.pharmagator.mappers;

import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDto;
import com.eleks.academy.pharmagator.entities.Medicine;

public class MedicineMapper {
    public static Medicine toMedicineEntity(MedicineDto dto) {
        Medicine medicine = new Medicine();
        medicine.setTitle(dto.getTitle());
        return medicine;
    }
}
