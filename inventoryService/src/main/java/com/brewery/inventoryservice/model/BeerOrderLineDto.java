package com.brewery.inventoryservice.model;

import com.brewery.inventoryservice.domain.BeerStyleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BeerOrderLineDto {

    private Integer id;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    private Integer version ;

    private Integer beerId;

    private String upc;

    private BeerStyleEnum beerStyle;

    private String beerName;

    private Integer quantity;

    private BigDecimal totalPrice;

    private Integer quantityAllocated = 0;

    private Integer orderQuantity;

}
