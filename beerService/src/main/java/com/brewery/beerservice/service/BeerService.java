package com.brewery.beerservice.service;
import com.brewery.beerservice.model.BeerDto;
import org.springframework.data.domain.PageRequest;
import java.util.List;

public interface BeerService {

     BeerDto findBeerByUpc(String upc, Boolean showInventory);

     BeerDto findBeerById(Integer id);

     List<BeerDto> findAllBeers(PageRequest pageRequest);

     void deleteBeerByUpc(String upc);
     void updateBeerByUpc(String upc, BeerDto beerDto);

     BeerDto addNewBeer(BeerDto beerDto);
}
