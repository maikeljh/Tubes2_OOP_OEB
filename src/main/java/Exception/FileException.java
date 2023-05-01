package Exception;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class FileException extends Exception{
    @Override
    public void showError(){
        // Show alert
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("File Error");
        // Center the header and content text
        Label header = new Label("Invalid File");
        header.setStyle("-fx-font-size: 1.5em;");
        header.setStyle("-fx-font-weight: bold;");
        header.setAlignment(Pos.CENTER);

        Text content = new Text("File not found. Please check if the file exist.");
        content.setTextAlignment(TextAlignment.CENTER);

        // Set the content text as a graphic
        alert.getDialogPane().setContent(new VBox(header, content));
        alert.setHeaderText("");
        alert.showAndWait();
    }
}
