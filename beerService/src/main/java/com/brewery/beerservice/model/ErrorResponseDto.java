package com.brewery.beerservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
public class ErrorResponseDto {

    private String errMessage;
    private LocalDateTime timestamp;
    private String description;
}
