package com.main.beerorderservice.service.customer;
import com.main.beerorderservice.domain.Customer;
import com.main.beerorderservice.mappers.CustomerMapper;
import com.main.beerorderservice.model.CustomerDto;
import com.main.beerorderservice.repository.CustomerRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    @Override
    public Page<Customer> listAll(Pageable pageable) {
        return  customerRepository.findAll(pageable);
   }

    @Override
    public CustomerDto findCustomerById(Integer customerId) {
        return  customerMapper.customerToDto(customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer with id " + customerId + " not found!")));
    }

}
