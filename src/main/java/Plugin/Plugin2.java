package Plugin;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.chart.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import System.Inventory;
import System.Item;
import System.PurchasedItem;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class Plugin2 extends BasePlugin {
    private Inventory<PurchasedItem> data;
    private List<String> cat;
    private List<Double> profit;
    public Plugin2(){
        // Create Name
        this.setPluginName("Pie Chart");
    }

    public static void main(String args[]){}
    public void filterData(){
        this.cat = new ArrayList<>();
        this.profit = new ArrayList<>();
        for (int i = 0; i < this.data.getNeff(); i++){
            if (!this.cat.contains(this.data.getElement(i).getCategory())){
                this.cat.add(this.data.getElement(i).getCategory());
                this.profit.add(this.data.getElement(i).calculateNetProfit());
            }
            else{
                int idx = cat.indexOf(this.data.getElement(i).getCategory());
                double newProfit = profit.get(idx) + this.data.getElement(i).calculateNetProfit();
                profit.set(idx, newProfit);
            }
        }
    }

    public double countNetProfit(){
        double p = 0;
        for (double data : this.profit){
            p += data;
        }
        return p;
    }

    public Node initialize(){
        // Dummy Inventory
        data = new Inventory<PurchasedItem>();
        Image image = new Image("/images/dummy.png");
        Item tes = new Item("Cappucino", 1, 2, 1, "Drinks", image);
        Item tes1 = new Item("Tea", 2, 3, 2, "Drinks", image);
        Item tes2 = new Item("Sake", 3, 4, 3, "Drinks", image);
        Item tes3 = new Item("Whiskey", 4, 6, 4, "Drinks", image);
        Item tes4 = new Item("Wine", 5, 8, 5, "Drinks", image);
        Item tes5 = new Item("Hamburger", 7, 2, 1, "Food", image);
        Item tes6 = new Item("Nasgor", 6, 3, 2, "Food", image);
        Item tes7 = new Item("Escargot", 8, 4, 3, "Food", image);
        Item tes8 = new Item("Spoon", 3, 6, 4, "Utensil", image);
        Item tes9 = new Item("Fork", 4, 8, 5, "Utensil", image);

        PurchasedItem item1 = new PurchasedItem(tes, 1);
        PurchasedItem item2 = new PurchasedItem(tes1, 2);
        PurchasedItem item3 = new PurchasedItem(tes2, 3);
        PurchasedItem item4 = new PurchasedItem(tes3, 4);
        PurchasedItem item5 = new PurchasedItem(tes4, 5);
        PurchasedItem item6 = new PurchasedItem(tes5, 7);
        PurchasedItem item7 = new PurchasedItem(tes6, 6);
        PurchasedItem item8 = new PurchasedItem(tes7, 8);
        PurchasedItem item9 = new PurchasedItem(tes8, 3);
        PurchasedItem item10 = new PurchasedItem(tes9, 4);

        this.data.addElement(item1);
        this.data.addElement(item2);
        this.data.addElement(item3);
        this.data.addElement(item4);
        this.data.addElement(item5);
        this.data.addElement(item6);
        this.data.addElement(item7);
        this.data.addElement(item8);
        this.data.addElement(item9);
        this.data.addElement(item10);
        this.filterData();
        VBox pchart = new VBox();
        PieChart chart = new PieChart();
        Label chartTitle = new Label("Income of Every Category");
        chartTitle.setFont(Font.font("Montserrat", FontWeight.BOLD, 32));
        pchart.getChildren().add(chartTitle);
        chart.setTitle("");
        chart.setStyle("-fx-font-size: 16px; -fx-font-family: Montserrat; -fx-background-color: #C8DFE8;");
        chart.setPadding(new Insets(0, 0, 22, 0));
        for (int i = 0; i < this.cat.size(); i++){
            chart.getData().add(new PieChart.Data(this.cat.get(i), this.profit.get(i)));
        }
        // Add Data
//        chart.getData().add(new PieChart.Data("Grapefruit", 13));
//        chart.getData().add(new PieChart.Data("Oranges", 25));
//        chart.getData().add(new PieChart.Data("Plums", 10));
//        chart.getData().add(new PieChart.Data("Pears", 22));
//        chart.getData().add(new PieChart.Data("Apples", 30));
        DecimalFormat df = new DecimalFormat("0.#");
        for (PieChart.Data data : chart.getData()) {
            data.nameProperty().bind(
                    Bindings.concat(data.getName(), " ", df.format(data.getPieValue() / chart.getData().stream().mapToDouble(PieChart.Data::getPieValue).sum() * 100), "%")
            );
        }
        // Set the properties of the pie chart to show percentage values
        chart.setLabelLineLength(10);
        chart.setLegendVisible(true);
        chart.setLabelsVisible(true);
        chart.setStartAngle(180);


        pchart.getChildren().add(chart);
        // Shows total net profit
        Label totalNetProfit = new Label();
        totalNetProfit.setText(String.valueOf("Total Net Profit:  " + this.countNetProfit()));
        totalNetProfit.setFont(Font.font("Montserrat", FontWeight.SEMI_BOLD, 20));
        totalNetProfit.setPadding(new Insets(10, 0, 0, 0));
        pchart.getChildren().add(totalNetProfit);

        pchart.setAlignment(Pos.CENTER);
        pchart.setSpacing(5);
        pchart.setMinHeight(665);
        pchart.setStyle("-fx-background-color: #F3F9FB;");

        // Percentage Number
//        final Label caption = new Label("");
//        caption.setTextFill(Color.DARKORANGE);
//        caption.setStyle("-fx-font: 24 arial;");
//        pchart.getChildren().add(caption);
//
//        for (final PieChart.Data data : chart.getData()) {
//            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
//                    e -> {
//                        caption.setTranslateX(e.getSceneX());
//                        caption.setTranslateY(e.getSceneY());
//                        caption.setText(String.valueOf(data.getPieValue()) + "%");
//                    });
//        }

        return pchart;
    }
}
