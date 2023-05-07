package UI;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import Core.*;
import Plugin.Decorator.CashierDecorator;
import Plugin.Decorator.CashierDetailDecorator;
import Plugin.Plugin;
import DataStore.DataStore;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.FileNotFoundException;
import com.itextpdf.text.DocumentException;
import java.lang.IndexOutOfBoundsException;
import java.math.RoundingMode;
import java.util.Comparator;

public class CashierPage extends Page {
    private Inventory<PurchasedItem> purchasedItems;
    protected double totalPrice = 0;
    protected RegisteredCustomer regisCust = null;
    private double finalTotalPrice = 0;
    private boolean isUsePoint = false;
    private ChoiceBox<RegisteredCustomer> choiceBox;
    private List<Integer> tempID;
    private int selectionIndex;
    private Inventory<Item> items;
    private Inventory<Customer> customers;
    private int mode;
    private int totalItem = 0;
    private String totalItemString = "";
    private String searchedText= "";
    private ToggleGroup group = null;
    private ChoiceBox<String> choiceCategoriesBox = null;
    private Spinner<Double> spinner2 = null;
    private Spinner<Double> spinner = null;
    private CheckBox checkBox2 = null;
    private CheckBox checkBox1 = null;
    private Button backButton = null;
    private Label totalItemLabel = null;
    private Label allItem = null;
    private VBox leftVBox = null;
    private VBox libraryBox = null;
    private VBox bodyLibraryVBox = null;
    private HBox headLibraryBox = null;
    private VBox headLibraryTitleBox = null;
    private HBox totalPriceBox = null;
    private TabPane tabPane;
    private double formattedDiscount;
    private Text totalPriceBillLabel;
    private Label fixTotalPriceBill;
    private Label totalPriceBills;
    private String totalPriceBill;
    private String fixTotalPrice;
    private ToggleButton usePointButton;
    private NumberFormat formatter;
    private SalesReport report;
    private DataStore<Customer> customerDS;
    private DataStore<SalesReport> reportDS;
    private DataStore<Item> itemDS;

    public CashierPage(Stage stage, Tab tab, Inventory<Item> items, TabPane tabPane, Inventory<Customer> customers, Integer mode, Inventory<PurchasedItem> purchasedItems, boolean usePoint, RegisteredCustomer registeredCust, Settings settings, DataStore<Settings> settingsDS, SalesReport report){
        super(stage, tab, settings, settingsDS);

        // Assign values
        this.report = report;
        this.tabPane = tabPane;
        this.customers = customers;
        this.items = items;
        this.selectionIndex = -1;
        this.regisCust = registeredCust;
        this.isUsePoint = usePoint;
        this.purchasedItems = purchasedItems;
        this.mode = mode;
        this.customerDS = new DataStore<Customer>();
        this.reportDS = new DataStore<SalesReport>();
        this.itemDS = new DataStore<Item>();

        // Create main VBox
        VBox mainVBox = new VBox();
        
        // Create main HBox (mainVBox)
        HBox mainHBox = new HBox();

        // Create left VBox (mainHBox)
        this.leftVBox = new VBox();

        // Create right HBox (mainHBox)
        VBox rightVBox = new VBox();

        // Create header HBox (mainVBox)
        HBox hBox = new HBox();


        // Header

        // Create title (header)
        Label title = new Label("Cashier");
        title.setFont(Font.font("Montserrat", FontWeight.BOLD, 28));
        title.setTextAlignment(TextAlignment.CENTER);

        // Add header to HBox (header)
        hBox.getChildren().addAll(title);

        //Styling left & right box & header & main
        mainVBox.setMaxWidth(1280);
        mainVBox.setMaxHeight(600);
        this.leftVBox.setPrefWidth(620);
        rightVBox.setPrefWidth(620);
        mainHBox.prefWidthProperty().bind(mainVBox.widthProperty());
        hBox.prefWidthProperty().bind(mainVBox.widthProperty());


        // leftVBox

        // Create a new TabPane (leftVBox)
        TabPane tabPaneCashier = new TabPane();
        tabPaneCashier.setStyle("-fx-background-color: #F3F9FB;-fx-padding: 5;-fx-background-radius: 10;-fx-focus-color: transparent;");
        tabPaneCashier.getStyleClass().add("floating");
    
        // Create two tabs (tabPaneCashier)
        Tab tab1 = new Tab("Favorites");
        tab1.setStyle("-fx-background-color: #3B919B; -fx-text-fill: white;-fx-background-radius: 10;-fx-focus-color: transparent;");
        Tab tab2 = new Tab("Library");
        tab2.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: white;-fx-background-radius: 10;-fx-focus-color: transparent;");


        // Create HBox for grid (tab 1 content)
        HBox gridHBox = new HBox();

        //Styling grid box (leftVBox)
        gridHBox.setPrefHeight(600);
        gridHBox.setStyle("-fx-background: #F3F9FB; -fx-background-color: #F3F9FB;");
        gridHBox.prefWidthProperty().bind(this.leftVBox.widthProperty());

        // List of Items (gridHBox)
        GridPane grid = new GridPane();

        // Styling gridpane (gridHBox)
        grid.setStyle("-fx-background-color: #F3F9FB;");
        grid.setHgap(40);
        grid.setVgap(40);
        grid.prefWidthProperty().bind(this.leftVBox.widthProperty());

        // Create item display (grid)
        int row = 0;
        int col = 0;
        int count = 0;

        // Display List Of Items (grid)
        for (Item item : this.report.getItems().getBox()) {
            if (count < 9 && item.getStock() > 0){
                boolean valid = false;
                Item chosen = item;
                for(Item temp : this.items.getBox()){
                    if(item.getItemID() == temp.getItemID()){
                        valid = true;
                        chosen = temp;
                    }
                }

                if(!valid){
                    continue;
                }

                // VBox Display Item
                VBox itemDisplay = new VBox();

                // Image View Item
                ImageView itemImage = new ImageView(chosen.getImage());

                // Styling Item Image
                itemImage.setPreserveRatio(true);
                itemImage.setSmooth(true);
                itemImage.setCache(true);
                itemImage.setFitWidth(134);
                itemImage.setFitHeight(109);

                // Item Name
                Text itemName = new Text(chosen.getName());

                // Styling Item Name
                itemName.setFont(Font.font("Montserrat", 14));

                // Styling Item Display
                itemDisplay.getChildren().addAll(itemImage, itemName);
                itemDisplay.setAlignment(Pos.CENTER);
                itemDisplay.setPadding(new Insets(5));
                itemDisplay.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px;");
                itemDisplay.setPrefWidth(153);
                itemDisplay.setPrefHeight(135);
                itemDisplay.setSpacing(5);

                // Add onclick event
                Item finalChosen = chosen;
                itemDisplay.setOnMouseClicked(event -> {
                    setRegCust();
                    CashierDetailPage detailCashierContent = new CashierDetailPage(this.stage, tab, finalChosen, this.purchasedItems, this.items, tabPane, this.customers, this.mode, this.isUsePoint, this.regisCust, this.settings, this.settingsDS, report);
                    boolean found = false;
                    for(Plugin plugin : this.getSettings().getPluginManager().getPlugins()){
                        if(plugin instanceof CashierDetailDecorator){
                            Class<?> cashierDetailDecoratorClass = plugin.getClass();
                            Constructor<?> constructor = null;
                            try {
                                constructor = cashierDetailDecoratorClass.getDeclaredConstructor();
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            }
                            CashierDetailDecorator cashierDetailDecorated = null;
                            try {
                                assert constructor != null;
                                cashierDetailDecorated = (CashierDetailDecorator) constructor.newInstance();
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                            cashierDetailDecorated.setPage(detailCashierContent);
                            cashierDetailDecorated.getPage().setStage(stage);
                            cashierDetailDecorated.getPage().setSettings(settings);
                            cashierDetailDecorated.getPage().setSettingsDS(settingsDS);
                            cashierDetailDecorated.execute();
                            tab.setContent(cashierDetailDecorated.getPage());
                            found = true;
                        }
                    }
                    if(!found){
                        tab.setContent(detailCashierContent);
                    }
                });
                
                // Add Item Display to Grid
                grid.add(itemDisplay, col, row);
                count++;
                col++;
                if (col == 3) {
                    col = 0;
                    row++;
                }
            }
        }

        // Display empty list (grid)
        while (count <9){
            // VBox Display Item
            VBox itemDisplay = new VBox();

            // Styling Item Display
            itemDisplay.setAlignment(Pos.CENTER);
            itemDisplay.setPadding(new Insets(5));
            itemDisplay.setStyle("-fx-background-color: #C8DFE8; -fx-background-radius: 10px;");
            itemDisplay.setPrefWidth(153);
            itemDisplay.setPrefHeight(135);
            itemDisplay.setSpacing(5);

            // Add Item Display to Grid
            grid.add(itemDisplay, col, row);
            count++;
            col++;
            if (col == 3) {
                col = 0;
                row++;
            }
        }

        // Add grid to gridHBox (gridHBox)
        gridHBox.getChildren().add(grid);

        // Create libraryMainBox (tab 2 content)
        VBox libraryMainBox = new VBox();

        // Styling libraryMainBox
        libraryMainBox.setStyle("-fx-background-color: #F3F9FB;");
        libraryMainBox.prefWidthProperty().bind(this.leftVBox.widthProperty());

        // Create a searchbar (libraryMainBox)
        // Create a search box
        HBox searchBox = new HBox();

        // Create a Textfield (searchBox)
        TextField searchBar = new TextField();

        // Styling searchBar
        searchBar.setPromptText("Search. . .");
        searchBar.setPrefWidth(600);
        searchBar.setStyle("-fx-background-color: transparent;-fx-padding: 0;-fx-background-insets: 0;-fx-border-color: transparent;-fx-border-width: 0;-fx-border-radius: 0;-fx-prompt-text-fill: #8CAEBB;-fx-font-size: 14;-fx-font-weight: bold;");


        // Create a filter button (searchBox)
        ImageView filterIcon = new ImageView("/images/icon/filterButton.png");

        // Styling filterIcon
        filterIcon.setPreserveRatio(true);
        filterIcon.setSmooth(true);
        filterIcon.setCache(true);
        filterIcon.setFitWidth(14);
        filterIcon.setFitHeight(14);

        // Create a menubutton dropdown (searchBox)
        MenuButton filterDropDownButton = new MenuButton();
        filterDropDownButton.setGraphic(filterIcon);

        // Create main drop down container (menuItem)
        HBox containerDropDown = new HBox();

        // Create detail drop down container (filterDropDownButton)
        VBox containerDropDownDetails = new VBox();

        // Create detail 2 drop down container (filterDropDownButton)
        VBox containerDropDownDetails2 = new VBox();

        // Create checkBox title (containerDropDownDetails)
        Label checkBoxTitle = new Label("Sort By:");
        checkBoxTitle.setAlignment(Pos.CENTER_LEFT);
        
        // Create checkboxes for sort by price & categories (containerDropDownDetails)
        VBox checkBoxBox = new VBox();
        this.checkBox1 = new CheckBox("Sort by Price");
        this.checkBox2 = new CheckBox("Sort by Categories");
        checkBoxBox.getChildren().addAll(this.checkBox1, this.checkBox2);

        // Create toogle group & radio buttons for descending or ascending (containerDropDownDetails)
        this.group = new ToggleGroup();
        VBox radioButtonBox = new VBox();
        RadioButton radioButton1 = new RadioButton("Descending");
        RadioButton radioButton2 = new RadioButton("Ascending");
        radioButton1.setToggleGroup(this.group);
        radioButton2.setToggleGroup(this.group);
        radioButtonBox.getChildren().addAll(radioButton1, radioButton2);
        this.group.selectToggle(radioButton2);

        // Create price slider title (containerDropDownDetails2)
        Label priceSliderTitle = new Label("Price Range: ");
        priceSliderTitle.setAlignment(Pos.CENTER_LEFT);

        // Create priceBox (containerDropDownDetails2)
        HBox priceBox = new HBox();

        // Create min & max price spinner (priceBox)
        // Create min price spinner
        Label minPrice = new Label("Min: ");
        this.spinner = new Spinner<Double>();
        double initVal1 = getMinPrice();
        double initVal2 = getMaxPrice();
        this.spinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, initVal2, initVal1));
        this.spinner.setEditable(true);

