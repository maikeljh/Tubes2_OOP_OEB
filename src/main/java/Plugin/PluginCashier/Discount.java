package Plugin.PluginCashier;

import java.io.Serializable;

public class Discount implements Serializable {
    private String discountName;
    private double discount;

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
}
