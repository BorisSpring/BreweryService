package com.brewery.beerservice.service.clients;

import com.brewery.beerservice.model.BeerInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;


@Primary
@FeignClient(name = "inventory"  , url = "${inventoryurl}",  fallback = InventoryFeingClientFallback.class)
public interface InventoryFeingClient {
    @GetMapping("/api/v1/inventory/upc")
    ResponseEntity<List<BeerInventoryDto>> findInventoryByBeerUpc(@RequestParam(name = "beerUpc") String beerUpc);
}
