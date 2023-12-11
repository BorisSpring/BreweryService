package com.brewery.inventoryservice.events;

import com.brewery.inventoryservice.model.BeerOrderDto;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DealocateOrderRequest {

    private BeerOrderDto beerOrderDto;
}
