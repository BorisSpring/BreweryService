package com.main.beerorderservice.service;

import com.main.beerorderservice.model.BeerOrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BeerOrderService {

        Page<BeerOrderDto> listOrders(Integer customerId, Pageable pageable);

        BeerOrderDto placeOrder(Integer customerId, BeerOrderDto beerOrderDto);

        BeerOrderDto getBeerOrderById(Integer customerId, Integer orderId);

        void pickUpOrder(Integer OrderId);

}
