package System;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@NoArgsConstructor
@XmlRootElement
public class Member extends RegisteredCustomer {

    public Member(int customerId, String name, String phoneNumber, Bill bill) {
        super(customerId, name, phoneNumber, bill);
    }

    public Member(int customerId, String name, String phoneNumber, int point, boolean activeStatus, Inventory<Bill> bills) {
        super(customerId, name, phoneNumber, point, activeStatus);
        this.setTransaction(bills);
    }

    public double calculateDiscount(double totalPrice, boolean point){
        if (isActiveStatus()) {
            double result = 0;
            if(point){
                if ( this.point > totalPrice){
                    this.point -= totalPrice;
                } else {
                    result = totalPrice - (double) this.point;
                    this.point = 0;
                }
            } else {
                result = totalPrice;
            }
            return result;
        }
        return totalPrice;
    }
}
