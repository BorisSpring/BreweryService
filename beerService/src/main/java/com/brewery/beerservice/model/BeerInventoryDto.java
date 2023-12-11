package com.brewery.beerservice.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeerInventoryDto {

    private Integer id;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    private Integer version ;

    private String beerUpc;
    private Integer quantityOnHand;
    private String upc;
    private Integer beerId;
}
