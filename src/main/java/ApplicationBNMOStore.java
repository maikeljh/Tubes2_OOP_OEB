<<<<<<< HEAD
import DataStore.*;
=======
>>>>>>> 0be1e83d458f2a59b3704eae29b7acf489d15a8e
import UI.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Plugin.Plugin2;


public class ApplicationBNMOStore extends Application {
    private TabPane tabPane;
    private MainPage mainPage;

    @Override
    public void start(Stage stage) {
        // Create Group and new Scene
        Group root = new Group();
        Scene scene = new Scene(root, 1280, 720);

        // Page Menu
        Menu menu = new Menu("Menu");

        /* Menu Items */
        // Members
        MenuItem membersPage = new MenuItem("Members");
        membersPage.setOnAction(event -> {
            // Handle open menu item click
            Tab newTab = new Tab("Members");
            newTab.setStyle("-fx-background-color: #F3F9FB;");
            ListMemberPage listMemberPage = new ListMemberPage(stage);
            newTab.setContent(listMemberPage);
            tabPane.getTabs().add(newTab);
            tabPane.getSelectionModel().select(newTab);
        });

        // Items
        MenuItem itemsPage = new MenuItem("Items");
        itemsPage.setOnAction(event -> {
            // Handle open menu item click
            Tab newTab = new Tab("Items");
            newTab.setStyle("-fx-background-color: #F3F9FB;");
            ListItemPage listItemPage = new ListItemPage(stage, newTab);
            newTab.setContent(listItemPage);
            tabPane.getTabs().add(newTab);
            tabPane.getSelectionModel().select(newTab);
        });

        // Cashier
        MenuItem cashierPage = new MenuItem("Cashier");
        cashierPage.setOnAction(event -> {
            // Handle open menu item click
            Tab newTab = new Tab("Cashier");
            newTab.setStyle("-fx-background-color: #F3F9FB;");
            tabPane.getTabs().add(newTab);
            tabPane.getSelectionModel().select(newTab);
        });

        // Sales Report
        MenuItem salesReportPage = new MenuItem("Sales Report");
        salesReportPage.setOnAction(event -> {
            // Handle open menu item click
            Tab newTab = new Tab("Sales Report");
            newTab.setStyle("-fx-background-color: #F3F9FB;");
            tabPane.getTabs().add(newTab);
            tabPane.getSelectionModel().select(newTab);
        });

        // Plugins
        MenuItem pluginsPage = new MenuItem("Plugins");
        pluginsPage.setOnAction(event -> {
            // Handle open menu item click
            Tab newTab = new Tab("Plugins");
            newTab.setStyle("-fx-background-color: #F3F9FB;");
            tabPane.getTabs().add(newTab);
            tabPane.getSelectionModel().select(newTab);
            newTab.setContent(new Plugin2());
        });

        // Settings
        MenuItem settingsPage = new MenuItem("Settings");
        settingsPage.setOnAction(event -> {
            // Handle open menu item click
            Tab newTab = new Tab("Settings");
            newTab.setStyle("-fx-background-color: #F3F9FB;");
            tabPane.getTabs().add(newTab);
            tabPane.getSelectionModel().select(newTab);
        });

        // Add Menu Items to Menu
        menu.getItems().add(membersPage);
        menu.getItems().add(itemsPage);
        menu.getItems().add(cashierPage);
        menu.getItems().add(salesReportPage);
        menu.getItems().add(pluginsPage);
        menu.getItems().add(settingsPage);

        // Main Menu Bar
        MenuBar menuBar = new MenuBar();
        menuBar.setPrefWidth(scene.getWidth());
        menuBar.getMenus().addAll(menu);
        menuBar.setStyle("-fx-background-color: #8CAEBB;");

        // Tab Pane
        tabPane = new TabPane();
        tabPane.setStyle("-fx-background-color: #C8DFE8;");
        tabPane.getStyleClass().add("floating");

        // Tab
        Tab mainTab = new Tab("Main Page");
        mainTab.setStyle("-fx-background-color: #F3F9FB;");

        // Main Page
        mainPage = new MainPage();

        // Setup Main Page Tab
        mainTab.setContent(mainPage);
        mainTab.setClosable(false);
        tabPane.getTabs().add(mainTab);

        // Add menu bar and tab bar
        VBox vbox = new VBox();
        vbox.getChildren().add(menuBar);
        vbox.getChildren().add(tabPane);
        root.getChildren().add(vbox);

        // Set stage settings
        stage.setTitle("GoShOOP");
        stage.setResizable(false);
        stage.setScene(scene);

        // Show Stage
        stage.show();

        // Stop thread if application closed
        stage.setOnCloseRequest(event -> mainPage.setStop(true));
<<<<<<< HEAD

//        Coba.tes();
=======
>>>>>>> 0be1e83d458f2a59b3704eae29b7acf489d15a8e
    }

    public static void main(String[] args) {
        launch();
    }
}