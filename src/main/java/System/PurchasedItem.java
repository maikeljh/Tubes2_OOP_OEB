package System;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement
public class PurchasedItem extends Item {
    protected int quantity;

    public PurchasedItem(){
        super();
    }

    public PurchasedItem(Item e, int quantity){
        super(e);
        this.quantity = quantity;
    }

    /* Setter */
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    /* Getter */
    public int getQuantity(){
        return this.quantity;
    }

    /* Methods */
    public double calculateGrossProfit(){
        return this.quantity * this.sell_price;
    }
    public double calculateNetProfit(){
        return this.calculateGrossProfit() - (this.quantity * this.buy_price);
    }
}
