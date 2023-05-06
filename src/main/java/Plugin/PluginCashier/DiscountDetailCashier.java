package Plugin.PluginCashier;

import DataStore.DataStore;
import Plugin.Decorator.CashierDetailDecorator;
import UI.CashierPage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.Serializable;

public class DiscountDetailCashier extends CashierDetailDecorator implements Serializable {
    private double tempDiscount;

    public DiscountDetailCashier(){
        this.pluginName = "Plugin Detail Discount";
        this.nextPlugin = "Plugin Tax & Service";
        this.tempDiscount = 0;
    }

    public void execute(){
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

        Label studentDiscount = new Label("Student 10%");
        Label dineInDiscount = new Label("Dine in 15%");
        Label independenceDiscount = new Label("Independence Day 17%");
        Label fortuneDiscount = new Label("Weekend Fortune 20%");
        Label anniversaryDiscount = new Label("Anniversary 25%");
        Label borongDiscount = new Label("Borong Day 50%");

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
                if(secondToggleButton.isSelected()){
                    secondToggleButton.fire();
                } else if(thirdToggleButton.isSelected()){
                    thirdToggleButton.fire();
                } else if(fourthToggleButton.isSelected()){
                    fourthToggleButton.fire();
                } else if(fifthToggleButton.isSelected()){
                    fifthToggleButton.fire();
                } else if (sixthToggleButton.isSelected()){
                    sixthToggleButton.fire();
                }
                tempDiscount = 0.1;
            }
            else {
                firstToggleButton.setGraphic(offToggle1);
                tempDiscount = 0;
            }
        });

        // Handling Toggle Button event
        secondToggleButton.setOnAction(event -> {
            if (secondToggleButton.isSelected()) {
                secondToggleButton.setGraphic(onToggle2);
                if(firstToggleButton.isSelected()){
                    firstToggleButton.fire();
                } else if(thirdToggleButton.isSelected()){
                    thirdToggleButton.fire();
                } else if(fourthToggleButton.isSelected()){
                    fourthToggleButton.fire();
                } else if(fifthToggleButton.isSelected()){
                    fifthToggleButton.fire();
                } else if (sixthToggleButton.isSelected()){
                    sixthToggleButton.fire();
                }
                tempDiscount = 0.15;
            }
            else {
                secondToggleButton.setGraphic(offToggle2);
                tempDiscount = 0;
            }
        });

        // Handling Toggle Button event
        thirdToggleButton.setOnAction(event -> {
            if (thirdToggleButton.isSelected()) {
                thirdToggleButton.setGraphic(onToggle3);
                if(secondToggleButton.isSelected()){
                    secondToggleButton.fire();
                } else if(firstToggleButton.isSelected()){
                    firstToggleButton.fire();
                } else if(fourthToggleButton.isSelected()){
                    fourthToggleButton.fire();
                } else if(fifthToggleButton.isSelected()){
                    fifthToggleButton.fire();
                } else if (sixthToggleButton.isSelected()){
                    sixthToggleButton.fire();
                }
                tempDiscount = 0.17;
            }
            else {
                thirdToggleButton.setGraphic(offToggle3);
                tempDiscount = 0;
            }
        });

        // Handling Toggle Button event
        fourthToggleButton.setOnAction(event -> {
            if (fourthToggleButton.isSelected()) {
                fourthToggleButton.setGraphic(onToggle4);
                if(secondToggleButton.isSelected()){
                    secondToggleButton.fire();
                } else if(thirdToggleButton.isSelected()){
                    thirdToggleButton.fire();
                } else if(firstToggleButton.isSelected()){
                    firstToggleButton.fire();
                } else if(fifthToggleButton.isSelected()){
                    fifthToggleButton.fire();
                } else if (sixthToggleButton.isSelected()){
                    sixthToggleButton.fire();
                }
                tempDiscount = 0.20;
            }
            else {
                fourthToggleButton.setGraphic(offToggle4);
                tempDiscount = 0;
            }
        });

        // Handling Toggle Button event
        fifthToggleButton.setOnAction(event -> {
            if (fifthToggleButton.isSelected()) {
                fifthToggleButton.setGraphic(onToggle5);
                if(secondToggleButton.isSelected()){
                    secondToggleButton.fire();
                } else if(thirdToggleButton.isSelected()){
                    thirdToggleButton.fire();
                } else if(fourthToggleButton.isSelected()){
                    fourthToggleButton.fire();
                } else if(firstToggleButton.isSelected()){
                    firstToggleButton.fire();
                } else if (sixthToggleButton.isSelected()){
                    sixthToggleButton.fire();
                }
                tempDiscount = 0.25;
            }
            else {
                fifthToggleButton.setGraphic(offToggle5);
                tempDiscount = 0;
            }
        });

        // Handling Toggle Button event
        sixthToggleButton.setOnAction(event -> {
            if (sixthToggleButton.isSelected()) {
                sixthToggleButton.setGraphic(onToggle6);
                if(secondToggleButton.isSelected()){
                    secondToggleButton.fire();
                } else if(thirdToggleButton.isSelected()){
                    thirdToggleButton.fire();
                } else if(fourthToggleButton.isSelected()){
                    fourthToggleButton.fire();
                } else if(fifthToggleButton.isSelected()){
                    fifthToggleButton.fire();
                } else if (firstToggleButton.isSelected()){
                    firstToggleButton.fire();
                }
                tempDiscount = 0.50;
            }
            else {
                sixthToggleButton.setGraphic(offToggle6);
                tempDiscount = 0;
            }
        });

        // Set Toggle Button to Toggle Input
        firstToggleBox.getChildren().addAll(studentDiscount, firstToggle);
        secondToggleBox.getChildren().addAll(dineInDiscount, secondToggle);
        thirdToggleBox.getChildren().addAll(independenceDiscount, thirdToggle);
        fourthToggleBox.getChildren().addAll(fortuneDiscount, fourthToggle);
        fifthToggleBox.getChildren().addAll(anniversaryDiscount, fifthToggle);
        sixthToggleBox.getChildren().addAll(borongDiscount, sixthToggle);

        // Styling Toggle Box
        HBox.setHgrow(firstToggle, Priority.ALWAYS);
        firstToggle.setAlignment(Pos.CENTER_RIGHT);
        studentDiscount.setAlignment(Pos.CENTER);

        HBox.setHgrow(secondToggle, Priority.ALWAYS);
        secondToggle.setAlignment(Pos.CENTER_RIGHT);
        dineInDiscount.setAlignment(Pos.CENTER);

        HBox.setHgrow(thirdToggle, Priority.ALWAYS);
        thirdToggle.setAlignment(Pos.CENTER_RIGHT);
        independenceDiscount.setAlignment(Pos.CENTER);

        HBox.setHgrow(fourthToggle, Priority.ALWAYS);
        fourthToggle.setAlignment(Pos.CENTER_RIGHT);
        fortuneDiscount.setAlignment(Pos.CENTER);

        HBox.setHgrow(fifthToggle, Priority.ALWAYS);
        fifthToggle.setAlignment(Pos.CENTER_RIGHT);
        anniversaryDiscount.setAlignment(Pos.CENTER);

        HBox.setHgrow(sixthToggle, Priority.ALWAYS);
        sixthToggle.setAlignment(Pos.CENTER_RIGHT);
        borongDiscount.setAlignment(Pos.CENTER);

        // Add Toggle Inputs to VBox
        leftDiscountBox.getChildren().addAll(firstToggleBox, secondToggleBox, thirdToggleBox);
        rightDiscountBox.getChildren().addAll(fourthToggleBox, fifthToggleBox, sixthToggleBox);

        // Add Discounts to HBox
        discountInput.getChildren().addAll(leftDiscountBox, rightDiscountBox);

        // Add Discount Input to Discount Box
        discountBox.getChildren().addAll(labelDiscount, discountInput);

        // Styling
        labelDiscount.setFont(Font.font("Montserrat", FontWeight.BOLD, 24));
        labelDiscount.setStyle("-fx-text-fill: #3B919B");

        studentDiscount.setFont(Font.font("Montserrat",20));
        firstToggleBox.setPadding(new Insets(10, 20, 10 ,18));
        firstToggleBox.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px;");
        firstToggleBox.setPrefWidth(580);

        dineInDiscount.setFont(Font.font("Montserrat",20));
        secondToggleBox.setPadding(new Insets(10, 20, 10 ,18));
        secondToggleBox.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px;");
        secondToggleBox.setPrefWidth(580);

        independenceDiscount.setFont(Font.font("Montserrat",20));
        thirdToggleBox.setPadding(new Insets(10, 20, 10 ,18));
        thirdToggleBox.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px;");
        thirdToggleBox.setPrefWidth(580);

        fortuneDiscount.setFont(Font.font("Montserrat",20));
        fourthToggleBox.setPadding(new Insets(10, 20, 10 ,18));
        fourthToggleBox.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px;");
        fourthToggleBox.setPrefWidth(580);

        anniversaryDiscount.setFont(Font.font("Montserrat",20));
        fifthToggleBox.setPadding(new Insets(10, 20, 10 ,18));
        fifthToggleBox.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px;");
        fifthToggleBox.setPrefWidth(580);

        borongDiscount.setFont(Font.font("Montserrat",20));
        sixthToggleBox.setPadding(new Insets(10, 20, 10 ,18));
        sixthToggleBox.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px;");
        sixthToggleBox.setPrefWidth(580);

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
