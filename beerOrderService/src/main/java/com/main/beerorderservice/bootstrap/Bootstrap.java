package com.main.beerorderservice.bootstrap;

import com.main.beerorderservice.domain.Customer;
import com.main.beerorderservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    @Override
    public void run(String... args) throws Exception {
        if(customerRepository.count() == 0){
            Customer customer = customerRepository.saveAndFlush(Customer.builder()
                    .birth(LocalDateTime.of(1997, 11, 27, 8, 24, 24, 22))
                    .firstName("Boris")
                    .lastName("Dimitrijevic")
                    .build());
            System.out.println("Saved customer: " + customer);
        }
    }
}
