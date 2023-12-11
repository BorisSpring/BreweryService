package com.main.beerorderservice.statemachine.actions;

import com.main.beerorderservice.domain.BeerOrder;
import com.main.beerorderservice.events.ValidateOrderRequest;
import com.main.beerorderservice.mappers.BeerOrderMapper;
import com.main.beerorderservice.repository.BeerOrderRepository;
import com.main.beerorderservice.service.BeerOrderManagerImpl;
import com.main.beerorderservice.statemachine.BeerOrderEventEnum;
import com.main.beerorderservice.statemachine.BeerOrderStatusEnum;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("validateOrderAction")
@RequiredArgsConstructor
@Slf4j
public class ValidateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final BeerOrderRepository beerOrderRepository;
    private final StreamBridge streamBridge;
    private final BeerOrderMapper beerOrderMapper;
    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
        Integer beerOrderId = (Integer) Objects.requireNonNull(context.getMessage().getHeaders().get(BeerOrderManagerImpl.BEER_ORDER_ID_HEADER));
        BeerOrder beerOrder = beerOrderRepository.findById(beerOrderId)
                .orElseThrow(() -> new NotFoundException("Order with id " + beerOrderId + " not found!"));

        ValidateOrderRequest validateOrderRequest = ValidateOrderRequest.builder()
                .beerOrderDto(beerOrderMapper.beerOrderToDto(beerOrder))
                .build();
        validationRequest(validateOrderRequest);
    }

    private void validationRequest(ValidateOrderRequest validateOrderRequest){
       streamBridge.send("validationRequest-out-0", validateOrderRequest);
    }
}
