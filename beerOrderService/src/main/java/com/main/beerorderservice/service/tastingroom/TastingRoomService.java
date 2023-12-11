package com.main.beerorderservice.service.tastingroom;
import com.main.beerorderservice.model.BeerDto;
import com.main.beerorderservice.model.BeerOrderDto;
import com.main.beerorderservice.model.BeerOrderLineDto;
import com.main.beerorderservice.repository.CustomerRepository;
import com.main.beerorderservice.service.BeerOrderService;
import com.main.beerorderservice.service.clients.BeerServiceFeingClient;
import com.main.beerorderservice.statemachine.BeerOrderStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TastingRoomService {

    private final BeerServiceFeingClient beerServiceFeingClient;
    private final CustomerRepository customerRepository;
    private final BeerOrderService beerOrderService;
    @Transactional
    @Scheduled(fixedRate = 5000)
    public void placeOrder(){

        if(customerRepository.count() > 0){
            ResponseEntity<BeerDto> beerByUpc = beerServiceFeingClient.findBeerByUpc(getRandomBeerUpc(), false);
                if(beerByUpc != null && beerByUpc.getBody() != null) {
                    BeerDto beerDto = beerByUpc.getBody();
                    Random random = new Random();
                    int randomOrderQuantity = random.nextInt(10);

                    BeerOrderLineDto beerOrderLine = BeerOrderLineDto.builder()
                            .beerId(beerDto.getId())
                            .beerName(beerDto.getBeerName())
                            .beerStyle(beerDto.getBeerStyle())
                            .upc(beerDto.getUpc())
                            .quantityAllocated(0)
                            .orderQuantity(randomOrderQuantity)
                            .totalPrice((new BigDecimal(randomOrderQuantity).multiply(beerDto.getPrice())))
                            .build();


                    BeerOrderDto beerOrderDto = BeerOrderDto.builder()
                            .beerOrderLines(Collections.singleton(beerOrderLine))
                            .totalPrice(beerOrderLine.getTotalPrice())
                            .orderStatus(BeerOrderStatusEnum.NEW)
                            .quantityOrdered(randomOrderQuantity)
                            .quantityAllocated(0)
                            .quantityOrdered(randomOrderQuantity)
                            .customerId(1)
                            .build();

                    beerOrderService.placeOrder(1, beerOrderDto);
                }
            }
    }


    public String getRandomBeerUpc(){
        List<String> list = Arrays.asList("123456789", "12345678", "1234567", "123456", "12345");
        return list.get(new Random().nextInt(4));
    }

}
