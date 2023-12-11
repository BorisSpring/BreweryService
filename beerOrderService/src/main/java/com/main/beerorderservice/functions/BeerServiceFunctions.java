package com.main.beerorderservice.functions;

import com.main.beerorderservice.events.ValidateOrderResult;
import com.main.beerorderservice.service.BeerOrderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class BeerServiceFunctions {

    private final BeerOrderManager beerOrderManager;
    @Bean
    Consumer<ValidateOrderResult> validationResult(){
        return validateOrderResult -> {
            System.out.println(validateOrderResult);
            beerOrderManager.processValidationResult(validateOrderResult.getBeerOrderId(), validateOrderResult.isValid());
        };
    }
}
