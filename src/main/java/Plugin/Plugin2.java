package Plugin;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.chart.*;
import javafx.scene.input.MouseEvent;


public class Plugin2 extends BasePlugin {
    public Plugin2(){
        // Create Name
        this.setPluginName("Pie Chart");
    }

    public static void main(String args[]){}

    public Node initialize(){
        VBox main = new VBox();
        PieChart chart = new PieChart();
        chart.setTitle("Imported Fruits");

        // Add Data
        chart.getData().add(new PieChart.Data("Grapefruit", 13));
        chart.getData().add(new PieChart.Data("Oranges", 25));
        chart.getData().add(new PieChart.Data("Plums", 10));
        chart.getData().add(new PieChart.Data("Pears", 22));
        chart.getData().add(new PieChart.Data("Apples", 30));
//        ObservableList<PieChart.Data> pieChartData =
//                FXCollections.observableArrayList(
//                        new PieChart.Data("Grapefruit", 13),
//                        new PieChart.Data("Oranges", 25),
//                        new PieChart.Data("Plums", 10),
//                        new PieChart.Data("Pears", 22),
//                        new PieChart.Data("Apples", 30));
        main.getChildren().add(chart);
        main.setAlignment(Pos.CENTER);
        main.setSpacing(5);
        main.setMinHeight(665);
        main.setStyle("-fx-background-color: #F3F9FB;");

        // Percentage Number
        final Label caption = new Label("");
        caption.setTextFill(Color.DARKORANGE);
        caption.setStyle("-fx-font: 24 arial;");
        main.getChildren().add(caption);
        for (final PieChart.Data data : chart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            caption.setTranslateX(e.getSceneX());
                            caption.setTranslateY(e.getSceneY());
                            caption.setText(String.valueOf(data.getPieValue()) + "%");
                        }
                    });
        }
        return main;
    }
}
