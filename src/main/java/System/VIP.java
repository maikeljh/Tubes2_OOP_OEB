package System;

public class VIP extends RegisteredCustomer{
    public VIP(int customerId, String name, String phoneNumber, FixedBill bill) {
        super(customerId, name, phoneNumber, bill);
    }
    public double calculateDiscount(double total_price, boolean point){

        double result = 0;
        if (point){
            if ( this.point > total_price){
                this.point -= total_price;
            } else {
                result =  total_price - ((double) this.point + 0.1 * total_price);
                this.point = 0;
            }
        } else {
            result =  total_price - ( 0.1 * total_price);
        }
        
        return result;
    }
}
