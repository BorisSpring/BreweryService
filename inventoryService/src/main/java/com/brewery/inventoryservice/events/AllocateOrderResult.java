package com.brewery.inventoryservice.events;

import com.brewery.inventoryservice.model.BeerOrderDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllocateOrderResult {

    private boolean isAllocated;
    private boolean isException;
    private BeerOrderDto beerOrderDto;
}
