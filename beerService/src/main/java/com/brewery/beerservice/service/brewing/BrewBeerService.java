package com.brewery.beerservice.service.brewing;

import com.brewery.beerservice.domain.Beer;
import com.brewery.beerservice.mappers.BeerMapper;
import com.brewery.beerservice.model.BeerDto;
import com.brewery.beerservice.repository.BeerRepository;
import com.brewery.beerservice.service.inventory.BeerInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class BrewBeerService {

    private final BeerInventoryService beerInventoryService;
    private final BeerRepository beerRepository;
    private final StreamBridge streamBridge;
    private final BeerMapper beerMapper;
    @Scheduled(fixedRate = 10000)
    public void checkForLowInventories(){
        beerRepository.findAll().forEach(beer -> {
            Integer quantityOnHandSum = beerInventoryService.getInventoriesForBeerByUpc(beer.getUpc());
            if(quantityOnHandSum == null || quantityOnHandSum <= beer.getMinOnHand()){
                sendBrewBeerEvent(beer);
            }
        });
    }

    public void sendBrewBeerEvent(Beer beer){
        BeerDto beerDto = beerMapper.beerToDto(beer);
        boolean send = streamBridge.send("brewBeerInventory-out-0", beerDto);
        System.out.println("Event for brewing inventory for beer with upc: " + beer.getUpc() + " is triggered: " + send);
    }
}
