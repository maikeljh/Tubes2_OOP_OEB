package Plugin;

import jakarta.xml.bind.annotation.XmlRootElement;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import Core.Inventory;
import Core.PurchasedItem;

@XmlRootElement
public class BasePlugin extends Plugin {
    protected Inventory<PurchasedItem> items;

    public Node initialize(){
        return new VBox();
    }

    public Inventory<PurchasedItem> getItems(){
        return this.items;
    }

    public void setItems(Inventory<PurchasedItem> items){
        this.items = items;
    }
}