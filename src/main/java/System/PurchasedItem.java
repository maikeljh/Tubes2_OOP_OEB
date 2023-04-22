package System;

public class PurchasedItem extends Item {
    protected int quantity;

    public PurchasedItem(Item e, int quantity){
        super(e.name, e.stock, e.sell_price, e.buy_price, e.category, e.image);
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
}