        // Create max price spinner
        Label maxPrice = new Label("Max: ");
        this.spinner2 = new Spinner<Double>();
        this.spinner2.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1, initVal2+100, initVal2));
        this.spinner2.setEditable(true);

        // Add min & max price spinner to priceBox
        priceBox.getChildren().addAll(minPrice, this.spinner, maxPrice, this.spinner2);
        priceBox.setSpacing(5);

        // Create min & max price slider (priceBox)
        VBox priceSliderBox = new VBox();

        // Create min price slider
        Slider slider1 = new Slider(0, initVal2, initVal1);
        priceSliderBox.getChildren().add(slider1);

        // Crate max price slider
        Slider slider2 = new Slider(1, initVal2+100, initVal2);
        priceSliderBox.getChildren().add(slider2);

        // Create categories dropdown (containerDropDownDetails2)
        Label dropDownCategoriesTitle = new Label("Category: ");
        dropDownCategoriesTitle.setAlignment(Pos.CENTER_LEFT);
        VBox dropDownCategoriesBox = new VBox();
        this.choiceCategoriesBox = new ChoiceBox<>();

        // Create categories dropdown member (dropDownCategoriesBox)
        List<String> temp = new ArrayList<String>();
        boolean flag = false;

        // Add all categories available in items
        for (Item item : this.items.getBox() ){
            flag = false;

            // Avoid adding the same categories
            for (String category : temp){
                if(item.getCategory().equalsIgnoreCase(category)){
                    flag = true;
                    break;
                }
            }
            if (!flag){
                this.choiceCategoriesBox.getItems().add(item.getCategory());
                temp.add(item.getCategory());
            }

        }
        this.choiceCategoriesBox.setValue("Not Chosen");

        // Styling categories choiceBox
        this.choiceCategoriesBox.setStyle("-fx-background-color: transparent;-fx-padding: 0;-fx-background-insets: 0;-fx-border-color: transparent;-fx-border-width: 0;-fx-border-radius: 0;-fx-prompt-text-fill: #3B919B;-fx-font-size: 14;-fx-font-weight: bold;-fx-text-fill: #3B919B;");

        // Add categories checkBox to dropDownCategoriesBox
        dropDownCategoriesBox.getChildren().add(this.choiceCategoriesBox);

        // Add childrens to containerDropDownDetails
        containerDropDownDetails.getChildren().addAll(checkBoxTitle, checkBoxBox, radioButtonBox);
        containerDropDownDetails.setSpacing(10);

        // Add childrens to containerDropDown
        containerDropDown.getChildren().addAll(containerDropDownDetails, containerDropDownDetails2);
        containerDropDown.setSpacing(20);

        // Add containerDropDown to menuitem
        CustomMenuItem menuItem = new CustomMenuItem(containerDropDown,false);
        
        // Add menuItem to filterDropDownButton
        filterDropDownButton.getItems().add(menuItem);

        // Styling filterDropDownButton
        filterDropDownButton.setStyle("-fx-background-color: transparent;-fx-padding: 0;-fx-background-insets: 0;-fx-border-color: transparent;-fx-border-width: 0;-fx-border-radius: 0;-fx-prompt-text-fill: #8CAEBB;-fx-font-size: 14;-fx-font-weight: bold;-fx-focus-color: transparent;");

        // Add childrens to searchBox
        searchBox.getChildren().addAll(searchBar, filterDropDownButton);

        // Styling searchBox
        searchBox.prefWidthProperty().bind(this.leftVBox.widthProperty());
        searchBox.setStyle("-fx-background-color: white; -fx-text-fill: #8CAEBB;-fx-border-radius: 10;-fx-border-width: 0.2;-fx-border-color: black;-fx-background-radius: 10");
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setPadding(new Insets(4,4,4,4));
        HBox.setMargin(filterDropDownButton, new Insets(0, 0, 0, 20));

        // Create VBox library (libraryMainBox)
        this.libraryBox = new VBox();
        this.libraryBox.setPrefHeight(430);
        this.libraryBox.prefWidthProperty().bind(this.leftVBox.widthProperty().subtract(60));
        this.libraryBox.setStyle("-fx-background-color: white;-fx-border-radius: 10;-fx-border-width: 0.2;-fx-border-color: black;-fx-background-radius: 10");

        // Create HBox header library (libraryBox)
        this.headLibraryBox = new HBox();
        this.headLibraryBox.setPrefHeight(70);
        this.headLibraryBox.prefWidthProperty().bind(this.leftVBox.widthProperty().subtract(60));
        this.headLibraryBox.setStyle("-fx-background-color: white;");

        // Create back button (headLibraryBox)
        ImageView backIcon = new ImageView("/images/icon/backButton.png");
        this.backButton = new Button("", backIcon);

        // Styling back Button
        backIcon.setPreserveRatio(true);
        backIcon.setSmooth(true);
        backIcon.setCache(true);
        backIcon.setFitWidth(14);
        backIcon.setFitHeight(14);
        this.backButton.setStyle("-fx-background-color: transparent;-fx-padding: 0;-fx-background-insets: 0;-fx-border-color: transparent;-fx-border-width: 0;-fx-border-radius: 0;-fx-prompt-text-fill: #8CAEBB;-fx-font-size: 14;-fx-font-weight: bold;");
        this.backButton.setAlignment(Pos.CENTER);

        // Create VBox headerTitleBox (headLibraryBox)
        this.headLibraryTitleBox = new VBox();

        // Create all item label (headLibraryTitleBox)
        this.allItem = new Label("All Items");

        // Styling allItem
        this.allItem.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        this.allItem.setStyle("-fx-text-fill: #3B919B;");

        // Create totalItem label (headLibraryTitleBox)
        // Stock > 0 validation
        int tempItem = 0;
        for (Item item : this.items.getBox()){
            if (item.getStock() > 0){
                tempItem++;
            }
        }
        this.totalItem = tempItem;
        this.totalItemString = String.valueOf(this.totalItem) + " Items";
        this.totalItemLabel = new Label(this.totalItemString);

        // Styling totalItemLabel 
        this.totalItemLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 10));
        this.totalItemLabel.setStyle("-fx-text-fill: #C8DFE8;");

        // Add childrens to headLibraryTitleBox
        this.headLibraryTitleBox.getChildren().addAll(this.allItem, this.totalItemLabel);
        this.headLibraryTitleBox.setSpacing(5);
        this.headLibraryTitleBox.setAlignment(Pos.CENTER);

        // Add childrens to headerLibraryBox
        this.headLibraryBox.getChildren().addAll(this.backButton, this.headLibraryTitleBox);

        // Styling headLibraryBox
        HBox.setMargin(this.headLibraryTitleBox, new Insets(0, 0, 0, 200));
        HBox.setMargin(this.backButton, new Insets(0, 0, 0, 20));
        this.headLibraryBox.setAlignment(Pos.CENTER_LEFT);

        // Create VBox (scrollPane)
        this.bodyLibraryVBox = new VBox();
        this.bodyLibraryVBox.prefWidthProperty().bind(this.leftVBox.widthProperty().subtract(60));

        // Create libraryDisplay (bodyLibraryVBox)
        // for loop item
        for (Item library : this.items.getBox()) {

            if(library.getStock()>0){

                // HBox Display Item
                HBox libraryDisplay = new HBox();
                libraryDisplay.prefWidthProperty().bind(this.leftVBox.widthProperty().subtract(60));
                libraryDisplay.setPrefHeight(40);

                // Image View Item
                ImageView libraryImage = new ImageView(library.getImage());

                // Styling Item Image
                libraryImage.setPreserveRatio(true);
                libraryImage.setSmooth(true);
                libraryImage.setCache(true);
                libraryImage.setFitWidth(40);
                libraryImage.setFitHeight(40);

                // Item Name
                Label libraryName = new Label(library.getName());

                // Styling Item Name
                libraryName.setFont(Font.font("Montserrat", 14));

                // Item Price
                double libraryPrice = library.getSellPrice();
                NumberFormat formatter = NumberFormat.getInstance();
                formatter.setGroupingUsed(true);
                String libraryPriceBill = "Rp" + formatter.format(libraryPrice);
                Label libraryPriceBills = new Label(libraryPriceBill);
                libraryPriceBills.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
                libraryPriceBills.setTextAlignment(TextAlignment.CENTER);

                // Create HLine 4
                HBox hLine4 = new HBox();
                hLine4.setPrefHeight(4);
                hLine4.setStyle("-fx-background-color: #C8DFE8");
                hLine4.prefWidthProperty().bind(this.leftVBox.widthProperty().subtract(60));

                // Add to body libraryDisplay
                libraryDisplay.getChildren().addAll(libraryImage, libraryName, libraryPriceBills);
                libraryDisplay.setAlignment(Pos.CENTER_LEFT);
                HBox.setMargin(libraryImage, new Insets(0,0,0,20));
                HBox.setMargin(libraryName, new Insets(0,0,0,20));
                HBox.setMargin(libraryPriceBills, new Insets(0,0,0,320));

                // Add onclick event
                libraryDisplay.setOnMouseClicked(event -> {
                    setRegCust();
                    CashierDetailPage detailCashierContent = new CashierDetailPage(this.stage, tab, library, this.purchasedItems, this.items, tabPane, this.customers, this.mode, this.isUsePoint, this.regisCust, this.settings, this.settingsDS, report);
                    boolean found = false;
                    for(Plugin plugin : this.getSettings().getPluginManager().getPlugins()){
                        if(plugin instanceof CashierDetailDecorator){
                            Class<?> cashierDetailDecoratorClass = plugin.getClass();
                            Constructor<?> constructor = null;
                            try {
                                constructor = cashierDetailDecoratorClass.getDeclaredConstructor();
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            }
                            CashierDetailDecorator cashierDetailDecorated = null;
                            try {
                                assert constructor != null;
                                cashierDetailDecorated = (CashierDetailDecorator) constructor.newInstance();
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                            cashierDetailDecorated.setPage(detailCashierContent);
                            cashierDetailDecorated.getPage().setStage(stage);
                            cashierDetailDecorated.getPage().setSettings(settings);
                            cashierDetailDecorated.getPage().setSettingsDS(settingsDS);
                            cashierDetailDecorated.execute();
                            tab.setContent(cashierDetailDecorated.getPage());
                            found = true;
                        }
                    }
                    if(!found){
                        tab.setContent(detailCashierContent);
                    }
                });

                // Add childrens to bodyLibraryVBox
                this.bodyLibraryVBox.getChildren().addAll(hLine4, libraryDisplay);

                // Styling bodyLibraryVBox
                this.bodyLibraryVBox.setSpacing(5);
                this.bodyLibraryVBox.setAlignment(Pos.CENTER);
            }
        }

        // Create scrollpane (libraryBox)
        ScrollPane scrollPane = new ScrollPane(this.bodyLibraryVBox);

        // Styling scrollPane
        scrollPane.setMinHeight(350);
        scrollPane.prefWidthProperty().bind(this.leftVBox.widthProperty().subtract(60));
        scrollPane.setStyle("-fx-background: white;-fx-background-color: white;-fx-text-fill: #C8DFE8;");

        // Add childrens to libraryBox
        this.libraryBox.getChildren().addAll(this.headLibraryBox, scrollPane);

        // Styling libraryBox
        this.libraryBox.setSpacing(10);
        this.libraryBox.setPadding(new Insets(10,10,10,10));
        
        // Add childrens to libraryMainBox
        libraryMainBox.getChildren().addAll(searchBox, this.libraryBox);

        // Styling libraryMainBox
        VBox.setMargin(searchBox, new Insets(10,10,0,10));
        VBox.setMargin(this.libraryBox, new Insets(0, 10, 10, 10));
        libraryMainBox.setSpacing(20);

        // set backButton functionality (Reset libraryBox)
        this.backButton.setOnAction(event -> {

            // Reset countTemp to 0
            int countTemp = 0;

            // Reset checkBox to false
            this.checkBox1.setSelected(false);
            this.checkBox2.setSelected(false);

            // Reset choiceCategoriesBox to not chosen
            this.choiceCategoriesBox.setValue("Not Chosen");

            // Reset spinners
            this.spinner.getValueFactory().setValue(Double.valueOf(initVal1));
            this.spinner2.getValueFactory().setValue(Double.valueOf(initVal2));

            // Reset sliders
            slider1.setValue(this.spinner.getValue());
            slider2.setValue(this.spinner2.getValue());

            // Reset toogle group to radioButton2
            this.group.selectToggle(radioButton2);

            // Reset searchBar text to ""
            searchBar.setText("");

            // Reset containerDropDownDetails2
            containerDropDownDetails2.getChildren().remove(priceSliderTitle);
            containerDropDownDetails2.getChildren().remove(priceBox);
            containerDropDownDetails2.getChildren().remove(priceSliderBox);
            containerDropDownDetails2.getChildren().remove(dropDownCategoriesTitle);
            containerDropDownDetails2.getChildren().remove(dropDownCategoriesBox);

            // Reset totalItem
            this.totalItem = this.items.getBox().size();
            this.totalItemString = String.valueOf(this.totalItem) + " Items";
            this.totalItemLabel.setText(this.totalItemString);
            this.totalItemLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 10));
            this.totalItemLabel.setStyle("-fx-text-fill: #C8DFE8;");

            // Clear bodyLibraryVBox children
            this.bodyLibraryVBox.getChildren().clear();

            // Reset libraryDisplay
            // for loop item
            for (Item library : items.getBox()) {
                
                if(library.getStock()>0){

                    // HBox Display Item
                    HBox libraryDisplay = new HBox();
                    libraryDisplay.prefWidthProperty().bind(this.leftVBox.widthProperty().subtract(60));
                    libraryDisplay.setPrefHeight(40);

                    // Image View Item
                    ImageView libraryImage = new ImageView(library.getImage());

                    // Styling Item Image
                    libraryImage.setPreserveRatio(true);
                    libraryImage.setSmooth(true);
                    libraryImage.setCache(true);
                    libraryImage.setFitWidth(40);
                    libraryImage.setFitHeight(40);

                    // Item Name
                    Label libraryName = new Label(library.getName());

                    // Styling Item Name
                    libraryName.setFont(Font.font("Montserrat", 14));

                    // Item Price
                    double libraryPrice = library.getSellPrice();
                    formatter = NumberFormat.getInstance();
                    formatter.setGroupingUsed(true);
                    String libraryPriceBill = "Rp" + formatter.format(libraryPrice);
                    Label libraryPriceBills = new Label(libraryPriceBill);
                    libraryPriceBills.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
                    libraryPriceBills.setTextAlignment(TextAlignment.CENTER);

                    // Create HLine 4
                    HBox hLine4 = new HBox();
                    hLine4.setPrefHeight(4);
                    hLine4.setStyle("-fx-background-color: #C8DFE8");
                    hLine4.prefWidthProperty().bind(this.leftVBox.widthProperty().subtract(60));

                    // Add to body library box
                    libraryDisplay.getChildren().addAll(libraryImage, libraryName, libraryPriceBills);
                    libraryDisplay.setAlignment(Pos.CENTER_LEFT);
                    HBox.setMargin(libraryImage, new Insets(0,0,0,20));
                    HBox.setMargin(libraryName, new Insets(0,0,0,20));
                    HBox.setMargin(libraryPriceBills, new Insets(0,0,0,320));

                    // Add onclick event
                    clickItem(libraryDisplay, library);

                    // Add childrens to bodyLibraryVBox
                    this.bodyLibraryVBox.getChildren().addAll(hLine4, libraryDisplay);
                    this.bodyLibraryVBox.setSpacing(5);
                    this.bodyLibraryVBox.setAlignment(Pos.CENTER);
                    countTemp++;
                }
            }

            // Reset totalItemLabel
            this.totalItem = countTemp;
            this.totalItemString = String.valueOf(this.totalItem) + " Items";
            this.totalItemLabel = new Label(this.totalItemString);
            this.totalItemLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 10));
            this.totalItemLabel.setStyle("-fx-text-fill: #C8DFE8;");

            // Reset ScrollPane
            ScrollPane scrollPane4 = new ScrollPane(this.bodyLibraryVBox);
            scrollPane4.setMinHeight(350);
            scrollPane4.prefWidthProperty().bind(this.leftVBox.widthProperty().subtract(60));
            scrollPane4.setStyle("-fx-background: white;-fx-background-color: white;-fx-text-fill: #C8DFE8;");

            // Reset childrens
            this.headLibraryTitleBox.getChildren().clear();
            this.headLibraryBox.getChildren().clear();
            this.libraryBox.getChildren().clear();

            // Add childrens to library box
            this.headLibraryTitleBox.getChildren().addAll(this.allItem, this.totalItemLabel);
            this.headLibraryBox.getChildren().addAll(this.backButton, this.headLibraryTitleBox);
            this.libraryBox.getChildren().addAll(this.headLibraryBox, scrollPane4);
        });

        // Add searchBar functionality
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {

            // Reset childrens
            this.headLibraryTitleBox.getChildren().clear();
            this.headLibraryBox.getChildren().clear();
            this.bodyLibraryVBox.getChildren().clear();
            this.libraryBox.getChildren().clear();

            // Set searchedText with newValue
            this.searchedText = newValue;

            // Search
            searchItem();
        });

        // Add checkBox1 functionality
        this.checkBox1.selectedProperty().addListener((observable, oldValue, newValue) -> {

            // Reset childrens
            this.headLibraryTitleBox.getChildren().clear();
            this.headLibraryBox.getChildren().clear();
            this.bodyLibraryVBox.getChildren().clear();
            this.libraryBox.getChildren().clear();
            
            // Update searchedText
            this.searchedText = searchBar.getText();

            // if checked
            if (newValue) {

                // Add childrens to containerDropDownDetails2
                containerDropDownDetails2.getChildren().add(0,priceSliderTitle);
                containerDropDownDetails2.getChildren().add(1,priceBox);
                containerDropDownDetails2.getChildren().add(2,priceSliderBox);

                // Styling containerDropDownDetails2
                VBox.setMargin(priceBox, new Insets(5,0,0,0));
                VBox.setMargin(priceSliderBox, new Insets(5,0,10,0));

                // Search
                searchItem();

            } else { // if not checked

                // Remove childrens from containerDropDownDetails2
                containerDropDownDetails2.getChildren().remove(priceSliderTitle);
                containerDropDownDetails2.getChildren().remove(priceBox);
                containerDropDownDetails2.getChildren().remove(priceSliderBox);

                // Reset sliders value
                slider1.setValue(initVal1);
                slider2.setValue(initVal2);

                // Search
                searchItem();
            }
        });

        // Add checkBox1 functionality
        this.checkBox2.selectedProperty().addListener((observable, oldValue, newValue) -> {

            // if checked
            if (newValue) {

                // Add childrens to containerDropDownDetails2
                containerDropDownDetails2.getChildren().addAll(dropDownCategoriesTitle, dropDownCategoriesBox);

                // Styling containerDropDownDetails2
                VBox.setMargin(dropDownCategoriesBox, new Insets(5,0,0,0));

            } else { // if not checked

                // Remove childrens from containerDropDownDetails2
                containerDropDownDetails2.getChildren().remove(dropDownCategoriesTitle);
                containerDropDownDetails2.getChildren().remove(dropDownCategoriesBox);

                // Reset choiceCategoriesBox
                this.choiceCategoriesBox.setValue("Not Chosen");
            }
        });

        // Add slider1 functionality
        slider1.valueProperty().addListener((observable, oldValue, newValue) -> {

            // Reset childrens
            this.headLibraryTitleBox.getChildren().clear();
            this.headLibraryBox.getChildren().clear();
            this.bodyLibraryVBox.getChildren().clear();
            this.libraryBox.getChildren().clear();

            // Update searchedText
            this.searchedText = searchBar.getText();

            // Update spinner value
            this.spinner.getValueFactory().setValue(Double.valueOf(Math.round((newValue.doubleValue() * 100.0) / 100.0)));

            // Minimum & maximum validation
            if(slider2.getValue() < slider1.getValue()){

                // Set slider2 value to slider1 value + 1
                slider2.setValue(slider1.getValue() + 1);

                // Set spinner2 value with slider2 value
                this.spinner2.getValueFactory().setValue(slider2.getValue());
            }

            // Search
            searchItem();

        });

        // Add slider2 functionality
        slider2.valueProperty().addListener((observable, oldValue, newValue) -> {

            // Reset childrens
            this.headLibraryTitleBox.getChildren().clear();
            this.headLibraryBox.getChildren().clear();
            this.bodyLibraryVBox.getChildren().clear();
            this.libraryBox.getChildren().clear();

            // Update searchedText
            this.searchedText = searchBar.getText();

            // Update2 spinner value
            this.spinner2.getValueFactory().setValue(Double.valueOf(Math.round((newValue.doubleValue() * 100.0) / 100.0)));

            // Minimum & maximum validation
            if(slider1.getValue() > slider2.getValue()){

                // Set slider1 value to slider2 value - 1
                slider1.setValue(slider2.getValue() - 1);

                // Set spinner value with slider1 value
                this.spinner.getValueFactory().setValue(slider1.getValue());
            }

            // Search
            searchItem();

        });

        // Add spinner functionality
        this.spinner.valueProperty().addListener((obs, oldValue, newValue) -> {

            // Reset childrens
            this.headLibraryTitleBox.getChildren().clear();
            this.headLibraryBox.getChildren().clear();
            this.bodyLibraryVBox.getChildren().clear();
            this.libraryBox.getChildren().clear();

            // Update searchedText
            this.searchedText = searchBar.getText();

            // Set slider1 value with spinner value
            slider1.setValue(this.spinner.getValue());

            // Search
            searchItem();

        });

        // Add spinner2 functionality
        this.spinner2.valueProperty().addListener((obs, oldValue, newValue) -> {

            // Reset childrens
            this.headLibraryTitleBox.getChildren().clear();
            this.headLibraryBox.getChildren().clear();
            this.bodyLibraryVBox.getChildren().clear();
            this.libraryBox.getChildren().clear();

            // Update searchedText
            this.searchedText = searchBar.getText();

            // Set slider2 value with spinner2 value
            slider2.setValue(this.spinner2.getValue());

            // Search
            searchItem();

        });

        // Add choiceCategoriesBox functionality
        this.choiceCategoriesBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            // Reset childrens
            this.headLibraryTitleBox.getChildren().clear();
            this.headLibraryBox.getChildren().clear();
            this.bodyLibraryVBox.getChildren().clear();
            this.libraryBox.getChildren().clear();

            // Update searchedText
            this.searchedText = searchBar.getText();

            // Search
            searchItem();

        });

        // Add radioButton1 functionality
        radioButton1.setOnAction(event -> {

            // if radioButton1 is selected
            if (radioButton1.isSelected()) {

                // Reset childrens
                this.headLibraryTitleBox.getChildren().clear();
                this.headLibraryBox.getChildren().clear();
                this.bodyLibraryVBox.getChildren().clear();
                this.libraryBox.getChildren().clear();

                // Update searchedText
                this.searchedText = searchBar.getText();

                // Search
                searchItem();

            }
        });

        // Add radioButton2 functionality
        radioButton2.setOnAction(event -> {

            // if radioButton2 is selected
            if (radioButton2.isSelected()) {

                // Reset childrens
                this.headLibraryTitleBox.getChildren().clear();
                this.headLibraryBox.getChildren().clear();
                this.bodyLibraryVBox.getChildren().clear();
                this.libraryBox.getChildren().clear();

                // Update searchedText
                this.searchedText = searchBar.getText();

                // Search
                searchItem();

            }
        });

        // Create tab1Content (tab1)
        StackPane tab1Content = new StackPane();

        // Add gridHBox to tab1Content
        tab1Content.getChildren().add(gridHBox);

        // Styling tab1Content
        tab1Content.setPadding(new Insets(10,10, 10, 10));
        tab1Content.setStyle("-fx-background-color: #F3F9FB;");

        // Add content to tab1
        tab1.setContent(tab1Content);

        // Create tab1Content (tab2)
        StackPane tab2Content = new StackPane();

        // Add libraryMainBox to tab2Content
        tab2Content.getChildren().addAll(libraryMainBox);

        // Styling tab2Content
        tab2Content.setStyle("-fx-background-color: white;");

        // Add content to tab2
        tab2.setContent(tab2Content);

        // Set tab1 & tab2 to be non-closeable
        tab1.setClosable(false);
        tab2.setClosable(false);

        // Add tab1 functionality
        tab1.setOnSelectionChanged(event -> {

            // if tab1 is selected
            if (tab1.isSelected()) {

                // Styling tab1 & tab2
                tab1.setStyle("-fx-background-color: #3B919B; -fx-text-fill: white;-fx-background-radius: 10;-fx-focus-color: transparent;");
                tab2.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: white;-fx-background-radius: 10;-fx-focus-color: transparent;");
            }
        });

        // Add tab2 functionality
        tab2.setOnSelectionChanged(event -> {

            // if tab2 is selected
            if (tab2.isSelected()) {

                // Styling tab1 & tab2
                tab2.setStyle("-fx-background-color: #3B919B; -fx-text-fill: white;-fx-background-radius: 10;-fx-focus-color: transparent;");
                tab1.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: white;-fx-background-radius: 10;-fx-focus-color: transparent;");
            }
        });

        // Add tab1 & tab2 to the TabPane
        tabPaneCashier.getTabs().addAll(tab1, tab2);

        // Add tabPaneCashier to leftVBox
        this.leftVBox.getChildren().addAll(tabPaneCashier);


        // RightVBox

        // Create HBox addCustBox (rightVBox)
        HBox addCustBox = new HBox();

        // Styling addCustBox
        addCustBox.prefWidthProperty().bind(rightVBox.widthProperty().subtract(10));
        addCustBox.setPadding(new Insets(5, 0, 0, 0));

        // Create vLine 1 (addCustBox)
        VBox vLine1 = new VBox();

        // Styling vLine1
        vLine1.setPrefWidth(4);
        vLine1.setStyle("-fx-background-color: #F3F9FB");
        vLine1.setOpacity(0.47);

        // Create billIcon (addCustBox)
        ImageView billIcon = new ImageView("/images/icon/billingList.png");
        billIcon.fitHeightProperty().bind(vLine1.heightProperty());
        
        // Create addCustomerButton (addCustBox)
        Button addCustomerButton = new Button("+ Add Customer");

        // Styling addCustomerButton
        addCustomerButton.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        addCustomerButton.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: #3B919B;");
        addCustomerButton.setAlignment(Pos.CENTER);
        addCustomerButton.prefWidthProperty().bind(addCustBox.widthProperty().subtract(billIcon.getFitWidth()).subtract(165));
        addCustomerButton.prefHeightProperty().bind(vLine1.heightProperty());

        // Add addCustomerButton functionality
        addCustomerButton.setOnAction(event -> {

            // Create newTab (tabPane)
            Tab newTab = new Tab("Cashier");

            // Styling newTab
            newTab.setStyle("-fx-background-color: #F3F9FB;");

            // Create cashierContent (newTab)
            CashierPage cashierContent = new CashierPage(this.stage, newTab, this.items, tabPane, this.customers, 0, new Inventory<PurchasedItem>(), false, null, settings, settingsDS, report);
            boolean found = false;
            for(Plugin plugin : this.getSettings().getPluginManager().getPlugins()){
                if(plugin instanceof CashierDecorator){
                    Class<?> cashierDecoratorClass = plugin.getClass();
                    Constructor<?> constructor = null;
                    try {
                        constructor = cashierDecoratorClass.getDeclaredConstructor();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    CashierDecorator cashierDecorated = null;
                    try {
                        assert constructor != null;
                        cashierDecorated = (CashierDecorator) constructor.newInstance();
                    } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    assert cashierDecorated != null;
                    cashierDecorated.setPage(cashierContent);
                    cashierDecorated.getPage().setStage(stage);
                    cashierDecorated.getPage().setSettings(settings);
                    cashierDecorated.getPage().setSettingsDS(settingsDS);
                    cashierDecorated.execute();
                    newTab.setContent(cashierDecorated.getPage());
                    found = true;
                }
            }
            if(!found){
                newTab.setContent(cashierContent);
            }

            // Add newTab to tabPane
            tabPane.getTabs().add(newTab);

            // Move page
            tabPane.getSelectionModel().select(newTab);
        });

        // Add childrens to addCustBox
        addCustBox.getChildren().addAll(billIcon,vLine1,addCustomerButton);

        // Create VBox memberItemsBox (rightVBox)
        VBox memberItemsBox = new VBox();

        // Styling memberItemsBox
        memberItemsBox.prefWidthProperty().bind(rightVBox.widthProperty().subtract(10));
        memberItemsBox.setPrefHeight(400);
        memberItemsBox.setStyle("-fx-background-color: white;");

        // Create HBox memberBox (memberItemsBox)
        HBox memberBox = new HBox();

        // Styling memberBox
        memberBox.prefWidthProperty().bind(rightVBox.widthProperty().subtract(10));
        memberBox.setStyle("-fx-background-color: white;");

        // Create dropDownBox (memberBox)
        VBox dropDownBox = new VBox();

        // Create choiceBox (dropDownBox)
        this.choiceBox = new ChoiceBox<>();

        // Create tempID
        this.tempID = new ArrayList<Integer>();

        // Init tempRegisCust
        RegisteredCustomer tempRegisCust = null;

        // Init tempCountMember
        int tempCountMember = 0;
        // for loop customer
        for (Customer customer : this.customers.getBox() ){

            // if customer class is Customer
            if (customer.getClass().getSimpleName().equalsIgnoreCase("Customer")){;}
            else { // if customer class is not Customer

                // Make customer into registeredCustomer
                RegisteredCustomer regisCustomer = (RegisteredCustomer) customer;

                // add regisCustomer to choiceBox
                this.choiceBox.getItems().add(regisCustomer);

                // add customer.id to tempID
                this.tempID.add(customer.getId());

                // Add tempCountMember by 1
                tempCountMember++;

                // Make a dummy Not Member
                if (tempRegisCust == null){

                    // Create tempCust (choiceBox)
                    Customer tempCust = new Member(-1, "Not Member", "000000", null);

                    // Make tempCust registeredCustomer
                    tempRegisCust = (RegisteredCustomer) tempCust;

                    // Set tempRegisCust active status to true
                    tempRegisCust.setActiveStatus(true);
                }
            }
        }

        // Add tempRegisCust to choiceBox
        this.choiceBox.getItems().add(tempRegisCust);
        
        // Create a string converter to display the name of the customer
        StringConverter<RegisteredCustomer> converter = new StringConverter<RegisteredCustomer>() {
            @Override
            public String toString(RegisteredCustomer customer) {

                // return customer name
                return customer.getName();

            }
            @Override
            public RegisteredCustomer fromString(String string) {
                return null;
            }
        };

        // Set converter to choiceBox
        this.choiceBox.setConverter(converter);

        // Check if there is a customer that has been chosen
        if (this.regisCust != null) {

            // set customer index
            this.selectionIndex = getCustIndex();
        }
    
        // Set regisCust into the selected customer
        setRegCust();

        // Set choiceBox value        
        int index = getCustIndex();
        if (this.regisCust != null && index != -1){ // if a customer is selected
            
            // Set choiceBox value to the customer
            this.choiceBox.setValue(this.choiceBox.getItems().get(index));
        } else { // if no customer selected

            // Set choiceBox value to tempRegisCust
            this.choiceBox.setValue(tempRegisCust);
        }

        //styling choiceBox
        this.choiceBox.setStyle("-fx-background-color: transparent;-fx-padding: 0;-fx-background-insets: 0;-fx-border-color: transparent;-fx-border-width: 0;-fx-border-radius: 0;-fx-prompt-text-fill: #3B919B;-fx-font-size: 14;-fx-font-weight: bold;-fx-text-fill: #3B919B;");

        // Add choiceBox to dropDownBox
        dropDownBox.getChildren().add(this.choiceBox);

        // Add dropDownBox to memberBox
        memberBox.getChildren().add(dropDownBox);

        // Styling memberBox
        memberBox.setAlignment(Pos.CENTER);

        // Create hLine 1 (memberItemsBox)
        HBox hLine1 = new HBox();

        // Styling hLine1
        hLine1.setPrefHeight(4);
        hLine1.setStyle("-fx-background-color: #C8DFE8");

        // Create VBox itemsVBox (scrollPane2)
        VBox itemsVBox = new VBox();

        // Styling itemsVBox
        itemsVBox.prefWidthProperty().bind(rightVBox.widthProperty().subtract(10));

        // Display items bill
        int countBill = 0;

        // for loop purchasedItems
        for (PurchasedItem itemBill : this.purchasedItems.getBox()){

            // Create HBox billDisplay (itemsVBox)
            HBox billDisplay  = new HBox();
            billDisplay.prefWidthProperty().bind(rightVBox.widthProperty().subtract(10));

            // Create quantityNameBox
            HBox quantityNameBox = new HBox();
            quantityNameBox.setMinWidth(270);

            // Create quantityItemBills (quantityNameBox)
            int quantityItem = itemBill.getQuantity();
            String quantityItemBill = String.valueOf(quantityItem) + "x";
            Label quantityItemBills = new Label(quantityItemBill);

            // Styling quantityItemBills
            quantityItemBills.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
            quantityItemBills.setStyle("-fx-text-fill: black;");
            quantityItemBills.setTextAlignment(TextAlignment.CENTER);
            quantityItemBills.setMinWidth(80);

            // Create itemNameBill (quantityNameBox)
            Label itemNameBill = new Label(itemBill.getName());

            // Styling itemNameBill
            itemNameBill.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
            itemNameBill.setStyle("-fx-text-fill: black;");
            itemNameBill.setTextAlignment(TextAlignment.CENTER);
            itemNameBill.setMinWidth(170);

            // Create itemPriceBox
            HBox itemPriceBox = new HBox();
            itemPriceBox.setMinWidth(200);

            // Create itemPriceBills (itemPriceBox)
            double itemPrice = itemBill.getSellPrice() * quantityItem;
            NumberFormat formatter = NumberFormat.getInstance();
            formatter.setGroupingUsed(true);
            String itemPriceBill = "Rp" + formatter.format(itemPrice);
            Label itemPriceBills = new Label(itemPriceBill);

            // Styling itemPriceBills
            itemPriceBills.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
            itemPriceBills.setStyle("-fx-text-fill: black;");
            itemPriceBills.setTextAlignment(TextAlignment.CENTER);

            // Add childrens to billDisplay
            quantityNameBox.getChildren().addAll(quantityItemBills, itemNameBill);
            itemPriceBox.getChildren().add(itemPriceBills);
            billDisplay.getChildren().addAll(quantityNameBox, itemPriceBox);

            // Add Styling
            quantityNameBox.setSpacing(20);
            quantityNameBox.setAlignment(Pos.CENTER_LEFT);
            itemPriceBox.setAlignment(Pos.CENTER_RIGHT);
            billDisplay.setSpacing(50);
            
            // Add billDisplay to itemsVBox
            itemsVBox.getChildren().add(billDisplay);

            // Add countBill by 1
            countBill++;

            // Add totalPrice with itemPrice
            this.totalPrice += itemPrice;

        }

        // if there is any purchasedItem
        if(countBill == 0){

            // Create noItemBill (itemsVBox)
            Label noItemBill = new Label("No Items");
            noItemBill.setFont(Font.font("Montserrat", FontWeight.BOLD, 12));
            itemsVBox.getChildren().add(noItemBill);
            itemsVBox.setAlignment(Pos.CENTER);

        }

        // Styling itemsVBox
        itemsVBox.setPadding(new Insets(20, 20, 0, 20));
        itemsVBox.setSpacing(20);
        itemsVBox.setStyle("-fx-background-color: white;-fx-text-fill: #C8DFE8;");

        // Create scrollpane2 (memberItemsBox)
        ScrollPane scrollPane2 = new ScrollPane(itemsVBox);

        // Styling scrollPane2
        scrollPane2.setPrefHeight(350);
        scrollPane2.prefWidthProperty().bind(rightVBox.widthProperty().subtract(40));
        scrollPane2.setStyle("-fx-background: white;-fx-background-color: white;-fx-text-fill: #C8DFE8;");
        
        // Add childrens to memberItems box
        if(tempCountMember > 0){
            memberItemsBox.getChildren().addAll(memberBox, hLine1, scrollPane2);
        }
        else {
            memberItemsBox.getChildren().add(scrollPane2);
        }

        // Create HBox billButton (rightVBox)
        HBox  billButton = new HBox();

        // Styling billButton
        billButton.prefWidthProperty().bind(rightVBox.widthProperty().subtract(10));
        billButton.setPadding(new Insets(5, 0, 0, 0));
        billButton.setPrefHeight(30);

        // Create saveButton (billButton)
        Button saveButton = new Button("Save Bill");

        // Styling saveButton
        saveButton.setPrefWidth(280);
        saveButton.setPrefHeight(26);
        saveButton.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        saveButton.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: #3B919B;");
        saveButton.setAlignment(Pos.CENTER);

        if (isPurchasedItemsEmpty()){
            saveButton.setStyle("-fx-background-color: #769295; -fx-text-fill: #F3F9FB;");
            saveButton.setDisable(true);
        }

        // Create vLine2 (billButton)
        VBox vLine2 = new VBox();

        // Styling vLine2
        vLine2.setPrefWidth(4);
        vLine2.setStyle("-fx-background-color: #F3F9FB");
        vLine2.setOpacity(0.47);

        // Create usePointButton (billButton)
        usePointButton = new ToggleButton("Use Point");

        // Styling usePointButton
        usePointButton.setPrefWidth(280);
        usePointButton.setPrefHeight(26);
        usePointButton.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        usePointButton.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: #3B919B;");
        usePointButton.setAlignment(Pos.CENTER);

        // Add childrens to billButton
        billButton.getChildren().addAll(usePointButton, vLine2, saveButton);

        // Create hLine2 (rightVBox)
        HBox hLine2 = new HBox();

        // Styling hLine2
        hLine2.setPrefHeight(4);
        hLine2.setStyle("-fx-background-color: #F3F9FB");
        hLine2.setOpacity(0.47);

        // Create HBox totalPriceBox (rightVBox)
        totalPriceBox = new HBox();
        totalPriceBox.setPrefHeight(50);

        // Create Text Discount
        if(this.selectionIndex < this.tempID.size() && this.selectionIndex != -1){ // if a customer is selected

            // Set regisCust into the selected customer
            setRegCust();

            // Set finalTotalPrice with calculateDiscount
            this.finalTotalPrice = this.regisCust.calculateDiscount(this.totalPrice, usePointButton.isSelected());

        } else { // if no customer selected

            // Set finalTotalPrice with totalPrice
            this.finalTotalPrice = this.totalPrice;
            
        }

        // Create Text Total price
        formatter = NumberFormat.getInstance();
        formatter.setGroupingUsed(true);
        totalPriceBill = formatter.format(this.totalPrice);
        fixTotalPrice = formatter.format(this.finalTotalPrice);
        fixTotalPriceBill = new Label("Rp" + fixTotalPrice);
        if(this.finalTotalPrice == this.totalPrice){
            totalPriceBills = new Label("Charge Rp" + totalPriceBill);

            // Add totalPriceBills to totalPriceBox
            totalPriceBox.getChildren().add(totalPriceBills);

        } else { // if customer get discount

            // Create totalPriceBills (totalPriceBox)
            totalPriceBillLabel = new Text("Rp" + totalPriceBill);
            totalPriceBills = new Label("Charge");

            // Add childrens to totalPriceBox
            totalPriceBox.getChildren().addAll(totalPriceBills, totalPriceBillLabel, fixTotalPriceBill);

            // Add Styling
            HBox.setMargin(fixTotalPriceBill, new Insets(0,0,0,10));
            totalPriceBox.setSpacing(10);
            totalPriceBillLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
            totalPriceBillLabel.setTextAlignment(TextAlignment.CENTER);
            totalPriceBillLabel.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: black;-fx-strikethrough-width: 2px;");
            totalPriceBillLabel.setStrikethrough(true);
        }


        // Styling totalPriceBills
        totalPriceBills.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
        totalPriceBills.setTextAlignment(TextAlignment.CENTER);
        totalPriceBills.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: black;");

        // Styling fixTotalPriceBill
        fixTotalPriceBill.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
        fixTotalPriceBill.setTextAlignment(TextAlignment.CENTER);
        fixTotalPriceBill.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: black;");

        // Styling totalPriceBox
        totalPriceBox.prefWidthProperty().bind(rightVBox.widthProperty().subtract(10));
        totalPriceBox.setPadding(new Insets(5, 10, 0, 10));
        totalPriceBox.setAlignment(Pos.CENTER);
        totalPriceBox.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: #3B919B;");

        // Add choiceBox functionality
        this.choiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            choiceBoxHandler(newValue);
        });

        // Add saveButton functionality
        saveButton.setOnAction(event -> {
            // Create alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            // Styling alert
            alert.setTitle("Successful");
            alert.setHeaderText("Save bill was successful!");
            alert.setContentText("Do you want to print the bill?");

            // Create Yes & No Button
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType yesButton = new ButtonType("Yes");

            // Add Yes & No button to alert
            alert.getButtonTypes().setAll(noButton, yesButton);

            // Create result based on the alert result
            Optional<ButtonType> result = alert.showAndWait();

            // Create timenow (newBill)
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMMM YYYY\nHH:mm:ss");
            final String timenow = sdf.format(new Date());
            
            // Create custID
            int custID = -1;

            // Init newBill
            Bill newBill;

            // Create formattedDiscount (newBill)
            double discount = (this.totalPrice - this.finalTotalPrice) / this.totalPrice;
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.DOWN);
            formattedDiscount = Double.parseDouble(df.format(discount));

            // if no customer selected
            if(this.selectionIndex >= this.tempID.size() || this.selectionIndex == -1){

                // Create newBill (newCust)
                newBill = new Bill(timenow, this.totalPrice, formattedDiscount);

                // Create newCust (customers)
                Customer newCust = new Customer(newBill);

                // Add newCust to customers
                this.customers.addElement(newCust);

            } else  { // if a customer is selected
                // for loop customers
                for (Customer cust : this.customers.getBox()) {
                    if(this.tempID.get(this.selectionIndex) == cust.getId()){
                        // Update regisCust
                        this.regisCust = (RegisteredCustomer) cust;
                    }
                }

                // Update custID
                custID = this.tempID.get(this.selectionIndex);

                // Update customer point
                this.regisCust.calculatePoint(this.finalTotalPrice);

                // Create newBill to the customer
                newBill = new Bill (timenow, custID, this.totalPrice, formattedDiscount);
            }

            // for loop purchasedItems
            for(PurchasedItem purchItem : this.purchasedItems.getBox()){
                // for loop items
                for (Item item : this.items.getBox()){
                    if (item.getItemID() == purchItem.getItemID()){
                        // Update item stock
                        item.setStock(item.getStock() - purchItem.getQuantity());
                    }
                }

                // Add purchItem to newBill
                newBill.getItems().addElement(purchItem);
            }

            if(this.regisCust != null){
                // Add newBill to transactions
                this.regisCust.getTransaction().addElement(newBill);
            }

            // Update report
            report.updateReport(newBill);

            if (result.isPresent() && result.get() == yesButton) { // if result is yesButton, printBill then close alert
                // Print bill
                try {
                    newBill.printBill();
                    alert.close();
                } catch (DocumentException | FileNotFoundException | IndexOutOfBoundsException e){
                    // Do Nothing
                }

            } else { // if result is noButton, close alert
                // Close alert
                alert.close();
            }

            // Reset purchasedItems
            this.purchasedItems.getBox().clear();
            
            // Set regisCust into the selected customer
            setRegCust();

            // Reset usePointButton
            if (isUsePoint){
                usePointButton.fire();
            }

            // Save data
            Inventory<SalesReport> tempReport = new Inventory<>();
            tempReport.addElement(report);
            this.reportDS.saveData("report", this.settings, new Class<?>[]{Inventory.class, SalesReport.class, PurchasedItem.class}, tempReport);
            this.customerDS.saveData("customer", this.settings, new Class<?>[]{Inventory.class, Customer.class, RegisteredCustomer.class, Member.class, VIP.class, Bill.class, PurchasedItem.class}, customers);
            this.itemDS.saveData("item", this.settings, new Class<?>[]{Inventory.class, Item.class}, items);

            // Create cashierContent (tab)
            CashierPage cashierContent = new CashierPage(this.stage, tab, this.items, tabPane, this.customers, 0, new Inventory<PurchasedItem>(), false, null, settings, settingsDS, report);
            boolean found = false;
            for(Plugin plugin : this.getSettings().getPluginManager().getPlugins()){
                if(plugin instanceof CashierDecorator){
                    Class<?> cashierDecoratorClass = plugin.getClass();
                    Constructor<?> constructor = null;
                    try {
                        constructor = cashierDecoratorClass.getDeclaredConstructor();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    CashierDecorator cashierDecorated = null;
                    try {
                        assert constructor != null;
                        cashierDecorated = (CashierDecorator) constructor.newInstance();
                    } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    assert cashierDecorated != null;
                    cashierDecorated.setPage(cashierContent);
                    cashierDecorated.getPage().setStage(stage);
                    cashierDecorated.getPage().setSettings(settings);
                    cashierDecorated.getPage().setSettingsDS(settingsDS);
                    cashierDecorated.execute();
                    tab.setContent(cashierDecorated.getPage());
                    found = true;
                }
            }
            if(!found){
                tab.setContent(cashierContent);
            }
        });

        // Add usePointButton functionality
        usePointButton.setOnAction(event -> {
            usePointHandler();
        });

        // Reset usePointButton
        if (isUsePoint){
            usePointButton.fire();
        }

        // Add right contents to rightVBox
        rightVBox.getChildren().addAll(addCustBox, memberItemsBox, billButton, hLine2, totalPriceBox);

        // add left & right VBox to mainHBox
        mainHBox.getChildren().addAll(this.leftVBox,rightVBox);

        // add header & mainHBox to mainVBox
        mainVBox.getChildren().addAll(hBox,mainHBox);

        // Styling Layout leftVBox
        this.leftVBox.setSpacing(20);
        this.leftVBox.setStyle("-fx-background-color: #F3F9FB;");

        // Styling Layout rightVBox
        rightVBox.setPadding(new Insets(0, 2, 0, 2));
        rightVBox.setStyle("-fx-background-color: #C8DFE8;-fx-background-radius: 10px;");

        // Styling Layout mainHBox
        mainHBox.setSpacing(40);
        mainHBox.setStyle("-fx-background-color: #F3F9FB;");

        // Styling Layout mainVBox
        mainVBox.setSpacing(20);
        mainVBox.setStyle("-fx-background-color: #F3F9FB;");

        // Add contents
        getChildren().add(mainVBox);

        // Styling Layout
        setPadding(new Insets(20, 30, 20, 60));
        setSpacing(20);
        setStyle("-fx-background-color: #F3F9FB;");

    }

    // Function for clicking item
    private void clickItem(HBox itemDisplay, Item item){

        // Add itemDisplay functionality
        itemDisplay.setOnMouseClicked(event -> {

            // Set regisCust into the selected customer
            setRegCust();
            CashierDetailPage detailCashierContent = new CashierDetailPage(this.stage, tab, item, this.purchasedItems, this.items, tabPane, this.customers, this.mode, this.isUsePoint, this.regisCust, this.settings, this.settingsDS, this.report);
            boolean found = false;
            for(Plugin plugin : this.getSettings().getPluginManager().getPlugins()){
                if(plugin instanceof CashierDetailDecorator){
                    Class<?> cashierDetailDecoratorClass = plugin.getClass();
                    Constructor<?> constructor = null;
                    try {
                        constructor = cashierDetailDecoratorClass.getDeclaredConstructor();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    CashierDetailDecorator cashierDetailDecorated = null;
                    try {
                        assert constructor != null;
                        cashierDetailDecorated = (CashierDetailDecorator) constructor.newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    cashierDetailDecorated.setPage(detailCashierContent);
                    cashierDetailDecorated.getPage().setStage(stage);
                    cashierDetailDecorated.getPage().setSettings(settings);
                    cashierDetailDecorated.getPage().setSettingsDS(settingsDS);
                    cashierDetailDecorated.execute();
                    tab.setContent(cashierDetailDecorated.getPage());
                    found = true;
                }
            }
            if(!found){
                tab.setContent(detailCashierContent);
            }
        });
    }

    // Function for searching item based on every filter and searchedText
    private void searchItem(){

        // Init attributes
        this.mode = 0;
        int count = 0;
        boolean isPriceChecked = this.checkBox1.isSelected();
        boolean isCategoriesChecked = this.checkBox2.isSelected();
        Double minPrice = this.spinner.getValue();
        Double maxPrice = this.spinner2.getValue();
        String selectedCategories = this.choiceCategoriesBox.getValue();
        Toggle selectedToggle = this.group.getSelectedToggle();
        RadioButton selectedRadioButton = (RadioButton) selectedToggle;
        String selectedValue = selectedRadioButton.getText();

        // Compare
        Comparator<Item> itemSellPrice = Comparator.comparingDouble(Item::getSellPrice);
        Comparator<Item> itemSellPriceComparator = Comparator.comparingDouble(Item::getSellPrice).thenComparing(Item::getName);
        Comparator<Item> itemSellPriceDescendingComparator = itemSellPrice.reversed().thenComparing(Item::getName);
        Comparator<Item> itemNameComparator = Comparator.comparing(Item::getName);
        Comparator<Item> itemNameDescendingComparator = itemNameComparator.reversed();

        // Set mode based on sort by price checked/no, sort by categories checked/no, and descending/ascending
        // Sort items based on mode
        if (isPriceChecked){
            if(isCategoriesChecked){
                if(selectedValue.equalsIgnoreCase("Descending")){
                    this.mode = 1;

                    // Sort 
                    this.items.getBox().sort(itemSellPriceDescendingComparator);
                }
                else {
                    this.mode = 2;

                    // Sort 
                    this.items.getBox().sort(itemSellPriceComparator);
                }
            }
            else {
                if(selectedValue.equalsIgnoreCase("Descending")){
                    this.mode = 3;

                    // Sort 
                    this.items.getBox().sort(itemSellPriceDescendingComparator);
                }
                else {
                    this.mode = 4;

                    // Sort 
                    this.items.getBox().sort(itemSellPriceComparator);
                }
            }
        }
        else {
            if(isCategoriesChecked){
                if(selectedValue.equalsIgnoreCase("Descending")){
                    this.mode = 5;

                    // Sort 
                    this.items.getBox().sort(itemNameDescendingComparator);
                }
                else {
                    this.mode = 6;

                    // Sort 
                    this.items.getBox().sort(itemNameComparator);
                }
            }
            else {
                if(selectedValue.equalsIgnoreCase("Descending")){
                    this.mode = 7;
                    // Sort 
                    this.items.getBox().sort(itemNameDescendingComparator);
                }
                else {
                    // Sort 
                    this.items.getBox().sort(itemNameComparator);
                }
            }
        }

        // Create totalItemLabel (headLibraryTitleBox)
        this.totalItem = this.items.getBox().size();
        this.totalItemString = String.valueOf(this.totalItem) + " Items";
        this.totalItemLabel = new Label(this.totalItemString);

        // Styling totalItemLabel
        this.totalItemLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 10));
        this.totalItemLabel.setStyle("-fx-text-fill: #C8DFE8;");

        // Reset bodyLibraryVBox
        this.bodyLibraryVBox.getChildren().clear();

        // Update library based on mode
        switch(this.mode){
            case 1:
            case 2:
                
                // for loop item
                for (Item library : this.items.getBox()) {

                    if(library.getSellPrice() >= minPrice && library.getSellPrice() <= maxPrice && library.getCategory().equalsIgnoreCase(selectedCategories) && library.getName().toLowerCase().contains(this.searchedText.toLowerCase()) && library.getStock()>0){

                        // HBox Display Item
                        HBox libraryDisplay = new HBox();
                        libraryDisplay.prefWidthProperty().bind(this.leftVBox.widthProperty().subtract(60));
                        libraryDisplay.setPrefHeight(40);

                        // Image View Item
                        ImageView libraryImage = new ImageView(library.getImage());

                        // Styling Item Image
                        libraryImage.setPreserveRatio(true);
                        libraryImage.setSmooth(true);
                        libraryImage.setCache(true);
                        libraryImage.setFitWidth(40);
                        libraryImage.setFitHeight(40);

                        // Item Name
                        Label libraryName = new Label(library.getName());

                        // Styling Item Name
                        libraryName.setFont(Font.font("Montserrat", 14));

                        // Item Price
                        double libraryPrice = library.getSellPrice();
                        NumberFormat formatter = NumberFormat.getInstance();
                        formatter.setGroupingUsed(true);
                        String libraryPriceBill = "Rp" + formatter.format(libraryPrice);
                        Label libraryPriceBills = new Label(libraryPriceBill);
                        libraryPriceBills.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
                        libraryPriceBills.setTextAlignment(TextAlignment.CENTER);

                        // Create HLine 4
                        HBox hLine4 = new HBox();
                        hLine4.setPrefHeight(4);
                        hLine4.setStyle("-fx-background-color: #C8DFE8");
                        hLine4.prefWidthProperty().bind(this.leftVBox.widthProperty().subtract(60));

                        // Add to body library box
                        libraryDisplay.getChildren().addAll(libraryImage, libraryName, libraryPriceBills);
                        libraryDisplay.setAlignment(Pos.CENTER_LEFT);
                        HBox.setMargin(libraryImage, new Insets(0,0,0,20));
                        HBox.setMargin(libraryName, new Insets(0,0,0,20));
                        HBox.setMargin(libraryPriceBills, new Insets(0,0,0,320));

                        // Add onclick event
                        clickItem(libraryDisplay, library);

                        this.bodyLibraryVBox.getChildren().addAll(hLine4, libraryDisplay);
                        this.bodyLibraryVBox.setSpacing(5);
                        this.bodyLibraryVBox.setAlignment(Pos.CENTER);
                        count++;
                    }
                }
                break;

            case 3:
            case 4:

                // for loop item
                for (Item library : this.items.getBox()) {

                    if(library.getSellPrice() >= minPrice && library.getSellPrice() <= maxPrice && library.getName().toLowerCase().contains(this.searchedText.toLowerCase()) && library.getStock()>0){

                        // HBox Display Item
                        HBox libraryDisplay = new HBox();
                        libraryDisplay.prefWidthProperty().bind(this.leftVBox.widthProperty().subtract(60));
                        libraryDisplay.setPrefHeight(40);

                        // Image View Item
                        ImageView libraryImage = new ImageView(library.getImage());

                        // Styling Item Image
                        libraryImage.setPreserveRatio(true);
                        libraryImage.setSmooth(true);
                        libraryImage.setCache(true);
                        libraryImage.setFitWidth(40);
                        libraryImage.setFitHeight(40);

                        // Item Name
                        Label libraryName = new Label(library.getName());

                        // Styling Item Name
                        libraryName.setFont(Font.font("Montserrat", 14));

                        // Item Price
                        double libraryPrice = library.getSellPrice();
                        NumberFormat formatter = NumberFormat.getInstance();
                        formatter.setGroupingUsed(true);
                        String libraryPriceBill = "Rp" + formatter.format(libraryPrice);
                        Label libraryPriceBills = new Label(libraryPriceBill);
                        libraryPriceBills.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
                        libraryPriceBills.setTextAlignment(TextAlignment.CENTER);

                        // Create HLine 4
                        HBox hLine4 = new HBox();
                        hLine4.setPrefHeight(4);
                        hLine4.setStyle("-fx-background-color: #C8DFE8");
                        hLine4.prefWidthProperty().bind(this.leftVBox.widthProperty().subtract(60));

                        // Add to body library box
                        libraryDisplay.getChildren().addAll(libraryImage, libraryName, libraryPriceBills);
                        libraryDisplay.setAlignment(Pos.CENTER_LEFT);
                        HBox.setMargin(libraryImage, new Insets(0,0,0,20));
                        HBox.setMargin(libraryName, new Insets(0,0,0,20));
                        HBox.setMargin(libraryPriceBills, new Insets(0,0,0,320));

                        // Add onclick event
                        clickItem(libraryDisplay, library);

                        this.bodyLibraryVBox.getChildren().addAll(hLine4, libraryDisplay);
                        this.bodyLibraryVBox.setSpacing(5);
                        this.bodyLibraryVBox.setAlignment(Pos.CENTER);
                        count++;
                    }
                }
                break;

            case 5:
            case 6:

                // for loop item
                for (Item library : this.items.getBox()) {

                    if(library.getCategory().equalsIgnoreCase(selectedCategories) && library.getName().toLowerCase().contains(this.searchedText.toLowerCase()) && library.getStock()>0){

                        // HBox Display Item
                        HBox libraryDisplay = new HBox();
                        libraryDisplay.prefWidthProperty().bind(this.leftVBox.widthProperty().subtract(60));
                        libraryDisplay.setPrefHeight(40);

                        // Image View Item
                        ImageView libraryImage = new ImageView(library.getImage());

                        // Styling Item Image
                        libraryImage.setPreserveRatio(true);
                        libraryImage.setSmooth(true);
                        libraryImage.setCache(true);
                        libraryImage.setFitWidth(40);
                        libraryImage.setFitHeight(40);

                        // Item Name
                        Label libraryName = new Label(library.getName());

                        // Styling Item Name
                        libraryName.setFont(Font.font("Montserrat", 14));

                        // Item Price
                        double libraryPrice = library.getSellPrice();
                        NumberFormat formatter = NumberFormat.getInstance();
                        formatter.setGroupingUsed(true);
                        String libraryPriceBill = "Rp" + formatter.format(libraryPrice);
                        Label libraryPriceBills = new Label(libraryPriceBill);
                        libraryPriceBills.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
                        libraryPriceBills.setTextAlignment(TextAlignment.CENTER);

                        // Create HLine 4
                        HBox hLine4 = new HBox();
                        hLine4.setPrefHeight(4);
                        hLine4.setStyle("-fx-background-color: #C8DFE8");
                        hLine4.prefWidthProperty().bind(this.leftVBox.widthProperty().subtract(60));

                        // Add to body library box
                        libraryDisplay.getChildren().addAll(libraryImage, libraryName, libraryPriceBills);
                        libraryDisplay.setAlignment(Pos.CENTER_LEFT);
                        HBox.setMargin(libraryImage, new Insets(0,0,0,20));
                        HBox.setMargin(libraryName, new Insets(0,0,0,20));
                        HBox.setMargin(libraryPriceBills, new Insets(0,0,0,320));

                        // Add onclick event
                        clickItem(libraryDisplay, library);

                        this.bodyLibraryVBox.getChildren().addAll(hLine4, libraryDisplay);
                        this.bodyLibraryVBox.setSpacing(5);
                        this.bodyLibraryVBox.setAlignment(Pos.CENTER);
                        count++;
                    }
                }
                break;

            case 7:
            default:
                
                // for loop item
                for (Item library : this.items.getBox()) {
                    if(library.getName().toLowerCase().contains(this.searchedText.toLowerCase()) && library.getStock()>0){
                    
                        // HBox Display Item
                        HBox libraryDisplay = new HBox();
                        libraryDisplay.prefWidthProperty().bind(this.leftVBox.widthProperty().subtract(60));
                        libraryDisplay.setPrefHeight(40);

                        // Image View Item
                        ImageView libraryImage = new ImageView(library.getImage());

                        // Styling Item Image
                        libraryImage.setPreserveRatio(true);
                        libraryImage.setSmooth(true);
                        libraryImage.setCache(true);
                        libraryImage.setFitWidth(40);
                        libraryImage.setFitHeight(40);

                        // Item Name
                        Label libraryName = new Label(library.getName());

                        // Styling Item Name
                        libraryName.setFont(Font.font("Montserrat", 14));

                        // Item Price
                        double libraryPrice = library.getSellPrice();
                        NumberFormat formatter = NumberFormat.getInstance();
                        formatter.setGroupingUsed(true);
                        String libraryPriceBill = "Rp" + formatter.format(libraryPrice);
                        Label libraryPriceBills = new Label(libraryPriceBill);
                        libraryPriceBills.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
                        libraryPriceBills.setTextAlignment(TextAlignment.CENTER);

                        // Create HLine 4
                        HBox hLine4 = new HBox();
                        hLine4.setPrefHeight(4);
                        hLine4.setStyle("-fx-background-color: #C8DFE8");
                        hLine4.prefWidthProperty().bind(this.leftVBox.widthProperty().subtract(60));

                        // Add to body library box
                        libraryDisplay.getChildren().addAll(libraryImage, libraryName, libraryPriceBills);
                        libraryDisplay.setAlignment(Pos.CENTER_LEFT);
                        HBox.setMargin(libraryImage, new Insets(0,0,0,20));
                        HBox.setMargin(libraryName, new Insets(0,0,0,20));
                        HBox.setMargin(libraryPriceBills, new Insets(0,0,0,320));

                        // Add onclick event
                        clickItem(libraryDisplay, library);

                        this.bodyLibraryVBox.getChildren().addAll(hLine4, libraryDisplay);
                        this.bodyLibraryVBox.setSpacing(5);
                        this.bodyLibraryVBox.setAlignment(Pos.CENTER);
                        count++;
                    }
                }
                break;
        }

        // Add styling
        this.totalItem = count;
        this.totalItemString = String.valueOf(this.totalItem) + " Items";
        this.totalItemLabel.setText(totalItemString);

        // Create scrollpane3 (libraryBox)
        ScrollPane scrollPane3 = new ScrollPane(this.bodyLibraryVBox);
        scrollPane3.setMinHeight(350);
        scrollPane3.prefWidthProperty().bind(this.leftVBox.widthProperty().subtract(60));
        scrollPane3.setStyle("-fx-background: white;-fx-background-color: white;-fx-text-fill: #C8DFE8;");

        // Reset childrens
        this.headLibraryTitleBox.getChildren().clear();
        this.headLibraryBox.getChildren().clear();
        this.libraryBox.getChildren().clear();

        // Add childrens to library box
        this.headLibraryTitleBox.getChildren().addAll(this.allItem, this.totalItemLabel);
        this.headLibraryBox.getChildren().addAll(this.backButton, this.headLibraryTitleBox);
        this.libraryBox.getChildren().addAll(this.headLibraryBox, scrollPane3);
    }

    // Function to get max price from items
    public double getMaxPrice (){
        double max = 0;
        for (Item itemElement : this.items.getBox()){
            if (itemElement.getSellPrice() > max && itemElement.getStock() > 0){
                max = itemElement.getSellPrice();
            }
        }
        return max;
    }

    // Function to get min price from items
    public double getMinPrice(){
        double min = getMaxPrice();
        for (Item itemElement : this.items.getBox()){
            if (itemElement.getSellPrice() < min && itemElement.getStock() > 0){
                min = itemElement.getSellPrice();
            }
        }
        return min;

    }

    // Function to set regisCust with a customer
    public void setRegCust(){
        if(this.selectionIndex < this.tempID.size() && this.selectionIndex != -1){
            for (Customer cust : this.customers.getBox()) {
                if(this.tempID.get(this.selectionIndex) == cust.getId()){
                    this.regisCust = (RegisteredCustomer) cust;
                }
            }
        } else {
            this.regisCust = null;
        }
    }

    // Function to get customer index
    public int getCustIndex(){
        int count = -1;
        if (this.regisCust != null) {
            count++;
            for (int id : this.tempID) {
                if(this.regisCust.getId() == id){
                    break;
                }
                count++;
            }
        }
        return count;
    }

    // Function to check if purchased items empty or not
    public boolean isPurchasedItemsEmpty(){
        int countPurchItems = 0;
        for (int i = 0; i < purchasedItems.getBox().size(); i++) {
            countPurchItems++;
        }
        if (countPurchItems == 0) {
            return true;
        } else {
            return false;
        }
    }

    // Function to get totalPriceBox
    public HBox getTotalPriceBox() {
        return totalPriceBox;
    }

    public Tab getTab() {
        return tab;
    }

    public Stage getStage() {
        return stage;
    }

    // Function to set stage
    public void setStage(Stage stage){
        this.stage = stage;
    }

    // Function to set settings
    public void setSettings(Settings settings){
        this.settings = settings;
    }

    // Function to set settingsDS
    public void setSettingsDS(DataStore<Settings> settingsDS){
        this.settingsDS = settingsDS;
    }

    // Function to get settings
    public Settings getSettings(){
        return this.settings;
    }

    // Function to get settingDS
    public DataStore<Settings> getSettingsDS(){
        return this.settingsDS;
    }

    public void setTotalPrice(double totalPrice){
        this.totalPrice = totalPrice;
    }

    public double getTotalPrice(){
        return this.totalPrice;
    }

    public void setFinalTotalPrice(double finalTotalPrice){
        this.finalTotalPrice = finalTotalPrice;
    }

    public double getFinalTotalPrice(){
        return this.finalTotalPrice;
    }

    public Label getTotalPriceBills(){
        return this.totalPriceBills;
    }

    public Text getTotalPriceBillLabel(){
        return this.totalPriceBillLabel;
    }

    public Label getFixTotalPriceBill(){
        return this.fixTotalPriceBill;
    }

    public void usePointHandler(){
        if (this.selectionIndex < this.tempID.size() && this.selectionIndex != -1){ // if a customer is selected
            // Reset totalPriceBox
            totalPriceBox.getChildren().clear();

            // Create text total price
            totalPriceBill = formatter.format(this.totalPrice);

            if (usePointButton.isSelected()) { // if usePointButton is selected

                // Set isUsePoint to true
                this.isUsePoint = true;

                // Styling usePointButton
                usePointButton.setStyle("-fx-background-color: #3B919B;-fx-text-fill:#C8DFE8");

                // Create text discount
                // Set regisCust into the selected customer
                setRegCust();

                // Set finalTotalPrice with calculateDiscount
                this.finalTotalPrice = this.regisCust.calculateDiscount(this.totalPrice, usePointButton.isSelected());

            } else {

                // Set isUsePoint to false
                this.isUsePoint = false;

                // Styling usePointButton
                usePointButton.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: #3B919B;");

                // Create text discount
                // Set regisCust into the selected customer
                setRegCust();

                // Set finalTotalPrice with calculateDiscount
                this.finalTotalPrice = this.regisCust.calculateDiscount(this.totalPrice, usePointButton.isSelected());
            }

            fixTotalPrice = formatter.format(this.finalTotalPrice);
            fixTotalPriceBill = new Label("Rp" + fixTotalPrice);

            if(this.finalTotalPrice == this.totalPrice){ // if customer don't get discount

                // Create totalPriceBills2 (totalPriceBox)
                totalPriceBills = new Label("Charge Rp" + totalPriceBill);

                // Add totalPriceBills to totalPriceBox
                totalPriceBox.getChildren().add(totalPriceBills);

            } else { // if customer get discount

                // Create totalPriceBills2 (totalPriceBox)
                totalPriceBillLabel = new Text("Rp" + totalPriceBill);
                totalPriceBills = new Label("Charge");

                // Add childrens to totalPriceBox
                totalPriceBox.getChildren().addAll(totalPriceBills, totalPriceBillLabel, fixTotalPriceBill);

                // Add Styling
                HBox.setMargin(fixTotalPriceBill, new Insets(0,0,0,10));
                totalPriceBox.setSpacing(10);
                totalPriceBillLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
                totalPriceBillLabel.setTextAlignment(TextAlignment.CENTER);
                totalPriceBillLabel.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: black;");
                totalPriceBillLabel.setStrikethrough(true);

            }

            // Styling fixTotalPriceBill
            fixTotalPriceBill.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
            fixTotalPriceBill.setTextAlignment(TextAlignment.CENTER);
            fixTotalPriceBill.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: black;");

            // Styling totalPriceBills
            totalPriceBills.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
            totalPriceBills.setTextAlignment(TextAlignment.CENTER);
            totalPriceBills.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: black;");

        }
    }

    public void choiceBoxHandler(Number newValue){
        // if usePointButton is selected
        if(usePointButton.isSelected()){

            // Click usePointButton & set it to false
            usePointButton.fire();
            this.isUsePoint = false;

        }

        // Update selectionIndex
        this.selectionIndex = (Integer) newValue;

        // Reset totalPriceBox
        totalPriceBox.getChildren().clear();

        // Create text total price
        totalPriceBill = formatter.format(this.totalPrice);

        setRegCust();
        
        if (this.regisCust != null){

            // Set finalTotalPrice with calculateDiscount
            this.finalTotalPrice = this.regisCust.calculateDiscount(this.totalPrice, usePointButton.isSelected());

        } else {
            // Set finalTotalPrice with totalPrice
            this.finalTotalPrice = this.totalPrice;
        }

        fixTotalPrice = formatter.format(this.finalTotalPrice);
        fixTotalPriceBill = new Label("Rp" + fixTotalPrice);

        if(this.finalTotalPrice == this.totalPrice){ // if customer don't get discount

            // Create totalPriceBills2 (totalPriceBox)
            totalPriceBills = new Label("Charge Rp" + totalPriceBill);

            // Add totalPriceBills to totalPriceBox
            totalPriceBox.getChildren().add(totalPriceBills);

        } else { // if customer get discount

            // Create totalPriceBills2 (totalPriceBox)
            totalPriceBillLabel = new Text("Rp" + totalPriceBill);
            totalPriceBills = new Label("Charge");

            // Add childrens to totalPriceBox
            totalPriceBox.getChildren().addAll(totalPriceBills, totalPriceBillLabel, fixTotalPriceBill);

            // Add Styling
            HBox.setMargin(fixTotalPriceBill, new Insets(0,0,0,10));
            totalPriceBox.setSpacing(10);
            totalPriceBillLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
            totalPriceBillLabel.setTextAlignment(TextAlignment.CENTER);
            totalPriceBillLabel.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: black;");
            totalPriceBillLabel.setStrikethrough(true);

        }

        // Styling fixTotalPriceBill
        fixTotalPriceBill.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
        fixTotalPriceBill.setTextAlignment(TextAlignment.CENTER);
        fixTotalPriceBill.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: black;");

        // Styling totalPriceBills
        totalPriceBills.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
        totalPriceBills.setTextAlignment(TextAlignment.CENTER);
        totalPriceBills.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: black;");
    }

    public ToggleButton getUsePointButton() {
        return usePointButton;
    }

    public ChoiceBox<RegisteredCustomer> getChoiceBox() {
        return choiceBox;
    }

    public RegisteredCustomer getRegisCust() {
        return regisCust;
    }
}
