package System;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import javafx.scene.image.Image;

import java.io.Serializable;

@XmlRootElement
public class Item implements Serializable {
    protected int item_id;
    protected String name;
    protected int stock;
    protected double sell_price;
    protected double buy_price;
    protected String category;
    protected transient Image image;
    protected static int itemIDCount;

    public Item(){}

    public Item(String name, int stock, double sell_price, double buy_price, String category, Image image){
        itemIDCount++;
        this.item_id = itemIDCount;
        this.name = name;
        this.stock = stock;
        this.sell_price = sell_price;
        this.buy_price = buy_price;
        this.category = category;
        this.image = image;
    }

    public Item(Item other){
        this.item_id = other.item_id;
        this.name = other.name;
        this.stock = other.stock;
        this.sell_price = other.sell_price;
        this.buy_price = other.buy_price;
        this.category = other.category;
        this.image = other.image;
    }

    /* Setter */
    public void setItemID(int id){
        this.item_id = id;
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

    public void setImage(Image image){
        this.image = image;
    }

    /* Getter */
    public int getItemID(){
        return this.item_id;
    }

    public int getItem_id(){
        return this.item_id;
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
    public double getSell_price(){return this.sell_price;}

    public double getBuyPrice(){
        return this.buy_price;
    }
    public double getBuy_price(){return this.buy_price;}

    public String getCategory(){
        return this.category;
    }

    @JsonIgnore
    @XmlTransient
    public Image getImage(){
        return this.image;
    }

    public static void setItemIDCount(int item_count){
        itemIDCount = item_count;
    }
}
