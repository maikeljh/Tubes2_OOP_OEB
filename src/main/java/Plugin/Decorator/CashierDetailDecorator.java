package Plugin.Decorator;

import UI.CashierDetailPage;

import java.io.Serializable;

public class CashierDetailDecorator extends Decorator<CashierDetailPage> implements Serializable {
    public CashierDetailDecorator(){}

    // Method needs to be overridden or else executes nothing
    public void execute(){}
}
