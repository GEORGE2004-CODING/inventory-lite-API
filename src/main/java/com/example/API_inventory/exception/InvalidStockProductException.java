package com.example.API_inventory.exception;

public class InvalidStockProductException extends RuntimeException {
    public InvalidStockProductException(String message) {
        super(message);
    }
}
