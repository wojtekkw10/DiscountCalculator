package com.example.discountcalculator.exceptions;

public class TooManyProductsException extends Exception {
    public TooManyProductsException(){
        super("Provided too many products");
    }
}
