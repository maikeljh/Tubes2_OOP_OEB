package DataStore;
import System.PurchasedItem;
import System.Item;
import javafx.scene.image.Image;

import java.io.File;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement
class Bill {

    private double totalPrice;
    private double discount;
    private String date;
    private String customerId;
    private String billId;
    private List<Item> items = new ArrayList<Item>();

    // default constructor required by JAXB
    public Bill() {}

    public Bill(double totalPrice, double discount, String date, String customerId, String billId, List<Item> items) {
        this.totalPrice = totalPrice;
        this.discount = discount;
        this.date = date;
        this.customerId = customerId;
        this.billId = billId;
        this.items = items;
    }

    @XmlElement(name = "total_price")
    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @XmlElement(name = "customer_id")
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @XmlElement(name = "bill_id")
    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    @XmlElement(name = "item")
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}

public class Coba {
    public static void tes() {
        try {
            // Step 1: Create a Java class for the root element
            Bill bill = new Bill();
            bill.setTotalPrice(100.00);
            bill.setDiscount(10.00);
            bill.setDate("2023-04-22");
            bill.setCustomerId("1234");
            bill.setBillId("5678");

            // Step 2: Create Java classes for the child elements
            Image image = new Image("/images/dummy.png");
            Item tes = new Item("Cappucino", 1, 1, 1, "Drinks", image);
            PurchasedItem item1 = new PurchasedItem(tes, 2);

            PurchasedItem item2 = new PurchasedItem(tes, 3);

            PurchasedItem item3 = new PurchasedItem(tes, 4);

            bill.getItems().add(item1);
            bill.getItems().add(item2);
            bill.getItems().add(item3);

            // Step 5: Create a JAXB context
            JAXBContext jaxbContext = JAXBContext.newInstance(Bill.class);

            // Step 6: Create a Marshaller object from the JAXB context
            Marshaller marshaller = jaxbContext.createMarshaller();

            // Step 7: Set the Marshaller properties
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            // Step 8: Call the Marshaller's marshal method
            File outputFile = new File("./src/main/resources/files/bill.xml");
            marshaller.marshal(bill, outputFile);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
