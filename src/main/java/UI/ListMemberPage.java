package UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import System.Inventory;
import System.RegisteredCustomer;
import System.Customer;
import javafx.stage.Stage;
import System.Settings;
import DataStore.DataStore;

public class ListMemberPage extends VBox {
    public ListMemberPage(Stage stage, Tab tab, Inventory<Customer> customers, DataStore<Customer> customerDS, Settings settings) {
        // Create HBox for header
        HBox hBox = new HBox();

        // Create HBox for add Member Button
        HBox addMemberButton = new HBox();

        // Create title
        Label title = new Label("Customers");
        title.setFont(Font.font("Montserrat", FontWeight.BOLD, 28));

        // Create add button
        Button addButton = new Button("+ Add Member");
        addButton.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        addButton.setStyle("-fx-background-color: #3B919B; -fx-text-fill: white;");
        addButton.setCursor(Cursor.HAND);
        addButton.setOnAction(event -> {
            AddMemberPage addMemberContent = new AddMemberPage(stage, tab, customers, customerDS, settings);
            tab.setContent(addMemberContent);
        });

        // Set add button to HBox
        addMemberButton.getChildren().add(addButton);
        HBox.setHgrow(addMemberButton, Priority.ALWAYS);
        addMemberButton.setAlignment(Pos.CENTER_RIGHT);

        // Add header to HBox
        hBox.getChildren().addAll(title, addMemberButton);

        // Set vbox for list of Members
        VBox vbox = new VBox(15); // spacing = 8
        vbox.setAlignment(Pos.BASELINE_CENTER);
        vbox.prefWidthProperty().bind(hBox.widthProperty());
        vbox.setPadding(new Insets(0, 30,30,0));
        vbox.setStyle("-fx-background-color: #F3F9FB");

        // Display list of customers
        int i = 1;
        for (Customer customer : customers.getList()) {
            // Create HBox for every customer
            HBox customerBox = new HBox();

            // Create HBox for displaying customer id and name
            HBox customerDetails = new HBox();

            // Create HBox for buttons
            HBox customerButtons = new HBox();

            // Set Label for every customer
            Label details = new Label();
            if (customer.getClass().getSimpleName().equals("Customer")) {
                details.setText(i + "   UnknownGuest" + customer.getId());
            }
            else {
                details.setText(i + "   " + ((RegisteredCustomer) customer).getName());
                if (!((RegisteredCustomer) customer).getActiveStatus()) {
                    details.setStyle("-fx-text-fill: #93A2A5");
                }
            }

            i++;
            details.setFont(Font.font("Montserrat", 20));
            details.setPadding(new Insets(10));

            // Set icon for history button
            ImageView historyIcon = new ImageView("/images/icon/historyButton.png");

            // Set icon for update button
            ImageView updateIcon = new ImageView("/images/icon/updateButton.png");

            // Set icon for preview button
            ImageView previewIcon = new ImageView("/images/icon/previewButton.png");

            // Styling update button icon
            updateIcon.setPreserveRatio(true);
            updateIcon.setSmooth(true);
            updateIcon.setCache(true);
            updateIcon.setFitWidth(20);
            updateIcon.setFitHeight(20);

            // Styling history button icon
            historyIcon.setPreserveRatio(true);
            historyIcon.setSmooth(true);
            historyIcon.setCache(true);
            historyIcon.setFitWidth(27);
            historyIcon.setFitHeight(27);

            // Styling preview button icon
            previewIcon.setPreserveRatio(true);
            previewIcon.setSmooth(true);
            previewIcon.setCache(true);
            previewIcon.setFitWidth(27);
            previewIcon.setFitHeight(27);

            // Making a button for history
            Button historyButton = new Button();
            historyButton.setPrefSize(27,27);
            historyButton.setStyle("-fx-background-color: #C8DFE8;");
            historyButton.setGraphic(historyIcon);
            historyButton.setCursor(Cursor.HAND);

            // Add event handler for historyButton
            historyButton.setOnAction(event-> {
                HistoryPage historyPage = new HistoryPage(stage, tab, customer, customers, customerDS, settings);
                tab.setContent(historyPage);
            });

            // Making a button for update
            Button updateButton = new Button();
            updateButton.setPrefSize(27,27);
            updateButton.setStyle("-fx-background-color: #C8DFE8;");
            updateButton.setGraphic(updateIcon);
            updateButton.setCursor(Cursor.HAND);

            // Add event handler for updateButton
            updateButton.setOnAction(event -> {
                UpdateMemberPage updateMemberPage = new UpdateMemberPage(stage, tab, customer, customers, customerDS, settings);
                tab.setContent(updateMemberPage);
            });

            // Making a button for preview
            Button previewButton = new Button();
            previewButton.setPrefSize(27,27);
            previewButton.setStyle("-fx-background-color: #C8DFE8;");
            previewButton.setGraphic(previewIcon);
            previewButton.setCursor(Cursor.HAND);

            // Add event handler for previewButton
            previewButton.setOnAction(event -> {
                DetailMemberPage detailMemberPage = new DetailMemberPage(stage, tab, customer, customers, customerDS, settings);
                tab.setContent(detailMemberPage);
            });

            // Set vip Logo for every VIP member
            ImageView vipLogo = new ImageView("/images/icon/vipLogo.png");

            // Set member logo for every member
            ImageView memberLogo = new ImageView("/images/icon/memberLogo.png");

            // Styling vip Logo
            vipLogo.setPreserveRatio(true);
            vipLogo.setSmooth(true);
            vipLogo.setCache(true);
            vipLogo.setFitWidth(39);
            vipLogo.setFitHeight(26);

            // Styling member Logo
            memberLogo.setPreserveRatio(true);
            memberLogo.setSmooth(true);
            memberLogo.setCache(true);
            memberLogo.setFitWidth(73);
            memberLogo.setFitHeight(24);

            // Set label to memberDetails
            customerDetails.getChildren().add(details);
            if (customer.getClass().getSimpleName().equals("Member")) {
                customerDetails.getChildren().add(memberLogo);
            }
            else if (customer.getClass().getSimpleName().equals("VIP")) {
                customerDetails.getChildren().add(memberLogo);
                customerDetails.getChildren().add(vipLogo);
            }

            // customerDetails styling
            customerDetails.setSpacing(3);
            HBox.setHgrow(customerDetails, Priority.ALWAYS);
            customerDetails.setAlignment(Pos.CENTER_LEFT);

            // historyButton styling
            if (customer.getClass().getSimpleName().equals("Member") || customer.getClass().getSimpleName().equals("VIP")) {
                if (((RegisteredCustomer) customer).getActiveStatus()) {
                    historyButton.setStyle("-fx-background-color: #98CBDE");
                }
                else {
                    historyButton.setStyle("-fx-background-color: #D9D9D9");
                }
            }

            // customerButtons styling
            customerButtons.setAlignment(Pos.CENTER_RIGHT);

            // Set buttons to customerButtons
            customerButtons.getChildren().add(historyButton);
            if (customer.getClass().getSimpleName().equals("VIP") || customer.getClass().getSimpleName().equals("Member")) {
                if (((RegisteredCustomer) customer).getActiveStatus()) {
                    updateButton.setStyle("-fx-background-color: #98CBDE");
                    previewButton.setStyle("-fx-background-color: #98CBDE");
                }
                else {
                    updateButton.setStyle("-fx-background-color: #D9D9D9");
                    previewButton.setStyle("-fx-background-color: #D9D9D9");
                }
                customerButtons.getChildren().add(previewButton);
                customerButtons.getChildren().add(updateButton);
            }

            // memberBox styling
            if (customer.getClass().getSimpleName().equals("VIP") || customer.getClass().getSimpleName().equals("Member")) {
                if (((RegisteredCustomer) customer).getActiveStatus()) {
                    customerBox.setStyle("-fx-background-color: #98CBDE; -fx-background-radius: 10px");
                }
                else {
                    customerBox.setStyle("-fx-background-color: #D9D9D9; -fx-background-radius: 10px");
                }
            }
            else {
                customerBox.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px");
            }
            customerBox.minWidth(1194);
            customerBox.minHeight(50);

            // Set memberDetails and memberButtons to memberBox
            customerBox.getChildren().add(customerDetails);
            customerBox.getChildren().add(customerButtons);

            // Set memberBox to vbox
            vbox.getChildren().addAll(customerBox);
        }

        // Create Scroll Pane for VBox
        ScrollPane scrollPane = new ScrollPane(vbox);

        // Styling Scroll Pane
        scrollPane.setMaxHeight(640);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: #F3F9FB;");
        scrollPane.setPadding(new Insets(0, 0, 20, 10));


        // Add contents
        getChildren().add(hBox);
        getChildren().add(vbox);
        getChildren().add(scrollPane);

        // Styling Layout
        setPadding(new Insets(20, 40, 0, 40));
        setSpacing(8);
        setStyle("-fx-background-color: #F3F9FB;");
    }
}
