package DataStore;

import jakarta.xml.bind.annotation.XmlElement;
import java.util.List;

public class ObjectList<T> {
    private List<T> objectList;

    @XmlElement(name = "object")
    public List<T> getObjectList() {
        return this.objectList;
    }

    public void setObjects(List<T> objects) {
        this.objectList = objects;
    }
}
