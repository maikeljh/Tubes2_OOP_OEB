package System;

import java.io.Serializable;

public class Settings implements Serializable {
    /* attributes */
    //private Plugin plugin_list[];
    private String format;
    private String save_directory;

    /* methods */
    /*public void addPlugin(Plugin plugin){

    }
    public void removePlugin(Plugin plugin){

    }*/
    public String getFormat(){
        return format;
    }
    public String getSaveDirectory(){
        return save_directory;
    }
    public void setFormat(String format){
        this.format = format;
    }
    public void setSaveDirectory(String save_directory){
        this.save_directory = save_directory;
    }
}
