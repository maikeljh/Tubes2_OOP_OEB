import DataStore.DataStore;
import Plugin.Decorator.SettingsDecorator;
import Plugin.Plugin;
import Plugin.BasePlugin;
import Plugin.PluginCashier.DiscountCashier;
import Plugin.PluginCashier.TaxAndServiceSettings;
import System.Settings;
import System.Member;
import UI.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import System.Inventory;
import System.Item;
import System.PurchasedItem;
import System.RegisteredCustomer;
import System.Customer;
import System.VIP;
import System.FixedBill;
import System.SalesReport;
import Plugin.PluginManager;


public class ApplicationBNMOStore extends Application {
    private TabPane tabPane;
    private MainPage mainPage;
    private Settings settings = new Settings();
    private SalesReport report = new SalesReport();
    private Inventory<Item> items = new Inventory<Item>();
    private Inventory<Customer> customers = new Inventory<Customer>();

    private final DataStore<Item> itemDS = new DataStore<Item>();
    private final DataStore<Settings> settingsDS = new DataStore<Settings>();
    private final DataStore<Customer> customerDS = new DataStore<Customer>();
    private final DataStore<SalesReport> reportDS = new DataStore<SalesReport>();

    @Override
    public void start(Stage stage) {
        // Load settings
        try {
            Settings temp = new Settings();
            Settings loadedSettings = settingsDS.loadData("settings", temp, new Class<?>[]{Inventory.class, Settings.class, PluginManager.class, SettingsDecorator.class}).getElement(0);
            if(loadedSettings != null){
                settings = loadedSettings;
            } else {
                settings.setFormat("xml");
            }
        } catch (Exception e){
            settings.setFormat("xml");
        }

        // Read Items Data
        try {
            Inventory<Item> loadedItems = itemDS.loadData("item", settings, new Class<?>[]{Inventory.class, Item.class});
            if(loadedItems != null){
                items = loadedItems;
            }
        } catch (Exception e){
            // Do Nothing
        }

        // Read Customers Data
        try {
            Inventory<Customer> loadedCustomers = customerDS.loadData("customer", settings, new Class<?>[]{Inventory.class, Customer.class, RegisteredCustomer.class, Member.class, VIP.class, FixedBill.class, PurchasedItem.class});
            if(loadedCustomers != null){
                customers = loadedCustomers;
            }
        } catch (Exception e){
            // Do Nothing
        }

        // Read Sales Report Data
        try {
            SalesReport loadedReport = reportDS.loadData("report", settings, new Class<?>[]{Inventory.class, SalesReport.class, PurchasedItem.class}).getElement(0);
            if(loadedReport != null){
                report = loadedReport;
            }
        } catch (Exception e){
            // Do Nothing
        }

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
        bill2.getItems().addElement(new PurchasedItem(new Item("Cappuccino", 10, 20000, 15000, "Coffee", new Image("/images/item/item4.png")), 3));
        bill2.getItems().addElement(new PurchasedItem(new Item("Blueberry Pie", 10, 38000, 30000, "Desserts", new Image("/images/item/item4.png")), 1));

        // Read transactions
        double totalPrice = 30000;
        double discount = 0.10;
        Inventory<FixedBill> transactions = new Inventory<FixedBill>();
        for (int i=0; i<5; i++){
            FixedBill fixedBill = new FixedBill("25/04/2023 21:21", i+1, totalPrice, discount);
            fixedBill.getItems().addElement(new PurchasedItem(new Item("Cappuccino", 10, 20000, 15000, "Coffee", new Image("/images/item/item4.png")), 3));
            fixedBill.getItems().addElement(new PurchasedItem(new Item("Blueberry Pie", 10, 38000, 30000, "Desserts", new Image("/images/item/item4.png")), 1));
            fixedBill.getItems().addElement(new PurchasedItem(new Item("Cheese Cake", 10, 40000, 36000, "Desserts", new Image("/images/item/item4.png")), 2));
            fixedBill.getItems().addElement(new PurchasedItem(new Item("Mineral Water", 10, 20000, 15000, "Non Coffee", new Image("/images/item/item4.png")), 1));
            transactions.addElement(fixedBill);
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
            ListMemberPage listMemberPage = new ListMemberPage(stage, newTab, customers, customerDS, settings);
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
            ListItemPage listItemPage = new ListItemPage(stage, newTab, items, itemDS, settings);
            newTab.setContent(listItemPage);
            tabPane.getTabs().add(newTab);
            tabPane.getSelectionModel().select(newTab);
        });

        // Cashier
        MenuItem cashierPage = new MenuItem("Cashier");
        cashierPage.setOnAction(event -> {
            // Handle open menu item click
            Tab newTab = new Tab("Cashier");
            CashierPage cashierTab = new CashierPage(stage, newTab, items, tabPane, customers, 0, transactions, new Inventory<PurchasedItem>(), false, null, settings, settingsDS);
            DiscountCashier tes = new DiscountCashier();
            tes.setPage(cashierTab);
            tes.getPage().setStage(stage);
            tes.getPage().setSettings(settings);
            tes.getPage().setSettingsDS(settingsDS);
            tes.execute();
            newTab.setContent(tes.getPage());
            // newTab.setStyle("-fx-background-color: #F3F9FB;");
            tabPane.getTabs().add(newTab);
            tabPane.getSelectionModel().select(newTab);
        });

