package com.brewery.beerservice.service;

import com.brewery.beerservice.domain.Beer;
import com.brewery.beerservice.mappers.BeerMapper;
import com.brewery.beerservice.model.BeerDto;
import com.brewery.beerservice.model.BeerInventoryDto;
import com.brewery.beerservice.repository.BeerRepository;
import com.brewery.beerservice.service.clients.InventoryFeingClient;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public BeerDto findBeerByUpc(String upc, Boolean showInventory) {
        Beer beer = beerRepository.findByUpc(upc)
                .orElseThrow(() -> new NotFoundException("Beer with upc " + upc + " not found!"));

        return showInventory ? beerMapper.beerToDtoWithInventory(beer) :  beerMapper.beerToDto(beer);
    }

    @Override
    public BeerDto findBeerById(Integer id) {
        return  beerMapper.beerToDto(beerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Beer with id " + id + " not found!")));
    }

    @Override
    public List<BeerDto> findAllBeers(PageRequest pageRequest) {
        return  beerRepository.findAll(pageRequest)
                                  .stream()
                                  .map(beerMapper::beerToDto)
                                  .collect(Collectors.toList());
    }

    @Override
    public void deleteBeerByUpc(String upc) {
        if(beerRepository.existsByUpc(upc))
            throw  new NotFoundException("Beer with upc " + upc + " not found!");
        beerRepository.deleteByUpc(upc);
    }

    @Override
    public void updateBeerByUpc(String upc, BeerDto beerDto) {
        Beer beerToUpdate = beerRepository.findByUpc(upc)
                .orElseThrow(() -> new NotFoundException("Beer with upc " + upc + " not found!"));
        Beer beer = beerMapper.dtoToBeer(beerDto);
        beer.setCreatedDate(beerToUpdate.getCreatedDate());
        beer.setId(beerToUpdate.getId());
        beerRepository.save(beer);
    }

    @Override
    public BeerDto addNewBeer(BeerDto beerDto) {
        return  beerMapper.beerToDto(beerRepository.save(beerMapper.dtoToBeer(beerDto)));
    }
}
