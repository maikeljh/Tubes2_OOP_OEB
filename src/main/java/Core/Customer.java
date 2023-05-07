package Core;

import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Data
@XmlRootElement
public class Customer implements Serializable {
    protected int id;
    private static int customerCount = 0;
    protected Inventory<Bill> transaction;

    public Customer(int id) {
        this.id = id;
        this.transaction = new Inventory<Bill>();
    }

    public Customer(Bill bill) {
        customerCount++;
        this.id = customerCount;
        this.transaction = new Inventory<Bill>();
        this.transaction.addElement(bill);
        bill.setCustomerID(this.id);
    }

    public static void setCustomerCount(int count){
        customerCount = count;
    }
}
