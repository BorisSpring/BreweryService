package com.main.beerorderservice.controllers;
import com.main.beerorderservice.model.BeerOrderDto;
import com.main.beerorderservice.service.BeerOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/customers/{customerId}")
@RequiredArgsConstructor
public class BeerOrderController {

    private final BeerOrderService beerOrderService;

    @GetMapping("/orders")
    public ResponseEntity<Page<BeerOrderDto>> listOrders(@PathVariable("customerId") Integer customerId,
                                                         @RequestParam(required = false, defaultValue = "25" , name = "pageSize") Integer pageSize,
                                                         @RequestParam(required = false, defaultValue = "0", name = "pageNumber") Integer pageNumber){
        return ResponseEntity.ok(beerOrderService.listOrders(customerId, PageRequest.of(pageNumber, pageSize)));
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public BeerOrderDto placeOrder(@PathVariable Integer customerId, @RequestBody BeerOrderDto dto){
        return beerOrderService.placeOrder(customerId, dto);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<BeerOrderDto> getOrder(@PathVariable("customerId") Integer customerId, @PathVariable("orderId") Integer orderId){
        return ResponseEntity.ok(beerOrderService.getBeerOrderById(customerId, orderId));
    }

    @PutMapping("/orders/{orderId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void pickUpOrder(@PathVariable("customerId") Integer customerId , @PathVariable("orderId") Integer orderId){
        beerOrderService.pickUpOrder( orderId);
    }

}
