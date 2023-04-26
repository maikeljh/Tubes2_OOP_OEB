package UI;

import com.itextpdf.text.DocumentException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import System.FixedBill;
import System.Inventory;
import System.VIP;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;

public class HistoryPage extends VBox {
    public HistoryPage(Stage stage, Tab tab, String customer_name, Inventory<FixedBill> transactions) {
        // Create header (HBox)
        HBox hbox = new HBox();

        // Create title label
        Label title = new Label("Transaction History Customer " + customer_name);
        title.setFont(Font.font("Montserrat", FontWeight.BOLD, 28));

        // Add title label to HBox header
        hbox.getChildren().addAll(title);

        // Create a list of history (VBox)
        VBox vbox = new VBox(15); // spacing = 8
        vbox.setAlignment(Pos.BASELINE_CENTER);
        vbox.prefWidthProperty().bind(hbox.widthProperty());
        vbox.setPadding(new Insets(0, 30,30,0));

        int i = 1; // counter

        // Display list of transactions / history
        for (FixedBill transaction : transactions.getList()) {
            // Create HBox for every transaction (fixed bill)
            HBox transactionHBox = new HBox();

            // Create HBox for datetime
            HBox transactionDetailHBox = new HBox();

            // Create HBox for buttons
            HBox transactionButtonsHBox = new HBox();

            // Create Label for transaction details
            Label details = new Label(i + "   " + transaction.getDate());
            details.setFont(Font.font("Montserrat", 20));
            details.setPadding(new Insets(10));
            i++;

            // Set image / icon for preview button
            ImageView previewIcon = new ImageView("/images/icon/previewButton.png");

            // Style preview button icon
            previewIcon.setPreserveRatio(true);
            previewIcon.setSmooth(true);
            previewIcon.setCache(true);
            previewIcon.setFitWidth(27);
            previewIcon.setFitHeight(27);

            // Create preview button
            Button previewButton = new Button();
            previewButton.setPrefSize(27, 27);
            previewButton.setStyle("-fx-background-color: #C8DFE8;");
            previewButton.setGraphic(previewIcon);
            previewButton.setCursor(Cursor.HAND);
            previewButton.setOnAction(event -> {
                try {
                    transaction.printBill();
                } catch (DocumentException | FileNotFoundException e){
                    e.printStackTrace();
                }
            });

            // Add label detail (datetime) to transaction details
            transactionDetailHBox.getChildren().add(details);

            // Style transaction detail HBox
            transactionDetailHBox.setSpacing(3);
            HBox.setHgrow(transactionDetailHBox, Priority.ALWAYS);
            transactionDetailHBox.setAlignment(Pos.CENTER_LEFT);

            // Style transaction buttons HBox
            transactionButtonsHBox.setAlignment(Pos.CENTER_RIGHT);

            // Add preview button to transaction buttons HBox
            transactionButtonsHBox.getChildren().add(previewButton);

            // Style transaction HBox
            transactionHBox.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px");
            transactionHBox.minWidth(1194);
            transactionHBox.minHeight(50);

            // Add transaction details HBox and transaction buttons HBox to transaction HBox
            transactionHBox.getChildren().add(transactionDetailHBox);
            transactionHBox.getChildren().add(transactionButtonsHBox);

            // Add transaction HBox to VBox
            vbox.getChildren().addAll(transactionHBox);
        }

        // Create scroll pane for VBox
        ScrollPane scrollPane = new ScrollPane(vbox);

        // Style scroll pane
        scrollPane.setMaxHeight(640);
        scrollPane.setStyle("-fx-background-color: #F3F9FB;");
        scrollPane.setPadding(new Insets(0, 0, 0, 10));

        // Add contents
        getChildren().add(hbox);
        getChildren().add(vbox);
        getChildren().add(scrollPane);

        // Styling Layout
        setPadding(new Insets(20, 40, 0, 40));
        setSpacing(8);
        setStyle("-fx-background-color: #F3F9FB;");
    }
}
