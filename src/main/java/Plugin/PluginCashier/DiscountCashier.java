package Plugin.PluginCashier;

import Plugin.Decorator.CashierDecorator;
import UI.CashierDetailPage;
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
    private boolean stop;

    public DiscountCashier(){
        this.pluginName = "Plugin Discount";
        this.TAS = new TaxAndService();
        this.TASStore = new DataStore<TaxAndService>();
        this.stop = false;
    }

    @Override
    public void execute() {
        // Try to read data
        try{
            this.TAS = this.TASStore.loadData("taxAndService", this.page.getSettings(), new Class<?>[]{Inventory.class, TaxAndService.class}).getElement(0);

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
        } catch (Exception e){
            e.printStackTrace();
        }

        // Create a thread to execute the task
        Thread thread = new Thread(() -> {
            while (!stop) {
                try {
                    // Start the task
                    try{
                        this.TAS = this.TASStore.loadData("taxAndService", this.page.getSettings(), new Class<?>[]{Inventory.class, TaxAndService.class}).getElement(0);
                        this.page.setTotalPrice(this.page.getTotalPrice() + this.TAS.getService() + this.TAS.getTax());
                        this.page.setFinalTotalPrice(this.page.getFinalTotalPrice() + this.TAS.getService() + this.TAS.getTax());
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    // Sleep for two seconds
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Start the thread
        thread.start();
        this.getPage().getTab().setOnCloseRequest(event -> {
            stop = true;
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
    }
}