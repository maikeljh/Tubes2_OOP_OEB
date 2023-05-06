package System;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor @Getter @Setter
@XmlRootElement
public class Bill implements Serializable {
    /* attributes */
    protected Inventory<PurchasedItem> items;
    protected double totalPrice;
    protected double discount;
    protected String date;
    protected int customerID;

    public Bill(String date) {
        this.date = date;
        items = new Inventory<PurchasedItem>();
    }

    public Bill(String date, int customerId) {
        this.date = date; this.customerID = customerID;
        items = new Inventory<PurchasedItem>();
    }

    /* methods */
    public PurchasedItem getItem(int idx){
        return items.getElement(idx);
    }

    public double calculateDiscount(){
        return discount * totalPrice;
    }
    public double calculateChargePrice(){
        return totalPrice - calculateDiscount();
    }
}
