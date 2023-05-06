package UI;

import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Core.Settings;
import DataStore.DataStore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class Page extends VBox implements Serializable {
    protected Stage stage;
    protected Tab tab;
    protected Settings settings;
    protected DataStore<Settings> settingsDS;
    protected boolean close;
    protected static boolean exit = false;

    public Page(Stage stage, Tab tab, Settings settings, DataStore<Settings> settingsDS){
        this.stage = stage;
        this.tab = tab;
        this.settings = settings;
        this.settingsDS = settingsDS;
        this.close = false;

        // Set stop if tab closed
        this.tab.setOnCloseRequest(event -> {
            this.close = true;
        });

        setMaxWidth(1280);
    }

    public static void setExit(boolean exit) {
        Page.exit = exit;
    }

    public static boolean isExit() {
        return exit;
    }
}
