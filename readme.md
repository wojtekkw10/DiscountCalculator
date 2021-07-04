# Discount Calculator
It is responsible for calculating discounts proportionally for given discount amount.
E.g <br>
Input data: <br>
Discount: 100zł <br>
Product1 = 500zł <br>
Product2 = 1500zł

Expected result: <br>
Discount1 = 25zł <br>
Discount2 = 75zł <br>

# Design
Application uses SpringBoot for dependency management. 
The application logic is implemented in 2 classes: 
- InputDataValidator
- DiscountCalculator

InputDataValidator is responsible for validating the data according to business requirements as well for handling empty data.
It has 2 public methods:
- public void validateProducts(List\<Number> products)
- public void validateDiscount(Number discount)

DiscountCalculator does proportionate division of the applied discount on the given products.
It has 1 public method: 
- public Map<Product, Money> determineDiscounts(List\<Product> products, Money discount)

Results are displayed in command line console using a logger.


# Assumptions
- Data is received in the form of:
  
        Number inputDiscount = 100;
        List<Number> inputProducts = Arrays.asList(500, 1500);
  

- The currency might change, so it's a property.
- The product limit validation might change, so it's also a property. 
- The discount calculation algorithm might change, so the public function takes a list of **Product**, so it's easy to add additional information about the product necessary for discount calculation.
In case we need 2 different algorithms the interface is easily extractable.
# Run application:
    ./gradlew bootRun
# Run tests
    ./gradlew test



