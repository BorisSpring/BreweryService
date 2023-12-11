package com.brewery.beerservice.domain;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
public class BeerOrderLineDto extends BaseItem {


    private Integer beerId;

    private String upc;

    private BeerStyleEnum beerStyle;

    private String beerName;

    private Integer quantity;

    private BigDecimal totalPrice;

    private Integer quantityAllocated = 0;

    private Integer orderQuantity;

}
