package com.main.beerorderservice.events;

import com.main.beerorderservice.model.BeerOrderDto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AllocateOrderRequest {

    private BeerOrderDto beerOrderDto;
}
