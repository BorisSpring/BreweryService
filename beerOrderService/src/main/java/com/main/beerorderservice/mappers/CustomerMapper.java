package com.main.beerorderservice.mappers;

import com.main.beerorderservice.domain.Customer;
import com.main.beerorderservice.model.CustomerDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

     Customer dtoToCustomer(CustomerDto customerDto);

     CustomerDto customerToDto(Customer customer);
}
