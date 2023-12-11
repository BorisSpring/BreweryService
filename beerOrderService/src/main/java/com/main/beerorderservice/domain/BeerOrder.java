package com.main.beerorderservice.domain;

import com.main.beerorderservice.statemachine.BeerOrderStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class BeerOrder extends BaseEntity{


    @ManyToOne
    private Customer customer;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BeerOrderStatusEnum orderStatus = BeerOrderStatusEnum.NEW;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private Integer quantityAllocated;

    @Column(nullable = false)
    private Integer quantityOrdered;

    @OneToMany(mappedBy = "beerOrder", cascade = CascadeType.ALL)
    private Set<BeerOrderLine> beerOrderLines;


}
