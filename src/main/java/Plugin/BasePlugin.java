package Plugin;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class BasePlugin {
    private String pluginName;

    public Node initialize(){
        return new VBox();
    }

    public void setPluginName(String pluginName){
        this.pluginName = pluginName;
    }

    public String getPluginName() {
        return this.pluginName;
    }
}
