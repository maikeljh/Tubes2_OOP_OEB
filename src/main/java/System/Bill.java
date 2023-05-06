package System;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement
public class Bill implements Serializable {
    /* attributes */
    protected Inventory<PurchasedItem> items;
    protected double totalPrice;
    protected double discount;
    protected String date;
    protected int customerID;

    public Bill(){
        items = new Inventory<PurchasedItem>();
    }

    public Bill(String date) {
        this.date = date;
        items = new Inventory<PurchasedItem>();
    }

    public Bill(String date, int customerId) {
        this.date = date; this.customerID = customerID;
        items = new Inventory<PurchasedItem>();
    }

    /* methods */
    public void setTotalPrice(double totalPrice){
        this.totalPrice = totalPrice;
    }
    public void setDiscount(double discount){
        this.discount = discount;
    }
    public void setDate(String date){
        this.date = date;
    }
    public void setCustomerID(int customerID){
        this.customerID = customerID;
    }
    public void setItems(Inventory<PurchasedItem> list){
        this.items = list;
    }

    public Inventory<PurchasedItem> getItems(){
        return items;
    }
    public double getTotalPrice(){
        return totalPrice;
    }
    public double getDiscount(){
        return discount;
    }
    public String getDate(){
        return date;
    }
    public int getCustomerID(){
        return customerID;
    }
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
