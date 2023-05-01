package Plugin.Decorator;

import UI.SettingsPage;
import Plugin.Plugin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class SettingsDecorator extends Plugin {
    protected transient SettingsPage page = null;

    public SettingsDecorator(){}

    @JsonIgnore
    @XmlTransient
    public SettingsPage getPage(){
        return this.page;
    }

    public void setPage(SettingsPage page){
        this.page = page;
    }

    // Method needs to be overridden or else executes nothing
    public void execute(){}

    // Page has method
    // 1. getOptions() = VBox -> Add more settings options
    // 2. getDetails() = VBox -> Add detail for settings options
}
