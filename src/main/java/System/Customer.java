package System;

public class Customer {
    protected int id;
    private static int customerCount = 1;

    Customer() {
        this.id = customerCount;
        customerCount++;
    }

    public static int getCustomerCount() {
        return customerCount;
    }
}
