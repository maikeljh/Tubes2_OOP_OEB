package System;

import java.io.Serializable;

public class Member extends RegisteredCustomer {
    public Member(int customerId, String name, String phoneNumber, FixedBill bill) {
        super(customerId, name, phoneNumber, bill);
    }

    public double calculateDiscount(int total_price){
        return (double) total_price - (double) this.point;
    }
}
