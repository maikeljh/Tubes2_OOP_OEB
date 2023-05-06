package UI;

import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import System.Settings;
import DataStore.DataStore;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Page extends VBox {
    protected Stage stage;
    protected Tab tab;
    protected Settings settings;
    protected DataStore<Settings> settingsDS;

    public Page(Stage stage, Tab tab, Settings settings, DataStore<Settings> settingsDS){
        this.stage = stage;
        this.tab = tab;
        this.settings = settings;
        this.settingsDS = settingsDS;
    }
}
