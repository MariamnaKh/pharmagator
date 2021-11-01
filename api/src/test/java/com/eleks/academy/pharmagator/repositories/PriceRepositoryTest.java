package com.eleks.academy.pharmagator.repositories;

import com.eleks.academy.pharmagator.entities.Medicine;
import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.entities.Price;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class PriceRepositoryTest {

    @Autowired
    PriceRepository priceRepository;
    @Autowired
    private MedicineRepository medicineRepository;
    @Autowired
    private PharmacyRepository pharmacyRepository;
    private Medicine medicine;
    private Pharmacy pharmacy;


    @BeforeEach
    public void setUp() {
        medicine = medicineRepository.save(new Medicine("Paracetamol"));
        pharmacy = pharmacyRepository.save(new Pharmacy("Pharma", "template"));

    }

    @AfterEach
    public void tearDown() {

        priceRepository.deleteAll();
        medicineRepository.deleteAll();
        pharmacyRepository.deleteAll();

    }

    @Test
    public void GivenIds_FindByMedicineIdAndPharmacyId() {

        Price price = new Price(pharmacy.getId(), medicine.getId(), new BigDecimal("1234"), "234", Instant.now());
        priceRepository.save(price);
        Optional<Price> expected = priceRepository.findByPharmacyIdAndMedicineId(pharmacy.getId(), medicine.getId());
        assertThat(expected).isNotEmpty();
        assertEquals(price, expected.get());

    }

    @Test
    public void givenNonExistingId_ShouldReturnEmptyOptional() {

        Optional<Price> priceOptional = priceRepository.findByPharmacyIdAndMedicineId(56L, 5L);
        assertTrue(priceOptional.isEmpty());

    }

}
