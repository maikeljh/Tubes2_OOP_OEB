package Plugin.PluginCashier;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement
public class Discount implements Serializable {
    private String discountName;
    private double discount;

    public Discount(){}

    public Discount(String name, double discount){
        this.discountName = name;
        this.discount = discount;
    }

    public double getDiscount() {
        return discount;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }
}
