package Plugin;

import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.layout.HBox;

public class Plugin1 extends HBox {

    public Plugin1(){
        // create line chart
        NumberAxis xAxis1 = new NumberAxis();
        NumberAxis yAxis1 = new NumberAxis();
        xAxis1.setLabel("Year");
        yAxis1.setLabel("Sales");
        xAxis1.setLowerBound(2000);
        xAxis1.setUpperBound(2006);
        xAxis1.setTickUnit(1);
        xAxis1.setAutoRanging(false);
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis1, yAxis1);
        XYChart.Series<Number, Number> lineSeries = new XYChart.Series<>();
        lineSeries.setName("Sales");
        lineSeries.getData().add(new XYChart.Data<>(2000, 100));
        lineSeries.getData().add(new XYChart.Data<>(2001, 150));
        lineSeries.getData().add(new XYChart.Data<>(2002, 200));
        lineSeries.getData().add(new XYChart.Data<>(2003, 300));
        lineSeries.getData().add(new XYChart.Data<>(2004, 400));
        lineSeries.getData().add(new XYChart.Data<>(2005, 500));
        lineChart.getData().add(lineSeries);

        // create bar chart
        CategoryAxis xAxis2 = new CategoryAxis();
        NumberAxis yAxis2 = new NumberAxis();
        xAxis2.setLabel("Month");
        yAxis2.setLabel("Sales");
        BarChart<String, Number> barChart = new BarChart<>(xAxis2, yAxis2);
        XYChart.Series<String, Number> barSeries = new XYChart.Series<>();
        barSeries.setName("Sales");
        barSeries.getData().add(new XYChart.Data<>("January", 100));
        barSeries.getData().add(new XYChart.Data<>("February", 150));
        barSeries.getData().add(new XYChart.Data<>("March", 200));
        barSeries.getData().add(new XYChart.Data<>("April", 300));
        barSeries.getData().add(new XYChart.Data<>("May", 400));
        barSeries.getData().add(new XYChart.Data<>("June", 500));
        barChart.getData().add(barSeries);

        getChildren().add(lineChart);
        getChildren().add(barChart);
        setAlignment(Pos.CENTER);
        setSpacing(5);
        setMinHeight(665);
        setStyle("-fx-background-color: #F3F9FB;");
    }
}
