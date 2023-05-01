package System;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class VIP extends RegisteredCustomer{
    public VIP() {
        super();
    }

    public VIP(int customerId, String name, String phoneNumber, FixedBill bill) {
        super(customerId, name, phoneNumber, bill);
    }

    public VIP(int customerId, String name, String phoneNumber, int point, boolean activeStatus, Inventory<FixedBill> bills) {
        super(customerId, name, phoneNumber, point, activeStatus);
        this.setTransaction(bills);
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
