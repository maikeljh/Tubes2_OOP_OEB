package Plugin.PluginCurrency;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement
class Currency implements Serializable {
    private String currency;
    private double kurs;

    public Currency(){}

    public Currency(String currency, double kurs){
        this.currency = currency;
        this.kurs = kurs;
    }

    public String getCurrency() {
        return currency;
    }

    public double getKurs() {
        return kurs;
    }

    public void setCurrency(String currency){
        this.currency = currency;
    }

    public void setKurs(double kurs){
        this.kurs = kurs;
    }
}