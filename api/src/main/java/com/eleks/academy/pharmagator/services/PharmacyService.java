package com.eleks.academy.pharmagator.services;

import com.eleks.academy.pharmagator.dto.PharmacyDto;

import java.util.List;

public interface PharmacyService {

    public List<PharmacyDto> getAll();

    public PharmacyDto getById(Long id);

    public void deletePharmacy(Long id);

    public PharmacyDto createPharmacy(PharmacyDto pharmacy);

    public PharmacyDto updatePharmacy(Long id, PharmacyDto pharmacy);

}
