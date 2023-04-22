package System;

public interface Payment {
    // Function to calculate point
    public int calculatePoint(int total_price);

    // Function to calculate discount
    public double calculateDiscount(int total_price);
}
