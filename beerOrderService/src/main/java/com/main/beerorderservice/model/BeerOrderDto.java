package com.main.beerorderservice.model;

import com.main.beerorderservice.statemachine.BeerOrderStatusEnum;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class BeerOrderDto   {

    private Integer id;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    private Integer version ;

    private Integer customerId;

    private String customerRef;

    private Set<BeerOrderLineDto> beerOrderLines;

    private BeerOrderStatusEnum orderStatus;

    private String orderStatusCallbackUrl;

    private Integer quantityAllocated = 0;

    private Integer quantityOrdered;

    private BigDecimal totalPrice;
}
