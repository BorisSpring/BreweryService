package com.brewery.inventoryservice.model;

import com.brewery.inventoryservice.domain.BeerStyleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@Component
public class BeerDto {

    @NotBlank(message = "Beer name must not be null")
    private String beerName;

    @NotBlank(message = "Beer name must not be null")
    private BeerStyleEnum beerStyle;

    @Positive(message = "Upc must be greather then zero!")
    private String upc;

    @Positive(message = "Quantity must be greather then zero!")
    private Integer quantityToBrew;

    @Positive(message = "Price must be greather then zero!")
    private BigDecimal price;

    @Positive(message = "Min on hand  must be greather then zero!")
    private Integer minOnHand;

    @Null(message = "Id must be null!")
    private Integer id;
}
