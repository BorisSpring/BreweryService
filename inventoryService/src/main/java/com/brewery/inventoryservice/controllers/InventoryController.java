package com.brewery.inventoryservice.controllers;

import com.brewery.inventoryservice.model.BeerDto;
import com.brewery.inventoryservice.model.BeerInventoryDto;
import com.brewery.inventoryservice.service.BeerInventoryService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final BeerInventoryService beerInventoryService;

    @GetMapping
    public ResponseEntity<List<BeerInventoryDto>> findAll(@Positive(message = "Page size must be greather then zero!") @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                          @Positive(message = "Page number must be greather then zero!") @RequestParam(required = false, defaultValue = "1") Integer pageNumber) {
        return ResponseEntity.ok(beerInventoryService.findAllInventories(PageRequest.of((pageNumber - 1), pageSize)));
    }

    @GetMapping("/upc")
    public ResponseEntity<List<BeerInventoryDto>> findInventoryByBeerUpc(@RequestParam(name = "beerUpc") String beerUpc){
        return  ResponseEntity.ok(beerInventoryService.findInventoryByBeerUpc(beerUpc));
    }
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public  void deleteInventoryByBeerUpc(@RequestParam(name = "upc") String beerUpc){
        beerInventoryService.deleteInventory(beerUpc);
    }

    @PostMapping
    public ResponseEntity<BeerInventoryDto> addInventory(@RequestBody BeerDto beerDto){
        return  ResponseEntity.status(HttpStatus.CREATED).body(beerInventoryService.addInventory(beerDto));
    }

}