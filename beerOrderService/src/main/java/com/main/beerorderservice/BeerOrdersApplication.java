package com.main.beerorderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BeerOrdersApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeerOrdersApplication.class, args);
	}

}
