package UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
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
    private Integer quantity = 1;

    public CashierDetailPage(Stage stage, Tab tab, Item item, Inventory<PurchasedItem> purchasedItems, Inventory<Item> items, TabPane tabPane, Inventory<Customer> customers, Integer mode, Inventory<FixedBill> transactions, boolean usePoint, RegisteredCustomer regisCust){

        for (PurchasedItem purchItem : purchasedItems.getList()){
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
        Button addButton = new Button("Save");
        addButton.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        addButton.setStyle("-fx-background-color: #3B919B; -fx-text-fill: white;");
        addButton.setOnAction(event -> {
            boolean flag = false;
            for (PurchasedItem purchItem : purchasedItems.getList()){
                if (purchItem.getItemID() == item.getItemID()){
                    flag = true;
                    purchItem.setQuantity(this.quantity);
                    break;
                }
            }
            
            if (!flag) {
                PurchasedItem newPurchasedItem = new PurchasedItem(item, this.quantity);
                purchasedItems.addElement(newPurchasedItem);
            }

            CashierPage cashierContent = new CashierPage(stage, tab, items, tabPane, customers, mode, transactions, purchasedItems, usePoint, regisCust);
            tab.setContent(cashierContent);
        });

        // Create cancel button
        Button cancelButton = new Button("Cancel");
        cancelButton.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        cancelButton.setStyle("-fx-background-color: #C34646; -fx-text-fill: white;");
        cancelButton.setOnAction(event -> {
            CashierPage cashierContent = new CashierPage(stage, tab, items, tabPane, customers, mode, transactions, purchasedItems, usePoint, regisCust);
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
            if(this.quantity > 1) this.quantity--;
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

        // Create Discount Input
        VBox discountBox = new VBox();
        Label labelDiscount = new Label("Discount");
        HBox discountInput = new HBox();
        VBox leftDiscountBox = new VBox();
        VBox rightDiscountBox = new VBox();

        // Discount Inputs
        HBox firstToggleBox = new HBox();
        HBox secondToggleBox = new HBox();
        HBox thirdToggleBox = new HBox();
        HBox fourthToggleBox = new HBox();
        HBox fifthToggleBox = new HBox();
        HBox sixthToggleBox = new HBox();

        Label memberDiscount = new Label("Member 10%");
        Label ownerDiscount = new Label("Owner 20%");
        Label complimentDiscount = new Label("Compliment 100%");
        Label buy1free1Discount = new Label("Buy 1 Free 1 100%");
        Label openingDiscount = new Label("Opening 30%");
        Label dineInDiscount = new Label("Dine in 15%");

        HBox firstToggle = new HBox();
        HBox secondToggle = new HBox();
        HBox thirdToggle = new HBox();
        HBox fourthToggle = new HBox();
        HBox fifthToggle = new HBox();
        HBox sixthToggle = new HBox();

        ToggleButton firstToggleButton = new ToggleButton();
        ToggleButton secondToggleButton = new ToggleButton();
        ToggleButton thirdToggleButton = new ToggleButton();
        ToggleButton fourthToggleButton = new ToggleButton();
        ToggleButton fifthToggleButton = new ToggleButton();
        ToggleButton sixthToggleButton = new ToggleButton();

        // Styling Toggle Button

        // Create Image View for Toggle Button
        ImageView offToggle1 = new ImageView("/images/icon/offToggle.png");
        ImageView offToggle2 = new ImageView("/images/icon/offToggle.png");
        ImageView offToggle3 = new ImageView("/images/icon/offToggle.png");
        ImageView offToggle4 = new ImageView("/images/icon/offToggle.png");
        ImageView offToggle5 = new ImageView("/images/icon/offToggle.png");
        ImageView offToggle6 = new ImageView("/images/icon/offToggle.png");

        ImageView onToggle1 = new ImageView("/images/icon/onToggle.png");
        ImageView onToggle2 = new ImageView("/images/icon/onToggle.png");
        ImageView onToggle3 = new ImageView("/images/icon/onToggle.png");
        ImageView onToggle4 = new ImageView("/images/icon/onToggle.png");
        ImageView onToggle5 = new ImageView("/images/icon/onToggle.png");
        ImageView onToggle6 = new ImageView("/images/icon/onToggle.png");

        // Styling Image View for Membership Status toggle button
        offToggle1.setPreserveRatio(true);
        offToggle1.setSmooth(true);
        offToggle1.setCache(true);
        offToggle1.setFitWidth(60);
        offToggle1.setFitHeight(31);

        onToggle1.setPreserveRatio(true);
        onToggle1.setSmooth(true);
        onToggle1.setCache(true);
        onToggle1.setFitWidth(60);
        onToggle1.setFitHeight(31);

        offToggle2.setPreserveRatio(true);
        offToggle2.setSmooth(true);
        offToggle2.setCache(true);
        offToggle2.setFitWidth(60);
        offToggle2.setFitHeight(31);

        onToggle2.setPreserveRatio(true);
        onToggle2.setSmooth(true);
        onToggle2.setCache(true);
        onToggle2.setFitWidth(60);
        onToggle2.setFitHeight(31);

        offToggle3.setPreserveRatio(true);
        offToggle3.setSmooth(true);
        offToggle3.setCache(true);
        offToggle3.setFitWidth(60);
        offToggle3.setFitHeight(31);

        onToggle3.setPreserveRatio(true);
        onToggle3.setSmooth(true);
        onToggle3.setCache(true);
        onToggle3.setFitWidth(60);
        onToggle3.setFitHeight(31);

        offToggle4.setPreserveRatio(true);
        offToggle4.setSmooth(true);
        offToggle4.setCache(true);
        offToggle4.setFitWidth(60);
        offToggle4.setFitHeight(31);

        onToggle4.setPreserveRatio(true);
        onToggle4.setSmooth(true);
        onToggle4.setCache(true);
        onToggle4.setFitWidth(60);
        onToggle4.setFitHeight(31);

        offToggle5.setPreserveRatio(true);
        offToggle5.setSmooth(true);
        offToggle5.setCache(true);
        offToggle5.setFitWidth(60);
        offToggle5.setFitHeight(31);

        onToggle5.setPreserveRatio(true);
        onToggle5.setSmooth(true);
        onToggle5.setCache(true);
        onToggle5.setFitWidth(60);
        onToggle5.setFitHeight(31);

        offToggle6.setPreserveRatio(true);
        offToggle6.setSmooth(true);
        offToggle6.setCache(true);
        offToggle6.setFitWidth(60);
        offToggle6.setFitHeight(31);

        onToggle6.setPreserveRatio(true);
        onToggle6.setSmooth(true);
        onToggle6.setCache(true);
        onToggle6.setFitWidth(60);
        onToggle6.setFitHeight(31);

        firstToggleButton.setMinWidth(60);
        firstToggleButton.setMinHeight(31);
        firstToggleButton.setStyle("-fx-background-color: #C8DFE8;");
        firstToggleButton.setSelected(false);
        firstToggleButton.setCursor(Cursor.HAND);
        firstToggleButton.setGraphic(offToggle1);

        secondToggleButton.setMinWidth(60);
        secondToggleButton.setMinHeight(31);
        secondToggleButton.setStyle("-fx-background-color: #C8DFE8;");
        secondToggleButton.setSelected(false);
        secondToggleButton.setCursor(Cursor.HAND);
        secondToggleButton.setGraphic(offToggle2);

        thirdToggleButton.setMinWidth(60);
        thirdToggleButton.setMinHeight(31);
        thirdToggleButton.setStyle("-fx-background-color: #C8DFE8;");
        thirdToggleButton.setSelected(false);
        thirdToggleButton.setCursor(Cursor.HAND);
        thirdToggleButton.setGraphic(offToggle3);

        fourthToggleButton.setMinWidth(60);
        fourthToggleButton.setMinHeight(31);
        fourthToggleButton.setStyle("-fx-background-color: #C8DFE8;");
        fourthToggleButton.setSelected(false);
        fourthToggleButton.setCursor(Cursor.HAND);
        fourthToggleButton.setGraphic(offToggle4);

        fifthToggleButton.setMinWidth(60);
        fifthToggleButton.setMinHeight(31);
        fifthToggleButton.setStyle("-fx-background-color: #C8DFE8;");
        fifthToggleButton.setSelected(false);
        fifthToggleButton.setCursor(Cursor.HAND);
        fifthToggleButton.setGraphic(offToggle5);

        sixthToggleButton.setMinWidth(60);
        sixthToggleButton.setMinHeight(31);
        sixthToggleButton.setStyle("-fx-background-color: #C8DFE8;");
        sixthToggleButton.setSelected(false);
        sixthToggleButton.setCursor(Cursor.HAND);
        sixthToggleButton.setGraphic(offToggle6);

        // Set Toggle Button to HBox
        firstToggle.getChildren().addAll(firstToggleButton);
        secondToggle.getChildren().addAll(secondToggleButton);
        thirdToggle.getChildren().addAll(thirdToggleButton);
        fourthToggle.getChildren().addAll(fourthToggleButton);
        fifthToggle.getChildren().addAll(fifthToggleButton);
        sixthToggle.getChildren().addAll(sixthToggleButton);

        // Handling Toggle Button event
        firstToggleButton.setOnAction(event -> {
            if (firstToggleButton.isSelected()) {
                firstToggleButton.setGraphic(onToggle1);
            }
            else {
                firstToggleButton.setGraphic(offToggle1);
            }
        });

        // Handling Toggle Button event
        secondToggleButton.setOnAction(event -> {
            if (secondToggleButton.isSelected()) {
                secondToggleButton.setGraphic(onToggle2);
            }
            else {
                secondToggleButton.setGraphic(offToggle2);
            }
        });

        // Handling Toggle Button event
        thirdToggleButton.setOnAction(event -> {
            if (thirdToggleButton.isSelected()) {
                thirdToggleButton.setGraphic(onToggle3);
            }
            else {
                thirdToggleButton.setGraphic(offToggle3);
            }
        });

        // Handling Toggle Button event
        fourthToggleButton.setOnAction(event -> {
            if (fourthToggleButton.isSelected()) {
                fourthToggleButton.setGraphic(onToggle4);
            }
            else {
                fourthToggleButton.setGraphic(offToggle4);
            }
        });

        // Handling Toggle Button event
        fifthToggleButton.setOnAction(event -> {
            if (fifthToggleButton.isSelected()) {
                fifthToggleButton.setGraphic(onToggle5);
            }
            else {
                fifthToggleButton.setGraphic(offToggle5);
            }
        });

        // Handling Toggle Button event
        sixthToggleButton.setOnAction(event -> {
            if (sixthToggleButton.isSelected()) {
                sixthToggleButton.setGraphic(onToggle6);
            }
            else {
                sixthToggleButton.setGraphic(offToggle6);
            }
        });

        // Set Toggle Button to Toggle Input
        firstToggleBox.getChildren().addAll(memberDiscount, firstToggle);
        secondToggleBox.getChildren().addAll(ownerDiscount, secondToggle);
        thirdToggleBox.getChildren().addAll(complimentDiscount, thirdToggle);
        fourthToggleBox.getChildren().addAll(buy1free1Discount, fourthToggle);
        fifthToggleBox.getChildren().addAll(openingDiscount, fifthToggle);
        sixthToggleBox.getChildren().addAll(dineInDiscount, sixthToggle);

        // Styling Toggle Box
        HBox.setHgrow(firstToggle, Priority.ALWAYS);
        firstToggle.setAlignment(Pos.CENTER_RIGHT);
        memberDiscount.setAlignment(Pos.CENTER);

        HBox.setHgrow(secondToggle, Priority.ALWAYS);
        secondToggle.setAlignment(Pos.CENTER_RIGHT);
        ownerDiscount.setAlignment(Pos.CENTER);

        HBox.setHgrow(thirdToggle, Priority.ALWAYS);
        thirdToggle.setAlignment(Pos.CENTER_RIGHT);
        complimentDiscount.setAlignment(Pos.CENTER);

        HBox.setHgrow(fourthToggle, Priority.ALWAYS);
        fourthToggle.setAlignment(Pos.CENTER_RIGHT);
        buy1free1Discount.setAlignment(Pos.CENTER);

        HBox.setHgrow(fifthToggle, Priority.ALWAYS);
        fifthToggle.setAlignment(Pos.CENTER_RIGHT);
        openingDiscount.setAlignment(Pos.CENTER);

        HBox.setHgrow(sixthToggle, Priority.ALWAYS);
        sixthToggle.setAlignment(Pos.CENTER_RIGHT);
        dineInDiscount.setAlignment(Pos.CENTER);

        // Add Toggle Inputs to VBox
        leftDiscountBox.getChildren().addAll(firstToggleBox, secondToggleBox, thirdToggleBox);
        rightDiscountBox.getChildren().addAll(fourthToggleBox, fifthToggleBox, sixthToggleBox);

        // Add Discounts to HBox
        discountInput.getChildren().addAll(leftDiscountBox, rightDiscountBox);

        // Add Discount Input to Discount Box
        discountBox.getChildren().addAll(labelDiscount, discountInput);

        // // Create Quantity Input
        // VBox notesBox = new VBox();
        // Label labelNotes = new Label("Notes");

        // // Quantity Inputs
        // TextField notes = new TextField();
        // notes.setPromptText("Description . . .");

        // Styling Label
        labelQuantity.setFont(Font.font("Montserrat", FontWeight.BOLD, 24));
        labelQuantity.setStyle("-fx-text-fill: #3B919B");

        labelDiscount.setFont(Font.font("Montserrat", FontWeight.BOLD, 24));
        labelDiscount.setStyle("-fx-text-fill: #3B919B");

        // labelNotes.setFont(Font.font("Montserrat", FontWeight.BOLD, 24));
        // labelNotes.setStyle("-fx-text-fill: #3B919B");

        // Styling Input
        quantity.setFont(Font.font("Montserrat",18));
        quantity.setPadding(new Insets(10, 20, 10 ,20));
        quantity.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px;");
        quantity.setPrefWidth(580);
        quantity.setAlignment(Pos.CENTER);

        memberDiscount.setFont(Font.font("Montserrat",20));
        firstToggleBox.setPadding(new Insets(10, 20, 10 ,18));
        firstToggleBox.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px;");
        firstToggleBox.setPrefWidth(580);

        ownerDiscount.setFont(Font.font("Montserrat",20));
        secondToggleBox.setPadding(new Insets(10, 20, 10 ,18));
        secondToggleBox.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px;");
        secondToggleBox.setPrefWidth(580);

        complimentDiscount.setFont(Font.font("Montserrat",20));
        thirdToggleBox.setPadding(new Insets(10, 20, 10 ,18));
        thirdToggleBox.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px;");
        thirdToggleBox.setPrefWidth(580);

        buy1free1Discount.setFont(Font.font("Montserrat",20));
        fourthToggleBox.setPadding(new Insets(10, 20, 10 ,18));
        fourthToggleBox.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px;");
        fourthToggleBox.setPrefWidth(580);

        openingDiscount.setFont(Font.font("Montserrat",20));
        fifthToggleBox.setPadding(new Insets(10, 20, 10 ,18));
        fifthToggleBox.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px;");
        fifthToggleBox.setPrefWidth(580);

        dineInDiscount.setFont(Font.font("Montserrat",20));
        sixthToggleBox.setPadding(new Insets(10, 20, 10 ,18));
        sixthToggleBox.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px;");
        sixthToggleBox.setPrefWidth(580);

        // notes.setFont(Font.font("Montserrat",16));
        // notes.setPadding(new Insets(10, 10, 10 ,10));
        // notes.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px;");
        // notes.setPrefWidth(1160);
        // notes.setAlignment(Pos.TOP_LEFT);

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
        leftDiscountBox.setSpacing(10);
        rightDiscountBox.setSpacing(10);
        discountBox.setSpacing(10);
        discountInput.setSpacing(20);
        // notesBox.setSpacing(10);

        // Add inputs to HBox
        buttonBox.getChildren().addAll(minusQuantity, plusQuantity);
        quantityInput.getChildren().addAll(quantity, buttonBox);
        quantityBox.getChildren().addAll(labelQuantity, quantityInput);
        // notesBox.getChildren().addAll(labelNotes, notes);

        // Add Contents
        getChildren().addAll(hBox, horizontalLine1, quantityBox, horizontalLine2, discountBox, horizontalLine3);

        // Styling Layout
        setPadding(new Insets(30, 50, 0, 50));
        setSpacing(20);
        setStyle("-fx-background-color: #F3F9FB;");
    }
}
