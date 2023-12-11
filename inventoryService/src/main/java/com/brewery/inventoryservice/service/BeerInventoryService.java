package com.brewery.inventoryservice.service;

import com.brewery.inventoryservice.model.BeerDto;
import com.brewery.inventoryservice.model.BeerInventoryDto;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface BeerInventoryService {

     BeerInventoryDto addInventory(BeerDto beerDto);

     void deleteInventory(String beerUpc);

     List<BeerInventoryDto> findAllInventories(PageRequest pageRequest);

     List<BeerInventoryDto> findInventoryByBeerUpc(String upc);
}
