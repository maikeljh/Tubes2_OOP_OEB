package UI;


import java.util.*;

import System.Settings;
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
import System.PurchasedItem;
import System.Inventory;
import System.Item;
import System.Customer;
import System.FixedBill;
import System.RegisteredCustomer;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
// import org.controlsfx.control.RangeSlider;
import javafx.scene.layout.VBox;
import System.FixedBill;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.image.Image;
import java.io.FileNotFoundException;
import com.itextpdf.text.DocumentException;
import java.lang.IndexOutOfBoundsException;
import java.math.RoundingMode;
import java.util.Comparator;
import System.Member;


public class CashierPage extends VBox {
    
    private Inventory<PurchasedItem> purchasedItems;
    private Settings settings;
    private double totalPrice = 0;
    private RegisteredCustomer regisCust = null;
    private double finalTotalPrice = 0;
    private boolean isUsePoint = false;
    private ChoiceBox<RegisteredCustomer> choiceBox;
    private List<Integer> tempID;
    private int selectionIndex;
    private Inventory<Item> items;
    private Inventory<Customer> customers;
    private Inventory<FixedBill> transactions;
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

    private Stage stage;
    private Tab tab;
    private TabPane tabPane;
    private DataStore<Settings> settingsDS;


    public CashierPage(Stage stage, Tab tab, Inventory<Item> items, TabPane tabPane, Inventory<Customer> customers, Integer mode, Inventory<FixedBill> transactions, Inventory<PurchasedItem> purchasedItems, boolean usePoint, RegisteredCustomer registeredCust){
        
        this.stage = stage;
        this.tab = tab;
        this.tabPane = tabPane;
        this.transactions = transactions;
        this.customers = customers;
        this.items = items;
        this.selectionIndex = -1;
        this.regisCust = registeredCust;
        this.isUsePoint = usePoint;
        this.purchasedItems = purchasedItems;
        this.mode = mode;

        // Create main VBox
        VBox mainVBox = new VBox();
        
        // Create main HBox
        HBox mainHBox = new HBox();

        // Create left VBox
        this.leftVBox = new VBox();

        // Create right HBox
        VBox rightVBox = new VBox();

        // Create HBox for header
        HBox hBox = new HBox();

        // Create title
        Label title = new Label("Cashier");
        title.setFont(Font.font("Montserrat", FontWeight.BOLD, 28));
        title.setTextAlignment(TextAlignment.CENTER);


        // Add header to HBox
        hBox.getChildren().addAll(title);

        //Styling left & right box & header & main
        mainVBox.setMaxWidth(1280);
        mainVBox.setMaxHeight(600);
        this.leftVBox.setPrefWidth(620);
        rightVBox.setPrefWidth(620);
        mainHBox.prefWidthProperty().bind(mainVBox.widthProperty());
        hBox.prefWidthProperty().bind(mainVBox.widthProperty());

        // Create a new TabPane
        TabPane tabPaneCashier = new TabPane();
        tabPaneCashier.setStyle("-fx-background-color: #F3F9FB;-fx-padding: 5;-fx-background-radius: 10;-fx-focus-color: transparent;");
        tabPaneCashier.getStyleClass().add("floating");
    
        // Create two tabs
        Tab tab1 = new Tab("Favorites");
        tab1.setStyle("-fx-background-color: #3B919B; -fx-text-fill: white;-fx-background-radius: 10;-fx-focus-color: transparent;");
        Tab tab2 = new Tab("Library");
        tab2.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: white;-fx-background-radius: 10;-fx-focus-color: transparent;");


        // Create HBox for grid
        HBox gridHBox = new HBox();

        //Styling grid box
        gridHBox.setPrefHeight(600);
        gridHBox.setStyle("-fx-background: #F3F9FB; -fx-background-color: #F3F9FB;");
        gridHBox.prefWidthProperty().bind(this.leftVBox.widthProperty());

        // List of Items
        GridPane grid = new GridPane();

        // Styling gridpane
        grid.setStyle("-fx-background-color: #F3F9FB;");
        grid.setHgap(40);
        grid.setVgap(40);
        grid.prefWidthProperty().bind(this.leftVBox.widthProperty());

        // Create item display
        int row = 0;
        int col = 0;
        int count = 0;

        // Display List Of Items
        for (Item item : this.items.getList()) {

            if (count < 9 && item.getStock() > 0){

                // VBox Display Item
                VBox itemDisplay = new VBox();

                // Image View Item
                ImageView itemImage = new ImageView(item.getImage());

                // Styling Item Image
                itemImage.setPreserveRatio(true);
                itemImage.setSmooth(true);
                itemImage.setCache(true);
                itemImage.setFitWidth(134);
                itemImage.setFitHeight(109);

                // Item Name
                Text itemName = new Text(item.getName());

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
                itemDisplay.setOnMouseClicked(event -> {
                    setRegCust();
                    CashierDetailPage detailCashierContent = new CashierDetailPage(this.stage, tab, item, this.purchasedItems, this.items, tabPane, this.customers, this.mode, this.transactions, this.isUsePoint, this.regisCust);
                    tab.setContent(detailCashierContent);
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

        // Display empty list
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

        // Add grid to grid box
        gridHBox.getChildren().add(grid);

        // Create libraryMainBox
        VBox libraryMainBox = new VBox();
        libraryMainBox.setStyle("-fx-background-color: #F3F9FB;");
        libraryMainBox.prefWidthProperty().bind(this.leftVBox.widthProperty());

        // Create a searchbar
        // Create a HBox
        HBox searchBox = new HBox();

        // Create a Textfield
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search. . .");
        searchBar.setPrefWidth(600);
        searchBar.setStyle("-fx-background-color: transparent;-fx-padding: 0;-fx-background-insets: 0;-fx-border-color: transparent;-fx-border-width: 0;-fx-border-radius: 0;-fx-prompt-text-fill: #8CAEBB;-fx-font-size: 14;-fx-font-weight: bold;");


        // Create a filter button
        ImageView filterIcon = new ImageView("/images/icon/filterButton.png");
        filterIcon.setPreserveRatio(true);
        filterIcon.setSmooth(true);
        filterIcon.setCache(true);
        filterIcon.setFitWidth(14);
        filterIcon.setFitHeight(14);

        // Create member dropdown
        MenuButton filterDropDownButton = new MenuButton();
        filterDropDownButton.setGraphic(filterIcon);

        HBox containerDropDown = new HBox();
        VBox containerDropDownDetails = new VBox();
        VBox containerDropDownDetails2 = new VBox();

        Label checkBoxTitle = new Label("Sort By:");
        checkBoxTitle.setAlignment(Pos.CENTER_LEFT);
        
        VBox checkBoxBox = new VBox();
        this.checkBox1 = new CheckBox("Sort by Price");
        this.checkBox2 = new CheckBox("Sort by Categories");
        checkBoxBox.getChildren().addAll(this.checkBox1, this.checkBox2);

        this.group = new ToggleGroup();
        VBox radioButtonBox = new VBox();
        RadioButton radioButton1 = new RadioButton("Descending");
        RadioButton radioButton2 = new RadioButton("Ascending");
        radioButton1.setToggleGroup(this.group);
        radioButton2.setToggleGroup(this.group);
        radioButtonBox.getChildren().addAll(radioButton1, radioButton2);
        this.group.selectToggle(radioButton2);


        Label priceSliderTitle = new Label("Price Range: ");
        priceSliderTitle.setAlignment(Pos.CENTER_LEFT);

        HBox priceBox = new HBox();
        Label minPrice = new Label("Min: ");
        this.spinner = new Spinner<Double>();
        double initVal1 = getMinPrice();
        double initVal2 = getMaxPrice();
        this.spinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, initVal2, initVal1));
        this.spinner.setEditable(true);
        Label maxPrice = new Label("Max: ");
        this.spinner2 = new Spinner<Double>();
        this.spinner2.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1, initVal2+100, initVal2));
        this.spinner2.setEditable(true);

        priceBox.getChildren().addAll(minPrice, this.spinner, maxPrice, this.spinner2);
        priceBox.setSpacing(5);

        VBox priceSliderBox = new VBox();
        Slider slider1 = new Slider(0, initVal2, initVal1);
        priceSliderBox.getChildren().add(slider1);
        Slider slider2 = new Slider(1, initVal2+100, initVal2);
        priceSliderBox.getChildren().add(slider2);

        Label dropDownCategoriesTitle = new Label("Category: ");
        dropDownCategoriesTitle.setAlignment(Pos.CENTER_LEFT);

        // Create member dropdown
        VBox dropDownCategoriesBox = new VBox();
        this.choiceCategoriesBox = new ChoiceBox<>();
        List<String> temp = new ArrayList<String>();
        boolean flag = false;
        for (Item item : this.items.getList() ){
            flag = false;
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

        //styling choice box
        this.choiceCategoriesBox.setStyle("-fx-background-color: transparent;-fx-padding: 0;-fx-background-insets: 0;-fx-border-color: transparent;-fx-border-width: 0;-fx-border-radius: 0;-fx-prompt-text-fill: #3B919B;-fx-font-size: 14;-fx-font-weight: bold;-fx-text-fill: #3B919B;");

        // Add drop down box to member box
        dropDownCategoriesBox.getChildren().add(this.choiceCategoriesBox);

        containerDropDownDetails.getChildren().addAll(checkBoxTitle, checkBoxBox, radioButtonBox);
        containerDropDownDetails.setSpacing(10);
        containerDropDown.getChildren().addAll(containerDropDownDetails, containerDropDownDetails2);
        containerDropDown.setSpacing(20);

        CustomMenuItem menuItem = new CustomMenuItem(containerDropDown,false);
        
        filterDropDownButton.getItems().add(menuItem);

        filterDropDownButton.setStyle("-fx-background-color: transparent;-fx-padding: 0;-fx-background-insets: 0;-fx-border-color: transparent;-fx-border-width: 0;-fx-border-radius: 0;-fx-prompt-text-fill: #8CAEBB;-fx-font-size: 14;-fx-font-weight: bold;-fx-focus-color: transparent;");
        

        // Add contents to searchBox
        searchBox.getChildren().addAll(searchBar, filterDropDownButton);
        searchBox.prefWidthProperty().bind(this.leftVBox.widthProperty());
        searchBox.setStyle("-fx-background-color: white; -fx-text-fill: #8CAEBB;-fx-border-radius: 10;-fx-border-width: 0.2;-fx-border-color: black;-fx-background-radius: 10");
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setPadding(new Insets(4,4,4,4));
        HBox.setMargin(filterDropDownButton, new Insets(0, 0, 0, 20));

        // Create VBox library
        this.libraryBox = new VBox();
        this.libraryBox.setPrefHeight(430);
        this.libraryBox.prefWidthProperty().bind(this.leftVBox.widthProperty().subtract(60));
        this.libraryBox.setStyle("-fx-background-color: white;-fx-border-radius: 10;-fx-border-width: 0.2;-fx-border-color: black;-fx-background-radius: 10");
        

        // Create HBox header library
        this.headLibraryBox = new HBox();
        this.headLibraryBox.setPrefHeight(70);
        this.headLibraryBox.prefWidthProperty().bind(this.leftVBox.widthProperty().subtract(60));
        this.headLibraryBox.setStyle("-fx-background-color: white;");

        // Create back button
        ImageView backIcon = new ImageView("/images/icon/backButton.png");
        backIcon.setPreserveRatio(true);
        backIcon.setSmooth(true);
        backIcon.setCache(true);
        backIcon.setFitWidth(14);
        backIcon.setFitHeight(14);
        this.backButton = new Button("", backIcon);
        this.backButton.setStyle("-fx-background-color: transparent;-fx-padding: 0;-fx-background-insets: 0;-fx-border-color: transparent;-fx-border-width: 0;-fx-border-radius: 0;-fx-prompt-text-fill: #8CAEBB;-fx-font-size: 14;-fx-font-weight: bold;");
        this.backButton.setAlignment(Pos.CENTER);

        // Create VBox header title
        this.headLibraryTitleBox = new VBox();

        this.allItem = new Label("All Items");
        this.allItem.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        this.allItem.setStyle("-fx-text-fill: #3B919B;");


        int tempItem = 0;
        for (Item item : this.items.getList()){
            if (item.getStock() > 0){
                tempItem++;
            }
        }

        this.totalItem = tempItem;
        this.totalItemString = String.valueOf(this.totalItem) + " Items";
        this.totalItemLabel = new Label(this.totalItemString);
        this.totalItemLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 10));
        this.totalItemLabel.setStyle("-fx-text-fill: #C8DFE8;");

