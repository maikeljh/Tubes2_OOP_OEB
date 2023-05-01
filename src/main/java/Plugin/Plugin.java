package Plugin;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement
public class Plugin implements Serializable {
    protected String pluginName;

    public Plugin(){}

    public void setPluginName(String pluginName){
        this.pluginName = pluginName;
    }

    public String getPluginName() {
        return this.pluginName;
    }
}
