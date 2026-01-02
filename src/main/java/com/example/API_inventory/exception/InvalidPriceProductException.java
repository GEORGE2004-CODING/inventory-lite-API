package com.example.API_inventory.exception;

public class InvalidPriceProductException extends RuntimeException {
    public InvalidPriceProductException(String message) {
        super(message);
    }
}
