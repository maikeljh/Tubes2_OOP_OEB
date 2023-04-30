package UI;

import DataStore.DataStore;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import System.Inventory;
import System.Settings;

public class SettingsPage extends HBox {

    public SettingsPage(Stage stage, Settings settings, DataStore<Settings> settingsDS) {
        // Creating VBox for Sidebar
        VBox sidebar = new VBox();

        // Styling Sidebar layout
        sidebar.setPrefWidth(500);
        sidebar.setSpacing(20);
        sidebar.setStyle("-fx-background-color: #FFFFFF");

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

            // VBox
            VBox saveBox = new VBox();
            saveBox.setSpacing(40);

            // Title
            Label detailTitle = new Label("Directory Save");
            detailTitle.setFont(Font.font("Montserrat", FontWeight.BOLD, 36));

            // Folder Directory
            VBox folderBox = new VBox();
            folderBox.setSpacing(20);

            Label folderTitle = new Label("Folder Directory");
            folderTitle.setFont(Font.font("Montserrat", FontWeight.SEMI_BOLD, 30));

            // Folder hbox for pick folder contents
            HBox folderDirectory = new HBox();
            folderDirectory.setSpacing(30);

            // Folder Icon
            ImageView folderIcon = new ImageView(new Image("/images/icon/folderIcon.png"));

            // Label showing current path
            Label currentPath = new Label(settings.getSaveDirectory());
            Tooltip tooltip = new Tooltip(settings.getSaveDirectory());
            Tooltip.install(currentPath, tooltip);
            currentPath.setTooltip(tooltip);

            // Button to change path
            Button pickFolder = new Button("Change path");
            pickFolder.setOnAction(e -> {
                DirectoryChooser directoryChooser = new DirectoryChooser();
                File selectedDirectory = directoryChooser.showDialog(stage);
                if (selectedDirectory != null) {
                    currentPath.setText(selectedDirectory.getAbsolutePath());
                    settings.setSaveDirectory(selectedDirectory.getAbsolutePath());
                    Settings temp = new Settings();
                    Inventory<Settings> tempSettings = new Inventory<Settings>();
                    tempSettings.addElement(settings);
                    settingsDS.saveData("settings", temp, new Class<?>[]{Inventory.class, Settings.class}, tempSettings);
                }
            });

            // Styling
            folderDirectory.setAlignment(Pos.CENTER);
            pickFolder.setFont(Font.font("Montserrat", FontWeight.SEMI_BOLD, 14));
            pickFolder.setStyle("-fx-background-color: #C8DFE8");

            // Format
            VBox formatBox = new VBox();
            formatBox.setSpacing(20);

            Label formatTitle = new Label("Format");
            formatTitle.setFont(Font.font("Montserrat", FontWeight.SEMI_BOLD, 30));

            // Folder hbox for pick format
            HBox formatDirectory = new HBox();
            formatDirectory.setSpacing(30);

            // Format Icon
            ImageView formatIcon = new ImageView(new Image("/images/icon/folderIcon.png"));

            // Create a combo box with some sample options
            ComboBox<String> formatOptions = new ComboBox<>();
            formatOptions.getItems().addAll("xml", "json", "obj");
            formatOptions.setValue(settings.getFormat());
            formatOptions.valueProperty().addListener((observable, oldValue, newValue) -> {
                settings.setFormat(newValue);
                Settings temp = new Settings();
                Inventory<Settings> tempSettings = new Inventory<Settings>();
                tempSettings.addElement(settings);
                settingsDS.saveData("settings", temp, new Class<?>[]{Inventory.class, Settings.class}, tempSettings);
            });

            // Styling
            formatDirectory.setAlignment(Pos.CENTER_LEFT);

            // Add elements to content
            folderDirectory.getChildren().addAll(folderIcon, currentPath, pickFolder);
            formatDirectory.getChildren().addAll(formatIcon, formatOptions);

            folderBox.getChildren().addAll(folderTitle, folderDirectory);
            formatBox.getChildren().addAll(formatTitle, formatDirectory);

            saveBox.getChildren().addAll(folderBox, formatBox);
            details.getChildren().addAll(detailTitle, saveBox);

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
        scrollPane.setStyle("-fx-background: #FFFFFF; -fx-background-color: #FFFFFF;");
        scrollPane.getContent().setStyle(("-fx-background-color: #FFFFFF"));

        // Adding options to sidebar
        sidebar.getChildren().add(scrollPane);

        // Adding components to main tab
        getChildren().add(sidebar);

        // Styling main layout
        setStyle("-fx-background-color: #F3F9FB;");
    }
}
