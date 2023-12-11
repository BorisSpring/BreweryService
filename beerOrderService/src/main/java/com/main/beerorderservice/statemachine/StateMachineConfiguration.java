package com.main.beerorderservice.statemachine;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
@RequiredArgsConstructor
@Slf4j
public class StateMachineConfiguration extends StateMachineConfigurerAdapter<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final Action<BeerOrderStatusEnum, BeerOrderEventEnum> validateOrderAction;
    private final Action<BeerOrderStatusEnum,BeerOrderEventEnum> allocateOrderAction;
    private final Action<BeerOrderStatusEnum, BeerOrderEventEnum> dealocateOrderAction;
    @Override
    public void configure(StateMachineStateConfigurer<BeerOrderStatusEnum, BeerOrderEventEnum> states) throws Exception {
        states.withStates()
                .initial(BeerOrderStatusEnum.NEW)
                .states(EnumSet.allOf(BeerOrderStatusEnum.class))
                .end(BeerOrderStatusEnum.ALLOCATION_EXCEPTION)
                .end(BeerOrderStatusEnum.VALIDATION_FAIL)
                .end(BeerOrderStatusEnum.CANCELED)
                .end(BeerOrderStatusEnum.DELIVERED)
                .end(BeerOrderStatusEnum.DELIVERED_EXCEPTION)
                .end(BeerOrderStatusEnum.PICKED_UP);

    }

    @Override
    public void configure(StateMachineTransitionConfigurer<BeerOrderStatusEnum, BeerOrderEventEnum> transitions) throws Exception {
        transitions.withExternal()
                .source(BeerOrderStatusEnum.NEW).target(BeerOrderStatusEnum.VALIDATION_PENDING)
                    .event(BeerOrderEventEnum.VALIDATE_ORDER).action(validateOrderAction)
                .and().withExternal()
                    .source(BeerOrderStatusEnum.VALIDATION_PENDING).target(BeerOrderStatusEnum.VALIDATED)
                    .event(BeerOrderEventEnum.VALIDATION_PASSED).action(allocateOrderAction)
                .and().withExternal()
                    .source(BeerOrderStatusEnum.VALIDATION_PENDING).target(BeerOrderStatusEnum.VALIDATION_FAIL)
                    .event(BeerOrderEventEnum.VALIDATION_FAILED)
                .and().withExternal()
                    .source(BeerOrderStatusEnum.VALIDATION_PENDING).target(BeerOrderStatusEnum.CANCELED)
                    .event(BeerOrderEventEnum.CANCEL_ORDER)
                .and().withExternal()
                    .source(BeerOrderStatusEnum.VALIDATED).target(BeerOrderStatusEnum.ALLOCATION_PENDING)
                    .event(BeerOrderEventEnum.ALLOCATE_ORDER)
                .and().withExternal()
                    .source(BeerOrderStatusEnum.VALIDATED).target(BeerOrderStatusEnum.CANCELED)
                    .event(BeerOrderEventEnum.CANCEL_ORDER)
                .and().withExternal()
                    .source(BeerOrderStatusEnum.ALLOCATION_PENDING).target(BeerOrderStatusEnum.ALLOCATED)
                    .event(BeerOrderEventEnum.ALLOCATION_PASSED)
                .and().withExternal()
                    .source(BeerOrderStatusEnum.ALLOCATION_PENDING).target(BeerOrderStatusEnum.PENDING_INVENTORY)
                    .event(BeerOrderEventEnum.ALLOCATION_PENDING_INVENTORY)
                .and().withExternal()
                    .source(BeerOrderStatusEnum.ALLOCATION_PENDING).target(BeerOrderStatusEnum.ALLOCATION_EXCEPTION)
                    .event(BeerOrderEventEnum.ALLOCATION_FAIL)
                .and().withExternal()
                    .source(BeerOrderStatusEnum.ALLOCATION_PENDING).target(BeerOrderStatusEnum.CANCELED)
                     .event(BeerOrderEventEnum.CANCEL_ORDER).action(dealocateOrderAction)
                .and().withExternal()
                    .source(BeerOrderStatusEnum.ALLOCATED).target(BeerOrderStatusEnum.CANCELED)
                     .event(BeerOrderEventEnum.CANCEL_ORDER).action(dealocateOrderAction)
                .and().withExternal()
                    .source(BeerOrderStatusEnum.ALLOCATED).target(BeerOrderStatusEnum.DELIVERED)
                     .event(BeerOrderEventEnum.BEER_ORDER_PICKED_UP);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<BeerOrderStatusEnum, BeerOrderEventEnum> config) throws Exception {
        StateMachineListenerAdapter<BeerOrderStatusEnum, BeerOrderEventEnum> listenerAdapter = new StateMachineListenerAdapter<>(){
            @Override
            public void stateChanged(State<BeerOrderStatusEnum, BeerOrderEventEnum> from, State<BeerOrderStatusEnum, BeerOrderEventEnum> to) {
                System.out.println("State changed from " + from.getId() + " to " + to.getId());
            }
        };
        config.withConfiguration().listener(listenerAdapter);
    }
}
