package com.main.beerorderservice.events;

import com.main.beerorderservice.model.BeerOrderDto;
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
