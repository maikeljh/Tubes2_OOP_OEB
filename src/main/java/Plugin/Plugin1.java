package Plugin;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import System.Item;
import System.PurchasedItem;
import System.Inventory;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class Plugin1 extends BasePlugin {
    private Inventory<PurchasedItem> data1;
    private Inventory<PurchasedItem> data2;
    public Plugin1(){
        this.setPluginName("Line & Bar Chart");
    }

    public static void main(String args[]){}

    public Node initialize(){
        // Dummy inventory 1
        data1 = new Inventory<PurchasedItem>();
        // Dummy Inventory 2
        data2 = new Inventory<PurchasedItem>();
        Image image = new Image("/images/dummy.png");
        Item tes = new Item("Cappucino", 1, 2, 1, "Drinks", image);
        Item tes1 = new Item("Tea", 2, 3, 2, "Drinks", image);
        Item tes2 = new Item("Sake", 3, 4, 3, "Drinks", image);
        Item tes3 = new Item("Whiskey", 4, 6, 4, "Drinks", image);
        Item tes4 = new Item("Wine", 5, 8, 5, "Drinks", image);

        PurchasedItem item1 = new PurchasedItem(tes, 2);
        PurchasedItem item2 = new PurchasedItem(tes1, 3);
        PurchasedItem item3 = new PurchasedItem(tes2, 4);
        PurchasedItem item4 = new PurchasedItem(tes3, 6);
        PurchasedItem item5 = new PurchasedItem(tes4, 10);

        this.data2.addElement(item1);
        this.data2.addElement(item2);
        this.data2.addElement(item3);
        this.data2.addElement(item4);
        this.data2.addElement(item5);

        HBox main = new HBox();
        main.setPadding(new Insets(25, 10, 25, 15));
        // Create v box for line chart
        VBox lchart = new VBox();
        lchart.setSpacing(0);
        lchart.setMinWidth(620);
        // Create label
        Label lineTitle = new Label("Buzz");
        lineTitle.setFont(Font.font("Montserrat", FontWeight.BOLD, 32));
        lineTitle.setAlignment(Pos.CENTER);
        lineTitle.setMinWidth(620);
        lchart.getChildren().add(lineTitle);
        // create line chart


        CategoryAxis xAxis1 = new CategoryAxis();
        NumberAxis yAxis1 = new NumberAxis();
        xAxis1.setLabel("Item");
        yAxis1.setLabel("Income");
        xAxis1.setAutoRanging(false);
        LineChart<String, Number> lineChart = new LineChart<>(xAxis1, yAxis1);
        lineChart.setMinHeight(565);
        lineChart.setStyle("-fx-font-size: 16px; -fx-font-family: Montserrat; -fx-background-color: #C8DFE8;");
        XYChart.Series<String, Number> lineSeries = new XYChart.Series<>();
        lineSeries.setName("Net Profit");
        for (int i = 0; i < this.data2.getNeff(); i++){
            lineSeries.getData().add(new XYChart.Data<>(this.data2.getElement(i).getName(), this.data2.getElement(i).getQuantity()));
        }
//        lineSeries.getData().add(new XYChart.Data<>(2000, 100));
//        lineSeries.getData().add(new XYChart.Data<>(2001, 150));
//        lineSeries.getData().add(new XYChart.Data<>(2002, 200));
//        lineSeries.getData().add(new XYChart.Data<>(2003, 300));
//        lineSeries.getData().add(new XYChart.Data<>(2004, 400));
//        lineSeries.getData().add(new XYChart.Data<>(2005, 500));
        lineChart.getData().add(lineSeries);
        lchart.getChildren().add(lineChart);

        // Create vbox for bar chart
        VBox bchart = new VBox();
        bchart.setSpacing(0);
        bchart.setMinWidth(620);
        // Create title for barchart
        Label titleBar = new Label("Top 10 Purchased Items");
        titleBar.setFont(Font.font("Montserrat", FontWeight.BOLD, 32));
        titleBar.setAlignment(Pos.CENTER);
        titleBar.setMinWidth(620);
        bchart.getChildren().add(titleBar);

        // create bar chart
        CategoryAxis xAxis2 = new CategoryAxis();
        NumberAxis yAxis2 = new NumberAxis();
        xAxis2.setLabel("Items");
        yAxis2.setLabel("Purchased amount");
        BarChart<String, Number> barChart = new BarChart<>(xAxis2, yAxis2);
        barChart.setMinHeight(565);
        barChart.setStyle("-fx-font-size: 16px; -fx-font-family: Montserrat; -fx-background-color: #C8DFE8;");
        XYChart.Series<String, Number> barSeries = new XYChart.Series<>();
        barSeries.setName("Item Sales");
        for (int i = 0; i < this.data2.getNeff(); i++){
            barSeries.getData().add(new XYChart.Data<>(this.data2.getElement(i).getName(), this.data2.getElement(i).getQuantity()));
        }
//        barSeries.getData().add(new XYChart.Data<>("January", 100));
//        barSeries.getData().add(new XYChart.Data<>("February", 150));
//        barSeries.getData().add(new XYChart.Data<>("March", 200));
//        barSeries.getData().add(new XYChart.Data<>("April", 300));
//        barSeries.getData().add(new XYChart.Data<>("May", 400));
//        barSeries.getData().add(new XYChart.Data<>("June", 500));
        barChart.getData().add(barSeries);
        bchart.getChildren().add(barChart);

        main.getChildren().add(lchart);
        main.getChildren().add(bchart);
        main.setAlignment(Pos.CENTER);
        main.setSpacing(5);
        main.setMinHeight(665);
        main.setStyle("-fx-background-color: #F3F9FB;");

        return main;
    }
}
