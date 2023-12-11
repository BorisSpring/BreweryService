package com.brewery.beerservice.bootstrap;

import com.brewery.beerservice.domain.Beer;
import com.brewery.beerservice.domain.BeerStyleEnum;
import com.brewery.beerservice.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

    private final BeerRepository beerRepository;

    @Override
    public void run(String... args) throws Exception {
        addBeer("NIKSICKO","LAGER","123456789",500, 200, new BigDecimal("9.25"));
        addBeer("JELEN","LAGER","12345678",300, 100, new BigDecimal("2.25"));
        addBeer("LAV","LAGER","1234567",250, 50, new BigDecimal("8.25"));
        addBeer("TUBORG","LAGER","123456",500, 200, new BigDecimal("5.25"));
        addBeer("TUBORG","LAGER","12345",500, 200, new BigDecimal("5.25"));

    }

    private Beer addBeer(String beerName, String beerStyle, String upc, Integer quantityToBrew, Integer minOnHand, BigDecimal price) {
        return  beerRepository.save(Beer.builder()
                        .beerName(beerName)
                        .upc(upc)
                        .beerStyle(BeerStyleEnum.valueOf(beerStyle))
                        .quantityToBrew(quantityToBrew)
                        .minOnHand(minOnHand)
                        .price(price)
                         .build());
    }
}
