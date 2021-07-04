package com.example.discountcalculator.exceptions;

public class DiscountIsNegativeException extends Exception {
    public DiscountIsNegativeException(){
        super("Provided discount is negative");
    }
}
