package Plugin.PluginCashier;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement
public class TaxAndService implements Serializable {
    private double tax;
    private double service;

    public TaxAndService(){
        tax = 0;
        service = 0;
    }

    public TaxAndService(double tax, double service){
        this.tax = tax;
        this.service = service;
    }

    public double getService() {
        return service;
    }

    public double getTax() {
        return tax;
    }

    public void setService(double service) {
        this.service = service;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }
}
