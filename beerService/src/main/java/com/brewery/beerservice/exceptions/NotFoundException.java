package com.brewery.beerservice.exceptions;


public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }
}