        // MenuItem cashierDetailPage = new MenuItem("Cashier Detail");
        // cashierDetailPage.setOnAction(event -> {
        //     // Handle open menu item click
        //     Tab newTab = new Tab("Cashier Detail");
        //     newTab.setStyle("-fx-background-color: #F3F9FB;");
        //     CashierDetailPage cashierDetailTab = new CashierDetailPage(stage, newTab);
        //     newTab.setContent(cashierDetailTab);
        //     tabPane.getTabs().add(newTab);
        //     tabPane.getSelectionModel().select(newTab);
        // });

        // Sales Report
        MenuItem salesReportPage = new MenuItem("Sales Report");
        salesReportPage.setOnAction(event -> {
            // Handle open menu item click
            Tab newTab = new Tab("History");
            newTab.setStyle("-fx-background-color: #F3F9FB;");
            SalesReportPage reportPage = new SalesReportPage(stage, newTab, report);
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
                    settings.getPluginManager().loadPlugin(selectedFile);

                    // Create New Menu from BasePlugin if the plugin is derived from BasePlugin
                    int idx =  settings.getPluginManager().getPlugins().size() - 1;
                    if(settings.getPluginManager().getPlugins().get(idx) instanceof BasePlugin){
                        MenuItem newPage = new MenuItem( settings.getPluginManager().getPlugins().get(idx).getPluginName());

                        newPage.setOnAction(e -> {
                            // Handle open menu item click
                            Plugin newPlugin = settings.getPluginManager().getPlugins().get(idx);
                            Tab newTab = new Tab(newPlugin.getPluginName());
                            newTab.setStyle("-fx-background-color: #F3F9FB;");

                            // Set plugin to tab's content
                            BasePlugin newBasePlugin = (BasePlugin) newPlugin;
                            newBasePlugin.setItems(report.getItems());
                            newTab.setContent(newBasePlugin.initialize());
                            tabPane.getTabs().add(newTab);
                            tabPane.getSelectionModel().select(newTab);
                        });

                        menu.getItems().add(newPage);
                    }

                    List<Class<?>> clazzes = settings.getPluginManager().getClazzes();
                    Class<?>[] classesArray = clazzes.toArray(new Class<?>[0]);
                    Class<?>[] others = {Inventory.class, Settings.class, PluginManager.class, Plugin.class, BasePlugin.class};
                    Class<?>[] concatenated = Arrays.copyOf(classesArray, classesArray.length + others.length);
                    System.arraycopy(others, 0, concatenated, classesArray.length, others.length);

                    Inventory<Settings> temp = new Inventory<Settings>();
                    temp.addElement(settings);

                    Settings template = new Settings();
                    settingsDS.saveData("settings", template, concatenated, temp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
            SettingsPage settingsTab = new SettingsPage(stage, newTab, menu, settings, items, customers, report, itemDS, customerDS, settingsDS, reportDS);
            boolean found = false;
            for(Plugin plugin : settings.getPluginManager().getPlugins()){
                if(plugin instanceof SettingsDecorator settingsDecorated){
                    settingsDecorated.setPage(settingsTab);
                    settingsDecorated.getPage().setStage(stage);
                    settingsDecorated.getPage().setSettings(settings);
                    settingsDecorated.getPage().setSettingsDS(settingsDS);
                    settingsDecorated.execute();
                    newTab.setContent(settingsDecorated.getPage());
                    found = true;
                }
            }
            if(!found) {
                newTab.setContent(settingsTab);
            }
            TaxAndServiceSettings tes = new TaxAndServiceSettings();
            tes.setPage(settingsTab);
            tes.getPage().setStage(stage);
            tes.getPage().setSettings(settings);
            tes.getPage().setSettingsDS(settingsDS);
            tes.execute();
            newTab.setContent(tes.getPage());
        });

        // Add Menu Items to Menu
        menu.getItems().add(membersPage);
        menu.getItems().add(itemsPage);
        menu.getItems().add(cashierPage);
        menu.getItems().add(salesReportPage);
        menu.getItems().add(pluginsPage);
        menu.getItems().add(settingsPage);

        // Add BasePlugins to Menu
        for(Plugin plugin : settings.getPluginManager().getPlugins()){
            if(plugin instanceof BasePlugin){
                MenuItem newPage = new MenuItem(plugin.getPluginName());
                newPage.setOnAction(e -> {
                    // Handle open menu item click
                    Tab newTab = new Tab(plugin.getPluginName());
                    newTab.setStyle("-fx-background-color: #F3F9FB;");

                    // Set plugin to tab's content
                    BasePlugin newBasePlugin = (BasePlugin) plugin;
                    newBasePlugin.setItems(report.getItems());
                    newTab.setContent(newBasePlugin.initialize());
                    tabPane.getTabs().add(newTab);
                    tabPane.getSelectionModel().select(newTab);
                });
                menu.getItems().add(newPage);
            }
        }

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