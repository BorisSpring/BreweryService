package com.main.beerorderservice.service;

import com.main.beerorderservice.domain.BeerOrder;
import com.main.beerorderservice.domain.Customer;
import com.main.beerorderservice.mappers.BeerOrderMapper;
import com.main.beerorderservice.model.BeerOrderDto;
import com.main.beerorderservice.repository.BeerOrderRepository;
import com.main.beerorderservice.repository.CustomerRepository;
import com.main.beerorderservice.statemachine.BeerOrderStatusEnum;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BeerOrderServiceImpl implements BeerOrderService {

    private final BeerOrderManager beerOrderManager;
    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;
    private final CustomerRepository customerRepository;
    @Override
    public Page<BeerOrderDto> listOrders(Integer customerId, Pageable pageable) {
        Customer customer = getCustomerById(customerId);
        return beerOrderRepository.findAllByCustomer(customer, pageable).map(beerOrderMapper::beerOrderToDto);
    }

    @Transactional
    @Override
    public BeerOrderDto placeOrder(Integer customerId, BeerOrderDto beerOrderDto) {
        Customer customer = getCustomerById(customerId);
        BeerOrder beerOrder = beerOrderMapper.dtoToBeerOrder(beerOrderDto);
        beerOrder.setId(null);
        beerOrder.setCustomer(customer);
        beerOrder.setOrderStatus(BeerOrderStatusEnum.NEW);
        beerOrder.getBeerOrderLines().forEach(beerOrderLine -> beerOrderLine.setBeerOrder(beerOrder));
        return beerOrderMapper.beerOrderToDto(beerOrderManager.createOrder(beerOrder));
    }

    @Override
    public BeerOrderDto getBeerOrderById(Integer customerId, Integer orderId) {
        return  beerOrderMapper.beerOrderToDto(beerOrderRepository.findByCustomerIdAndId(customerId, orderId)
                        .orElseThrow(() -> new NotFoundException("Order with customer id " + customerId + " and wit order id : " + orderId + "  not found")));
    }

    @Transactional
    @Override
    public void pickUpOrder(Integer orderId) {
        beerOrderManager.pickUpOrder(orderId);
    }

    public Customer getCustomerById(Integer customerId){
       return customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer with id " + customerId + "not found"));
    }
}
