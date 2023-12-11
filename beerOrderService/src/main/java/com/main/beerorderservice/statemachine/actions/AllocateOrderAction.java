package com.main.beerorderservice.statemachine.actions;

import com.main.beerorderservice.domain.BeerOrder;
import com.main.beerorderservice.events.AllocateOrderRequest;
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

@Component("allocateOrderAction")
@RequiredArgsConstructor
public class AllocateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final BeerOrderRepository beerOrderRepository;
    private final StreamBridge streamBridge;
    private final BeerOrderMapper beerOrderMapper;
    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
        Optional.ofNullable(context.getMessage().getHeaders().get(BeerOrderManagerImpl.BEER_ORDER_ID_HEADER)).ifPresent(beerOrderId -> {
            BeerOrder beerOrder = beerOrderRepository.findById((Integer) beerOrderId)
                    .orElseThrow(() -> new RuntimeException("Order with id " + beerOrderId + " doesnt exists"));

            sendAllocationRequest(AllocateOrderRequest.builder()
                    .beerOrderDto(beerOrderMapper.beerOrderToDto(beerOrder))
                    .build());
        });
    }

    private void sendAllocationRequest(AllocateOrderRequest allocateOrderRequest){
        boolean send = streamBridge.send("allocation-request", allocateOrderRequest);
        System.out.println("Allocation request trigered: " + send);
    }
}
