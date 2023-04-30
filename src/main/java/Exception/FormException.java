package Exception;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class FormException extends Exception{
    @Override
    public void showError(){
        // Show alert
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        // Center the header and content text
        Label header = new Label("Fail to Add Item");
        header.setStyle("-fx-font-size: 1.5em;");
        header.setStyle("-fx-font-weight: bold;");
        header.setAlignment(Pos.CENTER);

        Text content = new Text("All fields must be filled!");
        content.setTextAlignment(TextAlignment.LEFT);

        // Set the content text as a graphic
        alert.getDialogPane().setContent(new VBox(header, content));
        alert.setHeaderText("");
        alert.showAndWait();
    }
}
