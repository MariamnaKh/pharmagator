package com.eleks.academy.pharmagator.services.impl;

import com.eleks.academy.pharmagator.dto.PharmacyDto;
import com.eleks.academy.pharmagator.dto.mappers.PharmacyDtoMapper;
import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
import com.eleks.academy.pharmagator.services.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PharmacyServiceImpl implements PharmacyService {

    private final PharmacyRepository pharmacyRepository;

    @Override
    public List<Pharmacy> findAll() {
        return pharmacyRepository.findAll();
    }

    @Override
    public Optional<Pharmacy> findById(Long id) {
        return pharmacyRepository.findById(id);
    }

    @Override
    public Pharmacy save(PharmacyDto pharmacyDto) {
        Pharmacy pharmacy = PharmacyDtoMapper.toPharmacyEntity(pharmacyDto);
        return pharmacyRepository.save(pharmacy);
    }

    @Override
    public Optional<Pharmacy> update(Long id, PharmacyDto pharmacyDto) {
        return pharmacyRepository.findById(id)
                .map(ph -> {
                    Pharmacy pharmacy = PharmacyDtoMapper.toPharmacyEntity(pharmacyDto);
                    pharmacy.setId(id);
                    return pharmacyRepository.save(pharmacy);
                });
    }

    @Override
    public void deleteById(Long id) {
        pharmacyRepository.deleteById(id);
    }

}
