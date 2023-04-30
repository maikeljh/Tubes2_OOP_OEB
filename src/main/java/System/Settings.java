package System;

import Plugin.PluginManager;
import Plugin.Plugin;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement
public class Settings implements Serializable {
    /* Attributes */
    private PluginManager plugins;
    private String format;
    private String save_directory;

    public Settings(){
        format = "xml";
        save_directory = "src/main/resources/files/";
        plugins = new PluginManager();
    }

    /* Methods */
    public void removePlugin(Plugin plugin){

    }

    public String getFormat(){
        return format;
    }

    public String getSaveDirectory(){
        return save_directory;
    }

    public PluginManager getPluginManager(){
        return plugins;

    }
    public void setFormat(String format){
        this.format = format;
    }

    public void setSaveDirectory(String save_directory){
        this.save_directory = save_directory;
    }
}
