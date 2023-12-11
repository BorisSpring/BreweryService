package com.brewery.beerservice.mappers;

import com.brewery.beerservice.domain.Beer;
import com.brewery.beerservice.model.BeerDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {

     BeerDto beerToDto(Beer beer);
     Beer dtoToBeer(BeerDto beerDto);
     BeerDto beerToDtoWithInventory(Beer beer);
}
