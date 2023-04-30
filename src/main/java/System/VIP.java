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

    public double calculateDiscount(int total_price){
        return (double) total_price - ((double) this.point + 0.1 * total_price);
    }
}
