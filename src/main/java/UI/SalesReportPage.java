package UI;

import com.itextpdf.text.DocumentException;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import Core.SalesReport;
import Core.PurchasedItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import java.io.FileNotFoundException;

import Core.Settings;
import DataStore.DataStore;

public class SalesReportPage extends Page {
    public SalesReportPage(Stage stage, Tab tab, SalesReport report, Settings settings, DataStore<Settings> settingsDS) {
        super(stage, tab, settings, settingsDS);

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
                report.printReport(this.settings.getSaveDirectory());
                Thread.sleep(1000);
                printButton.setDisable(false);

                // Show alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successful");
                alert.setHeaderText("Print Sales Report was successful!");
                alert.setContentText("You can open it on saves/Sales Report.pdf");
                alert.showAndWait();
            } catch (DocumentException | FileNotFoundException | InterruptedException e){
                // Do Nothing
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

        TableColumn<PurchasedItem, Integer> buyPriceCol = new TableColumn("Buy Price");
        buyPriceCol.setMinWidth(180);
        buyPriceCol.setCellValueFactory(param -> new SimpleIntegerProperty((int) param.getValue().getBuyPrice()).asObject());
        buyPriceCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<PurchasedItem, Integer> sellPriceCol = new TableColumn("Sell Price");
        sellPriceCol.setMinWidth(180);
        sellPriceCol.setCellValueFactory(param -> new SimpleIntegerProperty((int) param.getValue().getSellPrice()).asObject());
        sellPriceCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<PurchasedItem, Integer> qtyCol = new TableColumn("Quantity");
        qtyCol.setMinWidth(100);
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        qtyCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<PurchasedItem, Integer> grossProfitCol = new TableColumn("Gross Profit");
        grossProfitCol.setMinWidth(180);
        grossProfitCol.setCellValueFactory(cellData -> {
            PurchasedItem item = cellData.getValue();
            Integer grossProfit = (int) item.calculateGrossProfit();
            return new SimpleIntegerProperty(grossProfit).asObject();
        });
        grossProfitCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<PurchasedItem, Integer> netProfitCol = new TableColumn("Net Profit");
        netProfitCol.setMinWidth(180);
        netProfitCol.setCellValueFactory(cellData -> {
            PurchasedItem item = cellData.getValue();
            Integer netProfit = (int) item.calculateNetProfit();
            return new SimpleIntegerProperty(netProfit).asObject();
        });
        netProfitCol.setStyle("-fx-alignment: CENTER;");

        // Add columns to table
        table.getColumns().addAll(idCol, nameCol, buyPriceCol, sellPriceCol, qtyCol, grossProfitCol, netProfitCol);

        // Define a list (data) of items
        ObservableList<PurchasedItem> tableItems = FXCollections.observableArrayList();
        tableItems.addAll(report.getItems().getBox());

        // Set data to table
        table.setItems(tableItems);
        table.refresh();

        // Create thread
        Thread thread = new Thread(() -> {
            while (!close && !Page.isExit()) {
                Platform.runLater(table::refresh);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    // Do nothing
                }
            }
        });

        thread.start();

        // Create label for total gross profit
        HBox totalGrossHBox = new HBox();
        Label totalGrossLabel = new Label("Total Gross Profit: ");
        Label totalGrossValueLabel = new Label(String.valueOf((int) report.getTotalGrossProfit()));
        
        // Style total net label
        totalGrossLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 16));
        totalGrossValueLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 16));

        // Add labels to HBox
        totalGrossHBox.getChildren().add(totalGrossLabel);
        totalGrossHBox.getChildren().add(totalGrossValueLabel);

        // Create a label for total net profit
        HBox totalNetHBox = new HBox();
        Label totalNetLabel = new Label("Total Net Profit: ");
        Label totalNetValueLabel = new Label(String.valueOf((int) report.getTotalNetProfit()));

        // Style total net label
        totalNetLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 16));
        totalNetValueLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 16));
        
        // Add labels to HBox
        totalNetHBox.getChildren().add(totalNetLabel);
        totalNetHBox.getChildren().add(totalNetValueLabel);

        // Create scroll pane for VBox
        ScrollPane scrollPane = new ScrollPane(table);

        // Style scroll pane
        scrollPane.setMaxHeight(700);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: #F3F9FB;");
        scrollPane.setPadding(new Insets(0, 10, 20, 10));

        // Add contents
        getChildren().add(hbox);
        getChildren().add(scrollPane);
        getChildren().add(totalGrossHBox);
        getChildren().add(totalNetHBox);

        // Styling Layout
        setPadding(new Insets(20, 40, 0, 40));
        setSpacing(20);
        setStyle("-fx-background-color: #F3F9FB;");
    }
}
