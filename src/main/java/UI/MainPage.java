package UI;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainPage extends VBox {
    private volatile boolean stop = false;
    private Text time;
    private Text kelompok;
    public MainPage() {

        // Create Logo
        ImageView logo = new ImageView(new Image("/images/icon/mainLogo.png"));
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
        getChildren().add(logo);
        getChildren().add(time);
        getChildren().add(kelompok);

        // Set Center
        setAlignment(Pos.CENTER);
        setSpacing(5);
        setMinHeight(665);

        // Set background image
        //BackgroundImage backgroundImage = new BackgroundImage(new Image("/images/icon/mainBackground.png");
        BackgroundImage bgImage = new BackgroundImage(new Image("/images/icon/mainBackground.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        setBackground(new Background(bgImage));
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

    public void setStop(){
        this.stop = true;
    }
}
