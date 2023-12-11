package com.brewery.beerservice.model;

import com.brewery.beerservice.domain.BeerStyleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@ToString
@Getter
@Setter
public class BeerDto {


    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    private Integer version ;

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

    private Integer quantityOnHand = 0;
}
