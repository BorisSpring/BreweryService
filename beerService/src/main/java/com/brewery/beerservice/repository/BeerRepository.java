package com.brewery.beerservice.repository;

import com.brewery.beerservice.domain.Beer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Integer> {
    Optional<Beer> findByUpc(String upc);

    @Modifying
    @Transactional
    void deleteByUpc(String upc);

    boolean existsByUpc(String upc);

    long countAllByIdIn(Set<Integer> collect);

}
