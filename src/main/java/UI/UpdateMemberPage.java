package UI;

import DataStore.DataStore;
import System.PurchasedItem;
import System.Customer;
import System.RegisteredCustomer;
import System.Inventory;
import System.VIP;
import System.Member;
import System.FixedBill;
import System.Settings;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;

public class UpdateMemberPage extends VBox {

    public UpdateMemberPage(Stage stage, Tab tab, Customer customer, Inventory<Customer> customers, DataStore<Customer> customerDS, Settings settings) {
        // Create HBox for header
        HBox hBox = new HBox();

        // Create HBox for save Button
        HBox rightButton = new HBox();

        // Create title
        Label title = new Label("Update Member");
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
        HBox.setHgrow(rightButton, Priority.ALWAYS);
        rightButton.setAlignment(Pos.CENTER_RIGHT);
        rightButton.setSpacing(3);

        // Add header to HBox
        hBox.getChildren().addAll(title, rightButton);

        // Create VBox for Name Field
        VBox nameField = new VBox();

        // Create label for name title
        Label nameTitle = new Label("Name");
        nameTitle.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));

        // Create textField for name
        TextField inputName = new TextField();
        inputName.setPadding(new Insets(0, 0, 0, 10));
        inputName.setFont(Font.font("Montserrat", 20));
        inputName.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px; -fx-text-fill: #8CAEBB");
        inputName.setMinWidth(1194);
        inputName.setMinHeight(50);
        inputName.setText(((RegisteredCustomer) customer).getName());

        inputName.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            inputName.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px; -fx-text-fill: black");
        });

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
        inputNumber.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px; -fx-text-fill: #8CAEBB");
        inputNumber.setMinWidth(1194);
        inputNumber.setMinHeight(50);
        inputNumber.setText(((RegisteredCustomer) customer).getPhoneNumber());

        inputNumber.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            inputNumber.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px; -fx-text-fill: black");
        });

        // set phoneField
        phoneField.getChildren().add(phoneTitle);
        phoneField.getChildren().add(inputNumber);

        // Create VBox for Membership Status and Deactivate Membership
        VBox updateStatus = new VBox();

        // Create HBox for MembershipStatus
        HBox membershipStatus = new HBox();

        // Create HBox for Label
        HBox membershipLabel = new HBox();

        // Create HBox for ToggleButton
        HBox membershipToggle = new HBox();

        // Create Image View for Membership Status toggle button
        ImageView statusMember = new ImageView("/images/icon/memberStatus.png");
        ImageView statusVIP = new ImageView("/images/icon/vipStatus.png");

        // Styling Image View for Membership Status toggle button
        statusMember.setPreserveRatio(true);
        statusMember.setSmooth(true);
        statusMember.setCache(true);
        statusMember.setFitWidth(113);
        statusMember.setFitHeight(31);

        statusVIP.setPreserveRatio(true);
        statusVIP.setSmooth(true);
        statusVIP.setCache(true);
        statusVIP.setFitWidth(113);
        statusVIP.setFitHeight(31);

        // Create label for Membership Status title
        Label membershipTitle = new Label("Membership Status");
        membershipTitle.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));

        // Set HBox for Membership Label
        membershipLabel.setAlignment(Pos.CENTER_LEFT);
        membershipLabel.getChildren().add(membershipTitle);

        // Create Toggle Button for Membership Status
        ToggleButton memberToggle = new ToggleButton();
        memberToggle.setMinWidth(113);
        memberToggle.setMinHeight(31);
        memberToggle.setStyle("-fx-background-color: #F3F9FB;");
        memberToggle.setCursor(Cursor.HAND);

        // Event for memberToggle
        if (customer.getClass().getSimpleName().equals("Member")) {
            memberToggle.setGraphic(statusMember);
            memberToggle.setOnAction(event -> {
                if (memberToggle.isSelected()) {
                    memberToggle.setGraphic(statusVIP);
                }
                else {
                    memberToggle.setGraphic(statusMember);
                }
            });
        }
        else {
            memberToggle.setGraphic(statusVIP);
            memberToggle.setOnAction(event -> {
                if (memberToggle.isSelected()) {
                    memberToggle.setGraphic(statusMember);
                } else {
                    memberToggle.setGraphic(statusVIP);
                }
            });
        }


        // Set HBox for Membership status toggle button
        membershipToggle.setMinHeight(50);
        membershipToggle.setAlignment(Pos.CENTER_RIGHT);
        membershipToggle.getChildren().add(memberToggle);
        HBox.setHgrow(membershipToggle, Priority.ALWAYS);


        // Set HBox for Membership Status
        membershipStatus.getChildren().add(membershipLabel);
        membershipStatus.getChildren().add(membershipToggle);

        // Create HBox for making a line
        HBox line = new HBox();
        line.setPrefSize(1194, 2);
        line.setStyle("-fx-background-color: #8CAEBB");

        // Create HBox for Deactivating Membership
        HBox deactivateStatus = new HBox();

        // Create HBox for Label
        HBox deactivateLabel = new HBox();

        // Create HBox for ToggleButton
        HBox deactivateToggle = new HBox();

        // Create Image View for Deactivate Membership toggle button
        ImageView deactivateMember = new ImageView("/images/icon/memberDeactivate.png");
        ImageView activateMember = new ImageView("/images/icon/memberActivate.png");

        // Styling Image View for Deactivate Membership toggle button
        deactivateMember.setPreserveRatio(true);
        deactivateMember.setSmooth(true);
        deactivateMember.setCache(true);
        deactivateMember.setFitWidth(113);
        deactivateMember.setFitHeight(31);

        activateMember.setPreserveRatio(true);
        activateMember.setSmooth(true);
        activateMember.setCache(true);
        activateMember.setFitWidth(113);
        activateMember.setFitHeight(31);

        // Create label for Deactivate Membership title
        Label deactivateTitle = new Label("Deactivate Membership");
        deactivateTitle.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));

        // Set HBox for Deactivate Membership Label
        deactivateLabel.setAlignment(Pos.CENTER_LEFT);
        deactivateLabel.getChildren().add(deactivateTitle);

        // Create Toggle Button for Deactivate Membership
        ToggleButton deactToggle = new ToggleButton();
        deactToggle.setMinWidth(113);
        deactToggle.setMinHeight(31);
        deactToggle.setStyle("-fx-background-color: #F3F9FB;");
        deactToggle.setCursor(Cursor.HAND);

        if (((RegisteredCustomer) customer).getActiveStatus()) {
            deactToggle.setGraphic(activateMember);
            deactToggle.setOnAction(event -> {
                if (deactToggle.isSelected()) {
                    deactToggle.setGraphic(deactivateMember);
                }
                else {
                    deactToggle.setGraphic(activateMember);
                }
            });
        }
        else {
            deactToggle.setGraphic(deactivateMember);
            deactToggle.setOnAction(event -> {
                if (deactToggle.isSelected()) {
                    deactToggle.setGraphic(activateMember);
                }
                else {
                    deactToggle.setGraphic(deactivateMember);
                }
            });
        }

        // Set HBox for Deactivate Membership toggle button
        deactivateToggle.setMinHeight(50);
        deactivateToggle.setAlignment(Pos.CENTER_RIGHT);
        deactivateToggle.getChildren().add(deactToggle);
        HBox.setHgrow(deactivateToggle, Priority.ALWAYS);


        // Set HBox for Deactivate Membership
        deactivateStatus.getChildren().add(deactivateLabel);
        deactivateStatus.getChildren().add(deactivateToggle);

        // Set VBox for Update Status
        updateStatus.getChildren().add(membershipStatus);
        updateStatus.getChildren().add(line);
        updateStatus.getChildren().add(deactivateStatus);
        updateStatus.setSpacing(10);

        // Add contents
        getChildren().add(hBox);
        getChildren().add(nameField);
        getChildren().add(phoneField);
        getChildren().add(updateStatus);

        // Styling layout
        setPadding(new Insets(30, 50, 0, 50));
        setSpacing(20);
        setStyle("-fx-background-color: #F3F9FB;");

        // Set event handler for save button
        saveButton.setOnAction(event -> {
            if (customer.getClass().getSimpleName().equals("Member")) {
                if (memberToggle.isSelected()) {
                    int id = customer.getId();
                    String name = inputName.getText();
                    String phoneNumber = inputNumber.getText();
                    int point = ((RegisteredCustomer) customer).getPoint();
                    boolean activeStat = ((RegisteredCustomer) customer).getActiveStatus();
                    Inventory<FixedBill> bills = customer.getTransaction();

                    if (customers.getElement(id-1).getId() == id) {
                        customers.setElement(id-1, new VIP(id, name, phoneNumber, point, activeStat, bills));
                    }
                }
                else {
                    ((RegisteredCustomer) customer).setName(inputName.getText());
                    ((RegisteredCustomer) customer).setPhoneNumber(inputNumber.getText());
                    if (customers.getElement(customer.getId()-1).getId() == customer.getId()) {
                        customers.setElement(customer.getId()-1, customer);
                    }
                }
            }
            else {
                if (memberToggle.isSelected()) {
                    int id = customer.getId();
                    String name = inputName.getText();
                    String phoneNumber = inputNumber.getText();
                    int point = ((RegisteredCustomer) customer).getPoint();
                    boolean activeStat = ((RegisteredCustomer) customer).getActiveStatus();
                    Inventory<FixedBill> bills = customer.getTransaction();

                    if (customers.getElement(id-1).getId() == id) {
                        customers.setElement(id-1, new Member(id, name, phoneNumber, point, activeStat, bills));
                    }
                }
                else {
                    ((RegisteredCustomer) customer).setName(inputName.getText());
                    ((RegisteredCustomer) customer).setPhoneNumber(inputNumber.getText());
                    if (customers.getElement(customer.getId()-1).getId() == customer.getId()) {
                        customers.setElement(customer.getId()-1, customer);
                    }
                }
            }

            if (((RegisteredCustomer) customer).getActiveStatus()) {
                if (deactToggle.isSelected()) {
                    ((RegisteredCustomer) customers.getElement(customer.getId()-1)).setActiveStatus(false);
                }
                else {
                    ((RegisteredCustomer) customers.getElement(customer.getId()-1)).setActiveStatus(true);
                }
            }
            else {
                if (deactToggle.isSelected()) {
                    ((RegisteredCustomer) customers.getElement(customer.getId()-1)).setActiveStatus(true);
                }
                else {
                    ((RegisteredCustomer) customers.getElement(customer.getId()-1)).setActiveStatus(false);
                }
            }

            // Save data
            customerDS.saveData("customer", settings, new Class<?>[]{Inventory.class, Customer.class, RegisteredCustomer.class, Member.class, VIP.class, FixedBill.class, PurchasedItem.class}, customers);

            // Change page back to ListMemberPage
            ListMemberPage listMemberPage = new ListMemberPage(stage, tab, customers, customerDS, settings);
            tab.setContent(listMemberPage);
        });
    }
}
