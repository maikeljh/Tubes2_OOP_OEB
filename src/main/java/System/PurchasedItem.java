package System;

public class PurchasedItem extends Item {
    private int quantity;

    public PurchasedItem(Item e, int quantity){
        super(e.name, e.stock, e.sell_price, e.buy_price, e.category, e.image);
        this.quantity = quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public int getQuantity(){
        return this.quantity;
    }
}
