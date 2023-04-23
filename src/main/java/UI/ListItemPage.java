package UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import System.Inventory;
import System.Item;
import javafx.stage.Stage;

public class ListItemPage extends VBox {

    public ListItemPage(Stage stage, Tab tab, Inventory<Item> items){
        // Create HBox for header
        HBox hBox = new HBox();

        // Create HBox for add button
        HBox rightButton = new HBox();

        // Create title
        Text title = new Text("Items");
        title.setFont(Font.font("Montserrat", FontWeight.BOLD, 28));
        title.setTextAlignment(TextAlignment.CENTER);

        // Create add button
        Button addButton = new Button("+ Add Item");
        addButton.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        addButton.setStyle("-fx-background-color: #3B919B; -fx-text-fill: white;");
        addButton.setOnAction(event -> {
            AddItemPage addItemContent = new AddItemPage(stage, tab, items);
            tab.setContent(addItemContent);
        });

        // Set add button to HBox
        rightButton.getChildren().add(addButton);
        HBox.setHgrow(rightButton, Priority.ALWAYS);
        rightButton.setAlignment(Pos.CENTER_RIGHT);

        // Add header to HBox
        hBox.getChildren().addAll(title, rightButton);

        // List of Items
        GridPane grid = new GridPane();

        // Styling gridpane
        grid.setStyle("-fx-background-color: #F3F9FB;");
        grid.setHgap(40);
        grid.setVgap(40);
        grid.prefWidthProperty().bind(hBox.widthProperty());

        // Create Dummy List of Items
        int row = 0;
        int col = 0;
        for(int i = 0; i < items.getNeff(); i++){
            Image image = new Image("/images/dummy.png");
            items.getElement(i).setImage(image);
        }

        // Display List Of Items
        for (Item item : items.getList()) {
            // VBox Display Item
            VBox itemDisplay = new VBox();

            // Image View Item
            ImageView itemImage = new ImageView(item.getImage());

            // Styling Item Image
            itemImage.setPreserveRatio(true);
            itemImage.setSmooth(true);
            itemImage.setCache(true);
            itemImage.setFitWidth(149);
            itemImage.setFitHeight(121);

            // Item Name
            Text itemName = new Text(item.getName());

            // Styling Item Name
            itemName.setFont(Font.font("Montserrat", 14));

            // Styling Item Display
            itemDisplay.getChildren().addAll(itemImage, itemName);
            itemDisplay.setAlignment(Pos.CENTER);
            itemDisplay.setPadding(new Insets(5));
            itemDisplay.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px;");
            itemDisplay.setSpacing(5);

            // Add onclick event
            itemDisplay.setOnMouseClicked(event -> {
                ItemDetailPage detailItemContent = new ItemDetailPage(stage, tab, item, items);
                tab.setContent(detailItemContent);
            });
            // Add Item Display to Grid
            grid.add(itemDisplay, col, row);
            col++;
            if (col == 6) {
                col = 0;
                row++;
            }
        }

        // Create Scroll Pane for GridPane
        ScrollPane scrollPane = new ScrollPane(grid);

        // Styling Scroll Pane
        scrollPane.setPrefHeight(600);
        scrollPane.setStyle("-fx-background: #F3F9FB; -fx-background-color: #F3F9FB;");


        // Add contents
        getChildren().add(hBox);
        getChildren().add(scrollPane);

        // Styling Layout
        setPadding(new Insets(20, 40, 0, 40));
        setSpacing(20);
        setStyle("-fx-background-color: #F3F9FB;");
    }
}
