package com.brewery.inventoryservice.events;

import com.brewery.inventoryservice.model.BeerOrderDto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AllocateOrderRequest {

    private BeerOrderDto beerOrderDto;
}
