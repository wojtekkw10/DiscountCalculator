package com.example.discountcalculator;

import org.javamoney.moneta.Money;

public class Product {
    private Money cost;

    public Product(Money cost) {
        this.cost = cost;
    }

    public Money getCost() {
        return cost;
    }

    public void setCost(Money cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Product{" +
                "cost=" + cost +
                '}';
    }
}
