package com.main.beerorderservice.events;

import com.main.beerorderservice.model.BeerOrderDto;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DealocateOrderRequest {

    private BeerOrderDto beerOrderDto;
}
