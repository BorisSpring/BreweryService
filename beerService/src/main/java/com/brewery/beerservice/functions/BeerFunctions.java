package com.brewery.beerservice.functions;

import com.brewery.beerservice.domain.BeerOrderLineDto;
import com.brewery.beerservice.events.ValidateOrderRequest;
import com.brewery.beerservice.events.ValidateOrderResult;
import com.brewery.beerservice.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class BeerFunctions {

    private final BeerRepository beerRepository;

    @Bean
    Function <ValidateOrderRequest,ValidateOrderResult> validate(){
        return orderValidationRequest -> {
            System.out.println("Validation request received!");
            Set<Integer> collect = orderValidationRequest.getBeerOrderDto().getBeerOrderLines().stream()
                    .mapToInt(BeerOrderLineDto::getBeerId)
                    .boxed()
                    .collect(Collectors.toSet());
            return  ValidateOrderResult.builder()
                    .beerOrderId(orderValidationRequest.getBeerOrderDto().getId())
                    .isValid((beerRepository.countAllByIdIn(collect) == orderValidationRequest.getBeerOrderDto().getBeerOrderLines().size()))
                    .build();
        };
    }

    @Bean
    Function<ValidateOrderResult, ValidateOrderResult> result(){
        return  validateOrderResult -> {
            System.out.println("Order validation result is: " + validateOrderResult.isValid());
            return  validateOrderResult;
        };
    }

}
