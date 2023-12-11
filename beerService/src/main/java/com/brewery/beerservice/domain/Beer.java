package com.brewery.beerservice.domain;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Component
public class Beer extends BaseItem {


    @Column(nullable = false)
    private String beerName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BeerStyleEnum beerStyle;

    @Column(nullable = false)
    private String upc;

    @Column(nullable = false)
    private Integer quantityToBrew;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer minOnHand;
}
