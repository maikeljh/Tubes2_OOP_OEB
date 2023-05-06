package System;

public interface Payment {
    // Function to calculate point
    public int calculatePoint(int totalPrice);

    // Function to calculate discount
    public double calculateDiscount(int totalPrice);
}
