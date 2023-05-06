package Plugin.Plugin1;

import Plugin.BasePlugin;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Label;
import Core.Inventory;
import Core.PurchasedItem;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

public class Plugin1 extends BasePlugin {

   public Plugin1(){
       this.pluginName = "Line & Bar Chart";
       this.mainPlugin = true;
   }

   public static void main(String args[]){}

   public Node initialize(){
       HBox main = new HBox();
       main.setPadding(new Insets(25, 10, 25, 15));

       // Create v box for line chart
       VBox lchart = new VBox();
       lchart.setSpacing(0);
       lchart.setMinWidth(620);

       // Create label
       Label lineTitle = new Label("Purchased Amount with Price Correlation");
       lineTitle.setFont(Font.font("Montserrat", FontWeight.BOLD, 32));
       lineTitle.setAlignment(Pos.CENTER);
       lineTitle.setMinWidth(620);
       lchart.getChildren().add(lineTitle);

       // create line chart
       NumberAxis xAxis1 = new NumberAxis();
       NumberAxis yAxis1 = new NumberAxis();
       xAxis1.setLabel("Sold Quantity");
       yAxis1.setLabel("Sell Price");
       xAxis1.setAutoRanging(false);
       LineChart<Number, Number> lineChart = new LineChart<>(xAxis1, yAxis1);
       lineChart.setMinHeight(565);
       lineChart.setStyle("-fx-font-size: 16px; -fx-font-family: Montserrat; -fx-background-color: #C8DFE8;");
       XYChart.Series<Number, Number> lineSeries = new XYChart.Series<>();
       lineSeries.setName("Net Profit");
       for (int i = 0; i < this.items.getNeff(); i++){
           lineSeries.getData().add(new XYChart.Data<>(this.items.getElement(i).getQuantity(), this.items.getElement(i).getSellPrice()));
       }
       lineChart.getData().add(lineSeries);
       lchart.getChildren().add(lineChart);

       // Create vbox for bar chart
       VBox bchart = new VBox();
       bchart.setSpacing(0);
       bchart.setMinWidth(620);
       // Create title for barchart
       Label titleBar = new Label("Top 5 Purchased Items");
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
       // Sorting purchased items data
       Inventory<PurchasedItem> sortedData = sortItems(this.items);
       if (this.items.getNeff() > 5){
        for (int i = 0; i < 5; i++){
            barSeries.getData().add(new XYChart.Data<>(sortedData.getElement(i).getName(), sortedData.getElement(i).getQuantity()));
        }
       }
       else {
        for (int i = 0; i < this.items.getNeff(); i++){
            barSeries.getData().add(new XYChart.Data<>(sortedData.getElement(i).getName(), sortedData.getElement(i).getQuantity()));
        }
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

   public Inventory<PurchasedItem> sortItems(Inventory<PurchasedItem> inventory) {
    List<PurchasedItem> items = inventory.getBox();
    Collections.sort(items, new Comparator<PurchasedItem>() {
        @Override
        public int compare(PurchasedItem item1, PurchasedItem item2) {
            int netProfit1 = item1.getQuantity();
            int netProfit2 = item2.getQuantity();
            return Integer.compare(netProfit2, netProfit1);
        }
    });
    Inventory<PurchasedItem> sortedInventory = new Inventory<PurchasedItem>();
    for (PurchasedItem item : items) {
        sortedInventory.addElement(item);
    }
    return sortedInventory;
  }
}
