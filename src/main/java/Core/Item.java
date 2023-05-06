package Core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor @Getter @Setter
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

    @JsonIgnore
    @XmlTransient
    public Image getImage(){
        return this.image;
    }

    public static void setItemIDCount(int itemCount){
        itemIDCount = itemCount;
    }
}
