package com.brewery.beerservice.service.inventory;

import com.brewery.beerservice.model.BeerInventoryDto;
import com.brewery.beerservice.service.clients.InventoryFeingClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BeerInventoryServiceImpl implements BeerInventoryService {

    private final InventoryFeingClient inventoryFeingClient;
    @Override
    public Integer getInventoriesForBeerByUpc(String upc) {
        ResponseEntity<List<BeerInventoryDto>> inventoryByBeerUpc = inventoryFeingClient.findInventoryByBeerUpc(upc);
        if(inventoryByBeerUpc != null && inventoryByBeerUpc.getBody() != null && !inventoryByBeerUpc.getBody().isEmpty()){
            return  inventoryByBeerUpc
                    .getBody()
                    .stream()
                    .mapToInt(BeerInventoryDto::getQuantityOnHand)
                    .sum();
        }
       return null;
    }
}
