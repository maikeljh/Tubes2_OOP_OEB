package UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class ItemDetailPage extends VBox {
    public ItemDetailPage(Stage stage){
        // Create HBox for header
        HBox hBox = new HBox();

        // Create HBox for add button
        HBox rightButton = new HBox();

        // Create title
        Label title = new Label("Detail Item");
        title.setFont(Font.font("Montserrat", FontWeight.BOLD, 28));
        title.setTextAlignment(TextAlignment.CENTER);

        // Create update button
        Button updateButton = new Button("Update");
        updateButton.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        updateButton.setStyle("-fx-background-color: #3B919B; -fx-text-fill: white;");

        // Create delete button
        Button deleteButton = new Button("Delete");
        deleteButton.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        deleteButton.setStyle("-fx-background-color: #C34646; -fx-text-fill: white;");

        // Set add button to HBox
        rightButton.getChildren().addAll(updateButton, deleteButton);
        rightButton.setSpacing(15);
        HBox.setHgrow(rightButton, Priority.ALWAYS);
        rightButton.setAlignment(Pos.CENTER_RIGHT);

        // Add header to HBox
        hBox.getChildren().addAll(title, rightButton);

        // Create Layout for Detail
        VBox detailLayout = new VBox();
        detailLayout.setSpacing(20);

        // Item Name
        Label name = new Label("Cappucino");

        // Others (All Other Attributes)
        HBox others = new HBox();

        // Item Image
        ImageView itemImage = new ImageView(new Image("/images/dummy2.png"));

        // Styling Item Image
        itemImage.setPreserveRatio(true);
        itemImage.setSmooth(true);
        itemImage.setCache(true);
        itemImage.setFitWidth(612);
        itemImage.setFitHeight(350);

        // Create VBox for Others
        VBox detail = new VBox();

        // Create Labels
        Label labelCategory = new Label("Item Category");
        Label labelSellPrice = new Label("Item Sell Price");
        Label labelBuyPrice = new Label("Item Buy Price");
        Label labelStocks = new Label("Stock");

        // Create item's attributes label
        Label category = new Label("Drink");
        Label sellPrice = new Label("30000");
        Label buyPrice = new Label("20000");
        Label stocks = new Label("5");

        // Create VBox for each attribute labels
        VBox categoryDetail = new VBox();
        VBox sellPriceDetail = new VBox();
        VBox buyPriceDetail = new VBox();
        VBox stocksDetail = new VBox();

        // Add attributes to vbox
        categoryDetail.getChildren().addAll(labelCategory, category);
        sellPriceDetail.getChildren().addAll(labelSellPrice, sellPrice);
        buyPriceDetail.getChildren().addAll(labelBuyPrice, buyPrice);
        stocksDetail.getChildren().addAll(labelStocks, stocks);

        // Add nodes to layouts
        detail.getChildren().addAll(categoryDetail, sellPriceDetail, buyPriceDetail, stocksDetail);
        others.getChildren().addAll(itemImage, detail);

        // Spacing Layouts
        detail.setSpacing(10);
        categoryDetail.setSpacing(5);
        sellPriceDetail.setSpacing(5);
        buyPriceDetail.setSpacing(5);
        stocksDetail.setSpacing(5);
        others.setSpacing(40);

        // Styling Name
        name.setFont(Font.font("Montserrat", FontWeight.BOLD, 28));

        // Styling Label
        labelCategory.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
        labelSellPrice.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
        labelBuyPrice.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
        labelStocks.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));

        // Styling Attributes
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

        // Add Details
        detailLayout.getChildren().add(name);
        detailLayout.getChildren().add(others);

        // Add Contents
        getChildren().addAll(hBox, detailLayout);

        // Styling Layout
        setPadding(new Insets(30, 50, 0, 50));
        setSpacing(40);
        setStyle("-fx-background-color: #F3F9FB;");
    }
}
