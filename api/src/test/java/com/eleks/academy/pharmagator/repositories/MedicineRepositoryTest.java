package com.eleks.academy.pharmagator.repositories;

import com.eleks.academy.pharmagator.entities.Medicine;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void givenMedicineToFind_FindByTitle() {

        medicineRepository.save(medicine);
        Optional<Medicine> expected = medicineRepository.findByTitle(medicine.getTitle());
        assertThat(expected).isNotEmpty();

    }

    @Test
    public void givenNonExistingTitle_ShouldReturnEmptyOptional() {

        Optional<Medicine> medicineOptional = medicineRepository.findByTitle("Vitamin D");
        assertTrue(medicineOptional.isEmpty());

    }

}
