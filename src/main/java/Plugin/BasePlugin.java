package Plugin;

import jakarta.xml.bind.annotation.XmlRootElement;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

@XmlRootElement
public class BasePlugin extends Plugin {

    public Node initialize(){
        return new VBox();
    }

}
