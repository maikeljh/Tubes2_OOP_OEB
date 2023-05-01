package UI;

import System.Customer;
import System.Inventory;
import System.Member;
import System.RegisteredCustomer;
import System.VIP;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import System.Settings;
import DataStore.DataStore;
import javafx.scene.image.ImageView;
import System.FixedBill;
import System.PurchasedItem;
import java.util.ArrayList;
import java.util.List;

public class AddMemberPage extends VBox {
    public AddMemberPage(Stage stage, Tab tab, Inventory<Customer> customers, DataStore<Customer> customerDS, Settings settings) {
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

        // Create cancel button
        Button cancelButton = new Button("Cancel");
        cancelButton.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        cancelButton.setStyle("-fx-background-color: #C34646; -fx-text-fill: white; -fx-background-radius: 30px;");
        cancelButton.setPrefSize(97, 41);
        cancelButton.setCursor(Cursor.HAND);

        // Add event handler for cancel button
        cancelButton.setOnAction(event -> {
            ListMemberPage listMemberPage = new ListMemberPage(stage, tab, customers, customerDS, settings);
            tab.setContent(listMemberPage);
        });

        // Set save button to HBox
        rightButton.getChildren().add(cancelButton);
        rightButton.getChildren().add(saveButton);
        rightButton.setSpacing(8);
        HBox.setHgrow(rightButton, Priority.ALWAYS);
        rightButton.setAlignment(Pos.CENTER_RIGHT);

        // Add header to HBox
        hBox.getChildren().addAll(title, rightButton);

        // Create VBox CustomerID Field
        VBox customerField = new VBox();

        // Create label for CustomerID Field
        Label customerTitle = new Label("Customer ID");
        customerTitle.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));

        // Listing all customerID that is available to turn to member
        List<Integer> idList = new ArrayList<Integer>();
        for (Customer customer : customers.getList()) {
            if (customer.getClass().getSimpleName().equals("Customer")) {
                idList.add(customer.getId());
            }
        }

        // Create combobox for CustomerID Field
        ComboBox<Integer> customerID = new ComboBox<Integer>();
        ObservableList<Integer> observableId = FXCollections.observableList(idList);
        customerID.setItems(observableId);

        // Styling customerID label
        customerID.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px;");
        customerID.setPadding(new Insets(0, 0, 0, 10));
        customerID.setMinWidth(1194);
        customerID.setMinHeight(50);

        // set customerField
        customerField.getChildren().add(customerTitle);
        customerField.getChildren().add(customerID);

        // Create VBox for Name Field
        VBox nameField = new VBox();

        // Create label for name title
        Label nameTitle = new Label("Name");
        nameTitle.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));

        // Create textField for name
        TextField inputName = new TextField();
        inputName.setPadding(new Insets(0, 0, 0, 10));
        inputName.setFont(Font.font("Montserrat", 20));
        inputName.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px; -fx-text-fill: black;");
        inputName.setPromptText("Enter name...");
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
        TextField inputNumber = new TextField();
        inputNumber.setPadding(new Insets(0, 0, 0, 10));
        inputNumber.setFont(Font.font("Montserrat", 20));
        inputNumber.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px; -fx-text-fill: black");
        inputNumber.setPromptText("Enter phone number...");
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

        // Add event handler for save button
        saveButton.setOnAction(event -> {
            int id = customerID.getValue();
            String name = inputName.getText();
            String phoneNumber = inputNumber.getText();
            FixedBill bill = new FixedBill();

            for (Customer customer : customers.getList()) {
                if (customer.getId() == id) {
                    bill = customer.getTransaction().getElement(0);
                }
            }

            if (!(customerID.getValue() == null) && !name.isEmpty() && !phoneNumber.isEmpty()) {
                // Create new member
                Member newMember = new Member(id, name, phoneNumber, bill);

                // Change customer to member
                if (customers.getElement(id-1).getId() == id) {
                    customers.setElement(id-1, newMember);
                }
                else {
                    throw new Error("Niggas are drunk up oop open it up");
                }

                // Save data
                customerDS.saveData("customer", settings, new Class<?>[]{Inventory.class, Customer.class, RegisteredCustomer.class, Member.class, VIP.class, FixedBill.class, PurchasedItem.class}, customers);

                // Change page back to ListMemberPage
                ListMemberPage listMemberPage = new ListMemberPage(stage, tab, customers, customerDS, settings);
                tab.setContent(listMemberPage);
            }
            // If input is not completed
            else {
                // Show alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Fail to Add Member");
                alert.setContentText("All fields must not be empty!");
                alert.showAndWait();
            }
        });
    }
}
