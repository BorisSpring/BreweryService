package com.main.beerorderservice.statemachine.actions;

import com.main.beerorderservice.domain.BeerOrder;
import com.main.beerorderservice.events.DealocateOrderRequest;
import com.main.beerorderservice.mappers.BeerOrderMapper;
import com.main.beerorderservice.repository.BeerOrderRepository;
import com.main.beerorderservice.service.BeerOrderManagerImpl;
import com.main.beerorderservice.statemachine.BeerOrderEventEnum;
import com.main.beerorderservice.statemachine.BeerOrderStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("dealocateOrderAction")
@RequiredArgsConstructor
public class DealocateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;
    private final StreamBridge streamBridge;
    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
        Optional.ofNullable(context.getMessage().getHeaders().get(BeerOrderManagerImpl.BEER_ORDER_ID_HEADER)).ifPresent(beerOrderId -> {
            BeerOrder beerOrder = beerOrderRepository.findById((Integer) beerOrderId)
                    .orElseThrow(() -> new RuntimeException("Order with id " + beerOrderId + " doesnt exists!"));

            sendDealocateOrderRequest(DealocateOrderRequest.builder()
                    .beerOrderDto(beerOrderMapper.beerOrderToDto(beerOrder))
                    .build());
        });
    }

    public void sendDealocateOrderRequest(DealocateOrderRequest dealocateOrderRequest){
        boolean send = streamBridge.send("dealocate-order-request", dealocateOrderRequest);
        System.out.println("Dealocate order request triggered: " + send);
    }
}
