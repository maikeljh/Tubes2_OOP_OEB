package System;

import java.time.LocalDate;

public class Bill {
    /* attributes */
    protected Inventory<PurchasedItem> items;
    protected double total_price;
    protected double discount;
    protected LocalDate date;
    protected int customer_id;

    /* methods */
    public void setTotalPrice(double total_price){
        this.total_price = total_price;
    }
    public void setDiscount(double discount){
        this.discount = discount;
    }
    public void setDate(LocalDate date){
        this.date = date;
    }
    public void setCustomerID(int customer_id){
        this.customer_id = customer_id;
    }

    public Inventory<PurchasedItem> getItems(){
        return items;
    }
    public double getTotalPrice(){
        return total_price;
    }
    public double getDiscount(){
        return discount;
    }
    public LocalDate getDate(){
        return date;
    }
    public int getCustomerID(){
        return customer_id;
    }
    public PurchasedItem getItem(int idx){
        return items.getElement(idx);
    }

    public double calculateDiscount(){
        return discount * total_price;
    }
    public double calculateChargePrice(){
        return total_price - calculateDiscount();
    }
}
