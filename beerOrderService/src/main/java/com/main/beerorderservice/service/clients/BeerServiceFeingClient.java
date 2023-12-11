package com.main.beerorderservice.service.clients;

import com.main.beerorderservice.model.BeerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Primary
@FeignClient(name = "beers",url = "${beerserviceurl}" , fallback = BeerServiceFeingClientFallback.class)
public interface BeerServiceFeingClient {

     @GetMapping("/api/v1/beers/upc")
     ResponseEntity<BeerDto> findBeerByUpc( @RequestParam("beerUpc") String beerUpc,
                                            @RequestParam("showInventory") boolean showInventory);
}
