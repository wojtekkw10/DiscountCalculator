package com.example.discountcalculator;

import com.example.discountcalculator.exceptions.DiscountIsNegativeException;
import com.example.discountcalculator.exceptions.TooManyProductsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InputDataValidatorTest {
    private static InputDataValidator inputDataValidator;

    @BeforeAll
    public static void init(){
        inputDataValidator = new InputDataValidator(5);
    }

    @Test
    void shouldThrowExceptionWhenDiscountIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> inputDataValidator.validateDiscount(null));
    }

    @Test
    void shouldThrowExceptionWhenDiscountIsNegative() {
        Assertions.assertThrows(DiscountIsNegativeException.class, () -> inputDataValidator.validateDiscount(-1));
    }

    @Test
    void shouldThrowExceptionWhenProductsAreNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> inputDataValidator.validateProducts(null));
    }

    @Test
    void shouldThrowExceptionWhenTooManyProductsAreProvided() {
        Assertions.assertThrows(TooManyProductsException.class, () -> {
            inputDataValidator.validateProducts(Arrays.asList(1,1,1,1,1,1,1));
        });
    }

}