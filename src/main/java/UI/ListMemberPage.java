package UI;

import System.Member;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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


public class ListMemberPage extends VBox {
    public ListMemberPage(Stage stage) {
        // Create HBox for header
        HBox hBox = new HBox();

        // Create HBox for add Member Button
        HBox addMemberButton = new HBox();

        // Create title
        Label title = new Label("Members");
        title.setFont(Font.font("Montserrat", FontWeight.BOLD, 28));

        // Create add button
        Button addButton = new Button("+ Add Member");
        addButton.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        addButton.setStyle("-fx-background-color: #3B919B; -fx-text-fill: white;");

        // Set add button to HBox
        addMemberButton.getChildren().add(addButton);
        HBox.setHgrow(addMemberButton, Priority.ALWAYS);
        addMemberButton.setAlignment(Pos.CENTER_RIGHT);

        // Add header to HBox
        hBox.getChildren().addAll(title, addMemberButton);

        // Create dummy List of Members
        Inventory<Member> members = new Inventory<Member>();
        for (int i = 0; i < 20; i++) {
            members.addElement(new Member("Marcel", "500537"));
        }

        // Set vbox for list of Members
        VBox vbox = new VBox(15); // spacing = 8
        vbox.setAlignment(Pos.BASELINE_CENTER);
        vbox.prefWidthProperty().bind(hBox.widthProperty());
        vbox.setPadding(new Insets(0, 30,0,0));

        int i = 1;
        // Display list of Members
        for (Member member : members.getList()) {
            // Create HBox for every Member
            HBox memberBox = new HBox();

            // Create HBox for displaying member id and name
            HBox memberDetails = new HBox();

            // Create HBox for buttons
            HBox memberButtons = new HBox();

            // Set Label for every member
            Label details = new Label(i + "   " + member.getName());
            details.setFont(Font.font("Montserrat", 20));
            details.setPadding(new Insets(10));
            i++;

            // Set icon for history button
            ImageView historyIcon = new ImageView("/images/icon/historyButton.png");

            // Styling history button icon
            historyIcon.setPreserveRatio(true);
            historyIcon.setSmooth(true);
            historyIcon.setCache(true);
            historyIcon.setFitWidth(27);
            historyIcon.setFitHeight(27);

            // Making a button for history
            Button historyButton = new Button();
            historyButton.setPrefSize(27,27);
            historyButton.setStyle("-fx-background-color: #C8DFE8;");
            historyButton.setGraphic(historyIcon);

            // Set vip Logo for every VIP member
            ImageView vipLogo = new ImageView("/images/icon/vipLogo.png");

            // Styling vip Logo
            vipLogo.setPreserveRatio(true);
            vipLogo.setSmooth(true);
            vipLogo.setCache(true);
            vipLogo.setFitWidth(39);
            vipLogo.setFitHeight(26);

            // Set label to memberDetails
            memberDetails.getChildren().add(details);
            memberDetails.getChildren().add(vipLogo);

            // memberDetails styling
            memberDetails.setSpacing(3);
            HBox.setHgrow(memberDetails, Priority.ALWAYS);
            memberDetails.setAlignment(Pos.CENTER_LEFT);

            // memberButtons styling
            memberButtons.setAlignment(Pos.CENTER_RIGHT);

            // Set buttons to memberButtons
            memberButtons.getChildren().add(historyButton);

            // memberBox styling
            memberBox.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px");
            memberBox.minWidth(1194);
            memberBox.minHeight(50);

            // Set memberDetails and memberButtons to memberBox
            memberBox.getChildren().add(memberDetails);
            memberBox.getChildren().add(memberButtons);

            // Set memberBox to vbox
            vbox.getChildren().addAll(memberBox);
        }

        // Create Scroll Pane for VBox
        ScrollPane scrollPane = new ScrollPane(vbox);

        // Styling Scroll Pane
        scrollPane.setMaxHeight(640);
        scrollPane.setStyle("-fx-background-color: #F3F9FB;");
        scrollPane.setPadding(new Insets(0, 0, 0, 10));


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
