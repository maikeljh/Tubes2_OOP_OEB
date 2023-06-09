package UI;
import DataStore.DataStore;
import Core.*;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class DetailMemberPage extends Page {
    private Label nameLabel;
    private Label numberLabel;
    private Label pointsLabel;
    private HBox membershipIcon;
    private ImageView deactivateLogo;
    private ImageView activateLogo;
    private ImageView regularLogo;
    private ImageView vipDetailLogo;

    public DetailMemberPage(Stage stage, Tab tab, Customer customer, Inventory<Customer> customers, DataStore<Customer> customerDS, Settings settings, DataStore<Settings> settingsDS) {
        super(stage, tab, settings, settingsDS);

        // Create HBox for header
        HBox hBox = new HBox();

        // Create HBox for save Button
        HBox rightButton = new HBox();

        // Create title
        Label title = new Label("Detail Member " + ((RegisteredCustomer) customer).getName());
        title.setFont(Font.font("Montserrat", FontWeight.BOLD, 28));


        // Create back button
        Button backButton = new Button("Back");
        backButton.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        backButton.setStyle("-fx-background-color: #3B919B; -fx-text-fill: white; -fx-background-radius: 30px;");
        backButton.setPrefSize(77, 27);
        backButton.setCursor(Cursor.HAND);

        // Add event handler for cancel button
        backButton.setOnAction(event -> {
            ListMemberPage listMemberPage = new ListMemberPage(stage, tab, customers, customerDS, settings, settingsDS);
            tab.setContent(listMemberPage);
        });

        // Set save button to HBox
        rightButton.getChildren().add(backButton);
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

        // Create label for name
        nameLabel = new Label();
        nameLabel.setPadding(new Insets(0, 0, 0, 10));
        nameLabel.setFont(Font.font("Montserrat", 20));
        nameLabel.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px; -fx-text-fill: black");
        nameLabel.setMinWidth(1194);
        nameLabel.setMinHeight(50);
        nameLabel.setText(((RegisteredCustomer) customer).getName());


        // set nameField
        nameField.getChildren().add(nameTitle);
        nameField.getChildren().add(nameLabel);

        // Create VBox for Phone Number Field
        VBox phoneField = new VBox();

        // Create label for Phone Number title
        Label phoneTitle = new Label("Phone Number");
        phoneTitle.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));

        // Create label for phone number
        numberLabel = new Label();
        numberLabel.setPadding(new Insets(0, 0, 0, 10));
        numberLabel.setFont(Font.font("Montserrat", 20));
        numberLabel.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px; -fx-text-fill: black");
        numberLabel.setMinWidth(1194);
        numberLabel.setMinHeight(50);
        numberLabel.setText(((RegisteredCustomer) customer).getPhoneNumber());

        // set phoneField
        phoneField.getChildren().add(phoneTitle);
        phoneField.getChildren().add(numberLabel);

        // Create vbox for Points field
        VBox pointsField = new VBox();

        // Create label for Phone Number title
        Label pointsTitle = new Label("Points");
        pointsTitle.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));

        // Create label for phone number
        pointsLabel = new Label();
        pointsLabel.setPadding(new Insets(0, 0, 0, 10));
        pointsLabel.setFont(Font.font("Montserrat", 20));
        pointsLabel.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px; -fx-text-fill: black");
        pointsLabel.setMinWidth(1194);
        pointsLabel.setMinHeight(50);
        pointsLabel.setText(Integer.toString(((RegisteredCustomer) customer).getPoint()));

        // Set points field
        pointsField.getChildren().add(pointsTitle);
        pointsField.getChildren().add(pointsLabel);

        // Create vbox for membership
        VBox membership = new VBox();

        // Create HBox for membership details
        HBox membershipDetails = new HBox();

        // Create HBox for membership details icon
        membershipIcon = new HBox();

        // Create Membership Status title
        Label membershipTitle = new Label("Membership Status");
        membershipTitle.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));

        // Setup image view for membership details
        deactivateLogo = new ImageView("/images/icon/deactivateLogo.png");
        activateLogo = new ImageView("/images/icon/activeLogo.png");
        regularLogo = new ImageView("/images/icon/regularLogo.png");
        vipDetailLogo = new ImageView("/images/icon/vipDetailLogo.png");

        // Styling Image View for deactivateLogo
        deactivateLogo.setPreserveRatio(true);
        deactivateLogo.setSmooth(true);
        deactivateLogo.setCache(true);
        deactivateLogo.setFitWidth(87);
        deactivateLogo.setFitHeight(31);

        // Styling Image View for activateLogo
        activateLogo.setPreserveRatio(true);
        activateLogo.setSmooth(true);
        activateLogo.setCache(true);
        activateLogo.setFitWidth(87);
        activateLogo.setFitHeight(31);

        // Styling Image View for regularLogo
        regularLogo.setPreserveRatio(true);
        regularLogo.setSmooth(true);
        regularLogo.setCache(true);
        regularLogo.setFitWidth(87);
        regularLogo.setFitHeight(31);

        // Styling Image View for vipDetailLogo
        vipDetailLogo.setPreserveRatio(true);
        vipDetailLogo.setSmooth(true);
        vipDetailLogo.setCache(true);
        vipDetailLogo.setFitWidth(87);
        vipDetailLogo.setFitHeight(31);

        if (customer.getClass().getSimpleName().equals("Member")) {
            membershipIcon.getChildren().add(regularLogo);
        }
        else {
            membershipIcon.getChildren().add(vipDetailLogo);
        }

        if (((RegisteredCustomer) customer).isActiveStatus()) {
            membershipIcon.getChildren().add(activateLogo);
        }
        else {
            membershipIcon.getChildren().add(deactivateLogo);
        }

        // Style membershipIcon box
        membershipIcon.setSpacing(6);
        membershipIcon.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(membershipIcon, Priority.ALWAYS);

        // Style membership details
        membershipDetails.setPrefSize(1194, 50);
        membershipDetails.setStyle("-fx-background-color: #F3F9FB");

        // Add contents to membershipDetails
        membershipDetails.getChildren().add(membershipTitle);
        membershipDetails.getChildren().add(membershipIcon);

        // Add membershipDetails to VBox
        membership.getChildren().add(membershipDetails);

        // Add contents
        getChildren().add(hBox);
        getChildren().add(nameField);
        getChildren().add(phoneField);
        getChildren().add(pointsField);
        getChildren().add(membership);

        // Styling layout
        setPadding(new Insets(30, 50, 0, 50));
        setSpacing(20);
        setStyle("-fx-background-color: #F3F9FB;");

        Thread thread = new Thread(() -> {
            while (!close && !Page.isExit()) {
                Platform.runLater(() -> {
                    nameLabel.setText(((RegisteredCustomer) customer).getName());
                    numberLabel.setText(((RegisteredCustomer) customer).getPhoneNumber());
                    pointsLabel.setText(Integer.toString(((RegisteredCustomer) customer).getPoint()));
                });

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    // Do nothing
                }
            }
        });

        thread.start();
    }
}
