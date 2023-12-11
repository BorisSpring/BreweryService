package com.main.beerorderservice.model;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CustomerDto {

    private Integer id;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    private Integer version ;

    private String firstName;
    private String lastName;
}
