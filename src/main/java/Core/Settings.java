package Core;

import Plugin.PluginManager;
import Plugin.Plugin;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@XmlRootElement
public class Settings implements Serializable {
    /* Attributes */
    private PluginManager pluginManager;
    private String format;
    private String saveDirectory;

    public Settings(){
        format = "obj";
        saveDirectory = "src/main/resources/files/";
        pluginManager = new PluginManager();
    }
}