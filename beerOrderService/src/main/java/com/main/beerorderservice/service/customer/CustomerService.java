package com.main.beerorderservice.service.customer;

import com.main.beerorderservice.domain.Customer;
import com.main.beerorderservice.model.CustomerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CustomerService {

     Page<Customer> listAll(Pageable pageable);

    CustomerDto findCustomerById(Integer customerId);
}
