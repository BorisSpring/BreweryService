package com.main.beerorderservice.functions;

import com.main.beerorderservice.events.AllocateOrderResult;
import com.main.beerorderservice.service.BeerOrderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class InventoryServiceFunctions {

    private final BeerOrderManager beerOrderManager;
    @Bean
    Consumer<AllocateOrderResult> allocationResult(){
        return allocateOrderResult -> {
            System.out.println("received allocation results: " + allocateOrderResult.isException() + " : " + allocateOrderResult.isAllocated());
            if(allocateOrderResult.isException()){
                beerOrderManager.processAllocationFail(allocateOrderResult.getBeerOrderDto());
            }else if(allocateOrderResult.isAllocated()){
                beerOrderManager.processAllocationPassed(allocateOrderResult.getBeerOrderDto());
            }else {
                beerOrderManager.beerOrderAllocationPending(allocateOrderResult.getBeerOrderDto());
            }
        };
    }
}
