package com.eleks.academy.pharmagator.services;

import com.eleks.academy.pharmagator.dto.mappers.PharmacyDtoMapper;
import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
import com.eleks.academy.pharmagator.services.impl.PharmacyServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PharmacyServiceImplTest {

    @Mock
    PharmacyRepository pharmacyRepository;

    @InjectMocks
    private PharmacyServiceImpl pharmacyService;
    Pharmacy pharmacy;
    Pharmacy pharmacy2;
    List<Pharmacy> pharmacyList;

    @BeforeEach
    public void setup() {

        pharmacy = new Pharmacy(8L, "My pharma", "linkk.com");
        pharmacy2 = new Pharmacy(9L, "Second pharma", "secongPharma.com");
        pharmacyList = Arrays.asList(pharmacy, pharmacy2);

    }

    @AfterEach
    void tearDown() {

        pharmacy = null;
        pharmacy2 = null;
        pharmacyList = null;

    }

    @Test
    public void getAllPharmacies() {

        when(pharmacyRepository.findAll()).thenReturn(pharmacyList);
        List<Pharmacy> pharmacyList = pharmacyService.findAll();
        assertEquals(2, pharmacyList.size());

    }

    @Test
    public void givenPharmacy_CreateNewPharmacy() {

        when(pharmacyRepository.save(any(Pharmacy.class))).thenReturn(pharmacy);
        Pharmacy savedPharmacy = pharmacyService.save(PharmacyDtoMapper.toPharmacyDto(pharmacy));
        verify(pharmacyRepository, times(1)).save(any(Pharmacy.class));
        assertEquals(pharmacy.getId(), savedPharmacy.getId());
        assertEquals(pharmacy.getName(), savedPharmacy.getName());
        assertEquals(pharmacy.getMedicineLinkTemplate(), savedPharmacy.getMedicineLinkTemplate());

    }

    @Test
    public void givenPharmacy_TestById() {

        when(pharmacyRepository.findById(anyLong()))
                .thenReturn(Optional.of(pharmacy));
        Pharmacy pharmacy1 = pharmacyService.findById(pharmacy.getId()).get();
        verify(pharmacyRepository, times(1)).findById(anyLong());
        assertEquals(pharmacy.getId(), pharmacy1.getId());
        assertEquals(pharmacy.getName(), pharmacy1.getName());
        assertEquals(pharmacy.getMedicineLinkTemplate(), pharmacy1.getMedicineLinkTemplate());

    }

//    @Test
//    public void deleteMethodThrowsResponseStatus() {
//
//        final Long pharmacyId = 56L;
//        assertThatThrownBy(() -> pharmacyService.delete(pharmacyId))
//                .isInstanceOf(ResponseStatusException.class);
//
//    }

    @Test
    public void givenPharmacy_UpdatePharmacy() {

        when(pharmacyRepository.findById(anyLong())).thenReturn(Optional.ofNullable(pharmacy));
        when(pharmacyRepository.save(any(Pharmacy.class))).thenReturn(pharmacy);
        Pharmacy savedPharmacy = pharmacyService.update(anyLong(), PharmacyDtoMapper.toPharmacyDto(pharmacy)).get();
        assertThat(savedPharmacy.getName()).isNotNull();
        assertEquals(pharmacy.getName(), savedPharmacy.getName());

    }

    @Test
    public void deletePharmacy() {

        doNothing().when(pharmacyRepository).deleteById(anyLong());
        pharmacyService.deleteById(pharmacy.getId());
        verify(pharmacyRepository, times(1)).deleteById(pharmacy.getId());

    }

}
