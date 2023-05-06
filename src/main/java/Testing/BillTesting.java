package Testing;
import Core.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class BillTesting {
    @Test
    public void testCalculateDiscount() {
        Bill bill = new Bill();
        bill.setDiscount(0.2);
        bill.setTotalPrice(200000);

        assertEquals(40000, (int) bill.calculateDiscount());
    }

    @Test
    public void testCalculateChargePrice() {
        Bill bill = new Bill();
        bill.setDiscount(0.2);
        bill.setTotalPrice(200000);

        assertEquals(160000, (int) bill.calculateChargePrice());
    }
}
