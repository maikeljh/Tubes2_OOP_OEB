package System;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FixedBill extends Bill{
    /* attributes */
    private int bill_id;

    public FixedBill(){
        super();
    }
    public FixedBill(int bill_id) { super(); this.bill_id = bill_id; }
    public FixedBill(int bill_id, String date, int customer_id) { super(date, customer_id); this.bill_id = bill_id; }

    /* methods */
    public int getBillID(){
        return bill_id;
    }

    public void setBillID(int bill_id){
        this.bill_id = bill_id;
    }
}
