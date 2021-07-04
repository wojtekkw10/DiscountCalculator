package com.example.discountcalculator;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DiscountCalculatorTest {
    private static DiscountCalculator discountCalculator;
    private static CurrencyUnit currencyUnit;

    @BeforeAll
    public static void init(){
        discountCalculator = new DiscountCalculator();
        currencyUnit = Monetary.getCurrency("PLN");
    }

    @Test
    void shouldReturnEmptyListForEmptyProductList(){
        Map<Product, Money> result = discountCalculator.determineDiscounts(new ArrayList<>(), Money.of(0, currencyUnit));

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnProportionalDiscounts(){
        Number inputDiscount = 100;
        List<Number> inputProducts = Arrays.asList(500, 1500);
        Money discount = Money.of(inputDiscount, currencyUnit);
        List<Product> products =  inputProducts.stream()
                .map(cost -> new Product(Money.of(cost, currencyUnit))).collect(Collectors.toList());

        Map<Product, Money> result = discountCalculator.determineDiscounts(products, discount);

        Assertions.assertTrue(result.containsValue(Money.of(75, currencyUnit)));
        Assertions.assertTrue(result.containsValue(Money.of(25, currencyUnit)));
    }

    @Test
    void shouldReturnProportionalDiscountsWithRemainder(){
        Number inputDiscount = 100;
        List<Number> inputProducts = Arrays.asList(100, 100, 100);
        Money discount = Money.of(inputDiscount, currencyUnit);
        List<Product> products =  inputProducts.stream()
                .map(cost -> new Product(Money.of(cost, currencyUnit))).collect(Collectors.toList());

        Map<Product, Money> result = discountCalculator.determineDiscounts(products, discount);

        Assertions.assertTrue(result.containsValue(Money.of(33.33, currencyUnit)));
        Assertions.assertTrue(result.containsValue(Money.of(33.34, currencyUnit)));
    }

}