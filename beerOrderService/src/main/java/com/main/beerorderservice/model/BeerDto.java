package com.main.beerorderservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.main.beerorderservice.domain.BeerStyleEnum;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BeerDto   {

    private Integer id;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    private Integer version ;
    private String beerName;

    private BeerStyleEnum beerStyle;

    private String upc;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal price;

    private Integer quantityOnHand;

}
