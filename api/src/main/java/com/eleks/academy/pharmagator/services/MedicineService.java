package com.eleks.academy.pharmagator.services;

import com.eleks.academy.pharmagator.dto.MedicineDto;

import java.util.List;

public interface MedicineService {

    public List<MedicineDto> findAll();

    public MedicineDto getById(Long id);

    public void deleteMedicine(Long id);

    public MedicineDto createMedicine(MedicineDto medicine);

    public MedicineDto updateMedicine(Long id, MedicineDto medicine);

}
