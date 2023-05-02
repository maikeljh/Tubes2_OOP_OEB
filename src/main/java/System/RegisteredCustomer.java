package System;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @Data
@XmlRootElement
public abstract class RegisteredCustomer extends Customer {
    protected String name;
    protected String phoneNumber;
    protected int point;
    protected boolean activeStatus;

    public RegisteredCustomer(int customerId, String name, String phoneNumber, FixedBill bill) {
        super(customerId);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.point = 0;
        this.activeStatus = true;
        this.transaction.addElement(bill);
    }

    public RegisteredCustomer(int customerId, String name, String phoneNumber, int point, boolean activeStatus) {
        super(customerId);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.point = point;
        this.activeStatus = activeStatus;
    }
    public boolean getActiveStatus() {
        return this.activeStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public void calculatePoint(double total_price) {
        this.setPoint(this.point += (int) (0.01 * total_price));
    }

    public abstract double calculateDiscount(double total_price, boolean point);

}
