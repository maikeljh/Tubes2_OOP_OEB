package UI;

import DataStore.DataStore;
import System.*;
import com.itextpdf.text.DocumentException;
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

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;

public class HistoryPage extends VBox {
    public HistoryPage(Stage stage, Tab tab, Customer customer, Inventory<Customer> customers, DataStore<Customer> customerDS, Settings settings) {
        // Create header (HBox)
        HBox hbox = new HBox();

        // Create title label
        Label title = new Label();
        if (customer.getClass().getSimpleName().equals("Customer")) {
            title.setText("Transaction History Customer UnknownGuest" + customer.getId());
        }
        else {
            title.setText("Transaction History Customer " + ((RegisteredCustomer) customer).getName());
        }
        title.setFont(Font.font("Montserrat", FontWeight.BOLD, 28));

        // Create hbox for back button
        HBox backBox = new HBox();

        // Create back button
        Button backButton = new Button("Back");
        backButton.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        backButton.setStyle("-fx-background-color: #3B919B; -fx-text-fill: white; -fx-background-radius: 30px;");
        backButton.setPrefSize(77, 27);
        backButton.setCursor(Cursor.HAND);

        // Add event handler for back button
        backButton.setOnAction(event -> {
            ListMemberPage listMemberPage = new ListMemberPage(stage, tab, customers, customerDS, settings);
            tab.setContent(listMemberPage);
        });

        // Style backBox
        backBox.setAlignment(Pos.CENTER_RIGHT);
        backBox.getChildren().add(backButton);
        HBox.setHgrow(backBox, Priority.ALWAYS);

        // Add title label to HBox header
        hbox.getChildren().addAll(title);
        hbox.getChildren().add(backBox);

        // Create a list of history (VBox)
        VBox vbox = new VBox(15); // spacing = 8
        vbox.setAlignment(Pos.BASELINE_CENTER);
        vbox.prefWidthProperty().bind(hbox.widthProperty());
        vbox.setPadding(new Insets(0, 30,30,0));

        int i = 1; // counter

        // Display list of transactions / history
        for (FixedBill transaction : customer.getTransaction().getBox()) {
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
            ImageView previewIcon = new ImageView("/images/icon/downloadLogo.png");

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
                    previewButton.setDisable(true);
                    transaction.printBill();
                    Thread.sleep(1000);
                    previewButton.setDisable(false);

                    // Show alert
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Successful");
                    alert.setHeaderText("Print Sales Report was successful!");
                    alert.setContentText("You can open it on resources/files/Sales Report.pdf");
                    alert.showAndWait();
                } catch (DocumentException | FileNotFoundException | InterruptedException e){
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
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: #F3F9FB;");
        scrollPane.setPadding(new Insets(0, 10, 20, 10));


        // Style vbox
        vbox.setStyle("-fx-background-color: #F3F9FB");

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
