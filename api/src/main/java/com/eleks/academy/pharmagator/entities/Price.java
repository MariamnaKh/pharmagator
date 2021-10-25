package com.eleks.academy.pharmagator.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "prices")
@IdClass(PriceId.class)
public class Price {
    @Id
    private Long pharmacyId;
    @Id
    private Long medicineId;
    private BigDecimal price;
    private String externalId;
    private Instant updatedAt;
}
