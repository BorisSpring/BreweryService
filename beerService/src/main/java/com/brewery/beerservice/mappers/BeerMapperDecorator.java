package com.brewery.beerservice.mappers;

import com.brewery.beerservice.domain.Beer;
import com.brewery.beerservice.model.BeerDto;
import com.brewery.beerservice.service.inventory.BeerInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract  class BeerMapperDecorator implements  BeerMapper{

    @Autowired
    private  BeerInventoryService beerInventoryService;

    @Autowired
    private  BeerMapper beerMapper;


    @Override
    public BeerDto beerToDto(Beer beer) {
        return  beerMapper.beerToDto(beer);
    }

    @Override
    public Beer dtoToBeer(BeerDto beerDto) {
        return  beerMapper.dtoToBeer(beerDto);
    }

    @Override
    public BeerDto beerToDtoWithInventory(Beer beer) {
        BeerDto beerDto = beerToDto(beer);
        beerDto.setQuantityOnHand(beerInventoryService.getInventoriesForBeerByUpc(beer.getUpc()));
        return  beerDto;
    }
}
