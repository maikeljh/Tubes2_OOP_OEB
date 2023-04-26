package UI;

import com.itextpdf.text.DocumentException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
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
                salesReport.printReport();
            } catch (DocumentException | FileNotFoundException e){
                e.printStackTrace();
            }
        });

        // Add print button to HBox
        printButtonHBox.getChildren().add(printButton);
        HBox.setHgrow(printButtonHBox, Priority.ALWAYS);
        printButtonHBox.setAlignment(Pos.CENTER_RIGHT);

        // Add title label to HBox header
        hbox.getChildren().addAll(title, printButtonHBox);

        // Add contents
        getChildren().add(hbox);

        // Styling Layout
        setPadding(new Insets(20, 40, 0, 40));
        setSpacing(8);
        setStyle("-fx-background-color: #F3F9FB;");
    }
}
