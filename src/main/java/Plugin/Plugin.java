package Plugin;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Plugin {
    private String pluginName;

    public Plugin(){}

    public void setPluginName(String pluginName){
        this.pluginName = pluginName;
    }

    public String getPluginName() {
        return this.pluginName;
    }
}
