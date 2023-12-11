package com.brewery.inventoryservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BeerInventory extends BaseEntity{

    @Column(nullable = false)
    private String beerUpc;

    @Column(nullable = false)
    private Integer quantityOnHand;


    @Column(nullable = false)
    private Integer beerId;
}
