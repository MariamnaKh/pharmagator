package com.eleks.academy.pharmagator.services;

import com.eleks.academy.pharmagator.dto.MedicineDto;
import com.eleks.academy.pharmagator.dto.mappers.MedicineDtoMapper;
import com.eleks.academy.pharmagator.entities.Medicine;
import com.eleks.academy.pharmagator.repositories.MedicineRepository;
import com.eleks.academy.pharmagator.services.impl.MedicineServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicineServiceImplTest {

    @Mock
    private MedicineRepository medicineRepository;
    private MedicineService medicineService;

    @BeforeEach
    public void setUp() {
        medicineService = new MedicineServiceImpl(medicineRepository);
    }

    @Test
    public void canGetAllMedicines() {
        //when
        medicineService.findAll();
        //then
        verify(medicineRepository).findAll();

    }

    @Test
    public void getAllMedicines() {
        Medicine medicine1 = new Medicine(1L, "Ibuprofen");
        Medicine medicine2 = new Medicine(2L, "Vitamin C");
        List<Medicine> medicines = Arrays.asList(medicine1, medicine2);

        when(medicineRepository.findAll()).thenReturn(medicines);

        List<Medicine> medicineList = medicineService.findAll();

        assertEquals(2, medicineList.size());

    }

    @Test
    public void givenMedicine_CreateNewMedicine() {
        Medicine medicine1 = new Medicine(1L, "Ibuprofen");
        when(medicineRepository.save(any(Medicine.class))).thenReturn(medicine1);
        MedicineDto medicineDto = MedicineDtoMapper.toMedicineDto(medicine1);

        Medicine savedMedicine = medicineService.save(medicineDto);
        // then
        verify(medicineRepository, times(1)).save(any(Medicine.class));
        assertEquals(medicine1.getId(), savedMedicine.getId());
        assertEquals(medicine1.getTitle(), savedMedicine.getTitle());

    }

    @Test
    public void givenMedicine_TestById() {
        //given
        Medicine medicine = new Medicine(3L, "Valerian root");
        when(medicineRepository.findById(anyLong()))
                .thenReturn(Optional.of(medicine));
        Medicine medicine2 = medicineService.findById(3L).get();

        // then
        verify(medicineRepository, times(1)).findById(anyLong());
        assertEquals(medicine.getId(), medicine2.getId());
        assertEquals(medicine.getTitle(), medicine2.getTitle());
    }

}

