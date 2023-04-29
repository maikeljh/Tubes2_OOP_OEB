package UI;


import java.util.*;
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
import java.text.NumberFormat;
import java.util.ArrayList;
import System.PurchasedItem;
import System.Inventory;
import System.Item;
import System.Customer;
import System.FixedBill;
import System.RegisteredCustomer;
import javafx.stage.Stage;
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
import java.util.Comparator;


public class CashierPage extends VBox {
    
    
    private Inventory<PurchasedItem> purchasedItems  = new Inventory<PurchasedItem>();

    public CashierPage(Stage stage, Tab tab, Inventory<Item> items, TabPane tabPane, Inventory<Customer> customers, Integer mode, Inventory<FixedBill> transactions){

        // Create main VBox
        VBox mainVBox = new VBox();
        
        // Create main HBox
        HBox mainHBox = new HBox();

        // Create left VBox
        VBox leftVBox = new VBox();

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
        leftVBox.setPrefWidth(620);
        rightVBox.setPrefWidth(620);
        mainHBox.prefWidthProperty().bind(mainVBox.widthProperty());
        hBox.prefWidthProperty().bind(mainVBox.widthProperty());

        // Create a new TabPane
        TabPane tabPaneCashier = new TabPane();
        tabPaneCashier.setStyle("-fx-background-color: #F3F9FB;-fx-padding: 5;-fx-background-radius: 10;-fx-focus-color: transparent;");
        tabPaneCashier.getStyleClass().add("floating");
    
        // Create two tabs
        Tab tab1 = new Tab("Favorites");
        // tab1.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        tab1.setStyle("-fx-background-color: #3B919B; -fx-text-fill: white;-fx-background-radius: 10;-fx-focus-color: transparent;");
        Tab tab2 = new Tab("Library");
        tab2.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: white;-fx-background-radius: 10;-fx-focus-color: transparent;");


        // Create HBox for grid
        HBox gridHBox = new HBox();

        //Styling grid box
        gridHBox.setPrefHeight(600);
        gridHBox.setStyle("-fx-background: #F3F9FB; -fx-background-color: #F3F9FB;");
        gridHBox.prefWidthProperty().bind(leftVBox.widthProperty());

        // List of Items
        GridPane grid = new GridPane();

        // Styling gridpane
        grid.setStyle("-fx-background-color: #F3F9FB;");
        grid.setHgap(40);
        grid.setVgap(40);
        grid.prefWidthProperty().bind(leftVBox.widthProperty());

        // Create item display
        int row = 0;
        int col = 0;
        int count = 0;

        // Display List Of Items
        for (Item item : items.getList()) {

            if (count < 9){

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
                    Tab newTab = new Tab("Cashier Detail");
                    newTab.setStyle("-fx-background-color: #F3F9FB;");
                    CashierDetailPage detailCashierContent = new CashierDetailPage(stage, tab);
                    newTab.setContent(detailCashierContent);
                    tabPane.getTabs().add(newTab);
                    tabPane.getSelectionModel().select(newTab);
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

            // Add onclick event
            itemDisplay.setOnMouseClicked(event -> {
                CashierDetailPage detailCashierContent = new CashierDetailPage(stage, tab);
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

        // Add grid to grid box
        gridHBox.getChildren().add(grid);

        // Create libraryMainBox
        VBox libraryMainBox = new VBox();
        libraryMainBox.setStyle("-fx-background-color: #F3F9FB;");
        libraryMainBox.prefWidthProperty().bind(leftVBox.widthProperty());

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
        CheckBox checkBox1 = new CheckBox("Sort by Price");
        CheckBox checkBox2 = new CheckBox("Sort by Categories");
        checkBoxBox.getChildren().addAll(checkBox1, checkBox2);

        ToggleGroup group = new ToggleGroup();
        VBox radioButtonBox = new VBox();
        RadioButton radioButton1 = new RadioButton("Descending");
        RadioButton radioButton2 = new RadioButton("Ascending");
        radioButton1.setToggleGroup(group);
        radioButton2.setToggleGroup(group);
        radioButtonBox.getChildren().addAll(radioButton1, radioButton2);
        group.selectToggle(radioButton2);


        Label priceSliderTitle = new Label("Price Range: ");
        priceSliderTitle.setAlignment(Pos.CENTER_LEFT);

        HBox priceBox = new HBox();
        Label minPrice = new Label("Min: ");
        Spinner<Double> spinner = new Spinner<Double>();
        double initVal1 = getMinPrice(items);
        double initVal2 = getMaxPrice(items);
        spinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, initVal2, initVal1));
        spinner.setEditable(true);
        Label maxPrice = new Label("Max: ");
        Spinner<Double> spinner2 = new Spinner<Double>();
        spinner2.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1, initVal2+100, initVal2));
        spinner2.setEditable(true);

        priceBox.getChildren().addAll(minPrice, spinner, maxPrice, spinner2);
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
        ChoiceBox<String> choiceCategoriesBox = new ChoiceBox<>();
        List<String> temp = new ArrayList<String>();
        boolean flag = false;
        for (Item item : items.getList() ){
            flag = false;
            for (String category : temp){
                if(item.getCategory().equalsIgnoreCase(category)){
                    flag = true;
                    break;
                }
            }
            if (!flag){
                choiceCategoriesBox.getItems().add(item.getCategory());
                temp.add(item.getCategory());
            }

        }
        choiceCategoriesBox.setValue("Not Chosen");

        //styling choice box
        choiceCategoriesBox.setStyle("-fx-background-color: transparent;-fx-padding: 0;-fx-background-insets: 0;-fx-border-color: transparent;-fx-border-width: 0;-fx-border-radius: 0;-fx-prompt-text-fill: #3B919B;-fx-font-size: 14;-fx-font-weight: bold;-fx-text-fill: #3B919B;");

        // Add drop down box to member box
        dropDownCategoriesBox.getChildren().add(choiceCategoriesBox);

        containerDropDownDetails.getChildren().addAll(checkBoxTitle, checkBoxBox, radioButtonBox);
        containerDropDownDetails.setSpacing(10);
        containerDropDown.getChildren().addAll(containerDropDownDetails, containerDropDownDetails2);
        containerDropDown.setSpacing(20);

        CustomMenuItem menuItem = new CustomMenuItem(containerDropDown,false);
        
        filterDropDownButton.getItems().add(menuItem);

        filterDropDownButton.setStyle("-fx-background-color: transparent;-fx-padding: 0;-fx-background-insets: 0;-fx-border-color: transparent;-fx-border-width: 0;-fx-border-radius: 0;-fx-prompt-text-fill: #8CAEBB;-fx-font-size: 14;-fx-font-weight: bold;-fx-focus-color: transparent;");
        

        // Add contents to searchBox
        searchBox.getChildren().addAll(searchBar, filterDropDownButton);
        searchBox.prefWidthProperty().bind(leftVBox.widthProperty());
        searchBox.setStyle("-fx-background-color: white; -fx-text-fill: #8CAEBB;-fx-border-radius: 10;-fx-border-width: 0.2;-fx-border-color: black;-fx-background-radius: 10");
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setPadding(new Insets(4,4,4,4));
        HBox.setMargin(filterDropDownButton, new Insets(0, 0, 0, 20));

        // Create VBox library
        VBox libraryBox = new VBox();
        libraryBox.setPrefHeight(430);
        libraryBox.prefWidthProperty().bind(leftVBox.widthProperty().subtract(60));
        libraryBox.setStyle("-fx-background-color: white;-fx-border-radius: 10;-fx-border-width: 0.2;-fx-border-color: black;-fx-background-radius: 10");
        

        // Create HBox header library
        HBox headLibraryBox = new HBox();
        headLibraryBox.setPrefHeight(70);
        headLibraryBox.prefWidthProperty().bind(leftVBox.widthProperty().subtract(60));
        headLibraryBox.setStyle("-fx-background-color: white;");

        // Create back button
        ImageView backIcon = new ImageView("/images/icon/backButton.png");
        backIcon.setPreserveRatio(true);
        backIcon.setSmooth(true);
        backIcon.setCache(true);
        backIcon.setFitWidth(14);
        backIcon.setFitHeight(14);
        Button backButton = new Button("", backIcon);
        backButton.setStyle("-fx-background-color: transparent;-fx-padding: 0;-fx-background-insets: 0;-fx-border-color: transparent;-fx-border-width: 0;-fx-border-radius: 0;-fx-prompt-text-fill: #8CAEBB;-fx-font-size: 14;-fx-font-weight: bold;");
        backButton.setAlignment(Pos.CENTER);

        // Create VBox header title
        VBox headLibraryTitleBox = new VBox();

        Label allItem = new Label("All Items");
        allItem.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        allItem.setStyle("-fx-text-fill: #3B919B;");

        int totalItem = items.getList().size();
        String totalItemString = String.valueOf(totalItem) + " Items";
        Label totalItemLabel = new Label(totalItemString);
        totalItemLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 10));
        totalItemLabel.setStyle("-fx-text-fill: #C8DFE8;");

        // Add contents to header title
        headLibraryTitleBox.getChildren().addAll(allItem, totalItemLabel);
        headLibraryTitleBox.setSpacing(5);
        headLibraryTitleBox.setAlignment(Pos.CENTER);

        // Add contents to header library
        headLibraryBox.getChildren().addAll(backButton, headLibraryTitleBox);
        HBox.setMargin(headLibraryTitleBox, new Insets(0, 0, 0, 200));
        HBox.setMargin(backButton, new Insets(0, 0, 0, 20));
        headLibraryBox.setAlignment(Pos.CENTER_LEFT);

        // Create HLine 3
        HBox hLine3 = new HBox();
        // hLine3.setMinHeight(4);
        hLine3.setPrefHeight(4);
        hLine3.setStyle("-fx-background-color: #C8DFE8");

        // Create VBox 
        VBox bodyLibraryVBox = new VBox();
        bodyLibraryVBox.prefWidthProperty().bind(leftVBox.widthProperty().subtract(60));

        // for loop item
        for (Item library : items.getList()) {

            // HBox Display Item
            HBox libraryDisplay = new HBox();
            libraryDisplay.prefWidthProperty().bind(leftVBox.widthProperty().subtract(60));
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
            hLine4.prefWidthProperty().bind(leftVBox.widthProperty().subtract(60));

            // Add to body library box
            libraryDisplay.getChildren().addAll(libraryImage, libraryName, libraryPriceBills);
            libraryDisplay.setAlignment(Pos.CENTER_LEFT);
            HBox.setMargin(libraryImage, new Insets(0,0,0,20));
            HBox.setMargin(libraryName, new Insets(0,0,0,20));
            HBox.setMargin(libraryPriceBills, new Insets(0,0,0,320));

            // Add onclick event
            libraryDisplay.setOnMouseClicked(event -> {
                Tab newTab = new Tab("Cashier Detail");
                newTab.setStyle("-fx-background-color: #F3F9FB;");
                CashierDetailPage detailCashierContent = new CashierDetailPage(stage, tab);
                newTab.setContent(detailCashierContent);
                tabPane.getTabs().add(newTab);
                tabPane.getSelectionModel().select(newTab);
            });

            bodyLibraryVBox.getChildren().addAll(hLine4, libraryDisplay);
            bodyLibraryVBox.setSpacing(5);
            bodyLibraryVBox.setAlignment(Pos.CENTER);
        }
        // Create scrollpane
        ScrollPane scrollPane = new ScrollPane(bodyLibraryVBox);
        scrollPane.setMinHeight(350);
        scrollPane.prefWidthProperty().bind(leftVBox.widthProperty().subtract(60));
        scrollPane.setStyle("-fx-background: white;-fx-background-color: white;-fx-text-fill: #C8DFE8;");

        // Add contents to library box
        libraryBox.getChildren().addAll(headLibraryBox, scrollPane);
        libraryBox.setSpacing(10);
        libraryBox.setPadding(new Insets(10,10,10,10));
        
        // // Add contents to librarymain box
        VBox.setMargin(searchBox, new Insets(10,10,0,10));
        VBox.setMargin(libraryBox, new Insets(0, 10, 10, 10));
        libraryMainBox.getChildren().addAll(searchBox, libraryBox);
        libraryMainBox.setSpacing(20);

        backButton.setOnAction(event -> {

            int countTemp = 0;

            checkBox1.setSelected(false);
            checkBox2.setSelected(false);
            choiceCategoriesBox.setValue("Not Chosen");
            spinner.getValueFactory().setValue(Double.valueOf(50));
            spinner2.getValueFactory().setValue(Double.valueOf(50));
            slider1.setValue(spinner.getValue());
            slider2.setValue(spinner2.getValue());
            group.selectToggle(radioButton2);
            searchBar.setText("");

            containerDropDownDetails2.getChildren().remove(priceSliderTitle);
            containerDropDownDetails2.getChildren().remove(priceBox);
            containerDropDownDetails2.getChildren().remove(priceSliderBox);
            containerDropDownDetails2.getChildren().remove(dropDownCategoriesTitle);
            containerDropDownDetails2.getChildren().remove(dropDownCategoriesBox);

            int totalItem2 = items.getList().size();
            String totalItemString2 = String.valueOf(totalItem2) + " Items";
            Label totalItemLabel2 = new Label(totalItemString2);
            totalItemLabel2.setFont(Font.font("Montserrat", FontWeight.BOLD, 10));
            totalItemLabel2.setStyle("-fx-text-fill: #C8DFE8;");

            bodyLibraryVBox.getChildren().clear();

            VBox bodyLibraryVBox2 = new VBox();
            bodyLibraryVBox2.prefWidthProperty().bind(leftVBox.widthProperty().subtract(60));
            // for loop item
            for (Item library : items.getList()) {

                // HBox Display Item
                HBox libraryDisplay = new HBox();
                libraryDisplay.prefWidthProperty().bind(leftVBox.widthProperty().subtract(60));
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
                hLine4.prefWidthProperty().bind(leftVBox.widthProperty().subtract(60));

                // Add to body library box
                libraryDisplay.getChildren().addAll(libraryImage, libraryName, libraryPriceBills);
                libraryDisplay.setAlignment(Pos.CENTER_LEFT);
                HBox.setMargin(libraryImage, new Insets(0,0,0,20));
                HBox.setMargin(libraryName, new Insets(0,0,0,20));
                HBox.setMargin(libraryPriceBills, new Insets(0,0,0,320));

                // Add onclick event
                clickItem(stage, tab, tabPane, libraryDisplay);

                bodyLibraryVBox2.getChildren().addAll(hLine4, libraryDisplay);
                bodyLibraryVBox2.setSpacing(5);
                bodyLibraryVBox2.setAlignment(Pos.CENTER);
                countTemp++;
            }

            int totalItem3 = countTemp;
            String totalItemString3 = String.valueOf(totalItem3) + " Items";
            Label totalItemLabel3 = new Label(totalItemString3);
            totalItemLabel3.setFont(Font.font("Montserrat", FontWeight.BOLD, 10));
            totalItemLabel3.setStyle("-fx-text-fill: #C8DFE8;");

            // Create scrollpane
            ScrollPane scrollPane4 = new ScrollPane(bodyLibraryVBox2);
            scrollPane4.setMinHeight(350);
            scrollPane4.prefWidthProperty().bind(leftVBox.widthProperty().subtract(60));
            scrollPane4.setStyle("-fx-background: white;-fx-background-color: white;-fx-text-fill: #C8DFE8;");

            // Add contents to library box
            headLibraryTitleBox.getChildren().clear();
            headLibraryBox.getChildren().clear();
            libraryBox.getChildren().clear();

            headLibraryTitleBox.getChildren().addAll(allItem, totalItemLabel3);
            headLibraryBox.getChildren().addAll(backButton, headLibraryTitleBox);
            libraryBox.getChildren().addAll(headLibraryBox, scrollPane4);
            
        });

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            headLibraryTitleBox.getChildren().clear();
            headLibraryBox.getChildren().clear();
            bodyLibraryVBox.getChildren().clear();
            libraryBox.getChildren().clear();
            searchItem(headLibraryTitleBox,headLibraryBox,bodyLibraryVBox, libraryBox, items, leftVBox, stage, tab, tabPane, allItem, totalItemLabel, backButton, checkBox1, checkBox2, spinner, spinner2, choiceCategoriesBox, group, newValue, totalItemString, totalItem);
        });

        checkBox1.selectedProperty().addListener((observable, oldValue, newValue) -> {
            headLibraryTitleBox.getChildren().clear();
            headLibraryBox.getChildren().clear();
            bodyLibraryVBox.getChildren().clear();
            libraryBox.getChildren().clear();
            
            String searchBarValue = searchBar.getText();
            if (newValue) {
                containerDropDownDetails2.getChildren().add(0,priceSliderTitle);
                containerDropDownDetails2.getChildren().add(1,priceBox);
                containerDropDownDetails2.getChildren().add(2,priceSliderBox);
                VBox.setMargin(priceBox, new Insets(5,0,0,0));
                VBox.setMargin(priceSliderBox, new Insets(5,0,10,0));
                searchItem(headLibraryTitleBox,headLibraryBox,bodyLibraryVBox, libraryBox, items, leftVBox, stage, tab, tabPane, allItem, totalItemLabel, backButton, checkBox1, checkBox2, spinner, spinner2, choiceCategoriesBox, group, searchBarValue, totalItemString, totalItem);
            } else {
                containerDropDownDetails2.getChildren().remove(priceSliderTitle);
                containerDropDownDetails2.getChildren().remove(priceBox);
                containerDropDownDetails2.getChildren().remove(priceSliderBox);
                slider1.setValue(initVal1);
                slider2.setValue(initVal2);
                searchItem(headLibraryTitleBox,headLibraryBox,bodyLibraryVBox, libraryBox, items, leftVBox, stage, tab, tabPane, allItem, totalItemLabel, backButton, checkBox1, checkBox2, spinner, spinner2, choiceCategoriesBox, group, searchBarValue, totalItemString, totalItem);
            }
        });

        checkBox2.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                containerDropDownDetails2.getChildren().addAll(dropDownCategoriesTitle, dropDownCategoriesBox);
                VBox.setMargin(dropDownCategoriesBox, new Insets(5,0,0,0));
            } else {
                containerDropDownDetails2.getChildren().remove(dropDownCategoriesTitle);
                containerDropDownDetails2.getChildren().remove(dropDownCategoriesBox);
                choiceCategoriesBox.setValue("Not Chosen");
            }
        });

        slider1.valueProperty().addListener((observable, oldValue, newValue) -> {
            headLibraryTitleBox.getChildren().clear();
            headLibraryBox.getChildren().clear();
            bodyLibraryVBox.getChildren().clear();
            libraryBox.getChildren().clear();
            String searchBarValue = searchBar.getText();
            spinner.getValueFactory().setValue(Double.valueOf(Math.round((newValue.doubleValue() * 100.0) / 100.0)));
            if(slider2.getValue() < slider1.getValue()){
                slider2.setValue(slider1.getValue() + 1);
                spinner2.getValueFactory().setValue(slider2.getValue());
            }
            searchItem(headLibraryTitleBox,headLibraryBox,bodyLibraryVBox, libraryBox, items, leftVBox, stage, tab, tabPane, allItem, totalItemLabel, backButton, checkBox1, checkBox2, spinner, spinner2, choiceCategoriesBox, group, searchBarValue, totalItemString, totalItem);
        });

        slider2.valueProperty().addListener((observable, oldValue, newValue) -> {
            headLibraryTitleBox.getChildren().clear();
            headLibraryBox.getChildren().clear();
            bodyLibraryVBox.getChildren().clear();
            libraryBox.getChildren().clear();
            String searchBarValue = searchBar.getText();
            spinner2.getValueFactory().setValue(Double.valueOf(Math.round((newValue.doubleValue() * 100.0) / 100.0)));
            if(slider1.getValue() > slider2.getValue()){
                slider1.setValue(slider2.getValue() - 1);
                spinner.getValueFactory().setValue(slider1.getValue());
            }
            searchItem(headLibraryTitleBox,headLibraryBox,bodyLibraryVBox, libraryBox, items, leftVBox, stage, tab, tabPane, allItem, totalItemLabel, backButton, checkBox1, checkBox2, spinner, spinner2, choiceCategoriesBox, group, searchBarValue, totalItemString, totalItem);
        });

        spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            headLibraryTitleBox.getChildren().clear();
            headLibraryBox.getChildren().clear();
            bodyLibraryVBox.getChildren().clear();
            libraryBox.getChildren().clear();
            String searchBarValue = searchBar.getText();
            slider1.setValue(spinner.getValue());
            searchItem(headLibraryTitleBox,headLibraryBox,bodyLibraryVBox, libraryBox, items, leftVBox, stage, tab, tabPane, allItem, totalItemLabel, backButton, checkBox1, checkBox2, spinner, spinner2, choiceCategoriesBox, group, searchBarValue, totalItemString, totalItem);
        });

        spinner2.valueProperty().addListener((obs, oldValue, newValue) -> {
            headLibraryTitleBox.getChildren().clear();
            headLibraryBox.getChildren().clear();
            bodyLibraryVBox.getChildren().clear();
            libraryBox.getChildren().clear();
            String searchBarValue = searchBar.getText();
            slider2.setValue(spinner2.getValue());
            searchItem(headLibraryTitleBox,headLibraryBox,bodyLibraryVBox, libraryBox, items, leftVBox, stage, tab, tabPane, allItem, totalItemLabel, backButton, checkBox1, checkBox2, spinner, spinner2, choiceCategoriesBox, group, searchBarValue, totalItemString, totalItem);
        });

        choiceCategoriesBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            headLibraryTitleBox.getChildren().clear();
            headLibraryBox.getChildren().clear();
            bodyLibraryVBox.getChildren().clear();
            libraryBox.getChildren().clear();
            String searchBarValue = searchBar.getText();
            searchItem(headLibraryTitleBox,headLibraryBox,bodyLibraryVBox, libraryBox, items, leftVBox, stage, tab, tabPane, allItem, totalItemLabel, backButton, checkBox1, checkBox2, spinner, spinner2, choiceCategoriesBox, group, searchBarValue, totalItemString, totalItem);
        });

        radioButton1.setOnAction(event -> {
            if (radioButton1.isSelected()) {
                headLibraryTitleBox.getChildren().clear();
                headLibraryBox.getChildren().clear();
                bodyLibraryVBox.getChildren().clear();
                libraryBox.getChildren().clear();
                String searchBarValue = searchBar.getText();
                searchItem(headLibraryTitleBox,headLibraryBox,bodyLibraryVBox, libraryBox, items, leftVBox, stage, tab, tabPane, allItem, totalItemLabel, backButton, checkBox1, checkBox2, spinner, spinner2, choiceCategoriesBox, group, searchBarValue, totalItemString, totalItem);
            }
        });

        radioButton2.setOnAction(event -> {
            if (radioButton2.isSelected()) {
                headLibraryTitleBox.getChildren().clear();
                headLibraryBox.getChildren().clear();
                bodyLibraryVBox.getChildren().clear();
                libraryBox.getChildren().clear();
                String searchBarValue = searchBar.getText();
                searchItem(headLibraryTitleBox,headLibraryBox,bodyLibraryVBox, libraryBox, items, leftVBox, stage, tab, tabPane, allItem, totalItemLabel, backButton, checkBox1, checkBox2, spinner, spinner2, choiceCategoriesBox, group, searchBarValue, totalItemString, totalItem);
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
        leftVBox.getChildren().addAll(tabPaneCashier);

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
            CashierPage cashierContent = new CashierPage(stage, tab, items, tabPane, customers, 0, transactions);
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
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        List<Integer> tempID = new ArrayList<Integer>();
        for (Customer customer : customers.getList() ){
            if (customer.getClass().getSimpleName().equalsIgnoreCase("Customer")){;}
            else {
                RegisteredCustomer regisCustomer = (RegisteredCustomer) customer;
                String regisCustomerName = regisCustomer.getName();
                choiceBox.getItems().add(regisCustomerName);
                tempID.add(customer.getId());
            }
        }
        choiceBox.getItems().add("Not Member");
        choiceBox.setValue("Not Member");

        //styling choice box
        choiceBox.setStyle("-fx-background-color: transparent;-fx-padding: 0;-fx-background-insets: 0;-fx-border-color: transparent;-fx-border-width: 0;-fx-border-radius: 0;-fx-prompt-text-fill: #3B919B;-fx-font-size: 14;-fx-font-weight: bold;-fx-text-fill: #3B919B;");

        // Add drop down box to member box
        dropDownBox.getChildren().add(choiceBox);
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
        for (PurchasedItem itemBill : purchasedItems.getList()){

            // Create HBox Display Item in Bill
            HBox billDisplay  = new HBox();
            billDisplay.prefWidthProperty().bind(rightVBox.widthProperty().subtract(10));

            // Quantity Item
            int quantityItem = itemBill.getQuantity();
            String quantityItemBill = String.valueOf(quantityItem) + "x";
            Text quantityItemBills = new Text(quantityItemBill);
            quantityItemBills.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
            quantityItemBills.setStyle("-fx-text-fill: #3B919B;");
            quantityItemBills.setTextAlignment(TextAlignment.CENTER);

            // Item Name
            Text itemNameBill = new Text(itemBill.getName());
            itemNameBill.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
            itemNameBill.setStyle("-fx-text-fill: #3B919B;");
            itemNameBill.setTextAlignment(TextAlignment.CENTER);

            // Item Price
            double itemPrice = itemBill.getSellPrice();
            NumberFormat formatter = NumberFormat.getInstance();
            formatter.setGroupingUsed(true);
            String itemPriceBill = "Rp" + formatter.format(itemPrice);
            Text itemPriceBills = new Text(itemPriceBill);
            itemPriceBills.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
            itemPriceBills.setStyle("-fx-text-fill: #3B919B;");
            itemPriceBills.setTextAlignment(TextAlignment.CENTER);

            billDisplay.getChildren().addAll(quantityItemBills, itemNameBill, itemPriceBills);
            HBox.setMargin(itemNameBill, new Insets(0, 0, 0, 20));
            HBox.setMargin(itemPriceBills, new Insets(0, 0, 0, 340));
            
            itemsVBox.getChildren().add(billDisplay);
            countBill++;
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
        scrollPane2.setPrefHeight(372);
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
        saveButton.setPrefWidth(616);
        saveButton.setPrefHeight(26);
        saveButton.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        saveButton.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: #3B919B;");
        saveButton.setAlignment(Pos.CENTER);

        // Add contents to billButton
        billButton.getChildren().addAll(saveButton);

        // Create HLine2
        HBox hLine2 = new HBox();
        hLine2.setPrefHeight(4);
        hLine2.setStyle("-fx-background-color: #F3F9FB");
        hLine2.setOpacity(0.47);

        // Create HBox for total Price
        HBox totalPriceBox = new HBox();
        totalPriceBox.setMaxHeight(45);

        // Create Text Total price
        int totalPrice = 30000;
        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setGroupingUsed(true);
        String totalPriceBill = "Charge Rp" + formatter.format(totalPrice);
        Label totalPriceBills = new Label(totalPriceBill);
        totalPriceBills.setFont(Font.font("Montserrat", FontWeight.BOLD, 20));
        totalPriceBills.setTextAlignment(TextAlignment.CENTER);
        totalPriceBills.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: #3B919B;");

        // Add contents to total price box
        totalPriceBox.prefWidthProperty().bind(rightVBox.widthProperty().subtract(10));
        totalPriceBox.setPadding(new Insets(5, 0, 0, 0));
        totalPriceBox.getChildren().add(totalPriceBills);
        totalPriceBox.setAlignment(Pos.CENTER);
        totalPriceBox.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: #3B919B;");

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


            int nBill = transactions.getList().size();
            int billID = transactions.getList().get(nBill-1).getBillID() + 1;

            int selectedIndex = choiceBox.getSelectionModel().getSelectedIndex();
            int custID = -1;
            if(selectedIndex >= tempID.size()){
                custID = customers.getList().size() - 1;
            } else {
                custID = tempID.get(selectedIndex);
            }

            FixedBill newBill = new FixedBill(billID, timenow, custID);

            newBill.setTotalPrice(totalPrice);

            newBill.setDiscount(0.10); // belom

            // belom
            newBill.getItems().addElement(new PurchasedItem(new Item("Cappuccino", 5, 20000, 15000, "Coffee", new Image("/images/item/item4.png")), 3));
            newBill.getItems().addElement(new PurchasedItem(new Item("Blueberry Pie", 5, 38000, 30000, "Desserts", new Image("/images/item/item4.png")), 1));
            newBill.getItems().addElement(new PurchasedItem(new Item("Cheese Cake", 5, 40000, 36000, "Desserts", new Image("/images/item/item4.png")), 2));
            newBill.getItems().addElement(new PurchasedItem(new Item("Mineral Water", 5, 20000, 15000, "Non Coffee", new Image("/images/item/item4.png")), 1));
            transactions.addElement(newBill);
        
            CashierPage cashierContent = new CashierPage(stage, tab, items, tabPane, customers, 0, transactions);
            tab.setContent(cashierContent);


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
        });

        // Add right contents to right Box
        rightVBox.getChildren().addAll(addCustBox, memberItemsBox, billButton, hLine2, totalPriceBox);

        // add left & right box to main box
        mainHBox.getChildren().addAll(leftVBox,rightVBox);

        // add hBox & mainHBox
        mainVBox.getChildren().addAll(hBox,mainHBox);

        // Styling Layout leftVBox
        leftVBox.setSpacing(20);
        leftVBox.setStyle("-fx-background-color: #F3F9FB;");

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

    private void clickItem(Stage stage, Tab tab, TabPane tabPane,HBox itemDisplay ){
        itemDisplay.setOnMouseClicked(event -> {
            Tab newTab = new Tab("Cashier Detail");
            newTab.setStyle("-fx-background-color: #F3F9FB;");
            CashierDetailPage detailCashierContent = new CashierDetailPage(stage, tab);
            newTab.setContent(detailCashierContent);
            tabPane.getTabs().add(newTab);
            tabPane.getSelectionModel().select(newTab);
        });
    }

    private void searchItem(VBox headLibraryTitleBox,HBox headLibraryBox, VBox bodyLibraryVBox, VBox libraryBox, Inventory<Item> items, VBox leftVBox, Stage stage, Tab tab, TabPane tabPane, Label allItem, Label totalItemLabel, Button backButton, CheckBox checkBox1, CheckBox checkBox2, Spinner<Double> spinner, Spinner<Double> spinner2, ChoiceBox<String> choiceCategoriesBox, ToggleGroup group, String newValue, String totalItemString, Integer totalItem ){

        int mode = 0;
        int count = 0;
        boolean isPriceChecked = checkBox1.isSelected();
        boolean isCategoriesChecked = checkBox2.isSelected();
        Double minPrice = spinner.getValue();
        Double maxPrice = spinner2.getValue();
        String selectedCategories = choiceCategoriesBox.getValue();
        Toggle selectedToggle = group.getSelectedToggle();
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
                    mode = 1;

                    // Sort 
                    items.getList().sort(itemSellPriceDescendingComparator);
                }
                else {
                    mode = 2;

                    // Sort 
                    items.getList().sort(itemSellPriceComparator);
                }
            }
            else {
                if(selectedValue.equalsIgnoreCase("Descending")){
                    mode = 3;

                    // Sort 
                    items.getList().sort(itemSellPriceDescendingComparator);
                }
                else {
                    mode = 4;

                    // Sort 
                    items.getList().sort(itemSellPriceComparator);
                }
            }
        }
        else {
            if(isCategoriesChecked){
                if(selectedValue.equalsIgnoreCase("Descending")){
                    mode = 5;

                    // Sort 
                    items.getList().sort(itemNameDescendingComparator);
                }
                else {
                    mode = 6;

                    // Sort 
                    items.getList().sort(itemNameComparator);
                }
            }
            else {
                if(selectedValue.equalsIgnoreCase("Descending")){
                    mode = 7;
                    // Sort 
                    items.getList().sort(itemNameDescendingComparator);
                }
                else {
                    // Sort 
                    items.getList().sort(itemNameComparator);
                }
            }
        }

        int totalItem2 = items.getList().size();
        String totalItemString2 = String.valueOf(totalItem2) + " Items";
        Label totalItemLabel2 = new Label(totalItemString2);
        totalItemLabel2.setFont(Font.font("Montserrat", FontWeight.BOLD, 10));
        totalItemLabel2.setStyle("-fx-text-fill: #C8DFE8;");

        bodyLibraryVBox.getChildren().clear();

        switch(mode){
            case 1:
            case 2:
                
                // for loop item
                for (Item library : items.getList()) {

                    if(library.getSellPrice() >= minPrice && library.getSellPrice() <= maxPrice && library.getCategory().equalsIgnoreCase(selectedCategories) && library.getName().toLowerCase().contains(newValue.toLowerCase())){

                        // HBox Display Item
                        HBox libraryDisplay = new HBox();
                        libraryDisplay.prefWidthProperty().bind(leftVBox.widthProperty().subtract(60));
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
                        hLine4.prefWidthProperty().bind(leftVBox.widthProperty().subtract(60));

                        // Add to body library box
                        libraryDisplay.getChildren().addAll(libraryImage, libraryName, libraryPriceBills);
                        libraryDisplay.setAlignment(Pos.CENTER_LEFT);
                        HBox.setMargin(libraryImage, new Insets(0,0,0,20));
                        HBox.setMargin(libraryName, new Insets(0,0,0,20));
                        HBox.setMargin(libraryPriceBills, new Insets(0,0,0,320));

                        // Add onclick event
                        clickItem(stage, tab, tabPane, libraryDisplay);

                        bodyLibraryVBox.getChildren().addAll(hLine4, libraryDisplay);
                        bodyLibraryVBox.setSpacing(5);
                        bodyLibraryVBox.setAlignment(Pos.CENTER);
                        count++;
                    }
                }
                break;

            case 3:
            case 4:

                // for loop item
                for (Item library : items.getList()) {

                    if(library.getSellPrice() >= minPrice && library.getSellPrice() <= maxPrice && library.getName().toLowerCase().contains(newValue.toLowerCase())){

                        // HBox Display Item
                        HBox libraryDisplay = new HBox();
                        libraryDisplay.prefWidthProperty().bind(leftVBox.widthProperty().subtract(60));
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
                        hLine4.prefWidthProperty().bind(leftVBox.widthProperty().subtract(60));

                        // Add to body library box
                        libraryDisplay.getChildren().addAll(libraryImage, libraryName, libraryPriceBills);
                        libraryDisplay.setAlignment(Pos.CENTER_LEFT);
                        HBox.setMargin(libraryImage, new Insets(0,0,0,20));
                        HBox.setMargin(libraryName, new Insets(0,0,0,20));
                        HBox.setMargin(libraryPriceBills, new Insets(0,0,0,320));

                        // Add onclick event
                        clickItem(stage, tab, tabPane, libraryDisplay);

                        bodyLibraryVBox.getChildren().addAll(hLine4, libraryDisplay);
                        bodyLibraryVBox.setSpacing(5);
                        bodyLibraryVBox.setAlignment(Pos.CENTER);
                        count++;
                    }
                }
                break;

            case 5:
            case 6:

                // for loop item
                for (Item library : items.getList()) {

                    if(library.getCategory().equalsIgnoreCase(selectedCategories) && library.getName().toLowerCase().contains(newValue.toLowerCase())){

                        // HBox Display Item
                        HBox libraryDisplay = new HBox();
                        libraryDisplay.prefWidthProperty().bind(leftVBox.widthProperty().subtract(60));
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
                        hLine4.prefWidthProperty().bind(leftVBox.widthProperty().subtract(60));

                        // Add to body library box
                        libraryDisplay.getChildren().addAll(libraryImage, libraryName, libraryPriceBills);
                        libraryDisplay.setAlignment(Pos.CENTER_LEFT);
                        HBox.setMargin(libraryImage, new Insets(0,0,0,20));
                        HBox.setMargin(libraryName, new Insets(0,0,0,20));
                        HBox.setMargin(libraryPriceBills, new Insets(0,0,0,320));

                        // Add onclick event
                        clickItem(stage, tab, tabPane, libraryDisplay);

                        bodyLibraryVBox.getChildren().addAll(hLine4, libraryDisplay);
                        bodyLibraryVBox.setSpacing(5);
                        bodyLibraryVBox.setAlignment(Pos.CENTER);
                        count++;
                    }
                }
                break;

            case 7:
            default:
                
                // for loop item
                for (Item library : items.getList()) {
                    if(library.getName().toLowerCase().contains(newValue.toLowerCase())){
                    
                        // HBox Display Item
                        HBox libraryDisplay = new HBox();
                        libraryDisplay.prefWidthProperty().bind(leftVBox.widthProperty().subtract(60));
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
                        hLine4.prefWidthProperty().bind(leftVBox.widthProperty().subtract(60));

                        // Add to body library box
                        libraryDisplay.getChildren().addAll(libraryImage, libraryName, libraryPriceBills);
                        libraryDisplay.setAlignment(Pos.CENTER_LEFT);
                        HBox.setMargin(libraryImage, new Insets(0,0,0,20));
                        HBox.setMargin(libraryName, new Insets(0,0,0,20));
                        HBox.setMargin(libraryPriceBills, new Insets(0,0,0,320));

                        // Add onclick event
                        clickItem(stage, tab, tabPane, libraryDisplay);

                        bodyLibraryVBox.getChildren().addAll(hLine4, libraryDisplay);
                        bodyLibraryVBox.setSpacing(5);
                        bodyLibraryVBox.setAlignment(Pos.CENTER);
                        count++;
                    }
                }
                break;
        }

        totalItem = count;
        totalItemString = String.valueOf(totalItem) + " Items";
        totalItemLabel.setText(totalItemString);
        

        // Create scrollpane
        ScrollPane scrollPane3 = new ScrollPane(bodyLibraryVBox);
        scrollPane3.setMinHeight(350);
        scrollPane3.prefWidthProperty().bind(leftVBox.widthProperty().subtract(60));
        scrollPane3.setStyle("-fx-background: white;-fx-background-color: white;-fx-text-fill: #C8DFE8;");

        headLibraryTitleBox.getChildren().clear();
        headLibraryBox.getChildren().clear();
        libraryBox.getChildren().clear();

        // Add contents to library box
        headLibraryTitleBox.getChildren().addAll(allItem, totalItemLabel);
        headLibraryBox.getChildren().addAll(backButton, headLibraryTitleBox);
        libraryBox.getChildren().addAll(headLibraryBox, scrollPane3);
    }

    private double getMaxPrice (Inventory<Item> items){
        double max = 0;
        for (Item itemElement : items.getList()){
            if (itemElement.getSellPrice() > max){
                max = itemElement.getSellPrice();
            }
        }
        return max;
    }

    private double getMinPrice(Inventory<Item> items){
        double min = getMaxPrice(items);
        for (Item itemElement : items.getList()){
            if (itemElement.getSellPrice() < min){
                min = itemElement.getSellPrice();
            }
        }
        return min;

    }
}
