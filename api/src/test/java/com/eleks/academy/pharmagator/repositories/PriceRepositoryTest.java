package com.eleks.academy.pharmagator.repositories;

import com.eleks.academy.pharmagator.entities.Medicine;
import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.entities.Price;
import com.eleks.academy.pharmagator.entities.PriceId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class PriceRepositoryTest {

    @Autowired
    PriceRepository priceRepository;
    @Autowired
    private MedicineRepository medicineRepository;
    @Autowired
    private PharmacyRepository pharmacyRepository;
    //Price price;


//    @BeforeEach
//    public void setUp() {
//        //price = new Price(5L, 2L, new BigDecimal("1234"), "234", Instant.now());
//        medicineRepository.deleteAll();
//        pharmacyRepository.deleteAll();
//    }
//    @AfterEach
//    public void tearDown() {
//        priceRepository.deleteAll();
//        //price = null;
//    }

//    @Test
//    public void GivenIds_FindByMedicineIdAndPharmacyId() {
//        medicineRepository.save(new Medicine(2L,"Paracetamol"));
//        pharmacyRepository.save(new Pharmacy(5L,"Pharma", "template"));
//        Price price = new Price(5L, 2L, new BigDecimal("1234"), "234", Instant.now());
//        priceRepository.save(price);
//        Optional<Price> expected = priceRepository.findByPharmacyIdAndMedicineId(5L, 2L);
//        assertThat(expected).isNotEmpty();
//        assertEquals(price, expected.get());
//        List<Price> all = priceRepository.findAll();
//        Optional<Price> byId = priceRepository.findById(new PriceId(2L, 5L));
//    }
}
