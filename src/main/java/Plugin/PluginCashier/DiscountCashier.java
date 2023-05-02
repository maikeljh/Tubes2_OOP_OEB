package Plugin.PluginCashier;

import Plugin.Decorator.CashierDecorator;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.Serializable;

public class DiscountCashier extends CashierDecorator implements Serializable {
    private double totalDiscount = 0;
    private double taxCharge = 0;
    private double serviceCharge = 0;

    public DiscountCashier(){
        this.pluginName = "Plugin Discount";
        this.totalDiscount = 0;
    }

    @Override
    public void execute() {
        // Create Discount, Tax, and Service Label
        Label discount = new Label("Discount: Rp" + totalDiscount);
        Label tax = new Label("Tax: Rp" + taxCharge);
        Label service = new Label("Service: Rp" + serviceCharge);

        // Create VBox for these labels
        VBox additionalCharge = new VBox();
        additionalCharge.getChildren().addAll(discount, tax, service);
        additionalCharge.setSpacing(3);
        additionalCharge.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(additionalCharge, Priority.ALWAYS);

        // Add additional charge to page
        this.page.getTotalPriceBox().getChildren().add(additionalCharge);
    }
}

class Discount {
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