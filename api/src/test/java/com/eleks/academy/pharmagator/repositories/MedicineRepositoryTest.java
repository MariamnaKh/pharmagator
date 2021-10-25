package com.eleks.academy.pharmagator.repositories;

import com.eleks.academy.pharmagator.entities.Medicine;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class MedicineRepositoryTest {

    @Autowired
    private MedicineRepository medicineRepository;
    private Medicine medicine;

    @BeforeEach
    public void setUp() {
        medicine = new Medicine(1L, "Paracetamol");
    }
    @AfterEach
    public void tearDown() {
        medicineRepository.deleteAll();
        medicine = null;
    }

    @Test
    public void givenMedicineToAdd_ShouldReturnAddedMedicine(){
        medicineRepository.save(medicine);
        Medicine fetchedMedicine = medicineRepository.findById(medicine.getId()).get();
        assertEquals(1, fetchedMedicine.getId());
    }

    @Test
    public void GivenGetAllMedicines_ShouldReturnListOfAllMedicines(){
        Medicine medicine1 = new Medicine(1L,"Ibuprofen");
        Medicine medicine2 = new Medicine(2L,"Valerian root");
        medicineRepository.save(medicine1);
        medicineRepository.save(medicine2);
        List<Medicine> productList = (List<Medicine>) medicineRepository.findAll();
        assertEquals("Valerian root", productList.get(1).getTitle());
    }

    @Test
    public void givenId_ShouldReturnMedicineOfThatId() {
        Medicine medicine1 = new Medicine(1L,"Ibuprofen");
        Medicine medicine2 = medicineRepository.save(medicine1);
        Optional<Medicine> optional =  medicineRepository.findById(medicine2.getId());
        assertEquals(medicine2.getId(), optional.get().getId());
        assertEquals(medicine2.getTitle(), optional.get().getTitle());
    }

    @Test
    public void givenNonExistingId_ShouldReturnEmptyOptional() {
        Optional<Medicine> medicineOptional= medicineRepository.findById(1500L);
        assertTrue(medicineOptional.isEmpty());
    }

}
