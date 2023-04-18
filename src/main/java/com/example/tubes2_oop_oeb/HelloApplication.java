package com.example.tubes2_oop_oeb;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HelloApplication extends Application {
    private volatile boolean stop = false;
    private Text time;
    private Text kelompok;
    private Text name;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Group root = new Group();
        Scene scene = new Scene(root, 800, 500);
        name = new Text();
        name.setX(370);
        name.setY(120);
        name.setText("OEB");
        name.setFont(Font.font("Montserrat", FontWeight.BOLD, 40));
        name.setTextAlignment(TextAlignment.CENTER);
        time = new Text();
        time.setX(250);
        time.setY(180);
        time.setFont(Font.font("Montserrat", 40));
        time.setTextAlignment(TextAlignment.CENTER);
        kelompok = new Text();
        kelompok.setX(240);
        kelompok.setY(280);
        kelompok.setText("13521059 / Arleen Chrysantha Gunardi\n" +
                "13521124 / Michael Jonathan Halim\n" +
                "13521127 / Marcel Ryan Antony\n" +
                "13521143 / Raynard Tanadi\n" +
                "13521145 / Kenneth Dave Bahana\n");
        kelompok.setFont(Font.font("Poppins", 20));
        root.getChildren().add(name);
        root.getChildren().add(time);
        root.getChildren().add(kelompok);
        stage.setTitle("OEB");
        stage.setScene(scene);
        Timenow();
        stage.show();
        stage.setOnCloseRequest(event -> {
            stop = true;
        });
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
    public static void main(String[] args) {
        launch();
    }
}