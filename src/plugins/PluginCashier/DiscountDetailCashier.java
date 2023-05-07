package Plugin.PluginCashier;

import Core.Inventory;
import Plugin.Decorator.CashierDetailDecorator;
import UI.CashierPage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import DataStore.DataStore;
import java.io.Serializable;

public class DiscountDetailCashier extends CashierDetailDecorator implements Serializable {
    private double tempDiscount;
    private DataStore<Discount> discountDS;
    private Inventory<Discount> discounts;

    public DiscountDetailCashier(){
        this.pluginName = "Plugin Detail Discount";
        this.nextPlugin = "Plugin Tax & Service";
        this.tempDiscount = 0;
        this.discountDS = new DataStore<Discount>();
        this.discounts = new Inventory<>();
    }

    public void execute(){
        // Try to read data
        try {
            discounts = discountDS.loadData("discount", this.page.getSettings(), new Class<?>[]{Inventory.class, Discount.class});
        } catch (Exception e){
            // Do nothing
        }

        // Create Discount Input
        VBox discountBox = new VBox();
        Label labelDiscount = new Label("Discount");
        HBox discountInput = new HBox();
        VBox leftDiscountBox = new VBox();
        VBox rightDiscountBox = new VBox();

        // Discount Inputs
        int idx = 0;

        for(Discount discount : discounts.getBox()){
            // Create toggle box
            HBox toggleBox = new HBox();

            // Create label name
            Label labelDiscountName = new Label(discount.getDiscountName());

            // Create toggle
            HBox toggle = new HBox();

            // Create toggle button
            ToggleButton toggleButton = new ToggleButton();

            // Create image for on and off toggle
            ImageView offToggle = new ImageView("/images/icon/offToggle.png");
            ImageView onToggle = new ImageView("/images/icon/onToggle.png");

            // Styling off and on
            offToggle.setPreserveRatio(true);
            offToggle.setSmooth(true);
            offToggle.setCache(true);
            offToggle.setFitWidth(60);
            offToggle.setFitHeight(31);

            onToggle.setPreserveRatio(true);
            onToggle.setSmooth(true);
            onToggle.setCache(true);
            onToggle.setFitWidth(60);
            onToggle.setFitHeight(31);

            toggleButton.setMinWidth(60);
            toggleButton.setMinHeight(31);
            toggleButton.setStyle("-fx-background-color: #C8DFE8;");
            toggleButton.setSelected(false);
            toggleButton.setCursor(Cursor.HAND);
            toggleButton.setGraphic(offToggle);

            toggle.getChildren().addAll(toggleButton);
            toggleButton.setOnAction(event -> {
                if (toggleButton.isSelected()) {
                    toggleButton.setGraphic(onToggle);

                    if (tempDiscount + discount.getDiscount() <= 1){
                        tempDiscount += discount.getDiscount();
                    } else {
                        tempDiscount = 1;
                    }

                } else {
                    toggleButton.setGraphic(offToggle);
                    if (tempDiscount - discount.getDiscount() >= 0){
                        tempDiscount -= discount.getDiscount();
                    } else {
                        tempDiscount = 0;
                    }
                }
            });

            toggleBox.getChildren().addAll(labelDiscountName, toggle);
            HBox.setHgrow(toggle, Priority.ALWAYS);
            toggle.setAlignment(Pos.CENTER_RIGHT);
            labelDiscountName.setAlignment(Pos.CENTER);

            labelDiscountName.setFont(Font.font("Montserrat",20));
            toggleBox.setPadding(new Insets(10, 20, 10 ,18));
            toggleBox.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px;");
            toggleBox.setPrefWidth(580);

            if(idx % 2 == 0){
                leftDiscountBox.getChildren().add(toggleBox);
            } else {
                rightDiscountBox.getChildren().add(toggleBox);
            }

            idx++;
        }

        // Add Discounts to HBox
        discountInput.getChildren().addAll(leftDiscountBox, rightDiscountBox);

        // Add Discount Input to Discount Box
        discountBox.getChildren().addAll(labelDiscount, discountInput);

        // Styling
        labelDiscount.setFont(Font.font("Montserrat", FontWeight.BOLD, 24));
        labelDiscount.setStyle("-fx-text-fill: #3B919B");

        leftDiscountBox.setSpacing(10);
        rightDiscountBox.setSpacing(10);
        discountBox.setSpacing(10);
        discountInput.setSpacing(20);

        // Override add button event handler
        this.page.getAddButton().setOnAction(event -> {
            if(this.page.findPurchasedItem() == null){
                double oldPrice = this.page.getItem().getSellPrice();
                this.page.getItem().setSellPrice(this.page.getItem().getSellPrice() - tempDiscount * this.page.getItem().getSellPrice());
                CashierPage newPage = this.page.processAdd();
                this.page.getItem().setSellPrice(oldPrice);
                newPage.getBackButton().fire();
                DiscountCashier newCashier = new DiscountCashier();
                newCashier.setPage(newPage);
                newCashier.getPage().setStage(newPage.getStage());
                newCashier.getPage().setSettings(newPage.getSettings());
                newCashier.getPage().setSettingsDS(newPage.getSettingsDS());
                newCashier.execute();
                this.page.getTab().setContent(newCashier.getPage());
            } else {
                this.page.findPurchasedItem().setSellPrice(this.page.getItem().getSellPrice() - tempDiscount * this.page.getItem().getSellPrice());
                CashierPage newPage = this.page.processAdd();
                DiscountCashier newCashier = new DiscountCashier();
                newCashier.setPage(newPage);
                newCashier.getPage().setStage(newPage.getStage());
                newCashier.getPage().setSettings(newPage.getSettings());
                newCashier.getPage().setSettingsDS(newPage.getSettingsDS());
                newCashier.execute();
                this.page.getTab().setContent(newCashier.getPage());
            }
        });

        this.page.getCancelButton().setOnAction(event -> {
            CashierPage newPage = getPage().makeChangePage();
            DiscountCashier newCashier = new DiscountCashier();
            newCashier.setPage(newPage);
            newCashier.getPage().setStage(newPage.getStage());
            newCashier.getPage().setSettings(newPage.getSettings());
            newCashier.getPage().setSettingsDS(newPage.getSettingsDS());
            newCashier.execute();
            this.page.getTab().setContent(newCashier.getPage());
        });

        this.page.getChildren().addAll(discountBox);
    }
}