        // Add contents to header title
        this.headLibraryTitleBox.getChildren().addAll(this.allItem, this.totalItemLabel);
        this.headLibraryTitleBox.setSpacing(5);
        this.headLibraryTitleBox.setAlignment(Pos.CENTER);

        // Add contents to header library
        this.headLibraryBox.getChildren().addAll(this.backButton, this.headLibraryTitleBox);
        HBox.setMargin(this.headLibraryTitleBox, new Insets(0, 0, 0, 200));
        HBox.setMargin(this.backButton, new Insets(0, 0, 0, 20));
        this.headLibraryBox.setAlignment(Pos.CENTER_LEFT);

        // Create HLine 3
        HBox hLine3 = new HBox();
        hLine3.setPrefHeight(4);
        hLine3.setStyle("-fx-background-color: #C8DFE8");

        // Create VBox 
        this.bodyLibraryVBox = new VBox();
        this.bodyLibraryVBox.prefWidthProperty().bind(this.leftVBox.widthProperty().subtract(60));

        // for loop item
        for (Item library : this.items.getList()) {

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

                // Add to body library box
                libraryDisplay.getChildren().addAll(libraryImage, libraryName, libraryPriceBills);
                libraryDisplay.setAlignment(Pos.CENTER_LEFT);
                HBox.setMargin(libraryImage, new Insets(0,0,0,20));
                HBox.setMargin(libraryName, new Insets(0,0,0,20));
                HBox.setMargin(libraryPriceBills, new Insets(0,0,0,320));

                // Add onclick event
                libraryDisplay.setOnMouseClicked(event -> {
                    setRegCust();
                    CashierDetailPage detailCashierContent = new CashierDetailPage(this.stage, tab, library, this.purchasedItems, this.items, tabPane, this.customers, this.mode, this.transactions, this.isUsePoint, this.regisCust);
                    tab.setContent(detailCashierContent);
                });

                this.bodyLibraryVBox.getChildren().addAll(hLine4, libraryDisplay);
                this.bodyLibraryVBox.setSpacing(5);
                this.bodyLibraryVBox.setAlignment(Pos.CENTER);
            }
        }
        // Create scrollpane
        ScrollPane scrollPane = new ScrollPane(this.bodyLibraryVBox);
        scrollPane.setMinHeight(350);
        scrollPane.prefWidthProperty().bind(this.leftVBox.widthProperty().subtract(60));
        scrollPane.setStyle("-fx-background: white;-fx-background-color: white;-fx-text-fill: #C8DFE8;");

        // Add contents to library box
        this.libraryBox.getChildren().addAll(this.headLibraryBox, scrollPane);
        this.libraryBox.setSpacing(10);
        this.libraryBox.setPadding(new Insets(10,10,10,10));
        
        // // Add contents to librarymain box
        VBox.setMargin(searchBox, new Insets(10,10,0,10));
        VBox.setMargin(this.libraryBox, new Insets(0, 10, 10, 10));
        libraryMainBox.getChildren().addAll(searchBox, this.libraryBox);
        libraryMainBox.setSpacing(20);

        this.backButton.setOnAction(event -> {

            int countTemp = 0;

            this.checkBox1.setSelected(false);
            this.checkBox2.setSelected(false);
            this.choiceCategoriesBox.setValue("Not Chosen");
            this.spinner.getValueFactory().setValue(Double.valueOf(50));
            this.spinner2.getValueFactory().setValue(Double.valueOf(50));
            slider1.setValue(this.spinner.getValue());
            slider2.setValue(this.spinner2.getValue());
            this.group.selectToggle(radioButton2);
            searchBar.setText("");

            containerDropDownDetails2.getChildren().remove(priceSliderTitle);
            containerDropDownDetails2.getChildren().remove(priceBox);
            containerDropDownDetails2.getChildren().remove(priceSliderBox);
            containerDropDownDetails2.getChildren().remove(dropDownCategoriesTitle);
            containerDropDownDetails2.getChildren().remove(dropDownCategoriesBox);

            this.totalItem = this.items.getList().size();
            this.totalItemString = String.valueOf(this.totalItem) + " Items";
            this.totalItemLabel = new Label(this.totalItemString);
            this.totalItemLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 10));
            this.totalItemLabel.setStyle("-fx-text-fill: #C8DFE8;");

            this.bodyLibraryVBox.getChildren().clear();

            this.bodyLibraryVBox = new VBox();
            this.bodyLibraryVBox.prefWidthProperty().bind(this.leftVBox.widthProperty().subtract(60));
            // for loop item
            for (Item library : items.getList()) {
                
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
                    countTemp++;
                }
            }
            this.totalItem = countTemp;
            this.totalItemString = String.valueOf(this.totalItem) + " Items";
            this.totalItemLabel = new Label(this.totalItemString);
            this.totalItemLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 10));
            this.totalItemLabel.setStyle("-fx-text-fill: #C8DFE8;");

            // Create scrollpane
            ScrollPane scrollPane4 = new ScrollPane(this.bodyLibraryVBox);
            scrollPane4.setMinHeight(350);
            scrollPane4.prefWidthProperty().bind(this.leftVBox.widthProperty().subtract(60));
            scrollPane4.setStyle("-fx-background: white;-fx-background-color: white;-fx-text-fill: #C8DFE8;");

            // Add contents to library box
            this.headLibraryTitleBox.getChildren().clear();
            this.headLibraryBox.getChildren().clear();
            this.libraryBox.getChildren().clear();

            this.headLibraryTitleBox.getChildren().addAll(this.allItem, this.totalItemLabel);
            this.headLibraryBox.getChildren().addAll(this.backButton, this.headLibraryTitleBox);
            this.libraryBox.getChildren().addAll(this.headLibraryBox, scrollPane4);
        });

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            this.headLibraryTitleBox.getChildren().clear();
            this.headLibraryBox.getChildren().clear();
            this.bodyLibraryVBox.getChildren().clear();
            this.libraryBox.getChildren().clear();
            this.searchedText = newValue;
            searchItem();
        });

        this.checkBox1.selectedProperty().addListener((observable, oldValue, newValue) -> {
            this.headLibraryTitleBox.getChildren().clear();
            this.headLibraryBox.getChildren().clear();
            this.bodyLibraryVBox.getChildren().clear();
            this.libraryBox.getChildren().clear();
            
            this.searchedText = searchBar.getText();
            if (newValue) {
                containerDropDownDetails2.getChildren().add(0,priceSliderTitle);
                containerDropDownDetails2.getChildren().add(1,priceBox);
                containerDropDownDetails2.getChildren().add(2,priceSliderBox);
                VBox.setMargin(priceBox, new Insets(5,0,0,0));
                VBox.setMargin(priceSliderBox, new Insets(5,0,10,0));
                searchItem();
            } else {
                containerDropDownDetails2.getChildren().remove(priceSliderTitle);
                containerDropDownDetails2.getChildren().remove(priceBox);
                containerDropDownDetails2.getChildren().remove(priceSliderBox);
                slider1.setValue(initVal1);
                slider2.setValue(initVal2);
                searchItem();
            }
        });

        this.checkBox2.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                containerDropDownDetails2.getChildren().addAll(dropDownCategoriesTitle, dropDownCategoriesBox);
                VBox.setMargin(dropDownCategoriesBox, new Insets(5,0,0,0));
            } else {
                containerDropDownDetails2.getChildren().remove(dropDownCategoriesTitle);
                containerDropDownDetails2.getChildren().remove(dropDownCategoriesBox);
                this.choiceCategoriesBox.setValue("Not Chosen");
            }
        });

        slider1.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.headLibraryTitleBox.getChildren().clear();
            this.headLibraryBox.getChildren().clear();
            this.bodyLibraryVBox.getChildren().clear();
            this.libraryBox.getChildren().clear();
            this.searchedText = searchBar.getText();
            this.spinner.getValueFactory().setValue(Double.valueOf(Math.round((newValue.doubleValue() * 100.0) / 100.0)));
            if(slider2.getValue() < slider1.getValue()){
                slider2.setValue(slider1.getValue() + 1);
                this.spinner2.getValueFactory().setValue(slider2.getValue());
            }
            searchItem();
        });

        slider2.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.headLibraryTitleBox.getChildren().clear();
            this.headLibraryBox.getChildren().clear();
            this.bodyLibraryVBox.getChildren().clear();
            this.libraryBox.getChildren().clear();
            this.searchedText = searchBar.getText();
            this.spinner2.getValueFactory().setValue(Double.valueOf(Math.round((newValue.doubleValue() * 100.0) / 100.0)));
            if(slider1.getValue() > slider2.getValue()){
                slider1.setValue(slider2.getValue() - 1);
                this.spinner.getValueFactory().setValue(slider1.getValue());
            }
            searchItem();
        });

        this.spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            this.headLibraryTitleBox.getChildren().clear();
            this.headLibraryBox.getChildren().clear();
            this.bodyLibraryVBox.getChildren().clear();
            this.libraryBox.getChildren().clear();
            this.searchedText = searchBar.getText();
            slider1.setValue(this.spinner.getValue());
            searchItem();
        });

        this.spinner2.valueProperty().addListener((obs, oldValue, newValue) -> {
            this.headLibraryTitleBox.getChildren().clear();
            this.headLibraryBox.getChildren().clear();
            this.bodyLibraryVBox.getChildren().clear();
            this.libraryBox.getChildren().clear();
            this.searchedText = searchBar.getText();
            slider2.setValue(this.spinner2.getValue());
            searchItem();
        });

        this.choiceCategoriesBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.headLibraryTitleBox.getChildren().clear();
            this.headLibraryBox.getChildren().clear();
            this.bodyLibraryVBox.getChildren().clear();
            this.libraryBox.getChildren().clear();
            this.searchedText = searchBar.getText();
            searchItem();
        });

        radioButton1.setOnAction(event -> {
            if (radioButton1.isSelected()) {
                this.headLibraryTitleBox.getChildren().clear();
                this.headLibraryBox.getChildren().clear();
                this.bodyLibraryVBox.getChildren().clear();
                this.libraryBox.getChildren().clear();
                this.searchedText = searchBar.getText();
                searchItem();
            }
        });

        radioButton2.setOnAction(event -> {
            if (radioButton2.isSelected()) {
                this.headLibraryTitleBox.getChildren().clear();
                this.headLibraryBox.getChildren().clear();
                this.bodyLibraryVBox.getChildren().clear();
                this.libraryBox.getChildren().clear();
                this.searchedText = searchBar.getText();
                searchItem();
            }
        });
        

    // }
        // Add content to each tab1
        StackPane tab1Content = new StackPane();
        tab1Content.getChildren().add(gridHBox);
        tab1Content.setPadding(new Insets(10,10, 10, 10));
        tab1Content.setStyle("-fx-background-color: #F3F9FB;");
        tab1.setContent(tab1Content);
        StackPane tab2Content = new StackPane();
        tab2Content.getChildren().addAll(libraryMainBox);
        tab2Content.setStyle("-fx-background-color: white;");
        tab2.setContent(tab2Content);
        tab1.setClosable(false);
        tab2.setClosable(false);
        tab1.setOnSelectionChanged(event -> {
            if (tab1.isSelected()) {
                tab1.setStyle("-fx-background-color: #3B919B; -fx-text-fill: white;-fx-background-radius: 10;-fx-focus-color: transparent;");
                tab2.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: white;-fx-background-radius: 10;-fx-focus-color: transparent;");
            }
        });
        tab2.setOnSelectionChanged(event -> {
            if (tab2.isSelected()) {
                tab2.setStyle("-fx-background-color: #3B919B; -fx-text-fill: white;-fx-background-radius: 10;-fx-focus-color: transparent;");
                tab1.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: white;-fx-background-radius: 10;-fx-focus-color: transparent;");
            }
        });

        // Add the tabs to the TabPane
        tabPaneCashier.getTabs().addAll(tab1, tab2);

        // Add left contents to left Box
        this.leftVBox.getChildren().addAll(tabPaneCashier);

        // Create HBox add customer
        HBox addCustBox = new HBox();
        addCustBox.prefWidthProperty().bind(rightVBox.widthProperty().subtract(10));
        addCustBox.setPadding(new Insets(5, 0, 0, 0));

        // Create VLine 1
        VBox vLine1 = new VBox();
        vLine1.setPrefWidth(4);
        vLine1.setStyle("-fx-background-color: #F3F9FB");
        vLine1.setOpacity(0.47);

        // Create Billing list icon
        ImageView billIcon = new ImageView("/images/icon/billingList.png");
        billIcon.fitHeightProperty().bind(vLine1.heightProperty());
        
        // Create add customer button
        Button addCustomerButton = new Button("+ Add Customer");
        addCustomerButton.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        addCustomerButton.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: #3B919B;");
        addCustomerButton.setAlignment(Pos.CENTER);
        addCustomerButton.prefWidthProperty().bind(addCustBox.widthProperty().subtract(billIcon.getFitWidth()).subtract(165));
        addCustomerButton.prefHeightProperty().bind(vLine1.heightProperty());
        addCustomerButton.setOnAction(event -> {
            Tab newTab = new Tab("Cashier");
            newTab.setStyle("-fx-background-color: #F3F9FB;");
            CashierPage cashierContent = new CashierPage(this.stage, tab, this.items, tabPane, this.customers, 0, this.transactions, new Inventory<PurchasedItem>(), false, null);
            newTab.setContent(cashierContent);
            tabPane.getTabs().add(newTab);
            tabPane.getSelectionModel().select(newTab);
        });

        // Add contents to addCustBox
        addCustBox.getChildren().addAll(billIcon,vLine1,addCustomerButton);

        // Create VBox member & items
        VBox memberItemsBox = new VBox();
        memberItemsBox.prefWidthProperty().bind(rightVBox.widthProperty().subtract(10));
        memberItemsBox.setPrefHeight(400);
        memberItemsBox.setStyle("-fx-background-color: white;");

        // Create HBox member
        HBox memberBox = new HBox();
        memberBox.prefWidthProperty().bind(rightVBox.widthProperty().subtract(10));
        memberBox.setStyle("-fx-background-color: white;");

        // Create member dropdown
        VBox dropDownBox = new VBox();
        this.choiceBox = new ChoiceBox<>();
        this.tempID = new ArrayList<Integer>();
        RegisteredCustomer tempRegisCust = null;
        for (Customer customer : this.customers.getList() ){
            if (customer.getClass().getSimpleName().equalsIgnoreCase("Customer")){;}
            else {
                RegisteredCustomer regisCustomer = (RegisteredCustomer) customer;
                this.choiceBox.getItems().add(regisCustomer);
                this.tempID.add(customer.getId());
                if (tempRegisCust == null){
                    Customer tempCust = new Member(-1, "Not Member", "000000", null);
                    tempRegisCust = (RegisteredCustomer) tempCust;
                    tempRegisCust.setActiveStatus(true);
                }
            }
        }
        this.choiceBox.getItems().add(tempRegisCust);
        
        // create a string converter to display the name of the customer
        StringConverter<RegisteredCustomer> converter = new StringConverter<RegisteredCustomer>() {
            @Override
            public String toString(RegisteredCustomer customer) {
                return customer.getName();
            }
            @Override
            public RegisteredCustomer fromString(String string) {
                // implement this method if needed
                return null;
            }
        };

        // set the converter for the choiceBox
        this.choiceBox.setConverter(converter);

        if (this.regisCust != null) {
            this.selectionIndex = getCustIndex();
        }
    
        setRegCust();
        int index = getCustIndex();
        if (this.regisCust != null && index != -1){
            this.choiceBox.setValue(this.choiceBox.getItems().get(index));
        } else {
            this.choiceBox.setValue(tempRegisCust);
        }

        //styling choice box
        this.choiceBox.setStyle("-fx-background-color: transparent;-fx-padding: 0;-fx-background-insets: 0;-fx-border-color: transparent;-fx-border-width: 0;-fx-border-radius: 0;-fx-prompt-text-fill: #3B919B;-fx-font-size: 14;-fx-font-weight: bold;-fx-text-fill: #3B919B;");

        // Add drop down box to member box
        dropDownBox.getChildren().add(this.choiceBox);
        memberBox.getChildren().add(dropDownBox);
        memberBox.setAlignment(Pos.CENTER);

        // Create HLine 1
        HBox hLine1 = new HBox();
        hLine1.setPrefHeight(4);
        hLine1.setStyle("-fx-background-color: #C8DFE8");

        // Create VBox items box
        VBox itemsVBox = new VBox();
        itemsVBox.prefWidthProperty().bind(rightVBox.widthProperty().subtract(10));

        // Display items bill
        int countBill = 0;
        for (PurchasedItem itemBill : this.purchasedItems.getList()){

            // Create HBox Display Item in Bill
            HBox billDisplay  = new HBox();
            billDisplay.prefWidthProperty().bind(rightVBox.widthProperty().subtract(10));

            HBox quantityNameBox = new HBox();
            quantityNameBox.setMinWidth(270);
            // Quantity Item
            int quantityItem = itemBill.getQuantity();
            String quantityItemBill = String.valueOf(quantityItem) + "x";
            Label quantityItemBills = new Label(quantityItemBill);
            quantityItemBills.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
            quantityItemBills.setStyle("-fx-text-fill: black;");
            quantityItemBills.setTextAlignment(TextAlignment.CENTER);
            quantityItemBills.setMinWidth(80);

            // Item Name
            Label itemNameBill = new Label(itemBill.getName());
            itemNameBill.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
            itemNameBill.setStyle("-fx-text-fill: black;");
            itemNameBill.setTextAlignment(TextAlignment.CENTER);
            itemNameBill.setMinWidth(170);

            HBox itemPriceBox = new HBox();
            itemPriceBox.setMinWidth(200);
            // Item Price
            double itemPrice = itemBill.getSellPrice() * quantityItem;
            NumberFormat formatter = NumberFormat.getInstance();
            formatter.setGroupingUsed(true);
            String itemPriceBill = "Rp" + formatter.format(itemPrice);
            Label itemPriceBills = new Label(itemPriceBill);
            itemPriceBills.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
            itemPriceBills.setStyle("-fx-text-fill: black;");
            itemPriceBills.setTextAlignment(TextAlignment.CENTER);

            quantityNameBox.getChildren().addAll(quantityItemBills, itemNameBill);
            itemPriceBox.getChildren().add(itemPriceBills);
            billDisplay.getChildren().addAll(quantityNameBox, itemPriceBox);
            quantityNameBox.setSpacing(20);
            quantityNameBox.setAlignment(Pos.CENTER_LEFT);
            itemPriceBox.setAlignment(Pos.CENTER_RIGHT);
            billDisplay.setSpacing(50);
            
            itemsVBox.getChildren().add(billDisplay);
            countBill++;
            this.totalPrice += itemPrice;
        }

        if(countBill == 0){
            Label noItemBill = new Label("No Items");
            noItemBill.setFont(Font.font("Montserrat", FontWeight.BOLD, 12));
            itemsVBox.getChildren().add(noItemBill);
            itemsVBox.setAlignment(Pos.CENTER);
        }

        // Styling itemsVBox
        itemsVBox.setPadding(new Insets(20, 20, 0, 20));
        itemsVBox.setSpacing(20);
        itemsVBox.setStyle("-fx-background-color: white;-fx-text-fill: #C8DFE8;");

        // Create scrollpane
        ScrollPane scrollPane2 = new ScrollPane(itemsVBox);
        scrollPane2.setPrefHeight(350);
        scrollPane2.prefWidthProperty().bind(rightVBox.widthProperty().subtract(40));
        scrollPane2.setStyle("-fx-background: white;-fx-background-color: white;-fx-text-fill: #C8DFE8;");
        
        // Add contents to memberItems box
        memberItemsBox.getChildren().addAll(memberBox, hLine1, scrollPane2);

        // Create HBox for billButton
        HBox  billButton = new HBox();
        billButton.prefWidthProperty().bind(rightVBox.widthProperty().subtract(10));
        billButton.setPadding(new Insets(5, 0, 0, 0));
        billButton.setPrefHeight(30);

        // Create save Button
        Button saveButton = new Button("Save Bill");
        saveButton.setPrefWidth(280);
        saveButton.setPrefHeight(26);
        saveButton.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        saveButton.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: #3B919B;");
        saveButton.setAlignment(Pos.CENTER);

        // Create VLine2
        VBox vLine2 = new VBox();
        vLine2.setPrefWidth(4);
        vLine2.setStyle("-fx-background-color: #F3F9FB");
        vLine2.setOpacity(0.47);

        // Create print Button
        ToggleButton usePointButton = new ToggleButton("Use Point");
        usePointButton.setPrefWidth(280);
        usePointButton.setPrefHeight(26);
        usePointButton.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        usePointButton.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: #3B919B;");
        usePointButton.setAlignment(Pos.CENTER);

        // Add contents to billButton
        billButton.getChildren().addAll(usePointButton, vLine2, saveButton);

        // Create HLine2
        HBox hLine2 = new HBox();
        hLine2.setPrefHeight(4);
        hLine2.setStyle("-fx-background-color: #F3F9FB");
        hLine2.setOpacity(0.47);

        // Create HBox for total Price
        totalPriceBox = new HBox();
        totalPriceBox.setPrefHeight(50);

        // Create Text Discount
        if(this.selectionIndex < this.tempID.size() && this.selectionIndex != -1){
            setRegCust();
            if (this.regisCust.isActiveStatus()){
                this.finalTotalPrice = this.regisCust.calculateDiscount(this.totalPrice, usePointButton.isSelected());
            } else {
                this.finalTotalPrice = this.totalPrice;
            }
        } else {
            this.finalTotalPrice = this.totalPrice;
        }
        // Create Text Total price
        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setGroupingUsed(true);
        String totalPriceBill = formatter.format(this.totalPrice);
        Label totalPriceBills;
        String fixTotalPrice = formatter.format(this.finalTotalPrice);
        Label fixTotalPriceBill = new Label("Rp" + fixTotalPrice);
        if(this.finalTotalPrice == this.totalPrice){
            totalPriceBills = new Label("Charge Rp" + totalPriceBill);
            totalPriceBox.getChildren().add(totalPriceBills);
        } else {
            Text totalPriceBillLabel = new Text("Rp" + totalPriceBill);
            totalPriceBills = new Label("Charge");
            totalPriceBox.getChildren().addAll(totalPriceBills, totalPriceBillLabel, fixTotalPriceBill);
            HBox.setMargin(fixTotalPriceBill, new Insets(0,0,0,10));
            totalPriceBox.setSpacing(10);
            totalPriceBillLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
            totalPriceBillLabel.setTextAlignment(TextAlignment.CENTER);
            totalPriceBillLabel.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: black;-fx-strikethrough-width: 2px;");
            totalPriceBillLabel.setStrikethrough(true);
        }
        totalPriceBills.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
        totalPriceBills.setTextAlignment(TextAlignment.CENTER);
        totalPriceBills.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: black;");

        fixTotalPriceBill.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
        fixTotalPriceBill.setTextAlignment(TextAlignment.CENTER);
        fixTotalPriceBill.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: black;");


        // Add contents to total price box
        totalPriceBox.prefWidthProperty().bind(rightVBox.widthProperty().subtract(10));
        totalPriceBox.setPadding(new Insets(5, 10, 0, 10));
        totalPriceBox.setAlignment(Pos.CENTER);
        totalPriceBox.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: #3B919B;");

        this.choiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if(usePointButton.isSelected()){
                usePointButton.fire();
                this.isUsePoint = false;
            }
            this.selectionIndex = (Integer) newValue;
            if(this.selectionIndex >= this.tempID.size() || this.selectionIndex == -1){
                this.regisCust = this.choiceBox.getItems().get(selectionIndex);
            }
        });

        saveButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successful");
            alert.setHeaderText("Save bill was successful!");
            alert.setContentText("Do you want to print the bill?");
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType yesButton = new ButtonType("Yes");
            alert.getButtonTypes().setAll(noButton, yesButton);

            Optional<ButtonType> result = alert.showAndWait();

            SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMMM YYYY\nHH:mm:ss");
            final String timenow = sdf.format(new Date());
            
            int custID = -1;
            FixedBill newBill;

            double discount = (this.totalPrice - this.finalTotalPrice) / this.totalPrice;
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.DOWN);
            double formattedDiscount = Double.parseDouble(df.format(discount));

            if(this.selectionIndex >= this.tempID.size() || this.selectionIndex == -1){
                newBill = new FixedBill(timenow, this.totalPrice, formattedDiscount);
                Customer newCust = new Customer(newBill);
                this.customers.addElement(newCust);
            } else  {
                for (Customer cust : this.customers.getList()) {
                    if(this.tempID.get(this.selectionIndex) == cust.getId()){
                        this.regisCust = (RegisteredCustomer) cust;
                    }
                }
                custID = this.tempID.get(this.selectionIndex);
                this.regisCust.calculatePoint(this.finalTotalPrice);
                newBill = new FixedBill (timenow, custID, this.totalPrice, formattedDiscount);
            }

            for(PurchasedItem purchItem : this.purchasedItems.getList()){
                for (Item item : this.items.getList()){
                    if (item.getItemID() == purchItem.getItemID()){
                        item.setStock(item.getStock() - purchItem.getQuantity());
                    }
                }
                newBill.getItems().addElement(purchItem);
            }
            this.transactions.addElement(newBill);

            if (result.isPresent() && result.get() == yesButton) {
                try {
                    newBill.printBill();
                    alert.close();
                } catch (DocumentException | FileNotFoundException | IndexOutOfBoundsException e){
                    e.printStackTrace();
                }
            } else {
                alert.close();
            }

            this.purchasedItems.getList().clear();
            
            setRegCust();
            CashierPage cashierContent = new CashierPage(this.stage, tab, this.items, tabPane, this.customers, 0, this.transactions, this.purchasedItems, this.isUsePoint, this.regisCust);
            tab.setContent(cashierContent);

        });

        usePointButton.setOnAction(event -> {
            if (this.selectionIndex < this.tempID.size() && this.selectionIndex != -1){
                totalPriceBox.getChildren().clear();
                // Create Text Total price
                String totalPriceBill2 = formatter.format(this.totalPrice);
                Label totalPriceBills2;
                if (usePointButton.isSelected()) {
                    this.isUsePoint = true;
                    usePointButton.setStyle("-fx-background-color: #3B919B;-fx-text-fill:#C8DFE8");

                    // Create Text Discount
                    if(this.selectionIndex < this.tempID.size()){
                        setRegCust();
                        if (this.regisCust.isActiveStatus()){
                            this.finalTotalPrice = this.regisCust.calculateDiscount(this.totalPrice, usePointButton.isSelected());
                        } else {
                            this.finalTotalPrice = this.totalPrice;
                        }
                    } else {
                        this.finalTotalPrice = this.totalPrice;
                    }

                    String fixTotalPrice2 = formatter.format(this.finalTotalPrice);
                    Label fixTotalPriceBill2 = new Label("Rp" + fixTotalPrice2);

                    if(this.finalTotalPrice == this.totalPrice){
                        totalPriceBills2 = new Label("Charge Rp" + totalPriceBill2);
                        totalPriceBox.getChildren().add(totalPriceBills2);
                    } else {
                        Text totalPriceBillLabel2 = new Text("Rp" +totalPriceBill2);
                        totalPriceBills2 = new Label("Charge");
                        totalPriceBox.getChildren().addAll(totalPriceBills2, totalPriceBillLabel2, fixTotalPriceBill2);
                        HBox.setMargin(fixTotalPriceBill2, new Insets(0,0,0,10));
                        totalPriceBox.setSpacing(10);
                        totalPriceBillLabel2.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
                        totalPriceBillLabel2.setTextAlignment(TextAlignment.CENTER);
                        totalPriceBillLabel2.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: black;");
                        totalPriceBillLabel2.setStrikethrough(true);
                    }
                    fixTotalPriceBill2.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
                    fixTotalPriceBill2.setTextAlignment(TextAlignment.CENTER);
                    fixTotalPriceBill2.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: black;");
                }
                else {
                    this.isUsePoint = false;
                    usePointButton.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: #3B919B;");
                    totalPriceBills2 = new Label("Charge Rp" + totalPriceBill2);
                    totalPriceBox.getChildren().add(totalPriceBills2);
                }
                totalPriceBills2.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
                totalPriceBills2.setTextAlignment(TextAlignment.CENTER);
                totalPriceBills2.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: black;");
            }
        });

        if (isUsePoint){
            usePointButton.fire();
        }

        // Add right contents to right Box
        rightVBox.getChildren().addAll(addCustBox, memberItemsBox, billButton, hLine2, totalPriceBox);

        // add left & right box to main box
        mainHBox.getChildren().addAll(this.leftVBox,rightVBox);

        // add hBox & mainHBox
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

    private void clickItem(HBox itemDisplay, Item item){
        itemDisplay.setOnMouseClicked(event -> {
            setRegCust();
            CashierDetailPage detailCashierContent = new CashierDetailPage(this.stage, tab, item, this.purchasedItems, this.items, tabPane, this.customers, this.mode, this.transactions, this.isUsePoint, this.regisCust);
            tab.setContent(detailCashierContent);
        });
    }

    private void searchItem(){

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

        // compare
        Comparator<Item> itemSellPrice = Comparator.comparingDouble(Item::getSellPrice);
        Comparator<Item> itemSellPriceComparator = Comparator.comparingDouble(Item::getSellPrice).thenComparing(Item::getName);
        Comparator<Item> itemSellPriceDescendingComparator = itemSellPrice.reversed().thenComparing(Item::getName);
        Comparator<Item> itemNameComparator = Comparator.comparing(Item::getName);
        Comparator<Item> itemNameDescendingComparator = itemNameComparator.reversed();


        if (isPriceChecked){
            if(isCategoriesChecked){
                if(selectedValue.equalsIgnoreCase("Descending")){
                    this.mode = 1;

                    // Sort 
                    this.items.getList().sort(itemSellPriceDescendingComparator);
                }
                else {
                    this.mode = 2;

                    // Sort 
                    this.items.getList().sort(itemSellPriceComparator);
                }
            }
            else {
                if(selectedValue.equalsIgnoreCase("Descending")){
                    this.mode = 3;

                    // Sort 
                    this.items.getList().sort(itemSellPriceDescendingComparator);
                }
                else {
                    this.mode = 4;

                    // Sort 
                    this.items.getList().sort(itemSellPriceComparator);
                }
            }
        }
        else {
            if(isCategoriesChecked){
                if(selectedValue.equalsIgnoreCase("Descending")){
                    this.mode = 5;

                    // Sort 
                    this.items.getList().sort(itemNameDescendingComparator);
                }
                else {
                    this.mode = 6;

                    // Sort 
                    this.items.getList().sort(itemNameComparator);
                }
            }
            else {
                if(selectedValue.equalsIgnoreCase("Descending")){
                    this.mode = 7;
                    // Sort 
                    this.items.getList().sort(itemNameDescendingComparator);
                }
                else {
                    // Sort 
                    this.items.getList().sort(itemNameComparator);
                }
            }
        }

        this.totalItem = this.items.getList().size();
        this.totalItemString = String.valueOf(this.totalItem) + " Items";
        this.totalItemLabel = new Label(this.totalItemString);
        this.totalItemLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 10));
        this.totalItemLabel.setStyle("-fx-text-fill: #C8DFE8;");

        this.bodyLibraryVBox.getChildren().clear();

        switch(this.mode){
            case 1:
            case 2:
                
                // for loop item
                for (Item library : this.items.getList()) {

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
                for (Item library : this.items.getList()) {

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
                for (Item library : this.items.getList()) {

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
                for (Item library : this.items.getList()) {
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

        this.totalItem = count;
        this.totalItemString = String.valueOf(this.totalItem) + " Items";
        this.totalItemLabel.setText(totalItemString);
        

        // Create scrollpane
        ScrollPane scrollPane3 = new ScrollPane(this.bodyLibraryVBox);
        scrollPane3.setMinHeight(350);
        scrollPane3.prefWidthProperty().bind(this.leftVBox.widthProperty().subtract(60));
        scrollPane3.setStyle("-fx-background: white;-fx-background-color: white;-fx-text-fill: #C8DFE8;");

        this.headLibraryTitleBox.getChildren().clear();
        this.headLibraryBox.getChildren().clear();
        this.libraryBox.getChildren().clear();

        // Add contents to library box
        this.headLibraryTitleBox.getChildren().addAll(this.allItem, this.totalItemLabel);
        this.headLibraryBox.getChildren().addAll(this.backButton, this.headLibraryTitleBox);
        this.libraryBox.getChildren().addAll(this.headLibraryBox, scrollPane3);
    }

    public double getMaxPrice (){
        double max = 0;
        for (Item itemElement : this.items.getList()){
            if (itemElement.getSellPrice() > max && itemElement.getStock() > 0){
                max = itemElement.getSellPrice();
            }
        }
        return max;
    }

    public double getMinPrice(){
        double min = getMaxPrice();
        for (Item itemElement : this.items.getList()){
            if (itemElement.getSellPrice() < min && itemElement.getStock() > 0){
                min = itemElement.getSellPrice();
            }
        }
        return min;

    }

    public void setRegCust(){
        if(this.selectionIndex < this.tempID.size() && this.selectionIndex != -1){
            for (Customer cust : this.customers.getList()) {
                if(this.tempID.get(this.selectionIndex) == cust.getId()){
                    this.regisCust = (RegisteredCustomer) cust;
                }
            }
        }
    }

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

    public HBox getTotalPriceBox() {
        return totalPriceBox;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setSettings(Settings settings){
        this.settings = settings;
    }

    public void setSettingsDS(DataStore<Settings> settingsDS){
        this.settingsDS = settingsDS;
    }

    public Settings getSettings(){
        return this.settings;
    }

    public DataStore<Settings> getSettingsDS(){
        return this.settingsDS;
    }
}
