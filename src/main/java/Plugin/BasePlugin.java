package Plugin;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class BasePlugin extends Plugin {

    public Node initialize(){
        return new VBox();
    }

}
