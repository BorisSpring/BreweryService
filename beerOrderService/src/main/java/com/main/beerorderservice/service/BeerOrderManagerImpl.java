package com.main.beerorderservice.service;

import com.main.beerorderservice.domain.BeerOrder;
import com.main.beerorderservice.model.BeerOrderDto;
import com.main.beerorderservice.repository.BeerOrderRepository;
import com.main.beerorderservice.statemachine.BeerOrderEventEnum;
import com.main.beerorderservice.statemachine.BeerOrderStateMacineInterceptor;
import com.main.beerorderservice.statemachine.BeerOrderStatusEnum;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BeerOrderManagerImpl implements BeerOrderManager {

    public  static  final String BEER_ORDER_ID_HEADER = "beer_order_id";
    private final StateMachineFactory<BeerOrderStatusEnum, BeerOrderEventEnum> stateMachineFactory;
    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderStateMacineInterceptor beerOrderStateMacineInterceptor;

    @Transactional
    @Override
    public BeerOrder createOrder(BeerOrder beerOrder) {
         beerOrder.setId(null);
         beerOrder.setOrderStatus(BeerOrderStatusEnum.NEW);
         BeerOrder savedBeerOrder = beerOrderRepository.save(beerOrder);
         sendBeerOrderEvent(savedBeerOrder  , BeerOrderEventEnum.VALIDATE_ORDER);
         return savedBeerOrder;
    }

    @Transactional
    @Override
    public void processValidationResult(Integer orderId, boolean valid) {
        BeerOrder beerOrder = findOrderById(orderId);
        sendBeerOrderEvent(beerOrder, valid ? BeerOrderEventEnum.VALIDATION_PASSED : BeerOrderEventEnum.VALIDATION_FAILED);
    }

    @Transactional
    @Override
    public void processAllocationFail(BeerOrderDto beerOrderDto) {
        BeerOrder beerOrder = findOrderById(beerOrderDto.getId());
        sendBeerOrderEvent(beerOrder, BeerOrderEventEnum.ALLOCATION_FAIL);
    }

    @Transactional
    @Override
    public void processAllocationPassed(BeerOrderDto beerOrderDto) {
        BeerOrder beerOrder = findOrderById(beerOrderDto.getId());
        sendBeerOrderEvent(beerOrder, BeerOrderEventEnum.ALLOCATION_PASSED);
    }

    @Transactional
    @Override
    public void beerOrderAllocationPending(BeerOrderDto beerOrderDto) {
        BeerOrder beerOrder = findOrderById(beerOrderDto.getId());
        sendBeerOrderEvent(beerOrder, BeerOrderEventEnum.ALLOCATION_PENDING_INVENTORY);
    }

    @Transactional
    @Override
    public void cancelOrder(Integer orderId) {
        BeerOrder beerOrder = findOrderById(orderId);
        sendBeerOrderEvent(beerOrder, BeerOrderEventEnum.CANCEL_ORDER);
    }

    @Transactional
    @Override
    public void pickUpOrder(Integer orderId) {
        BeerOrder beerOrder = findOrderById(orderId);
        sendBeerOrderEvent(beerOrder, BeerOrderEventEnum.BEER_ORDER_PICKED_UP);
    }

    private void sendBeerOrderEvent(BeerOrder beerOrder, BeerOrderEventEnum event){
        System.out.println("Sending event: " + event.toString());
        StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> stateMachine = build(beerOrder);

        Message<BeerOrderEventEnum> message = MessageBuilder
                                        .withPayload(event)
                                        .setHeader(BEER_ORDER_ID_HEADER, beerOrder.getId())
                                        .build();
        stateMachine.sendEvent(message);
    }

    private StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> build(BeerOrder beerOrder){
        StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> stateMachine = stateMachineFactory.getStateMachine(beerOrder.getId().toString());
        stateMachine.stop();
        stateMachine.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                    sma.addStateMachineInterceptor(beerOrderStateMacineInterceptor);
                    sma.resetStateMachine(new DefaultStateMachineContext<>(beerOrder.getOrderStatus(), null ,null , null));
                });
        stateMachine.start();
        return  stateMachine;
    }

    private BeerOrder findOrderById(Integer orderId){
        return  beerOrderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order with id " + orderId + " not found!"));
    }
}
