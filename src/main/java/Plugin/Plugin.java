package Plugin;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@XmlRootElement
public class Plugin implements Serializable {
    protected String pluginName;
    protected String nextPlugin;
    protected boolean mainPlugin;

    public Plugin(){}
}
