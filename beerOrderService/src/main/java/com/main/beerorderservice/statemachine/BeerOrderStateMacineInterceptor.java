package com.main.beerorderservice.statemachine;

import com.main.beerorderservice.domain.BeerOrder;
import com.main.beerorderservice.repository.BeerOrderRepository;
import com.main.beerorderservice.service.BeerOrderManagerImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BeerOrderStateMacineInterceptor extends StateMachineInterceptorAdapter<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final BeerOrderRepository beerOrderRepository;
    @Override
    public void postStateChange(State<BeerOrderStatusEnum, BeerOrderEventEnum> state, Message<BeerOrderEventEnum> message, Transition<BeerOrderStatusEnum, BeerOrderEventEnum> transition, StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> stateMachine, StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> rootStateMachine) {
        Optional.ofNullable(message).flatMap(msg -> Optional.ofNullable(msg.getHeaders().get(BeerOrderManagerImpl.BEER_ORDER_ID_HEADER))).ifPresent(beerOrderId -> {
            BeerOrder beerOrder = beerOrderRepository.findById((Integer) beerOrderId)
                    .orElseThrow(() -> new RuntimeException("Order with id " + beerOrderId + " not found!"));
            beerOrder.setOrderStatus(state.getId());
            beerOrderRepository.saveAndFlush(beerOrder);
        });
    }


}
