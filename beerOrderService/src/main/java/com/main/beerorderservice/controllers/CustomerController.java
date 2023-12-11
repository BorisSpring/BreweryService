package com.main.beerorderservice.controllers;


import com.main.beerorderservice.domain.Customer;
import com.main.beerorderservice.model.CustomerDto;
import com.main.beerorderservice.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@RestController
public class CustomerController {

    private final String PAGE_SIZE = "15";
    private final String PAGE_NUMBER = "0";
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<Page<Customer>> findAllCustomers(@RequestParam(name="pageSize", defaultValue = PAGE_SIZE) Integer pageSize,
                                              @RequestParam(name = "pageNumber", defaultValue = PAGE_NUMBER ) Integer pageNumber){
        return ResponseEntity.ok(customerService.listAll(PageRequest.of(pageNumber, pageSize)));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> findCustomer(@PathVariable("customerId") Integer customerId){
        return  ResponseEntity.ok(customerService.findCustomerById(customerId));
    }
}
