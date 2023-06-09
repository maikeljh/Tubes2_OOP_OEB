package UI;

import DataStore.DataStore;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.embed.swing.SwingFXUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import Core.Settings;
import Core.Inventory;
import Core.Item;

import javax.imageio.ImageIO;

public class AddItemPage extends Page {
    private final ImageView itemImage;

    public AddItemPage(Stage stage, Tab tab, Inventory<Item> items, DataStore<Item> itemDS, Settings settings, DataStore<Settings> settingsDS){
        super(stage, tab, settings, settingsDS);

        // Create HBox for header
        HBox hBox = new HBox();

        // Create HBox for add button
        HBox rightButton = new HBox();

        // Create title
        Label title = new Label("Add Item");
        title.setFont(Font.font("Montserrat", FontWeight.BOLD, 28));

        // Create add button
        Button addButton = new Button("Save");
        addButton.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        addButton.setStyle("-fx-background-color: #3B919B; -fx-text-fill: white;");

        // Create cancel button
        Button cancelButton = new Button("Cancel");
        cancelButton.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        cancelButton.setStyle("-fx-background-color: #C34646; -fx-text-fill: white;");
        cancelButton.setOnAction(event -> {
            ListItemPage listItemPage = new ListItemPage(stage, tab, items, itemDS, settings, settingsDS);
            tab.setContent(listItemPage);
        });

        // Set add button to HBox
        rightButton.getChildren().addAll(cancelButton, addButton);
        HBox.setHgrow(rightButton, Priority.ALWAYS);
        rightButton.setAlignment(Pos.CENTER_RIGHT);

        // Add header to HBox
        hBox.getChildren().addAll(title, rightButton);

        // Create Layout for Detail
        HBox detailLayout = new HBox();
        detailLayout.setSpacing(100);

        // Left Side Inputs
        VBox leftDetail = new VBox();

        // Create Image Input
        itemImage = new ImageView(new Image("/images/dummy4.png"));

        // Styling Image Input
        itemImage.setPreserveRatio(true);
        itemImage.setSmooth(true);
        itemImage.setCache(true);
        itemImage.setFitWidth(549);
        itemImage.setFitHeight(329);

        // Add Event Handler for Image Input
        itemImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            FileChooser fileChooser = new FileChooser();

            // Set the extension filters
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*jpeg"));

            // Show the file chooser dialog
            File selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null) {
                // Load the selected image into an ImageView
                Image image = new Image(selectedFile.toURI().toString());
                itemImage.setImage(image);
            }
        });

        // Right Side Inputs
        VBox rightDetail = new VBox();

        // Create Labels
        Label labelName = new Label("Item Name");
        Label labelPhoto = new Label("Item Photo");
        Label labelCategory = new Label("Item Category");
        Label labelSellPrice = new Label("Item Sell Price");
        Label labelBuyPrice = new Label("Item Buy Price");
        Label labelStocks = new Label("Stock");

        // Create item's attributes inputs
        TextField name = new TextField();
        TextField category = new TextField();
        TextField sellPrice = new TextField();
        TextField buyPrice = new TextField();
        TextField stocks = new TextField();

        // Set placeholder inputs
        name.setPromptText("Enter item name . . .");
        category.setPromptText("Enter item category . . .");
        sellPrice.setPromptText("Enter item sell price . . .");
        buyPrice.setPromptText("Enter item Buy price . . .");
        stocks.setPromptText("0");

        // Create VBox for each attribute inputs
        VBox nameDetail = new VBox();
        VBox photoDetail = new VBox();
        VBox categoryDetail = new VBox();
        VBox sellPriceDetail = new VBox();
        VBox buyPriceDetail = new VBox();
        VBox stocksDetail = new VBox();

        // Add attribute inputs to vbox
        nameDetail.getChildren().addAll(labelName, name);
        photoDetail.getChildren().addAll(labelPhoto, itemImage);
        categoryDetail.getChildren().addAll(labelCategory, category);
        sellPriceDetail.getChildren().addAll(labelSellPrice, sellPrice);
        buyPriceDetail.getChildren().addAll(labelBuyPrice, buyPrice);
        stocksDetail.getChildren().addAll(labelStocks, stocks);

        // Add nodes to layouts
        leftDetail.getChildren().addAll(nameDetail, photoDetail);
        rightDetail.getChildren().addAll(categoryDetail, sellPriceDetail, buyPriceDetail, stocksDetail);
        detailLayout.getChildren().addAll(leftDetail, rightDetail);

        // Spacing Layouts
        leftDetail.setSpacing(40);
        rightDetail.setSpacing(40);
        nameDetail.setSpacing(5);
        photoDetail.setSpacing(5);
        categoryDetail.setSpacing(5);
        sellPriceDetail.setSpacing(5);
        buyPriceDetail.setSpacing(5);
        stocksDetail.setSpacing(5);
        rightButton.setSpacing(15);

        // Styling Label
        labelName.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
        labelPhoto.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
        labelCategory.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
        labelSellPrice.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
        labelBuyPrice.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
        labelStocks.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));

        // Styling Inputs
        name.setFont(Font.font("Montserrat",18));
        name.setPadding(new Insets(10, 20, 10 ,20));
        name.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px;");
        name.setPrefWidth(550);

        category.setFont(Font.font("Montserrat",18));
        category.setPadding(new Insets(10, 20, 10 ,20));
        category.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px;");
        category.setPrefWidth(530);

        sellPrice.setFont(Font.font("Montserrat",18));
        sellPrice.setPadding(new Insets(10, 20, 10 ,20));
        sellPrice.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px;");
        sellPrice.setPrefWidth(530);

        buyPrice.setFont(Font.font("Montserrat",18));
        buyPrice.setPadding(new Insets(10, 20, 10 ,20));
        buyPrice.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px;");
        buyPrice.setPrefWidth(530);

        stocks.setFont(Font.font("Montserrat",18));
        stocks.setPadding(new Insets(10, 20, 10 ,20));
        stocks.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px;");
        stocks.setPrefWidth(530);

        // Add Contents
        getChildren().addAll(hBox, detailLayout);

        // Styling Layout
        setPadding(new Insets(30, 50, 0, 50));
        setSpacing(40);
        setStyle("-fx-background-color: #F3F9FB;");

        // Add Event
        addButton.setOnAction(event -> {
            // Getting Inputs
            String newName = name.getText();
            String newStockText = stocks.getText();
            String sellPriceText = sellPrice.getText();
            String buyPriceText = buyPrice.getText();
            String newCategory = category.getText();
            Image newImage = itemImage.getImage();

            // Check if all inputs are not empty
            if (!newName.isEmpty() && !newStockText.isEmpty() && !sellPriceText.isEmpty() && !buyPriceText.isEmpty() && !newCategory.isEmpty()) {
                // Parse Numeric Input
                int newStock = Integer.parseInt(newStockText);
                double newSellPrice = Double.parseDouble(sellPriceText);
                double newBuyPrice = Double.parseDouble(buyPriceText);

                // Create new Item
                Item newItem = new Item(newName, newStock, newSellPrice, newBuyPrice, newCategory, newImage);

                // Add item to inventory
                items.addElement(newItem);

                // Create Image File
                File output = new File("./saves/item" + newItem.getItemID() + ".png");
                try {
                    // Create buffer image
                    BufferedImage awtImage = new BufferedImage((int)newImage.getWidth(), (int)newImage.getHeight(), BufferedImage.TRANSLUCENT);
                    SwingFXUtils.fromFXImage(newImage, awtImage);

                    // Write image to file
                    ImageIO.write(awtImage, "png", output);
                } catch (IOException e) {
                    // Handle Error
                }

                // Save data to file
                itemDS.saveData("item", settings, new Class<?>[]{Inventory.class, Item.class}, items);

                // Change page
                ListItemPage listItemPage = new ListItemPage(stage, tab, items, itemDS, settings, settingsDS);
                tab.setContent(listItemPage);
            } else {
                // Show alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                // Center the header and content text
                Label header = new Label("Fail to Add Item");
                header.setStyle("-fx-font-size: 1.5em;");
                header.setStyle("-fx-font-weight: bold;");
                header.setAlignment(Pos.CENTER);

                Text content = new Text("All fields must be filled!");
                content.setTextAlignment(TextAlignment.LEFT);

                // Set the content text as a graphic
                alert.getDialogPane().setContent(new VBox(header, content));
                alert.setHeaderText("");
                alert.showAndWait();
            }
        });
    }
}
