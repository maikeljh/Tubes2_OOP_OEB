package Testing;
import Core.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class PurchasedItemTesting {
    @Test
    public void testCalculateGrossProfit() {
        PurchasedItem purchasedItem = new PurchasedItem();
        purchasedItem.setSellPrice(5000);
        purchasedItem.setQuantity(20);

        assertEquals(100000, (int) purchasedItem.calculateGrossProfit());
    }

    @Test
    public void testCalculateNetProfit() {
        PurchasedItem purchasedItem = new PurchasedItem();
        purchasedItem.setSellPrice(5000);
        purchasedItem.setQuantity(20);
        purchasedItem.setBuyPrice(3000);

        assertEquals(40000, (int) purchasedItem.calculateNetProfit());
    }

}
