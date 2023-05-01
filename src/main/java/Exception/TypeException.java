package Exception;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TypeException extends Exception{

    @Override
    public void showError(){
        // Show alert
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Type");
        // Center the header and content text
        Label header = new Label("Invalid Type");
        header.setStyle("-fx-font-size: 1.5em;");
        header.setStyle("-fx-font-weight: bold;");
        header.setAlignment(Pos.CENTER);

        Text content = new Text("Please enter a valid input of the expected type!");
        content.setTextAlignment(TextAlignment.CENTER);

        // Set the content text as a graphic
        alert.getDialogPane().setContent(new VBox(header, content));
        alert.setHeaderText("");
        alert.showAndWait();
    }
}
