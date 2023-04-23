package UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;

import java.util.concurrent.atomic.AtomicReference;

public class SettingsPage extends HBox {

    public SettingsPage(Stage stage) {
        // Creating VBox for Sidebar
        VBox sidebar = new VBox();

        // Styling Sidebar layout
        sidebar.setPrefWidth(500);
        sidebar.setSpacing(20);
        sidebar.setStyle("-fx-background-color: #FFFFFF");

        // Creating V Box for contents
        //VBox contents = new VBox();

        // Create title
        Label title = new Label("Settings");
        title.setFont(Font.font("Montserrat", FontWeight.BOLD, 36));
        title.setPadding(new Insets(30, 50, 10, 50));
        sidebar.getChildren().add(title);
        // Creating Setting Options
        // VBox for Options and Details
        VBox options = new VBox();
        VBox details = new VBox();
        // Setting Options layout
        options.setSpacing(20);
        options.setPrefWidth(500);
        options.setStyle("-fx-background-color: #FFFFFF");
        // Option 1
        // Option 1 Icon Image
        ImageView iconDirectorySave = new ImageView(new Image("/images/icon/directorySaveIcon.png"));

        // Option 1 Button
        Button opt1 = new Button("Directory Save");

        // Button style (Option 1)
        opt1.setPadding(new Insets(10, 0, 10, 65));
        opt1.setFont(Font.font("Montserrat", FontWeight.SEMI_BOLD, 20));
        opt1.setStyle("-fx-border-color: transparent; -fx-background-color: #FFFFFF");
        opt1.setOnMouseEntered(event -> opt1.setStyle("-fx-background-color: #C8DFE8"));
        opt1.setOnMouseExited(event -> opt1.setStyle("-fx-border-color: transparent; -fx-background-color: #FFFFFF"));
        opt1.setGraphic(iconDirectorySave);
        opt1.setPrefWidth(500);
        opt1.setGraphicTextGap(20);
        opt1.setContentDisplay(ContentDisplay.LEFT);
        opt1.setAlignment(Pos.CENTER_LEFT);

        // Button on pressed functionalities (Option 1)
        opt1.setOnAction(event ->{
            getChildren().remove(details);
            details.setPadding(new Insets(30, 50, 10, 50));
            details.setSpacing(35);
            details.getChildren().clear();
            // Title
            Label detailTitle = new Label("Directory Save");
            detailTitle.setFont(Font.font("Montserrat", FontWeight.BOLD, 36));

            // Folder Directory
            Label folderTitle = new Label("Folder Directory");
            folderTitle.setFont(Font.font("Montserrat", FontWeight.SEMI_BOLD, 30));

            // Folder hbox for pick folder contents
            HBox folderDirectory = new HBox();
            folderDirectory.setSpacing(30);
            ImageView folderIcon = new ImageView(new Image("/images/icon/folderIcon.png"));
            Button pickFolder = new Button("Pick a Folder");
            pickFolder.setFont(Font.font("Montserrat", FontWeight.SEMI_BOLD, 20));
            pickFolder.setStyle("-fx-background-color: #C8DFE8");
            folderDirectory.getChildren().addAll(folderIcon, pickFolder);

            details.getChildren().addAll(detailTitle, folderTitle, folderDirectory);
            getChildren().add(details);



        });

        // Adding Option 1 to sidebar
        options.getChildren().add(opt1);

        // Option 2
        // Option 2 Icon Image
        ImageView iconPlugin = new ImageView(new Image("/images/icon/pluginIcon.png"));

        // Option 2 Button
        Button opt2 = new Button("Plugins");

        // Button style (Option 2)
        opt2.setPadding(new Insets(10, 0, 10, 65));
        opt2.setFont(Font.font("Montserrat", FontWeight.SEMI_BOLD, 20));
        opt2.setStyle("-fx-border-color: transparent; -fx-background-color: #FFFFFF");
        opt2.setOnMouseEntered(event -> opt2.setStyle("-fx-background-color: #C8DFE8"));
        opt2.setOnMouseExited(event -> opt2.setStyle("-fx-border-color: transparent; -fx-background-color: #FFFFFF"));
        opt2.setGraphic(iconPlugin);
        opt2.setPrefWidth(500);
        opt2.setGraphicTextGap(20);
        opt2.setContentDisplay(ContentDisplay.LEFT);
        opt2.setAlignment(Pos.CENTER_LEFT);

        // Button on pressed functionalities (Option 2)
        opt2.setOnAction(event ->{
            getChildren().remove(details);
            details.setPadding(new Insets(30, 50, 10, 50));
            details.getChildren().clear();
            Label detailTitle2 = new Label("Plugin");
            detailTitle2.setFont(Font.font("Montserrat", FontWeight.BOLD, 36));
            details.getChildren().add(detailTitle2);
            getChildren().add(details);
        });
        // Adding Option 2 to sidebar
        options.getChildren().add(opt2);


        ScrollPane scrollPane = new ScrollPane(options);
        // Styling Scroll Pane
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPrefHeight(550);
        scrollPane.setPrefWidth(500);
        scrollPane.setMaxHeight(600);
        scrollPane.setStyle("-fx-background-color: #FFFFFF");
        scrollPane.getContent().setStyle(("-fx-background-color: #FFFFFF"));
        // Adding options to sidebar
        sidebar.getChildren().add(scrollPane);
        // Adding components to main tab
        getChildren().add(sidebar);
        // Styling main layout
        setStyle("-fx-background-color: #F3F9FB;");


    }

}
