package System;

import Plugin.PluginManager;
import Plugin.Plugin;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

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

    /* Methods */
    public void removePlugin(Plugin plugin){

    }

    public String getFormat(){
        return format;
    }

    public String getSaveDirectory(){
        return saveDirectory;
    }

    public PluginManager getPluginManager(){
        return pluginManager;

    }
    public void setFormat(String format){
        this.format = format;
    }

    public void setSaveDirectory(String saveDirectory){
        this.saveDirectory = saveDirectory;
    }

    public void setPluginManager(PluginManager pluginManager){
        this.pluginManager = pluginManager;
    }
}
