package UI;

import com.itextpdf.text.DocumentException;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import System.SalesReport;
import System.PurchasedItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.util.Callback;

import java.io.FileNotFoundException;

public class SalesReportPage extends VBox {
    public SalesReportPage(Stage stage, Tab tab, SalesReport report) {
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
            try {
                printButton.setDisable(true);
                report.printReport();
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

        // Create a table
        TableView<PurchasedItem> table = new TableView();
        table.setEditable(true);
        table.setStyle(".table-column {\n" +
                "  -fx-alignment: CENTER-RIGHT;\n" +
                "}");

        // Set table columns
        TableColumn<PurchasedItem, Integer> idCol = new TableColumn("Product ID");
        idCol.setMinWidth(100);
        idCol.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getItemID()).asObject());
        idCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<PurchasedItem, String> nameCol = new TableColumn("Product Name");
        nameCol.setMinWidth(252);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<PurchasedItem, Double> buyPriceCol = new TableColumn("Buy Price");
        buyPriceCol.setMinWidth(180);
        buyPriceCol.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getBuyPrice()).asObject());
        buyPriceCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<PurchasedItem, Double> sellPriceCol = new TableColumn("Sell Price");
        sellPriceCol.setMinWidth(180);
        sellPriceCol.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getSellPrice()).asObject());
        sellPriceCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<PurchasedItem, Integer> qtyCol = new TableColumn("Quantity");
        qtyCol.setMinWidth(100);
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        qtyCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<PurchasedItem, Double> grossProfitCol = new TableColumn("Gross Profit");
        grossProfitCol.setMinWidth(180);
        grossProfitCol.setCellValueFactory(cellData -> {
            PurchasedItem item = cellData.getValue();
            Double grossProfit = item.calculateGrossProfit();
            return new SimpleDoubleProperty(grossProfit).asObject();
        });
        grossProfitCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<PurchasedItem, Double> netProfitCol = new TableColumn("Net Profit");
        netProfitCol.setMinWidth(180);
        netProfitCol.setCellValueFactory(cellData -> {
            PurchasedItem item = cellData.getValue();
            Double netProfit = item.calculateNetProfit();
            return new SimpleDoubleProperty(netProfit).asObject();
        });
        netProfitCol.setStyle("-fx-alignment: CENTER;");

        // Add columns to table
        table.getColumns().addAll(idCol, nameCol, buyPriceCol, sellPriceCol, qtyCol, grossProfitCol, netProfitCol);

        // Define a list (data) of items
        ObservableList<PurchasedItem> tableItems = FXCollections.observableArrayList();
        tableItems.addAll(report.getItems().getList());

        // Set data to table
        table.setItems(tableItems);
        table.refresh();

        // Create scroll pane for VBox
        ScrollPane scrollPane = new ScrollPane(table);

        // Style scroll pane
        scrollPane.setMaxHeight(640);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: #F3F9FB;");
        scrollPane.setPadding(new Insets(0, 10, 20, 10));

        // Add contents
        getChildren().add(hbox);
        getChildren().add(scrollPane);

        // Styling Layout
        setPadding(new Insets(20, 40, 0, 40));
        setSpacing(20);
        setStyle("-fx-background-color: #F3F9FB;");
    }
}
