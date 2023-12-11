package com.main.beerorderservice.mappers;

import com.main.beerorderservice.domain.BeerOrder;
import com.main.beerorderservice.model.BeerOrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BeerOrderMapper {

    @Mapping(target = "customerId" , source = "customer.id")
    BeerOrderDto beerOrderToDto(BeerOrder beerOrder);

    BeerOrder dtoToBeerOrder(BeerOrderDto beerOrderDtob);
}
