package UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CashierDetailPage extends VBox {
    public CashierDetailPage(Stage stage, Tab tab){
        // Create HBox for header
        HBox hBox = new HBox();

        // Create HBox for add button
        HBox middle = new HBox();

        // Create title
        Label title = new Label("Cappucino - Rp30.000");
        title.setFont(Font.font("Montserrat", FontWeight.BOLD, 36));
        title.setStyle("-fx-text-fill: #3B919B");
        title.setAlignment(Pos.CENTER);

        // Create add button
        Button addButton = new Button("Save");
        addButton.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        addButton.setStyle("-fx-background-color: #3B919B; -fx-text-fill: white;");
        addButton.setOnAction(event -> {});

        // Create cancel button
        Button cancelButton = new Button("Cancel");
        cancelButton.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        cancelButton.setStyle("-fx-background-color: #C34646; -fx-text-fill: white;");
        cancelButton.setOnAction(event -> {});

        // Set title to HBox
        middle.getChildren().addAll(title);

        // Add header to HBox
        hBox.getChildren().addAll(cancelButton, middle, addButton);
        hBox.setAlignment(Pos.CENTER);
        middle.setAlignment(Pos.CENTER);
        hBox.setHgrow(middle, Priority.ALWAYS);

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
        TextField quantity = new TextField("1");
        Button minusQuantity = new Button("-");
        Button plusQuantity = new Button("+");

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

        Text memberDiscount = new Text("Member 10%");
        Text ownerDiscount = new Text("Owner 20%");
        Text complimentDiscount = new Text("Compliment 100%");
        Text buy1free1Discount = new Text("Buy 1 Free 1 100%");
        Text openingDiscount = new Text("Opening 30%");
        Text dineInDiscount = new Text("Dine in 15%");

        HBox firstToggle = new HBox();
        HBox secondToggle = new HBox();
        HBox thirdToggle = new HBox();
        HBox fourthToggle = new HBox();
        HBox fifthToggle = new HBox();
        HBox sixthToggle = new HBox();

        // Styling Label
        labelQuantity.setFont(Font.font("Montserrat", FontWeight.BOLD, 24));
        labelQuantity.setStyle("-fx-text-fill: #3B919B");

        // Styling Input
        quantity.setFont(Font.font("Montserrat",18));
        quantity.setPadding(new Insets(10, 20, 10 ,20));
        quantity.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px;");
        quantity.setPrefWidth(550);
        quantity.setAlignment(Pos.CENTER);

        // Styling Button
        minusQuantity.setFont(Font.font("Montserrat",18));
        minusQuantity.setPadding(new Insets(10, 20, 10 ,20));
        minusQuantity.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px;");
        minusQuantity.setPrefWidth(300);
        minusQuantity.setAlignment(Pos.CENTER);

        plusQuantity.setFont(Font.font("Montserrat",18));
        plusQuantity.setPadding(new Insets(10, 20, 10 ,20));
        plusQuantity.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px;");
        plusQuantity.setPrefWidth(300);
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
}
