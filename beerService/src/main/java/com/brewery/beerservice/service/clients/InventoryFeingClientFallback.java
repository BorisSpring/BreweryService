package com.brewery.beerservice.service.clients;

import com.brewery.beerservice.model.BeerInventoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class InventoryFeingClientFallback  implements InventoryFeingClient {
    @Override
    public ResponseEntity<List<BeerInventoryDto>> findInventoryByBeerUpc(String beerUpc) {
        System.out.println("Fallback triggered");
        return null;
    }
}
