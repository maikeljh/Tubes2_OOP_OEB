package System;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import javafx.scene.image.Image;

import java.io.Serializable;

@XmlRootElement
public class Item implements Serializable {
    protected int itemID;
    protected String name;
    protected int stock;
    protected double sellPrice;
    protected double buyPrice;
    protected String category;
    protected transient Image image;
    protected static int itemIDCount;

    public Item(){}

    public Item(String name, int stock, double sellPrice, double buyPrice, String category, Image image){
        itemIDCount++;
        this.itemID = itemIDCount;
        this.name = name;
        this.stock = stock;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
        this.category = category;
        this.image = image;
    }

    public Item(Item other){
        this.itemID = other.itemID;
        this.name = other.name;
        this.stock = other.stock;
        this.sellPrice = other.sellPrice;
        this.buyPrice = other.buyPrice;
        this.category = other.category;
        this.image = other.image;
    }

    /* Setter */
    public void setItemID(int id){
        this.itemID = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setStock(int stock){
        this.stock = stock;
    }

    public void setSellPrice(double sellPrice){
        this.sellPrice = sellPrice;
    }

    public void setBuyPrice(double buyPrice){
        this.buyPrice = buyPrice;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public void setImage(Image image){
        this.image = image;
    }

    /* Getter */
    public int getItemID(){
        return this.itemID;
    }

    public String getName(){
        return this.name;
    }

    public int getStock(){
        return this.stock;
    }

    public double getSellPrice(){
        return this.sellPrice;
    }

    public double getBuyPrice(){
        return this.buyPrice;
    }

    public String getCategory(){
        return this.category;
    }

    @JsonIgnore
    @XmlTransient
    public Image getImage(){
        return this.image;
    }

    public static void setItemIDCount(int itemCount){
        itemIDCount = itemCount;
    }
}
