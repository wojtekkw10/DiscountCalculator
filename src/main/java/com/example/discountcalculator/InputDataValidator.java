package com.example.discountcalculator;

import com.example.discountcalculator.exceptions.DiscountIsNegativeException;
import com.example.discountcalculator.exceptions.TooManyProductsException;

import java.util.List;

public class InputDataValidator {
    private final int productLimit;

    public InputDataValidator(int productLimit) {
        this.productLimit = productLimit;
    }

    public void validateProducts(List<Number> products) throws TooManyProductsException {
        if(products == null){
            throw new IllegalArgumentException("Product list is null");
        }
        else if(products.isEmpty()) {
            throw new IllegalArgumentException("Product list is empty");
        }
        else if(products.size() > productLimit) {
            throw new TooManyProductsException();
        }
    }

    public void validateDiscount(Number discount) throws DiscountIsNegativeException {
        if(discount == null){
            throw new IllegalArgumentException("Discount is null");
        }
        else if(discount.intValue() < 0) {
            throw new DiscountIsNegativeException();
        }
    }
}
