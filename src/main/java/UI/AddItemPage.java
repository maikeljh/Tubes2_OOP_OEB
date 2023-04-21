package UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AddItemPage extends VBox{
    private final ImageView itemImage;

    public AddItemPage(Stage stage){
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

        // Set add button to HBox
        rightButton.getChildren().add(addButton);
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
    }
}
