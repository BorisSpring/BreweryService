package com.main.beerorderservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Customer extends BaseEntity{

    @Builder
    public Customer(Integer id, Long version, LocalDateTime createdDate, LocalDateTime lastModifiedDate, String lastName, String firstName, LocalDateTime birth, Set<BeerOrder> beerOrders) {
        super(id, version, createdDate, lastModifiedDate);
        this.lastName = lastName;
        this.firstName = firstName;
        this.birth = birth;
        this.beerOrders = beerOrders;
    }

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false, updatable = false)
    private LocalDateTime birth;

    @OneToMany
    private Set<BeerOrder> beerOrders;
}
