package com.main.beerorderservice.events;

import com.main.beerorderservice.model.BeerOrderDto;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidateOrderRequest {

    private BeerOrderDto beerOrderDto;
}
