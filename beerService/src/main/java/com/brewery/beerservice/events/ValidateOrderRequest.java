package com.brewery.beerservice.events;

import com.brewery.beerservice.domain.BeerOrderDto;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidateOrderRequest {

    private BeerOrderDto beerOrderDto;
}
