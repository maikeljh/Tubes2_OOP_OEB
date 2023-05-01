package System;

import java.io.Serializable;

public class Member extends RegisteredCustomer {
    public Member(int customerId, String name, String phoneNumber, FixedBill bill) {
        super(customerId, name, phoneNumber, bill);
    }

    public double calculateDiscount(double total_price, boolean point){
        double result = 0;
        if(point){
            if ( this.point > total_price){
                this.point -= total_price;
            } else {
                result = total_price - (double) this.point;
                this.point = 0;
            }
        } else {
            result = total_price;
        }
        return result;
    }
}
