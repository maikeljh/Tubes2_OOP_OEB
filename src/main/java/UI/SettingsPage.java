package UI;

import DataStore.DataStore;
import Plugin.Decorator.SettingsDecorator;
import Plugin.PluginManager;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import System.Inventory;
import System.Settings;
import System.Item;
import System.Customer;
import System.FixedBill;
import System.PurchasedItem;
import System.RegisteredCustomer;
import System.Member;
import System.VIP;
import System.SalesReport;
import Plugin.Plugin;
import Plugin.BasePlugin;

public class SettingsPage extends HBox {
    protected final VBox options;
    protected final VBox details;
    protected Stage stage;
    protected Settings settings;
    protected SalesReport report;
    protected ComboBox<String> formatOptions = new ComboBox<>(FXCollections.observableArrayList("xml", "json", "obj"));
    protected Button pickFolder = new Button();
    protected Label currentPath;

    /* Inventories */
    protected Inventory<Item> items;
    protected Inventory<Customer> customers;

    /* DataStores */
    protected DataStore<Item> itemDS;
    protected DataStore<Settings> settingsDS;
    protected DataStore<Customer> customerDS;
    protected DataStore<SalesReport> reportDS;

    public SettingsPage(Stage stage, Tab tab, Menu menu, Settings settings, Inventory<Item> items, Inventory<Customer> customers, SalesReport report, DataStore<Item> itemDS, DataStore<Customer> customerDS, DataStore<Settings> settingsDS, DataStore<SalesReport> reportDS) {
        /* Set Attributes */
        this.items = items;
        this.customers = customers;
        this.itemDS = itemDS;
        this.customerDS = customerDS;
        this.settings = settings;
        this.report = report;
        this.reportDS = reportDS;

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
        options = new VBox();
        details = new VBox();

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

        // Format Options
        // Create a combo box with some sample options
        formatOptions.getItems().clear();
        formatOptions.getItems().addAll("xml", "json", "obj");
        formatOptions.setValue(settings.getFormat());
        formatOptions.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Change format and save settings
            settings.setFormat(newValue);
            Settings temp = new Settings();
            Inventory<Settings> tempSettings = new Inventory<Settings>();
            tempSettings.addElement(settings);

            // Remove old files and save with new format
            File directory = new File(settings.getSaveDirectory());
            File[] files = directory.listFiles();
            for (File file : files) {
                if (file.isFile() && (file.getName().startsWith("item") || file.getName().startsWith("customer") || file.getName().startsWith("settings"))){
                    file.delete();
                }
            }

            List<Class<?>> clazzes = settings.getPluginManager().getClazzes();
            Class<?>[] classesArray = clazzes.toArray(new Class<?>[clazzes.size()]);
            Class<?>[] others = {Inventory.class, Settings.class, PluginManager.class, Plugin.class};
            Class<?>[] concatenated = Arrays.copyOf(classesArray, classesArray.length + others.length);
            System.arraycopy(others, 0, concatenated, classesArray.length, others.length);

            settingsDS.saveData("settings", temp, concatenated, tempSettings);
            itemDS.saveData("item", settings, new Class<?>[]{Inventory.class, Item.class}, items);
            customerDS.saveData("customer", settings, new Class<?>[]{Inventory.class, Customer.class, RegisteredCustomer.class, Member.class, VIP.class, FixedBill.class, PurchasedItem.class}, customers);

            // Save report
            Inventory<SalesReport> tempReport = new Inventory<SalesReport>();
            tempReport.addElement(report);
            reportDS.saveData("report", settings, new Class<?>[]{Inventory.class, SalesReport.class, PurchasedItem.class}, tempReport);
        });

        // Pick Folder
        // Button to change path
        pickFolder = new Button("Change path");
        pickFolder.setOnAction(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(stage);
            if (selectedDirectory != null) {
                currentPath.setText(selectedDirectory.getAbsolutePath());
                settings.setSaveDirectory(selectedDirectory.getAbsolutePath());
                Settings temp = new Settings();
                Inventory<Settings> tempSettings = new Inventory<Settings>();
                tempSettings.addElement(settings);

                // Remove old files and save with new format
                File directory = new File(settings.getSaveDirectory());
                File[] files = directory.listFiles();
                for (File file : files) {
                    if (file.isFile() && (file.getName().startsWith("item") || file.getName().startsWith("customer") || file.getName().startsWith("settings"))){
                        file.delete();
                    }
                }

                List<Class<?>> clazzes = settings.getPluginManager().getClazzes();
                Class<?>[] classesArray = clazzes.toArray(new Class<?>[clazzes.size()]);
                Class<?>[] others = {Inventory.class, Settings.class, PluginManager.class, Plugin.class};
                Class<?>[] concatenated = Arrays.copyOf(classesArray, classesArray.length + others.length);
                System.arraycopy(others, 0, concatenated, classesArray.length, others.length);

                settingsDS.saveData("settings", temp, concatenated, tempSettings);
                itemDS.saveData("item", settings, new Class<?>[]{Inventory.class, Item.class}, items);
                customerDS.saveData("customer", settings, new Class<?>[]{Inventory.class, Customer.class, RegisteredCustomer.class, Member.class, VIP.class, FixedBill.class, PurchasedItem.class}, customers);

                // Save report
                Inventory<SalesReport> tempReport = new Inventory<SalesReport>();
                tempReport.addElement(report);
                reportDS.saveData("report", settings, new Class<?>[]{Inventory.class, SalesReport.class, PurchasedItem.class}, tempReport);
            }
        });

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
            currentPath = new Label(settings.getSaveDirectory());
            Tooltip tooltip = new Tooltip(settings.getSaveDirectory());
            Tooltip.install(currentPath, tooltip);
            currentPath.setTooltip(tooltip);

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
        opt2.setOnAction(event -> {
            getChildren().remove(details);
            details.setPadding(new Insets(30, 50, 10, 50));
            details.getChildren().clear();
            Label detailTitle2 = new Label("Plugin");
            detailTitle2.setFont(Font.font("Montserrat", FontWeight.BOLD, 36));

            VBox pluginBox = new VBox();
            pluginBox.setSpacing(10);

            for(Plugin plugin : settings.getPluginManager().getPlugins()){
                if(plugin.isMainPlugin()){
                    HBox pluginHBox = new HBox();
                    pluginHBox.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px");
                    pluginHBox.setPadding(new Insets(10));

                    HBox pluginNameBox = new HBox();
                    Label pluginName = new Label(plugin.getPluginName());
                    pluginName.setFont(Font.font("Montserrat", 20));
                    pluginNameBox.getChildren().add(pluginName);

                    // Create HBox for buttons
                    HBox buttonHBox = new HBox();

                    // Set image / icon for preview button
                    ImageView deleteIcon = new ImageView("/images/icon/delete.png");

                    // Style preview button icon
                    deleteIcon.setPreserveRatio(true);
                    deleteIcon.setSmooth(true);
                    deleteIcon.setCache(true);
                    deleteIcon.setFitWidth(27);
                    deleteIcon.setFitHeight(27);

                    // Create preview button
                    Button deleteButton = new Button();
                    deleteButton.setPrefSize(27, 27);
                    deleteButton.setStyle("-fx-background-color: #C8DFE8;");
                    deleteButton.setGraphic(deleteIcon);
                    deleteButton.setCursor(Cursor.HAND);

                    // Add event delete button
                    deleteButton.setOnAction(e -> {
                        if(settings.getPluginManager().getPlugin(plugin.getPluginName()) instanceof BasePlugin){
                            menu.getItems().stream()
                                    .filter(item -> settings.getPluginManager().getPlugin(plugin.getPluginName()).getPluginName().equals(item.getText()))
                                    .findFirst()
                                    .ifPresent(menu.getItems()::remove);
                        }

                        settings.getPluginManager().removePlugin(plugin.getPluginName());

                        // Save settings
                        Settings temp = new Settings();
                        Inventory<Settings> tempSettings = new Inventory<Settings>();
                        tempSettings.addElement(settings);

                        List<Class<?>> clazzes = settings.getPluginManager().getClazzes();
                        Class<?>[] classesArray = clazzes.toArray(new Class<?>[0]);
                        Class<?>[] others = {Inventory.class, Settings.class, PluginManager.class, Plugin.class};
                        Class<?>[] concatenated = Arrays.copyOf(classesArray, classesArray.length + others.length);
                        System.arraycopy(others, 0, concatenated, classesArray.length, others.length);

                        settingsDS.saveData("settings", temp, concatenated, tempSettings);

                        SettingsPage settingsTab = new SettingsPage(stage, tab, menu, settings, items, customers, report, itemDS, customerDS, settingsDS, reportDS);
                        boolean found = false;
                        for(Plugin tempPlugin : settings.getPluginManager().getPlugins()){
                            if(tempPlugin instanceof SettingsDecorator settingsDecorated){
                                settingsDecorated.setPage(settingsTab);
                                settingsDecorated.getPage().setStage(stage);
                                settingsDecorated.getPage().setSettings(settings);
                                settingsDecorated.getPage().setSettingsDS(settingsDS);
                                settingsDecorated.execute();
                                tab.setContent(settingsDecorated.getPage());
                                found = true;
                            }
                        }
                        if(!found) {
                            tab.setContent(settingsTab);
                        }

                        opt2.fire();
                    });

                    // Styling pluginHBox
                    HBox.setHgrow(pluginNameBox, Priority.ALWAYS);
                    pluginNameBox.setAlignment(Pos.CENTER_LEFT);

                    // Style transaction buttons HBox
                    buttonHBox.setAlignment(Pos.CENTER_RIGHT);

                    buttonHBox.getChildren().add(deleteButton);
                    pluginHBox.getChildren().addAll(pluginNameBox, buttonHBox);
                    pluginBox.getChildren().add(pluginHBox);
                    pluginBox.setPrefWidth(680);
                }
            }

            ScrollPane scrollPane = new ScrollPane(pluginBox);

            // Styling scrollPane
            scrollPane.setMaxHeight(500);
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPane.setStyle("-fx-background-color: #F3F9FB; -fx-background: #F3F9FB;");

            details.getChildren().addAll(detailTitle2, scrollPane);
            details.setSpacing(10);

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

    public VBox getOptions(){
        return this.options;
    }

    public VBox getDetails(){
        return this.details;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setSettings(Settings settings){
        this.settings = settings;
    }

    public void setReport(SalesReport report) {
        this.report = report;
    }

    public void setSettingsDS(DataStore<Settings> settingsDS){
        this.settingsDS = settingsDS;
    }

    public Inventory<Item> getItems(){
        return this.items;
    }

    public Inventory<Customer> getCustomers(){
        return this.customers;
    }

    public Settings getSettings(){
        return this.settings;
    }

    public SalesReport getReport() {
        return this.report;
    }

    public DataStore<Item> getItemDS(){
        return this.itemDS;
    }

    public DataStore<Customer> getCustomerDS(){
        return this.customerDS;
    }

    public DataStore<Settings> getSettingsDS(){
        return this.settingsDS;
    }

    public DataStore<SalesReport> getReportDS() {
        return this.reportDS;
    }

    public ComboBox<String> getFormatOptions(){
        return this.formatOptions;
    }

    public Button getPickFolder(){
        return this.pickFolder;
    }
}
