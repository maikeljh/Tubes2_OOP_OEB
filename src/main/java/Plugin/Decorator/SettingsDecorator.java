package Plugin.Decorator;

import UI.SettingsPage;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SettingsDecorator extends Decorator<SettingsPage> {
    public SettingsDecorator(){}

    // Method needs to be overridden or else executes nothing
    public void execute(){}

    // Page has methods
    // 1. getOptions() = VBox -> Add more settings options
    // 2. getDetails() = VBox -> Add detail for settings options
}
