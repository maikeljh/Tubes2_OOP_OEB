package UI;

import System.Customer;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;

public class AddMemberPage extends VBox {
    public AddMemberPage(Stage stage) {
        // Create HBox for header
        HBox hBox = new HBox();

        // Create HBox for save Button
        HBox rightButton = new HBox();

        // Create title
        Label title = new Label("New Member");
        title.setFont(Font.font("Montserrat", FontWeight.BOLD, 28));

        // Set image for save button
        ImageView saveIcon = new ImageView("/images/icon/saveButton.png");

        // Styling save button icon
        saveIcon.setPreserveRatio(true);
        saveIcon.setSmooth(true);
        saveIcon.setCache(true);
        saveIcon.setFitWidth(87);
        saveIcon.setFitHeight(31);

        // Create save button
        Button saveButton = new Button();
        saveButton.setStyle("-fx-background-color: #3B919B; -fx-text-fill: white; -fx-background-radius: 30px;");
        saveButton.setGraphic(saveIcon);
        saveButton.setPrefSize(87, 31);
        saveButton.setCursor(Cursor.HAND);

        // Set save button to HBox
        rightButton.getChildren().add(saveButton);
        HBox.setHgrow(rightButton, Priority.ALWAYS);
        rightButton.setAlignment(Pos.CENTER_RIGHT);

        // Add header to HBox
        hBox.getChildren().addAll(title, rightButton);

        // Create VBox CustomerID Field
        VBox customerField = new VBox();

        // Create label for CustomerID Field
        Label customer = new Label("Customer ID");
        customer.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));

        // Create label for CustomerID Field
        Label customerID = new Label(String.valueOf(Customer.getCustomerCount()));

        // Styling customerID label
        customerID.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px");
        customerID.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
        customerID.setPadding(new Insets(0, 0, 0, 10));
        customerID.setMinWidth(1194);
        customerID.setMinHeight(50);

        // set customerField
        customerField.getChildren().add(customer);
        customerField.getChildren().add(customerID);

        // Create VBox for Name Field
        VBox nameField = new VBox();

        // Create label for name title
        Label nameTitle = new Label("Name");
        nameTitle.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));

        // Create textField for name
        TextField inputName = new TextField("Enter name...");
        inputName.setPadding(new Insets(0, 0, 0, 10));
        inputName.setFont(Font.font("Montserrat", 20));
        inputName.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px; -fx-text-fill: #8CAEBB");
        inputName.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            inputName.setText("");
        });
        inputName.setMinWidth(1194);
        inputName.setMinHeight(50);

        // set nameField
        nameField.getChildren().add(nameTitle);
        nameField.getChildren().add(inputName);

        // Create VBox for Phone Number Field
        VBox phoneField = new VBox();

        // Create label for Phone Number title
        Label phoneTitle = new Label("Phone Number");
        phoneTitle.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));

        // Create textField for phone number
        TextField inputNumber = new TextField("Enter phone number...");
        inputNumber.setPadding(new Insets(0, 0, 0, 10));
        inputNumber.setFont(Font.font("Montserrat", 20));
        inputNumber.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px; -fx-text-fill: #8CAEBB");
        inputNumber.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            inputNumber.setText("");
        });
        inputNumber.setMinWidth(1194);
        inputNumber.setMinHeight(50);

        // set phoneField
        phoneField.getChildren().add(phoneTitle);
        phoneField.getChildren().add(inputNumber);

        // Add contents
        getChildren().add(hBox);
        getChildren().add(customerField);
        getChildren().add(nameField);
        getChildren().add(phoneField);

        // Styling layout
        setPadding(new Insets(30, 50, 0, 50));
        setSpacing(40);
        setStyle("-fx-background-color: #F3F9FB;");
    }
}
