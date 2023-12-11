package com.main.beerorderservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class BeerOrderLine extends BaseEntity{

    @Column(nullable = false)
    private Integer beerId;

    @Column(nullable = false)
    private String upc;

    @Column(nullable = false)
    private BeerStyleEnum beerStyle;

    @Column(nullable = false)
    private String beerName;

    @Column(nullable = false)
    private Integer orderQuantity;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private Integer quantityAllocated = 0;

    @ManyToOne
    private BeerOrder beerOrder;
}
