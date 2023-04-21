package UI;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainPage extends VBox {
    private volatile boolean stop = false;
    private Text time;
    private Text kelompok;
    private Text name;
    public MainPage() {
        name = new Text();
        name.setText("OEB");
        name.setFont(Font.font("Montserrat", FontWeight.BOLD, 40));
        name.setTextAlignment(TextAlignment.CENTER);

        // Create Time Text
        time = new Text();
        time.setFont(Font.font("Montserrat", 40));
        time.setTextAlignment(TextAlignment.CENTER);

        // Create Kelompok Text
        kelompok = new Text();
        kelompok.setText("13521059 Arleen Chrysantha Gunardi\n" +
                "13521124 Michael Jonathan Halim\n" +
                "13521127 Marcel Ryan Antony\n" +
                "13521143 Raynard Tanadi\n" +
                "13521145 Kenneth Dave Bahana\n");
        kelompok.setFont(Font.font("Poppins", 20));
        kelompok.setTextAlignment(TextAlignment.CENTER);

        // Add text to group
        getChildren().add(name);
        getChildren().add(time);
        getChildren().add(kelompok);

        // Set Center
        setAlignment(Pos.CENTER);
        setSpacing(5);
        setMinHeight(665);
        setStyle("-fx-background-color: #F3F9FB;");

        // Show Time
        Timenow();
    }

    private void Timenow(){
        Thread thread = new Thread(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMMM YYYY\nHH:mm:ss");
            while(!stop){
                try {
                    Thread.sleep(100);
                } catch (Exception e){
                    System.out.println(e);
                }
                final String timenow = sdf.format(new Date());
                Platform.runLater(()->{
                    time.setText(timenow);
                });
            }
        });
        thread.start();
    }

    public void setStop(boolean stop){
        this.stop = true;
    }
}
