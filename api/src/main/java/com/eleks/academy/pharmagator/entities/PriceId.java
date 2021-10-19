package com.eleks.academy.pharmagator.entities;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PriceId implements Serializable {
    private Long pharmacyId;
    private Long medicineId;
}
