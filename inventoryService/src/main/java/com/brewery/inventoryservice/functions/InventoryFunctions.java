package com.brewery.inventoryservice.functions;

import com.brewery.inventoryservice.domain.BeerInventory;
import com.brewery.inventoryservice.events.AllocateOrderRequest;
import com.brewery.inventoryservice.events.AllocateOrderResult;
import com.brewery.inventoryservice.events.DealocateOrderRequest;
import com.brewery.inventoryservice.model.BeerDto;
import com.brewery.inventoryservice.model.BeerOrderLineDto;
import com.brewery.inventoryservice.repositories.BeerInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;


@Configuration
@RequiredArgsConstructor
public class InventoryFunctions {

    private final BeerInventoryRepository beerInventoryRepository;
    @Bean
    Consumer<BeerDto> brewBeerInventory(){
        return beerDto -> {
            System.out.println("Makeing inventory for beer with upc " + beerDto.getUpc());
            beerInventoryRepository.save(BeerInventory.builder()
                    .beerId(beerDto.getId())
                    .beerUpc(beerDto.getUpc())
                    .quantityOnHand(beerDto.getQuantityToBrew())
                    .build());
        };
    };

    @Bean
    Consumer<DealocateOrderRequest> dealocateOrderRequestConsumer(){
        return dealocateOrderRequest -> {
            System.out.println("Processing delocate order request for beer order with id : " + dealocateOrderRequest.getBeerOrderDto().getId());
            dealocateOrderRequest.getBeerOrderDto().getBeerOrderLines().forEach(beerOrderLineDto -> {
                List<BeerInventory> inventoriesByBeerUpc = beerInventoryRepository.findByBeerUpc(beerOrderLineDto.getUpc());

                if(inventoriesByBeerUpc == null || inventoriesByBeerUpc.isEmpty()){
                    beerInventoryRepository.save(BeerInventory.builder()
                                    .quantityOnHand(beerOrderLineDto.getOrderQuantity())
                                    .beerId(beerOrderLineDto.getBeerId())
                                    .beerUpc(beerOrderLineDto.getUpc())
                                    .build());
                }else{
                    BeerInventory beerInventory = inventoriesByBeerUpc.get(0);
                    beerInventory.setQuantityOnHand((beerInventory.getQuantityOnHand() + beerOrderLineDto.getOrderQuantity()));
                    beerInventoryRepository.saveAndFlush(beerInventory);
                }
            });
        };
    }

    @Bean
    Function<AllocateOrderRequest, AllocateOrderRequest> allocateOrder(){
        return allocateOrderRequest -> {
            System.out.println("Allocation order received!");
            return  allocateOrderRequest;
        };
    }

    @Bean
    Function<AllocateOrderRequest, AllocateOrderResult> allocateOrderResult(){
        return  allocateOrderRequest -> {
            AllocateOrderResult allocateOrderResult = new AllocateOrderResult();
            AtomicInteger quantityAllocated = new AtomicInteger();

            int totalOrderQuantity = allocateOrderRequest.getBeerOrderDto()
                    .getBeerOrderLines()
                    .stream()
                    .mapToInt(BeerOrderLineDto::getOrderQuantity)
                    .sum();

            allocateOrderResult.setBeerOrderDto(allocateOrderRequest.getBeerOrderDto());

            try{
                allocateOrderRequest.getBeerOrderDto().getBeerOrderLines().forEach(beerOrderLineDto -> {

                    AtomicInteger lineQuantityAllocated = new AtomicInteger();
                    beerInventoryRepository.findByBeerUpc(beerOrderLineDto.getUpc()).forEach(inventory -> {
                        int quantityToAllocate = beerOrderLineDto.getOrderQuantity() - lineQuantityAllocated.get();
                        if(inventory.getQuantityOnHand() >= quantityToAllocate){
                            inventory.setQuantityOnHand((inventory.getQuantityOnHand() - quantityToAllocate));
                            lineQuantityAllocated.getAndAdd(quantityToAllocate);
                        }else
                            lineQuantityAllocated.getAndAdd(inventory.getQuantityOnHand());
                        if(inventory.getQuantityOnHand() == 0)
                            beerInventoryRepository.deleteById(inventory.getId());

                    });
                    quantityAllocated.getAndAdd(lineQuantityAllocated.get());
                    beerOrderLineDto.setQuantityAllocated(lineQuantityAllocated.get());
                });

                allocateOrderResult.setAllocated(totalOrderQuantity == quantityAllocated.get());
            }catch(Exception e){
                allocateOrderResult.setException(true);
            }
            System.out.println("sending results for order allocation : " + allocateOrderResult.isAllocated());
            return allocateOrderResult;
        };
    }
}
