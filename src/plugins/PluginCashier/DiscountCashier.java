package Plugin.PluginCashier;

import Core.VIP;
import Plugin.Decorator.CashierDecorator;
import UI.Page;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.io.Serializable;
import java.text.NumberFormat;

import Core.Inventory;
import DataStore.DataStore;

public class DiscountCashier extends CashierDecorator implements Serializable {
    private TaxAndService TAS;
    private DataStore<TaxAndService> TASStore;
    private double currentTax;
    private double currentService;
    private boolean stop = false;
    private NumberFormat formatter;
    private String totalPriceBill;
    private String fixTotalPrice;

    public DiscountCashier(){
        this.pluginName = "PluginCashier";
        this.mainPlugin = true;
        this.nextPlugin = "Plugin Detail Discount";
        this.TAS = new TaxAndService();
        this.TASStore = new DataStore<TaxAndService>();
        this.currentTax = 0;
        this.currentService = 0;
    }

    @Override
    public void execute() {
        // Create Discount, Tax, and Service Label
        Label tax = new Label();
        Label service = new Label();

        // Try to read data
        try{
            this.TAS = this.TASStore.loadData("taxAndService", this.page.getSettings(), new Class<?>[]{Inventory.class, TaxAndService.class}).getElement(0);
            // Variable
            if(this.page.getRegisCust() instanceof VIP){
                this.page.setFinalTotalPrice(this.page.getFinalTotalPrice() + ((this.TAS.getService() + this.TAS.getTax()) * 0.9) - currentTax - currentService);
                this.page.setTotalPrice((this.page.getFinalTotalPrice() / 0.9));
            } else {
                this.page.setTotalPrice(this.page.getTotalPrice() + this.TAS.getService() + this.TAS.getTax() - currentTax - currentService);
                this.page.setFinalTotalPrice(this.page.getFinalTotalPrice() + this.TAS.getService() + this.TAS.getTax() - currentTax - currentService);
            }
            tax.setText("Tax: Rp" + String.format("%.2f", TAS.getTax()));
            service.setText("Service: Rp" + String.format("%.2f", TAS.getService()));

            // UI
            formatter = NumberFormat.getInstance();
            formatter.setGroupingUsed(true);
            totalPriceBill = formatter.format(this.page.getTotalPrice());
            fixTotalPrice = formatter.format(this.page.getFinalTotalPrice());
            if(this.page.getTotalPrice() == this.page.getFinalTotalPrice()){
                this.page.getTotalPriceBills().setText("Charge Rp" + totalPriceBill);
            } else {
                this.page.getTotalPriceBillLabel().setText("Rp" + totalPriceBill);
                this.page.getFixTotalPriceBill().setText("Rp" + fixTotalPrice);
            }

            this.currentTax = this.TAS.getTax();
            this.currentService = this.TAS.getService();
        } catch (Exception e){
            // Do nothing
        }

        Thread thread = new Thread(() -> {
            while(!stop && !Page.isExit()){
                try{
                    this.TAS = this.TASStore.loadData("taxAndService", this.page.getSettings(), new Class<?>[]{Inventory.class, TaxAndService.class}).getElement(0);

                    if(this.TAS.getTax() != this.currentTax || this.TAS.getService() != this.currentService){
                        // Variable
                        if(this.page.getRegisCust() instanceof VIP){
                            this.page.setFinalTotalPrice(this.page.getFinalTotalPrice() + ((this.TAS.getService() + this.TAS.getTax()) * 0.9) - currentTax - currentService);
                            this.page.setTotalPrice((this.page.getFinalTotalPrice() / 0.9));
                        } else {
                            this.page.setTotalPrice(this.page.getTotalPrice() + this.TAS.getService() + this.TAS.getTax() - currentTax - currentService);
                            this.page.setFinalTotalPrice(this.page.getFinalTotalPrice() + this.TAS.getService() + this.TAS.getTax() - currentTax - currentService);
                        }

                        // UI update on JavaFX Application Thread
                        Platform.runLater(() -> {
                            tax.setText("Tax: Rp" + String.format("%.2f", TAS.getTax()));
                            service.setText("Service: Rp" + String.format("%.2f", TAS.getService()));

                            formatter = NumberFormat.getInstance();
                            formatter.setGroupingUsed(true);
                            totalPriceBill = formatter.format(this.page.getTotalPrice());
                            fixTotalPrice = formatter.format(this.page.getFinalTotalPrice());

                            if (this.page.getTotalPrice() == this.page.getFinalTotalPrice()) {
                                this.page.getTotalPriceBills().setText("Charge Rp" + totalPriceBill);
                            } else {
                                this.page.getTotalPriceBillLabel().setText("Rp" + totalPriceBill);
                                this.page.getFixTotalPriceBill().setText("Rp" + fixTotalPrice);
                            }
                        });

                        this.currentTax = this.TAS.getTax();
                        this.currentService = this.TAS.getService();
                    }
                } catch (Exception e){
                    // Do nothing
                }

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    // Do Nothing
                }
            }
        });

        // Set stop if tab closed
        this.page.getTab().setOnCloseRequest(event -> {
            this.stop = true;
        });

        // Set default text
        tax.setText("Tax: Rp" + String.format("%.2f", TAS.getTax()));
        service.setText("Service: Rp" + String.format("%.2f", TAS.getService()));

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
            if (this.getPage().getRegisCust() != null){
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

        this.getPage().getChoiceBox().getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            this.getPage().choiceBoxHandler(newValue);
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
        });

        thread.start();
    }

    public void reset(){

    }

    public static void main(String args[]){}
}