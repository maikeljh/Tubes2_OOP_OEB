package UI;

import com.itextpdf.text.DocumentException;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import System.SalesReport;
import System.Inventory;
import System.PurchasedItem;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.scene.layout.StackPane;

import java.io.FileNotFoundException;

public class SalesReportPage extends VBox {
    public SalesReportPage(Stage stage, Tab tab, Inventory<PurchasedItem> items) {
        // Create header (HBox)
        HBox hbox = new HBox();

        // Create title label
        Label title = new Label("Sales Report");
        title.setFont(Font.font("Montserrat", FontWeight.BOLD, 28));

        // Create HBox for print button
        HBox printButtonHBox = new HBox();

        // Create print button
        Button printButton = new Button("Print Report");
        printButton.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        printButton.setStyle("-fx-background-color: #3B919B; -fx-text-fill: white;");
        printButton.setCursor(Cursor.HAND);
        printButton.setOnAction(event -> {
            SalesReport salesReport = new SalesReport(items);
            try {
                printButton.setDisable(true);
                salesReport.printReport();
                Thread.sleep(1000);
                printButton.setDisable(false);

                // Show alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successful");
                alert.setHeaderText("Print Sales Report was successful!");
                alert.setContentText("You can open it on resources/files/Sales Report.pdf");
                alert.showAndWait();
            } catch (DocumentException | FileNotFoundException | InterruptedException e){
                e.printStackTrace();
            }
        });

        // Add print button to HBox
        printButtonHBox.getChildren().add(printButton);
        HBox.setHgrow(printButtonHBox, Priority.ALWAYS);
        printButtonHBox.setAlignment(Pos.CENTER_RIGHT);

        // Add title label to HBox header
        hbox.getChildren().addAll(title, printButtonHBox);

        // Create a preview for sales report (VBox)
        VBox vbox = new VBox(15); // spacing = 8
        vbox.setAlignment(Pos.BASELINE_CENTER);
        vbox.prefWidthProperty().bind(hbox.widthProperty());
        vbox.setPadding(new Insets(0, 30,30,0));

        // Create a table
        TableView<PurchasedItem> table = new TableView();
        table.setEditable(true);

        // Set table columns
        TableColumn<PurchasedItem, Integer> idCol = new TableColumn("Product ID");
        idCol.setMinWidth(100);
        idCol.setCellValueFactory(new PropertyValueFactory<PurchasedItem, Integer>("item_id"));

        TableColumn<PurchasedItem, String> nameCol = new TableColumn("Product Name");
        nameCol.setMinWidth(245);
        nameCol.setCellValueFactory(new PropertyValueFactory<PurchasedItem, String>("name"));

        TableColumn<PurchasedItem, Double> buyPriceCol = new TableColumn("Buy Price");
        buyPriceCol.setMinWidth(180);
        buyPriceCol.setCellValueFactory(new PropertyValueFactory<PurchasedItem, Double>("buy_price"));

        TableColumn<PurchasedItem, Double> sellPriceCol = new TableColumn("Sell Price");
        sellPriceCol.setMinWidth(180);
        sellPriceCol.setCellValueFactory(new PropertyValueFactory<PurchasedItem, Double>("sell_price"));

        TableColumn<PurchasedItem, Integer> qtyCol = new TableColumn("Quantity");
        qtyCol.setMinWidth(100);
        qtyCol.setCellValueFactory(new PropertyValueFactory<PurchasedItem, Integer>("quantity"));

        //TableColumn grossProfitCol = new TableColumn("Gross Profit");
        //grossProfitCol.setMinWidth(180);
        //grossProfitCol.setCellValueFactory(new PropertyValueFactory<>("gross_profit"));

        //TableColumn netProfitCol = new TableColumn("Net Profit");
        //netProfitCol.setMinWidth(180);
        //netProfitCol.setCellValueFactory(new PropertyValueFactory<>("net_profit"));

        // Add columns to table
        table.getColumns().addAll(idCol, nameCol, buyPriceCol, sellPriceCol, qtyCol/*, grossProfitCol, netProfitCol*/);

        // Define a list (data) of items
        SalesReport salesReport = new SalesReport(items);
        //ObservableList<PurchasedItem> data = FXCollections.observableArrayList();

        for (PurchasedItem item : salesReport.getItems().getList()){
            //data.add(item);
            table.getItems().add(item);
        }

        // Set data to table
        //table.getItems().addAll(data);
        //table.setItems(data);
        //table.refresh();





        // Add table to VBox
        vbox.getChildren().add(table);

        // Create scroll pane for VBox
        ScrollPane scrollPane = new ScrollPane(vbox);

        // Style scroll pane
        scrollPane.setMaxHeight(640);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: #F3F9FB;");
        scrollPane.setPadding(new Insets(0, 10, 20, 10));


        // Style vbox
        vbox.setStyle("-fx-background-color: #F3F9FB");

        // Add contents
        getChildren().add(hbox);
        getChildren().add(vbox);
        getChildren().add(scrollPane);

        // Styling Layout
        setPadding(new Insets(20, 40, 0, 40));
        setSpacing(8);
        setStyle("-fx-background-color: #F3F9FB;");
    }
}
