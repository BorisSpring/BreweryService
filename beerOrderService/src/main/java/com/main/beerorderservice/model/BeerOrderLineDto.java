package com.main.beerorderservice.model;

import com.main.beerorderservice.domain.BeerStyleEnum;
import lombok.*;

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
