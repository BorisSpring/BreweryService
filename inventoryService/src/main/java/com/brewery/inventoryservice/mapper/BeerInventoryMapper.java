package com.brewery.inventoryservice.mapper;

import com.brewery.inventoryservice.domain.BeerInventory;
import com.brewery.inventoryservice.model.BeerInventoryDto;
import org.mapstruct.Mapper;

@Mapper
public interface BeerInventoryMapper {

    BeerInventory dtoToBeerInventory(BeerInventoryDto beerInventoryDto);

    BeerInventoryDto beerInventoryToDto(BeerInventory beerInventory);
}
