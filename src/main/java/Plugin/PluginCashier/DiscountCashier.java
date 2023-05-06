package Plugin.PluginCashier;

import Plugin.Decorator.CashierDecorator;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.io.Serializable;
import System.Inventory;
import DataStore.DataStore;

public class DiscountCashier extends CashierDecorator implements Serializable {
    private TaxAndService TAS;
    private DataStore<TaxAndService> TASStore;
    private double currentTax;
    private double currentService;
    private boolean stop = false;

    public DiscountCashier(){
        this.pluginName = "Plugin Discount, Tax, and Service";
        this.mainPlugin = true;
        this.nextPlugin = "Plugin Detail Discount";
        this.TAS = new TaxAndService();
        this.TASStore = new DataStore<TaxAndService>();
        this.currentTax = 0;
        this.currentService = 0;
    }

    @Override
    public void execute() {
        // Try to read data
        try{
            this.TAS = this.TASStore.loadData("taxAndService", this.page.getSettings(), new Class<?>[]{Inventory.class, TaxAndService.class}).getElement(0);

            if(this.TAS.getTax() != this.currentTax || this.TAS.getService() != this.currentService){
                // Variable
                this.page.setTotalPrice(this.page.getTotalPrice() + this.TAS.getService() + this.TAS.getTax());
                this.page.setFinalTotalPrice(this.page.getFinalTotalPrice() + this.TAS.getService() + this.TAS.getTax());

                // UI
                if(this.page.getTotalPrice() == this.page.getFinalTotalPrice()){
                    this.page.getTotalPriceBills().setText("Charge Rp" + String.format("%.2f", this.page.getTotalPrice()));
                } else {
                    this.page.getTotalPriceBillLabel().setText("Charge Rp" + String.format("%.2f", this.page.getFinalTotalPrice()));
                    this.page.getFixTotalPriceBill().setText("Charge Rp" + String.format("%.2f", this.page.getFinalTotalPrice()));
                }

                this.currentTax = this.TAS.getTax();
                this.currentService = this.TAS.getService();
            }
        } catch (Exception e){
            // Do nothing
        }

        Thread thread = new Thread(() -> {
            while(!stop){
                try{
                    this.TAS = this.TASStore.loadData("taxAndService", this.page.getSettings(), new Class<?>[]{Inventory.class, TaxAndService.class}).getElement(0);

                    if(this.TAS.getTax() != this.currentTax || this.TAS.getService() != this.currentService){
                        // Variable
                        this.page.setTotalPrice(this.page.getTotalPrice() + this.TAS.getService() + this.TAS.getTax());
                        this.page.setFinalTotalPrice(this.page.getFinalTotalPrice() + this.TAS.getService() + this.TAS.getTax());

                        // UI
                        if(this.page.getTotalPrice() == this.page.getFinalTotalPrice()){
                            this.page.getTotalPriceBills().setText("Charge Rp" + String.format("%.2f", this.page.getTotalPrice()));
                        } else {
                            this.page.getTotalPriceBillLabel().setText("Charge Rp" + String.format("%.2f", this.page.getFinalTotalPrice()));
                            this.page.getFixTotalPriceBill().setText("Charge Rp" + String.format("%.2f", this.page.getFinalTotalPrice()));
                        }

                        this.currentTax = this.TAS.getTax();
                        this.currentService = this.TAS.getService();
                    }
                } catch (Exception e){
                    // Do nothing
                }
            }
        });

        thread.start();

        this.page.getTab().setOnCloseRequest(event -> {
            this.stop = true;
        });

        // Create Discount, Tax, and Service Label
        Label tax = new Label("Tax: Rp" + String.format("%.2f", TAS.getTax()));
        Label service = new Label("Service: Rp" + String.format("%.2f", TAS.getService()));

        // Create VBox for these labels
        VBox additionalCharge = new VBox();
        additionalCharge.getChildren().addAll(tax, service);
        additionalCharge.setSpacing(3);
        additionalCharge.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(additionalCharge, Priority.ALWAYS);

        // Add additional charge to page
        this.page.getTotalPriceBox().getChildren().add(additionalCharge);

        // Override use point button
        this.getPage().getUsePointButton().setOnAction(event -> {
            this.getPage().setRegCust();
            if(this.getPage().getRegisCust() != null){
                this.getPage().usePointHandler();
                // Create Discount, Tax, and Service Label
                Label tempTax = new Label("Tax: Rp" + String.format("%.2f", TAS.getTax()));
                Label tempService = new Label("Service: Rp" + String.format("%.2f", TAS.getService()));

                // Create VBox for these labels
                VBox tempAdditionalCharge = new VBox();
                tempAdditionalCharge.getChildren().addAll(tempTax, tempService);
                tempAdditionalCharge.setSpacing(3);
                tempAdditionalCharge.setAlignment(Pos.CENTER_RIGHT);
                HBox.setHgrow(tempAdditionalCharge, Priority.ALWAYS);

                // Add additional charge to page
                this.page.getTotalPriceBox().getChildren().add(tempAdditionalCharge);
            }
        });
    }

    public static void main(String args[]){}
}