package com.brewery.inventoryservice.exceptions;


public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }
}
