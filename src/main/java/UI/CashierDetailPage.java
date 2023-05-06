package UI;

import DataStore.DataStore;
import System.Settings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import System.PurchasedItem;
import System.RegisteredCustomer;
import System.Item;
import java.text.NumberFormat;

import System.Inventory;
import System.Customer;
import System.FixedBill;

public class CashierDetailPage extends VBox {
    protected Integer quantity = 0;
    protected Settings settings;
    protected DataStore<Settings> settingsDS;
    protected Stage stage;
    protected Tab tab;
    protected Inventory<PurchasedItem> purchasedItems;
    protected Item item;
    protected Button addButton;
    protected Button cancelButton;
    protected Inventory<Item> items;
    protected TabPane tabPane;
    protected Inventory<Customer> customers;
    protected Integer mode;
    protected Inventory<FixedBill> transactions;
    protected boolean usePoint;
    protected RegisteredCustomer regisCust;

    public CashierDetailPage(Stage stage, Tab tab, Item item, Inventory<PurchasedItem> purchasedItems, Inventory<Item> items, TabPane tabPane, Inventory<Customer> customers, Integer mode, Inventory<FixedBill> transactions, boolean usePoint, RegisteredCustomer regisCust, Settings settings, DataStore<Settings> settingsDS){
        this.stage = stage;
        this.tab = tab;
        this.settings = settings;
        this.settingsDS = settingsDS;
        this.item = item;
        this.purchasedItems = purchasedItems;
        this.items = items;
        this.tabPane = tabPane;
        this.customers = customers;
        this.mode = mode;
        this.transactions = transactions;
        this.usePoint = usePoint;
        this.regisCust = regisCust;

        for (PurchasedItem purchItem : purchasedItems.getBox()){
            if (purchItem.getItemID() == item.getItemID()){
                this.quantity = purchItem.getQuantity();
                break;
            }
        }

        // Create HBox for header
        HBox hBox = new HBox();

        // Create HBox for add button
        HBox middle = new HBox();

        // Create title
        String titleName = item.getName();
        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setGroupingUsed(true);
        String titlePrice = "Rp" + formatter.format(item.getSellPrice());
        Label title = new Label(titleName + " - " + titlePrice);
        title.setFont(Font.font("Montserrat", FontWeight.BOLD, 36));
        title.setStyle("-fx-text-fill: #3B919B");
        title.setAlignment(Pos.CENTER);

        // Create add button
        addButton = new Button("Save");
        addButton.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        addButton.setStyle("-fx-background-color: #3B919B; -fx-text-fill: white;");
        addButton.setOnAction(event -> {
            processAdd();
            CashierPage cashierContent = new CashierPage(stage, tab, items, tabPane, customers, mode, transactions, purchasedItems, usePoint, regisCust, settings, settingsDS);
            tab.setContent(cashierContent);
        });

        // Create cancel button
        cancelButton = new Button("Cancel");
        cancelButton.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        cancelButton.setStyle("-fx-background-color: #C34646; -fx-text-fill: white;");
        cancelButton.setOnAction(event -> {
            CashierPage cashierContent = new CashierPage(stage, tab, items, tabPane, customers, mode, transactions, purchasedItems, usePoint, regisCust, settings, settingsDS);
            tab.setContent(cashierContent);
        });

        // Set title to HBox
        middle.getChildren().addAll(title);

        // Add header to HBox
        hBox.getChildren().addAll(cancelButton, middle, addButton);
        hBox.setAlignment(Pos.CENTER);
        middle.setAlignment(Pos.CENTER);
        HBox.setHgrow(middle, Priority.ALWAYS);

        // Create Horizontal Line
        HBox horizontalLine1 = new HBox();
        horizontalLine1.setPrefHeight(2);
        horizontalLine1.setStyle("-fx-background-color: #C8DFE8");

        HBox horizontalLine2 = new HBox();
        horizontalLine2.setPrefHeight(2);
        horizontalLine2.setStyle("-fx-background-color: #C8DFE8");

        HBox horizontalLine3 = new HBox();
        horizontalLine3.setPrefHeight(2);
        horizontalLine3.setStyle("-fx-background-color: #C8DFE8");

        // Create Quantity Input
        VBox quantityBox = new VBox();
        Label labelQuantity = new Label("Quantity");
        HBox quantityInput = new HBox();
        HBox buttonBox = new HBox();

        // Quantity Inputs
        TextField quantity = new TextField(this.quantity.toString());
        Button minusQuantity = new Button("-");
        Button plusQuantity = new Button("+");

        minusQuantity.setOnAction(event -> {
            if(this.quantity > 0) this.quantity--;
            quantity.setText(this.quantity.toString());
        });

        plusQuantity.setOnAction(event -> {
            if(this.quantity < item.getStock()) this.quantity++;
            quantity.setText(this.quantity.toString());
        });

        quantity.textProperty().addListener((observable, oldValue, newValue) -> {
            Integer tempQuantity = 0;
            if (newValue != ""){
                tempQuantity = Integer.parseInt(newValue);
            }
            if(tempQuantity <= item.getStock()){
                this.quantity = tempQuantity;
                quantity.setText(this.quantity.toString());
            } else {
                tempQuantity = item.getStock();
                this.quantity = tempQuantity;
                quantity.setText(this.quantity.toString());
            }
        });

        // Styling Label
        labelQuantity.setFont(Font.font("Montserrat", FontWeight.BOLD, 24));
        labelQuantity.setStyle("-fx-text-fill: #3B919B");

        // Styling Input
        quantity.setFont(Font.font("Montserrat",18));
        quantity.setPadding(new Insets(10, 20, 10 ,20));
        quantity.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px;");
        quantity.setPrefWidth(580);
        quantity.setAlignment(Pos.CENTER);

        // Styling Button
        minusQuantity.setFont(Font.font("Montserrat",18));
        minusQuantity.setPadding(new Insets(10, 20, 10 ,20));
        minusQuantity.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px;");
        minusQuantity.setPrefWidth(285);
        minusQuantity.setAlignment(Pos.CENTER);

        plusQuantity.setFont(Font.font("Montserrat",18));
        plusQuantity.setPadding(new Insets(10, 20, 10 ,20));
        plusQuantity.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px;");
        plusQuantity.setPrefWidth(285);
        plusQuantity.setAlignment(Pos.CENTER);

        // Styling Layout
        quantityBox.setSpacing(10);
        quantityInput.setSpacing(20);
        buttonBox.setSpacing(10);

        // Add inputs to HBox
        buttonBox.getChildren().addAll(minusQuantity, plusQuantity);
        quantityInput.getChildren().addAll(quantity, buttonBox);
        quantityBox.getChildren().addAll(labelQuantity, quantityInput);

        // Add Contents
        getChildren().addAll(hBox, horizontalLine1, quantityBox, horizontalLine2);

        // Styling Layout
        setPadding(new Insets(30, 50, 0, 50));
        setSpacing(20);
        setStyle("-fx-background-color: #F3F9FB;");
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setSettings(Settings settings){
        this.settings = settings;
    }

    public void setSettingsDS(DataStore<Settings> settingsDS){
        this.settingsDS = settingsDS;
    }

    public Settings getSettings(){
        return this.settings;
    }

    public DataStore<Settings> getSettingsDS(){
        return this.settingsDS;
    }

    public Button getAddButton(){
        return this.addButton;
    }

    public Button getCancelButton(){
        return this.cancelButton;
    }

    public CashierPage processAdd(){
        boolean flag = false;
        for (PurchasedItem purchItem : purchasedItems.getBox()){
            if (purchItem.getItemID() == item.getItemID()){
                flag = true;
                purchItem.setQuantity(this.quantity);
                if (this.quantity == 0) {
                    purchasedItems.removeElement(purchItem);
                }
                break;
            }
        }

        if (!flag) {
            if (this.quantity > 0) {
                PurchasedItem newPurchasedItem = new PurchasedItem(item, this.quantity);
                purchasedItems.addElement(newPurchasedItem);
            }
        }

        CashierPage cashierContent = makeChangePage();

        return cashierContent;
    }

    public CashierPage makeChangePage(){
        CashierPage cashierContent = new CashierPage(stage, tab, items, tabPane, customers, mode, transactions, purchasedItems, usePoint, regisCust, settings, settingsDS);

        return cashierContent;
    }
    public PurchasedItem findPurchasedItem(){
        for (PurchasedItem purchItem : purchasedItems.getBox()){
            if (purchItem.getItemID() == item.getItemID()){
                return purchItem;
            }
        }
        return null;
    }

    public Tab getTab(){
        return this.tab;
    }

    public Item getItem(){
        return this.item;
    }

    public Integer getQuantity(){
        return this.quantity;
    }
}
