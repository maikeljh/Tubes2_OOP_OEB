package Plugin.Plugin2;

import Plugin.BasePlugin;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.chart.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class Plugin2 extends BasePlugin {
    private List<String> cat;
    private List<Double> profit;

    public Plugin2(){
        // Create Name
        this.pluginName = "Pie Chart";
        this.mainPlugin = true;
    }

    public void filterData(){
        this.cat = new ArrayList<>();
        this.profit = new ArrayList<>();
        for (int i = 0; i < this.items.getNeff(); i++){
            if (!this.cat.contains(this.items.getElement(i).getCategory())){
                this.cat.add(this.items.getElement(i).getCategory());
                this.profit.add(this.items.getElement(i).calculateNetProfit());
            }
            else{
                int idx = cat.indexOf(this.items.getElement(i).getCategory());
                double newProfit = profit.get(idx) + this.items.getElement(i).calculateNetProfit();
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
