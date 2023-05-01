package Testing;
import System.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class VIPTesting {
    @Test
    public void testCalculatePoint() {
        VIP vip = new VIP();
        vip.setPoint(0);
        vip.calculatePoint(150000);
        assertEquals(1500, vip.getPoint());
    }

    @Test
    public void testCalculateDiscount() {
        VIP vip = new VIP();
        vip.setPoint(3000);
        assertEquals(87000, (int) vip.calculateDiscount(100000, true));
        assertEquals(90000, (int) vip.calculateDiscount(100000, false));
    }
}
