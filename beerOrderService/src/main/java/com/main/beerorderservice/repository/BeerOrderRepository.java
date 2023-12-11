package com.main.beerorderservice.repository;

import com.main.beerorderservice.domain.BeerOrder;
import com.main.beerorderservice.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BeerOrderRepository extends JpaRepository<BeerOrder, Integer> {


    Page<BeerOrder> findAllByCustomer(Customer customer, Pageable pageable);

    Optional<BeerOrder> findByCustomerIdAndId(Integer customerId, Integer orderId);
}
