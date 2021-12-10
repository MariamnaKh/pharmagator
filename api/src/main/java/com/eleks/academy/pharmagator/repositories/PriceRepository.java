package com.eleks.academy.pharmagator.repositories;

import com.eleks.academy.pharmagator.entities.Price;
import com.eleks.academy.pharmagator.entities.PriceId;
import com.eleks.academy.pharmagator.projections.MedicinePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, PriceId> {

    Optional<Price> findByPharmacyIdAndMedicineId(Long pharmacyId, Long priceId);

    @Query("""
            SELECT p.price as price, m.title as title, p.pharmacyId as pharmacyId
            FROM Price p
            LEFT JOIN Medicine m ON m.id = p.medicineId
            """)
    List<MedicinePrice> findAllMedicinesPrices();

}
