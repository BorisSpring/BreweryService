package com.main.beerorderservice.service.clients;

import com.main.beerorderservice.model.BeerDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class BeerServiceFeingClientFallback   implements BeerServiceFeingClient {

    @Override
    public ResponseEntity<BeerDto> findBeerByUpc(String beerUpc, boolean showInventory) {
        System.out.println("Triggedred fallback");
        return null;
    }
}
