package System;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement
public class Bill implements Serializable {
    /* attributes */
    protected Inventory<PurchasedItem> items;
    protected double total_price;
    protected double discount;
    protected String date;
    protected int customer_id;

    public Bill(){
        items = new Inventory<PurchasedItem>();
    }
    public Bill(String date, int customer_id) { this.date = date; this.customer_id = customer_id; }

    /* methods */
    public void setTotalPrice(double total_price){
        this.total_price = total_price;
    }
    public void setDiscount(double discount){
        this.discount = discount;
    }
    public void setDate(String date){
        this.date = date;
    }
    public void setCustomerID(int customer_id){
        this.customer_id = customer_id;
    }
    public void setItems(Inventory<PurchasedItem> list){
        this.items = list;
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
    public String getDate(){
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
