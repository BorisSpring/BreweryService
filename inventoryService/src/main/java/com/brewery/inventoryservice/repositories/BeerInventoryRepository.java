package com.brewery.inventoryservice.repositories;

import com.brewery.inventoryservice.domain.BeerInventory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeerInventoryRepository extends JpaRepository<BeerInventory, Integer> {

    @Transactional
    @Modifying
    void deleteByBeerUpc(String beerUpc);

    boolean existsByBeerUpc(String beerUpc);

    List<BeerInventory> findByBeerUpc(String upc);
}
