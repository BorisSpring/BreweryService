package com.brewery.gatewayserver.controllers;

import com.brewery.gatewayserver.model.ContactDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallBackController {

    private  ContactDto contactDto;

    @Autowired
    public FallBackController(ContactDto contactDto) {
        this.contactDto = contactDto;
    }

    @RequestMapping("/contactSupport")
    public Mono<ContactDto> contactSupport(){
      return Mono.just(contactDto);
    };
}
