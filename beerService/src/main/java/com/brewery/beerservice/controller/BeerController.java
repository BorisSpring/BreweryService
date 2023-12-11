package com.brewery.beerservice.controller;

import com.brewery.beerservice.model.BeerDto;
import com.brewery.beerservice.service.BeerService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/beers")
public class BeerController {

    private final BeerService beerService;

//    @Retry(name = "findBeerByUpc" , fallbackMethod = "findBeerByUpcFallBack" )
//    @RateLimiter(name = "findBeerByUpcRateLimiter", fallbackMethod = "findBeerByUpcFallBack")
    @GetMapping("/upc")
    public ResponseEntity<BeerDto> findBeerByUpc(@Positive(message = "Beer upc must be greather then zero!") @RequestParam String beerUpc,
                                                 @RequestParam(name = "showInventory", required = false) boolean showInventory){
        return  ResponseEntity.ok(beerService.findBeerByUpc(beerUpc,showInventory));
    };

//
//    public ResponseEntity<BeerDto> findBeerByUpcFallBack(@Positive(message = "Beer upc must be greather then zero!") @RequestParam String upc,
//                                                         @RequestParam(name = "showInventory", required = false , defaultValue = "false") Boolean showInventory,
//                                                         Throwable throwable){
//        return  ResponseEntity.ok(null);
//    };



    @GetMapping("/id/{id}")
    public ResponseEntity<BeerDto> findBeerById(@Positive(message = "Beer id must be greather then zero!") @PathVariable Integer id){
        return  ResponseEntity.ok(beerService.findBeerById(id));
    };

    @GetMapping
    public ResponseEntity<List<BeerDto>> findAll(@Positive(message = "Page size must be greather then zero!") @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                 @Positive(message = "Page number must be greather then zero!") @RequestParam(required = false, defaultValue = "1") Integer pageNumber){
        return  ResponseEntity.ok(beerService.findAllBeers(PageRequest.of((pageNumber - 1), pageSize)));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeerByUpc(@Positive(message = "Beer upc must be greather then zero!") @RequestParam(name = "upc") String upc){
        beerService.deleteBeerByUpc(upc);
    }

    @PutMapping("/upc/{upc}")
    @ResponseStatus(HttpStatus.OK)
    public void updateBeerByUpc(@Positive(message = "Beer upc must be greather then zero!") @PathVariable String upc,
                                @Valid @RequestBody BeerDto beerDto){
        beerService.updateBeerByUpc(upc, beerDto);
    }

    @PostMapping
    public ResponseEntity<BeerDto> addBeer(@Valid @RequestBody BeerDto beerDto){
        return  ResponseEntity.status(HttpStatus.CREATED).body(beerService.addNewBeer(beerDto));
    }
}
