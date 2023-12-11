package com.main.beerorderservice.service;

import com.main.beerorderservice.domain.BeerOrder;
import com.main.beerorderservice.model.BeerOrderDto;

public interface BeerOrderManager {

    BeerOrder createOrder(BeerOrder beerOrder);

    void processValidationResult(Integer orderId, boolean valid);

    void processAllocationFail(BeerOrderDto beerOrder);

    void processAllocationPassed(BeerOrderDto beerOrder);

    void beerOrderAllocationPending(BeerOrderDto beerOrderDto);

    void cancelOrder(Integer orderId);

    void pickUpOrder(Integer orderId);
}
