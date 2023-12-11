package com.brewery.inventoryservice.service;

import com.brewery.inventoryservice.domain.BeerInventory;
import com.brewery.inventoryservice.exceptions.NotFoundException;
import com.brewery.inventoryservice.mapper.BeerInventoryMapper;
import com.brewery.inventoryservice.model.BeerDto;
import com.brewery.inventoryservice.model.BeerInventoryDto;
import com.brewery.inventoryservice.repositories.BeerInventoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BeerInventoryServiceImpl implements BeerInventoryService {

    private final BeerInventoryRepository beerInventoryRepository;
    private final BeerInventoryMapper beerInventoryMapper;

    @Override
    public BeerInventoryDto addInventory(BeerDto beerDto) {
        return  beerInventoryMapper.beerInventoryToDto(beerInventoryRepository.save(BeerInventory.builder()
                                                                .beerId(beerDto.getId())
                                                                .beerUpc(beerDto.getUpc())
                                                                .quantityOnHand(beerDto.getQuantityToBrew())
                                                                .build()));
    }

    @Transactional
    @Override
    public void deleteInventory(String beerUpc) {
        if(!beerInventoryRepository.existsByBeerUpc(beerUpc))
            throw new NotFoundException("Beer inventory for beer with upc " + beerUpc + " not found!");

        beerInventoryRepository.deleteByBeerUpc(beerUpc);
    }

    @Override
    public List<BeerInventoryDto> findAllInventories(PageRequest pageRequest) {
      return  beerInventoryRepository.findAll(pageRequest)
                                      .stream()
                                      .map(beerInventoryMapper::beerInventoryToDto)
                                      .collect(Collectors.toList());
    }

    @Override
    public List<BeerInventoryDto> findInventoryByBeerUpc(String upc) {
        return  beerInventoryRepository.findByBeerUpc(upc)
                                    .stream()
                                    .map(beerInventoryMapper::beerInventoryToDto)
                                    .collect(Collectors.toList());
    }
}
