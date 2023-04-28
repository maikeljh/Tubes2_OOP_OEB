package UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import java.text.NumberFormat;
import java.lang.reflect.Member;
import System.PurchasedItem;
import System.Inventory;
import System.Item;
import System.Customer;
import System.RegisteredCustomer;
import javafx.stage.Stage;

public class CashierPage extends VBox {
    
    private Inventory<PurchasedItem> purchasedItems  = new Inventory<PurchasedItem>();

    public CashierPage(Stage stage, Tab tab, Inventory<Item> items, TabPane tabPane, Inventory<Customer> customers, Integer mode){

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
        Button filterButton = new Button("", filterIcon);
        filterButton.setAlignment(Pos.CENTER);
        filterButton.setStyle("-fx-background-color: transparent;-fx-padding: 0;-fx-background-insets: 0;-fx-border-color: transparent;-fx-border-width: 0;-fx-border-radius: 0;-fx-prompt-text-fill: #8CAEBB;-fx-font-size: 14;-fx-font-weight: bold;");
        filterButton.setOnAction(event -> {
            // belom
        });

        // Create a search button
        ImageView searchIcon = new ImageView("/images/icon/searchButton.png");
        searchIcon.setPreserveRatio(true);
        searchIcon.setSmooth(true);
        searchIcon.setCache(true);
        searchIcon.setFitWidth(14);
        searchIcon.setFitHeight(14);
        Button searchButton = new Button("", searchIcon);
        searchButton.setStyle("-fx-background-color: transparent;-fx-padding: 0;-fx-background-insets: 0;-fx-border-color: transparent;-fx-border-width: 0;-fx-border-radius: 0;-fx-prompt-text-fill: #8CAEBB;-fx-font-size: 14;-fx-font-weight: bold;");
        searchButton.setAlignment(Pos.CENTER);
        searchButton.setOnAction(event -> {
            // belom
        });

        // Add contents to searchBox
        searchBox.getChildren().addAll(searchBar, filterButton, searchButton);
        searchBox.prefWidthProperty().bind(leftVBox.widthProperty());
        searchBox.setStyle("-fx-background-color: white; -fx-text-fill: #8CAEBB;-fx-border-radius: 10;-fx-border-width: 0.2;-fx-border-color: black;-fx-background-radius: 10");
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setPadding(new Insets(4,4,4,4));
        HBox.setMargin(filterButton, new Insets(0, 0, 0, 20));
        HBox.setMargin(searchButton, new Insets(0, 0, 0, 20));

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
        scrollPane.setMinHeight(370);
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
            headLibraryTitleBox.getChildren().clear();
            headLibraryBox.getChildren().clear();
            bodyLibraryVBox.getChildren().clear();
            libraryBox.getChildren().clear();

            int totalItem2 = items.getList().size();
            String totalItemString2 = String.valueOf(totalItem2) + " Items";
            Label totalItemLabel2 = new Label(totalItemString2);
            totalItemLabel2.setFont(Font.font("Montserrat", FontWeight.BOLD, 10));
            totalItemLabel2.setStyle("-fx-text-fill: #C8DFE8;");

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

                bodyLibraryVBox.getChildren().addAll(hLine4, libraryDisplay);
                bodyLibraryVBox.setSpacing(5);
                bodyLibraryVBox.setAlignment(Pos.CENTER);
            }

            // Create scrollpane
            ScrollPane scrollPane3 = new ScrollPane(bodyLibraryVBox);
            scrollPane3.setMinHeight(370);
            scrollPane3.prefWidthProperty().bind(leftVBox.widthProperty().subtract(60));
            scrollPane3.setStyle("-fx-background: white;-fx-background-color: white;-fx-text-fill: #C8DFE8;");

            // Add contents to library box
            headLibraryTitleBox.getChildren().addAll(allItem, totalItemLabel);
            headLibraryBox.getChildren().addAll(backButton, headLibraryTitleBox);
            libraryBox.getChildren().addAll(headLibraryBox, scrollPane3);
            
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
            CashierPage cashierContent = new CashierPage(stage, tab, items, tabPane, customers, 0);
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
        for (Customer customer : customers.getList() ){
            if (customer.getClass().getSimpleName().equals("Customer")){;}
            else {
                RegisteredCustomer regisCustomer = (RegisteredCustomer) customer;
                String regisCustomerName = regisCustomer.getName();
                choiceBox.getItems().add(regisCustomerName);
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
        saveButton.setOnAction(event -> {
            // belom
        });

        // // Create VLine2
        // VBox vLine2 = new VBox();
        // vLine2.setPrefWidth(4);
        // vLine2.setStyle("-fx-background-color: #F3F9FB");
        // vLine2.setOpacity(0.47);

        // Create print Button
        Button printButton = new Button("Print Bill");
        printButton.setPrefWidth(308);
        printButton.setPrefHeight(26);
        printButton.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        printButton.setStyle("-fx-background-color: #C8DFE8; -fx-text-fill: #3B919B;");
        printButton.setAlignment(Pos.CENTER);
        printButton.setOnAction(event -> {
            // belom
        });

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
}
