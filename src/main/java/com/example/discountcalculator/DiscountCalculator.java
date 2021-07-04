package com.example.discountcalculator;

import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DiscountCalculator {
    public Map<Product, Money> determineDiscounts(List<Product> products, Money discount) {
        if(products.isEmpty()) {
            return new HashMap<>();
        }

        List<Money> appliedDiscounts = calculateDiscountsProportionally(products, discount);
        Money remainingDiscount = calculateRemainingDiscount(discount, sumProductCosts(products), appliedDiscounts);
        appliedDiscounts.get(appliedDiscounts.size()-1).add(remainingDiscount);
        return zipToMap(products, appliedDiscounts);
    }

    private Money calculateRemainingDiscount(Money discount, Money totalProductCost, List<Money> appliedDiscounts) {
        Money totalAppliedDiscounts = appliedDiscounts.stream().reduce(Money.of(0, discount.getCurrency()), Money::add);
        return totalProductCost.subtract(totalAppliedDiscounts);
    }

    private List<Money> calculateDiscountsProportionally(List<Product> products, Money discount) {
        Number totalProductCost = sumProductCosts(products).getNumber();

        return products.stream()
                .map(Product::getCost)
                .map(cost -> cost.divide(totalProductCost).multiply(discount.getNumber()))
                .collect(Collectors.toList());
    }

    private Money sumProductCosts(List<Product> products) {
        CurrencyUnit currency = products.get(0).getCost().getCurrency();
        return products.stream().map(Product::getCost).reduce(Money.of(0, currency), Money::add);
    }

    private <K, V> Map<K, V> zipToMap(List<K> keys, List<V> values) {
        return IntStream.range(0, keys.size()).boxed()
                .collect(Collectors.toMap(keys::get, values::get));
    }
}
