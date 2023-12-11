package com.brewery.beerservice.events;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ValidateOrderResult {

    private boolean isValid;
    private Integer beerOrderId;
}
