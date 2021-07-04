package com.example.discountcalculator;

import com.example.discountcalculator.exceptions.DiscountIsNegativeException;
import com.example.discountcalculator.exceptions.TooManyProductsException;
import org.javamoney.moneta.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication
public class DiscountCalculatorApplication {
    private final static Logger logger = LoggerFactory.getLogger(DiscountCalculatorApplication.class);
    private DiscountCalculator discountCalculator;

    public static void main(String[] args) {
        SpringApplication.run(DiscountCalculatorApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(@Value("${currency}") String currency,
                                               InputDataValidator inputDataValidator,
                                               DiscountCalculator discountCalculator) {
        this.discountCalculator = discountCalculator;
        //INPUT DATA =============================================
        Number inputDiscount = 100;
        List<Number> inputProducts = Arrays.asList(500, 1500);
        //INPUT DATA ==============================================

        return args -> {
            try{
                inputDataValidator.validateDiscount(inputDiscount);
                inputDataValidator.validateProducts(inputProducts);

                Map<Product, Money> discounts = determineDiscounts(inputDiscount, inputProducts, currency);
                logger.info(discounts.toString());
            } catch (DiscountIsNegativeException | TooManyProductsException | IllegalArgumentException e){
                logger.error(e.getMessage());
            }
        };
    }

    private Map<Product, Money> determineDiscounts(Number inputDiscount, List<Number> inputProductCosts, String currency) {
        CurrencyUnit currencyUnit = Monetary.getCurrency(currency);
        Money discount = Money.of(inputDiscount, currencyUnit);
        List<Product> products =  inputProductCosts.stream()
                .map(cost -> new Product(Money.of(cost, currencyUnit))).collect(Collectors.toList());
        return discountCalculator.determineDiscounts(products, discount);
    }

    @Bean
    public DiscountCalculator discountCalculator(){
        return new DiscountCalculator();
    }

    @Bean
    public InputDataValidator inputDataValidator(@Value("${product.limit}") int productLimit){
        return new InputDataValidator(productLimit);
    }
}
