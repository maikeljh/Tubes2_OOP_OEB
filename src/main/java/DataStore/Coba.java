package DataStore;
import System.PurchasedItem;
import System.Item;
import javafx.scene.image.Image;

import System.FixedBill;
import System.Inventory;

public class Coba {
    public static <XMLElement> void tes() {
        // Step 1: Create a Java class for the root element
        FixedBill bill = new FixedBill();
        bill.setTotalPrice(100.00);
        bill.setDiscount(10.00);
        bill.setDate("2023-2-12");
        bill.setCustomerID(1234);
        bill.setBillID(5678);

        FixedBill bill2 = new FixedBill();
        bill2.setTotalPrice(100.00);
        bill2.setDiscount(10.00);
        bill2.setDate("2023-2-12");
        bill2.setCustomerID(234);
        bill2.setBillID(5679);

        Inventory<FixedBill> billList = new Inventory<FixedBill>();
        billList.addElement(bill);
        billList.addElement(bill2);

        // Step 2: Create Java classes for the child elements
        Image image = new Image("/images/dummy.png");
        Item tes = new Item("Cappucino", 1, 1, 1, "Drinks", image);
        PurchasedItem item1 = new PurchasedItem(tes, 2);
        PurchasedItem item2 = new PurchasedItem(tes, 3);
        PurchasedItem item3 = new PurchasedItem(tes, 4);

        bill.getItems().addElement(item1);
        bill.getItems().addElement(item2);
        bill.getItems().addElement(item3);

        bill2.getItems().addElement(item1);
        bill2.getItems().addElement(item2);
        bill2.getItems().addElement(item3);

        // Create DataStore
        DataStore<FixedBill> ds = new DataStore<FixedBill> ();

        // TESTING XML
        XMLAdapter<FixedBill> testing = new XMLAdapter<FixedBill>();
        ds.setAdapter(testing);
        ds.getDataAdapter().writeData("src/main/resources/files/bill.xml", FixedBill.class, new Class<?>[]{Inventory.class, FixedBill.class, PurchasedItem.class}, billList);
        Inventory<FixedBill> hasil = testing.readData("src/main/resources/files/bill.xml", FixedBill.class, new Class<?>[]{Inventory.class, FixedBill.class, PurchasedItem.class});
        for(FixedBill anjay : hasil.getList()){
            System.out.println(anjay.getBillID());
        }

        // TESTING JSON
        JSONAdapter<FixedBill> testing1 = new JSONAdapter<FixedBill>();
        ds.setAdapter(testing1);
        ds.getDataAdapter().writeData("src/main/resources/files/bill.json", FixedBill.class, new Class<?>[]{PurchasedItem.class}, billList);
        Inventory<FixedBill> hasil1 = testing1.readData("src/main/resources/files/bill.json", FixedBill.class, new Class<?>[]{PurchasedItem.class});
        for(FixedBill anjay : hasil1.getList()){
            System.out.println(anjay.getBillID());
        }

        // TESTING OBJ
        OBJAdapter<FixedBill> testing2 = new OBJAdapter<FixedBill>();
        ds.setAdapter(testing2);
        ds.getDataAdapter().writeData("src/main/resources/files/bill.txt", FixedBill.class, new Class<?>[]{PurchasedItem.class}, billList);
        Inventory<FixedBill> hasil2 = testing2.readData("src/main/resources/files/bill.txt", FixedBill.class, new Class<?>[]{PurchasedItem.class});
        for(FixedBill anjay : hasil2.getList()){
            System.out.println(anjay.getBillID());
        }
    }
}
