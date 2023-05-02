package Plugin.Decorator;

import Plugin.Plugin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.xml.bind.annotation.XmlTransient;

import java.io.Serializable;

public abstract class Decorator<T> extends Plugin implements Serializable {
    protected transient T page = null;

    public Decorator(){}

    @JsonIgnore
    @XmlTransient
    public T getPage(){
        return this.page;
    }

    public void setPage(T page){
        this.page = page;
    }

    // Method needs to be overridden
    public abstract void execute();
}
