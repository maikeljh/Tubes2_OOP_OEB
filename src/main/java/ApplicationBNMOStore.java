import DataStore.DataStore;
import Plugin.Plugin;
import Plugin.BasePlugin;
import Plugin.PluginManager;
import System.Member;
import UI.*;
import com.itextpdf.text.DocumentException;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import System.Inventory;
import System.Item;
import System.PurchasedItem;
import DataStore.XMLAdapter;
import DataStore.Tutorial;
import System.RegisteredCustomer;
import System.Customer;
import System.VIP;
import System.FixedBill;

public class ApplicationBNMOStore extends Application {
    private TabPane tabPane;
    private MainPage mainPage;
    private PluginManager pluginManager = new PluginManager();
    private Inventory<Item> items = new Inventory<Item>();
    private DataStore<Item> itemDS = new DataStore<Item>();

    @Override
    public void start(Stage stage) throws DocumentException, FileNotFoundException {
        // Testing Print PDF
        Tutorial.tes();

        // Read Item Data
        // Sementara XML dlu yak (Belum bikin setting)
        XMLAdapter itemXML = new XMLAdapter();
        itemDS.setAdapter(itemXML);
        items = itemDS.loadData("item.xml", new Class<?>[]{Inventory.class, Item.class});

        if(items.getNeff() > 0){
            Item.setItemIDCount(items.getElement(items.getNeff() - 1).getItemID());
            for(int i = 0; i < items.getNeff(); i++){
                Image image = new Image("/images/item/item" + items.getElement(i).getItemID() + ".png");
                items.getElement(i).setImage(image);
            }
        }

        FixedBill bill2 = new FixedBill();
        bill2.setTotalPrice(100.00);
        bill2.setDiscount(10.00);
        bill2.setDate("2023-2-12");
        bill2.setCustomerID(234);
        bill2.setBillID(5679);

        // Read customers data
        Inventory<Customer> customers = new Inventory<Customer>();
        for (int i = 0; i < 40; i++) {
            if (i > 34) {
                customers.addElement(new VIP(i+1, "Niggas are drunk", "0283103812", bill2));
                if (i % 3 == 0) {
                    ((RegisteredCustomer) customers.getList().get(i)).setActiveStatus(false);
                }
            }

            else if (i <= 34 && i >= 25) {
                customers.addElement(new Member(i+1, "Up OOP open it up", "19332192", bill2));
                if (i % 3 == 0) {
                    ((RegisteredCustomer) customers.getList().get(i)).setActiveStatus(false);
                }
            }

            else {
                customers.addElement(new Customer(bill2));
            }
        }

        // Read transactions
        Inventory<FixedBill> transactions = new Inventory<FixedBill>();
        for (int i=0; i<20; i++){
            transactions.addElement(new FixedBill(i+1, "25/04/2023 21:21", i+1));
        }

        // Read sold items
        Inventory<PurchasedItem> itemsSold = new Inventory<PurchasedItem>();
        itemsSold.addElement(new PurchasedItem(new Item("Cappuccino", 10, 20000, 15000, "Coffee", new Image("/images/item/item4.png")), 5));
        itemsSold.addElement(new PurchasedItem(new Item("Green Tea Latte", 10, 21000, 16500, "Non-Coffee", new Image("/images/item/item4.png")), 10));
        itemsSold.addElement(new PurchasedItem(new Item("Fried Rice", 7, 30000, 23000, "Indonesian Dish", new Image("/images/item/item4.png")), 2));

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
            ListMemberPage listMemberPage = new ListMemberPage(stage, newTab, customers);
            newTab.setContent(listMemberPage);
            tabPane.getTabs().add(newTab);
            tabPane.getSelectionModel().select(newTab);
        });


        // History
        MenuItem history = new MenuItem("History");
        history.setOnAction(event -> {
            // Handle open menu item click
            Tab newTab = new Tab("History");
            newTab.setStyle("-fx-background-color: #F3F9FB;");
            HistoryPage historyPage = new HistoryPage(stage, newTab, "Harry Potter", transactions);
            newTab.setContent(historyPage);
            tabPane.getTabs().add(newTab);
            tabPane.getSelectionModel().select(newTab);
        });

        // Items
        MenuItem itemsPage = new MenuItem("Items");
        itemsPage.setOnAction(event -> {
            // Handle open menu item click
            Tab newTab = new Tab("Items");
            newTab.setStyle("-fx-background-color: #F3F9FB;");
            ListItemPage listItemPage = new ListItemPage(stage, newTab, items, itemDS);
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

        MenuItem cashierDetailPage = new MenuItem("Cashier Detail");
        cashierDetailPage.setOnAction(event -> {
            // Handle open menu item click
            Tab newTab = new Tab("Cashier Detail");
            newTab.setStyle("-fx-background-color: #F3F9FB;");
            CashierDetailPage cashierDetailTab = new CashierDetailPage(stage, newTab);
            newTab.setContent(cashierDetailTab);
            tabPane.getTabs().add(newTab);
            tabPane.getSelectionModel().select(newTab);
        });

        // Sales Report
        MenuItem salesReportPage = new MenuItem("Sales Report");
        salesReportPage.setOnAction(event -> {
            // Handle open menu item click
            Tab newTab = new Tab("History");
            newTab.setStyle("-fx-background-color: #F3F9FB;");
            SalesReportPage reportPage = new SalesReportPage(stage, newTab, itemsSold);
            newTab.setContent(reportPage);
            tabPane.getTabs().add(newTab);
            tabPane.getSelectionModel().select(newTab);
        });

        // Plugins
        MenuItem pluginsPage = new MenuItem("Add Plugin");
        pluginsPage.setOnAction(event -> {
            // Handle open menu item click
            FileChooser fileChooser = new FileChooser();

            // Set the extension filters
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(".jar Files", "*.jar"));

            // Show the file chooser dialog
            File selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null) {
                try {
                    // Try to load plugin from selected jar file
                    pluginManager.loadPlugin(selectedFile);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }

                // Create New Menu from Plugin
                int idx = pluginManager.getPlugins().size() - 1;
                MenuItem newPage = new MenuItem(pluginManager.getPlugins().get(idx).getPluginName());

                newPage.setOnAction(e -> {
                    // Handle open menu item click
                    Plugin newPlugin = pluginManager.getPlugins().get(idx);
                    Tab newTab = new Tab(newPlugin.getPluginName());
                    newTab.setStyle("-fx-background-color: #F3F9FB;");

                    // Set plugin to tab's content
                    BasePlugin newBasePlugin = (BasePlugin) newPlugin;
                    newTab.setContent(newBasePlugin.initialize());
                    tabPane.getTabs().add(newTab);
                    tabPane.getSelectionModel().select(newTab);
                });

                menu.getItems().add(newPage);
            }
        });

        // Settings
        MenuItem settingsPage = new MenuItem("Settings");
        settingsPage.setOnAction(event -> {
            // Handle open menu item click
            Tab newTab = new Tab("Settings");
            newTab.setStyle("-fx-background-color: #F3F9FB;");
            tabPane.getTabs().add(newTab);
            tabPane.getSelectionModel().select(newTab);
            SettingsPage settingsTab = new SettingsPage(stage);
            newTab.setContent(settingsTab);
        });

        // Add Menu Items to Menu
        menu.getItems().add(membersPage);
        menu.getItems().add(itemsPage);
        menu.getItems().add(cashierPage);
        menu.getItems().add(salesReportPage);
        menu.getItems().add(pluginsPage);
        menu.getItems().add(settingsPage);
        menu.getItems().add(history);
        menu.getItems().add(cashierDetailPage);

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
    }

    public static void main(String[] args) {
        launch();
    }
}