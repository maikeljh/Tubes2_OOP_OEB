package System;

import java.sql.Blob;

public class Item {
    protected String name;
    protected int stock;
    protected double sell_price;
    protected double buy_price;
    protected String category;
    protected Blob image;

    public Item(String name, int stock, double sell_price, double buy_price, String category, Blob image){
        this.name = name;
        this.stock = stock;
        this.sell_price = sell_price;
        this.buy_price = buy_price;
        this.category = category;
        this.image = image;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setStock(int stock){
        this.stock = stock;
    }

    public void setSellPrice(double sell_price){
        this.sell_price = sell_price;
    }

    public void setBuyPrice(double buy_price){
        this.buy_price = buy_price;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public void setImage(Blob image){
        this.image = image;
    }

    public String getName(){
        return this.name;
    }

    public int getStock(){
        return this.stock;
    }

    public double getSellPrice(){
        return this.sell_price;
    }

    public double getBuyPrice(){
        return this.buy_price;
    }

    public String getCategory(){
        return this.category;
    }

    public Blob getImage(){
        return this.image;
    }
}
