package System;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor @Getter @Setter
@XmlRootElement
public class PurchasedItem extends Item {
    protected int quantity;


    public PurchasedItem(Item e, int quantity){
        super(e);
        this.quantity = quantity;
    }


    /* Methods */
    public double calculateGrossProfit(){
        return this.quantity * this.sellPrice;
    }
    public double calculateNetProfit(){
        return this.calculateGrossProfit() - (this.quantity * this.buyPrice);
    }
}
