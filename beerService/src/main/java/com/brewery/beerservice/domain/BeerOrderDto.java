package com.brewery.beerservice.domain;

import lombok.*;
import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Getter
@Setter
@ToString
public class BeerOrderDto extends BaseItem {


    private Integer id;

    private Integer customerId;

    private String customerRef;

    private Set<BeerOrderLineDto> beerOrderLines;

    private BeerOrderStatusEnum orderStatus;

    private String orderStatusCallbackUrl;

    private Integer quantityAllocated = 0;

    private Integer quantityOrdered;

    private BigDecimal totalPrice;
}
