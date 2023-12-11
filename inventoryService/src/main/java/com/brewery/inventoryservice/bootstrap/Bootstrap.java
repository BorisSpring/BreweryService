package com.brewery.inventoryservice.bootstrap;

import com.brewery.inventoryservice.domain.BeerInventory;
import com.brewery.inventoryservice.repositories.BeerInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {


    private final BeerInventoryRepository beerInventoryRepository;
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Inventories : " + beerInventoryRepository.count());
        saveNewInventory(200,"123456789", 1);
        saveNewInventory(200,"12345678", 2 );
        saveNewInventory(200, "1234567", 3);
        saveNewInventory(200, "123456", 4);

        System.out.println("Affter saving : " + beerInventoryRepository.count());

    }

    private void saveNewInventory(Integer quantityToBrew, String upc, Integer beerId){
        beerInventoryRepository.save(BeerInventory.builder()
                                        .quantityOnHand(quantityToBrew)
                                        .beerUpc(upc)
                                        .beerId(beerId)
                                        .build());
    }
}
