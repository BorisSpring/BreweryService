package com.main.beerorderservice.repository;


import com.main.beerorderservice.domain.BeerOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BeerOrderLineRepository extends PagingAndSortingRepository<BeerOrder, UUID> {
}
