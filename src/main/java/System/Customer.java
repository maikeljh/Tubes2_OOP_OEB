package System;

import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Data
@XmlRootElement
public class Customer implements Serializable {
    protected int id;
    private static int customerCount = 1;
    protected Inventory<FixedBill> transaction;

    public Customer(int id) {
        this.id = id;
        this.transaction = new Inventory<FixedBill>();
    }
    public Customer(FixedBill bill) {
        this.id = customerCount;
        this.transaction = new Inventory<FixedBill>();
        this.transaction.addElement(bill);
        customerCount++;
        bill.setCustomerID(this.id);
    }

}
