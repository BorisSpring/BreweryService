package com.brewery.inventoryservice.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BeerInventoryDto {

    private Integer id;
    private String beerUpc;
    private Integer quantityOnHand;
    private Integer beerId;
}
