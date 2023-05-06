package Plugin.PluginCashier;

import Plugin.Decorator.SettingsDecorator;
import Plugin.Plugin;
import Plugin.PluginManager;
import System.Inventory;
import System.Settings;
import DataStore.DataStore;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class TaxAndServiceSettings extends SettingsDecorator implements Serializable {
    private TaxAndService currentTAS;
    private DataStore<TaxAndService> TASStore;

    public TaxAndServiceSettings() {
        this.pluginName = "Plugin Tax & Service";
        this.nextPlugin = null;
        this.currentTAS = new TaxAndService();
        this.TASStore = new DataStore<TaxAndService>();
    }

    @Override
    public void execute() {
        // Try to load current TAS
        try {
            currentTAS = TASStore.loadData("taxAndService", this.page.getSettings(), new Class<?>[]{Inventory.class, TaxAndService.class}).getElement(0);
        } catch (Exception e){
            // Do nothing
        }

        // Create new settings option
        Button optionButton = new Button("Tax & Service");
        ImageView iconDiscount = new ImageView(new Image("/images/icon/taxservice.png"));

        // Button style
        optionButton.setPadding(new Insets(10, 0, 10, 65));
        optionButton.setFont(Font.font("Montserrat", FontWeight.SEMI_BOLD, 20));
        optionButton.setStyle("-fx-border-color: transparent; -fx-background-color: #FFFFFF");
        optionButton.setOnMouseEntered(event -> optionButton.setStyle("-fx-background-color: #C8DFE8"));
        optionButton.setOnMouseExited(event -> optionButton.setStyle("-fx-border-color: transparent; -fx-background-color: #FFFFFF"));
        optionButton.setGraphic(iconDiscount);
        optionButton.setPrefWidth(500);
        optionButton.setGraphicTextGap(20);
        optionButton.setContentDisplay(ContentDisplay.LEFT);
        optionButton.setAlignment(Pos.CENTER_LEFT);

        // When option button clicked
        optionButton.setOnAction(event -> {
            // Remove old details
            VBox details = this.page.getDetails();
            this.page.getChildren().remove(details);

            // Styling new details
            details.setPadding(new Insets(30, 50, 10, 50));
            details.setSpacing(35);
            details.getChildren().clear();

            // Create main layout for details
            VBox taxServiceBox = new VBox();

            // Add title setting
            Label title = new Label("Tax & Service");
            title.setFont(Font.font("Montserrat", FontWeight.SEMI_BOLD, 30));

            // Create tax box
            VBox taxBox = new VBox();

            Label taxLabel = new Label("Tax");
            taxLabel.setFont(Font.font("Montserrat", FontWeight.SEMI_BOLD, 20));

            TextField taxInput = new TextField(String.valueOf(this.currentTAS.getTax()));

            taxBox.getChildren().addAll(taxLabel, taxInput);
            taxBox.setSpacing(10);

            // Create service box
            VBox serviceBox = new VBox();

            Label serviceLabel = new Label("Service");
            serviceLabel.setFont(Font.font("Montserrat", FontWeight.SEMI_BOLD, 20));

            TextField serviceInput = new TextField(String.valueOf(this.currentTAS.getService()));

            serviceBox.getChildren().addAll(serviceLabel, serviceInput);
            serviceBox.setSpacing(10);

            // Create save button
            Button save = new Button("Save");

            // Add event onclick button save
            save.setOnAction(e -> {
                currentTAS.setTax(Double.parseDouble(taxInput.getText()));
                currentTAS.setService(Double.parseDouble(serviceInput.getText()));

                // Save tax and service
                Inventory<TaxAndService> saveTAS = new Inventory<TaxAndService>();
                saveTAS.addElement(currentTAS);
                TASStore.saveData("taxAndService", this.page.getSettings(), new Class<?>[]{Inventory.class, TaxAndService.class}, saveTAS);

                // Save settings
                Settings temp = new Settings();
                Inventory<Settings> tempSettings = new Inventory<Settings>();
                tempSettings.addElement(this.page.getSettings());
                List<Class<?>> clazzes = this.getPage().getSettings().getPluginManager().getClazzes();
                Class<?>[] classesArray = clazzes.toArray(new Class<?>[clazzes.size()]);
                Class<?>[] others = {Inventory.class, Settings.class, PluginManager.class, Plugin.class};
                Class<?>[] concatenated = Arrays.copyOf(classesArray, classesArray.length + others.length);
                System.arraycopy(others, 0, concatenated, classesArray.length, others.length);

                this.page.getSettingsDS().saveData("settings", temp, concatenated, tempSettings);
            });

            // Styling save button
            save.setFont(Font.font("Montserrat", FontWeight.SEMI_BOLD, 14));
            save.setStyle("-fx-background-color: #C8DFE8");

            // Merge contents
            taxServiceBox.getChildren().addAll(title, taxBox, serviceBox, save);
            taxServiceBox.setSpacing(20);

            details.getChildren().addAll(taxServiceBox);
            this.page.getChildren().add(details);
        });

        this.page.getOptions().getChildren().add(optionButton);
    }
}
