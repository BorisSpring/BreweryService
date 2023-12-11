package com.main.beerorderservice.events;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
public class ValidateOrderResult {

    private boolean isValid;
    private Integer beerOrderId;
}
