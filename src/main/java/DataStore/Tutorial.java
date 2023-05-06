package DataStore;
import System.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.image.Image;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/* DOKUMENTASI CARA READ DAN WRITE DATA + PRINT PDF */
public class Tutorial {
    public static <XMLElement> void tes() throws DocumentException, FileNotFoundException {
        // 1. Setup data yang ingin ditulis
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

        Inventory<FixedBill> customerBill = new Inventory<FixedBill>();
        customerBill.addElement(bill2);

        Customer customer1 = new Customer(bill);
        Customer customer2 = new Customer(bill2);
        Customer customer3 = new Customer(bill);
        Customer customer4 = new Customer(bill2);

        Inventory<Customer> customers = new Inventory<Customer>();
        customers.addElement(customer1);
        customers.addElement(customer2);
        customers.addElement(customer3);
        customers.addElement(customer4);

        Image image = new Image("/images/dummy.png");
        Item tes = new Item("Cappucino", 1, 2, 1, "Drinks", image);
        Item tes1 = new Item("Tea", 2, 3, 2, "Drinks", image);
        Item tes2 = new Item("Sake", 3, 4, 3, "Drinks", image);
        Item tes3 = new Item("Whiskey", 4, 6, 4, "Drinks", image);
        Item tes4 = new Item("Wine", 5, 8, 5, "Drinks", image);

        PurchasedItem item1 = new PurchasedItem(tes, 2);
        PurchasedItem item2 = new PurchasedItem(tes1, 3);
        PurchasedItem item3 = new PurchasedItem(tes2, 4);
        PurchasedItem item4 = new PurchasedItem(tes3, 6);
        PurchasedItem item5 = new PurchasedItem(tes4, 10);

        bill.getItems().addElement(item1);
        bill.getItems().addElement(item2);
        bill.getItems().addElement(item3);
        bill.getItems().addElement(item4);
        bill.getItems().addElement(item5);

        bill2.getItems().addElement(item1);
        bill2.getItems().addElement(item2);
        bill2.getItems().addElement(item3);

        // 2. Create DataStore (Ini buatnya di class ApplicationBNMOStore untuk setiap class yang diperlukan)
        DataStore<FixedBill> ds = new DataStore<FixedBill> ();
        DataStore<Customer> dataCustomer = new DataStore<Customer>();

        // 3. Cara Read dan Write Data di XML
        // Buat Format masih manual karena belum ada settings
        /*
        XMLAdapter testing = new XMLAdapter();
        ds.setAdapter(testing);
        ds.saveData("bill", "xml", new Class<?>[]{Inventory.class, FixedBill.class, PurchasedItem.class}, billList);
        Inventory<FixedBill> hasil = ds.loadData("bill", "xml", new Class<?>[]{Inventory.class, FixedBill.class, PurchasedItem.class});

        XMLAdapter testCustomer = new XMLAdapter();
        dataCustomer.setAdapter(testCustomer);
        dataCustomer.saveData("customer.xml", new Class<?>[] {Inventory.class, Customer.class, FixedBill.class, PurchasedItem.class}, customers);


        // 4. Cara Read dan Write Data di JSON
        JSONAdapter testing1 = new JSONAdapter();
        ds.setAdapter(testing1);
        ds.saveData("bill", "json", new Class<?>[]{Inventory.class, FixedBill.class}, billList);
        Inventory<FixedBill> hasil1 = ds.loadData("bill", "json", new Class<?>[]{Inventory.class, FixedBill.class});

        // 5. Cara Read dan Write Data di OBJ
        OBJAdapter testing2 = new OBJAdapter();
        ds.setAdapter(testing2);
        ds.saveData("bill", "txt", new Class<?>[]{Inventory.class, FixedBill.class, PurchasedItem.class}, billList);
        Inventory<FixedBill> hasil2 = ds.loadData("bill.txt", "txt", new Class<?>[]{Inventory.class, FixedBill.class, PurchasedItem.class});

        // 6. Cara Print PDF
        Document document = new Document();

        // Tentuin Lokasi + Nama File PDF
        PdfWriter.getInstance(document, new FileOutputStream("src/main/resources/files/Sales Report.pdf"));

        // Open PDFnya
        document.open();

        // Ini paragraph buat bikin teks bebas yang diperlukan
        // Ini cara bikin font
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 28, Font.BOLD, BaseColor.BLACK);
        Paragraph title = new Paragraph(new Phrase("Sales Report", titleFont));

        // Bisa styling juga kalau perlu
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(30);

        // Ini contoh penambahan elemen ke PDFnya
        document.add(title);

        // Ini buat bikin tabel, tentuin banyak kolomnya + lebarnya dulu
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);

        // Tambahin kolom utama (header)
        // Bikin font untuk header
        Font header = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);

        PdfPCell id = new PdfPCell(new Phrase("Product ID", header));
        PdfPCell name = new PdfPCell(new Phrase("Product Name", header));
        PdfPCell buyPrice = new PdfPCell(new Phrase("Buy Price", header));
        PdfPCell sellPrice = new PdfPCell(new Phrase("Sell Price", header));
        PdfPCell quantity = new PdfPCell(new Phrase("Quantity", header));
        PdfPCell gross_profit = new PdfPCell(new Phrase("Gross Profit", header));
        PdfPCell net_profit = new PdfPCell(new Phrase("Net Profit", header));

        // Styling header
        id.setHorizontalAlignment(Element.ALIGN_CENTER);
        id.setBackgroundColor(new BaseColor(0x8C, 0xAE, 0xBB));
        id.setPadding(5);

        name.setHorizontalAlignment(Element.ALIGN_CENTER);
        name.setBackgroundColor(new BaseColor(0x8C, 0xAE, 0xBB));
        name.setPadding(5);

        sellPrice.setHorizontalAlignment(Element.ALIGN_CENTER);
        sellPrice.setBackgroundColor(new BaseColor(0x8C, 0xAE, 0xBB));
        sellPrice.setPadding(5);

        buyPrice.setHorizontalAlignment(Element.ALIGN_CENTER);
        buyPrice.setBackgroundColor(new BaseColor(0x8C, 0xAE, 0xBB));
        buyPrice.setPadding(5);

        quantity.setHorizontalAlignment(Element.ALIGN_CENTER);
        quantity.setBackgroundColor(new BaseColor(0x8C, 0xAE, 0xBB));
        quantity.setPadding(5);

        gross_profit.setHorizontalAlignment(Element.ALIGN_CENTER);
        gross_profit.setBackgroundColor(new BaseColor(0x8C, 0xAE, 0xBB));
        gross_profit.setPadding(5);

        net_profit.setHorizontalAlignment(Element.ALIGN_CENTER);
        net_profit.setBackgroundColor(new BaseColor(0x8C, 0xAE, 0xBB));
        net_profit.setPadding(5);

        // Tambahin kolom header ke tabel
        table.addCell(id);
        table.addCell(name);
        table.addCell(buyPrice);
        table.addCell(sellPrice);
        table.addCell(quantity);
        table.addCell(gross_profit);
        table.addCell(net_profit);

        // Iterasi setiap item
        int idx = 0;
        double totalGrossProfit = 0;
        double totalNetProfit = 0;

        // Tambahin cell untuk tiap kolom secara terurut
        for (PurchasedItem item : hasil2.getElement(0).getItems().getBox()) {
            PdfPCell itemId = new PdfPCell(new Phrase(String.valueOf(item.getItemID())));
            PdfPCell itemName = new PdfPCell(new Phrase(item.getName()));
            PdfPCell itemBuyPrice = new PdfPCell(new Phrase(String.valueOf(item.getBuyPrice())));
            PdfPCell itemSellPrice = new PdfPCell(new Phrase(String.valueOf(item.getSellPrice())));
            PdfPCell itemQuantity = new PdfPCell(new Phrase(String.valueOf(item.getQuantity())));
            PdfPCell itemGrossProfit = new PdfPCell(new Phrase(String.valueOf(item.calculateGrossProfit())));
            PdfPCell itemNetProfit = new PdfPCell(new Phrase(String.valueOf(item.calculateNetProfit())));

            // Styling cellnya
            itemId.setHorizontalAlignment(Element.ALIGN_CENTER);
            itemId.setPadding(5);

            itemName.setHorizontalAlignment(Element.ALIGN_CENTER);
            itemName.setPadding(5);

            itemBuyPrice.setHorizontalAlignment(Element.ALIGN_CENTER);
            itemBuyPrice.setPadding(5);

            itemSellPrice.setHorizontalAlignment(Element.ALIGN_CENTER);
            itemSellPrice.setPadding(5);

            itemQuantity.setHorizontalAlignment(Element.ALIGN_CENTER);
            itemQuantity.setPadding(5);

            itemGrossProfit.setHorizontalAlignment(Element.ALIGN_CENTER);
            itemGrossProfit.setPadding(5);

            itemNetProfit.setHorizontalAlignment(Element.ALIGN_CENTER);
            itemNetProfit.setPadding(5);

            // Styling row berbeda sesuai Figma
            if(idx % 2 != 0){
                itemId.setBackgroundColor(new BaseColor(0xC8, 0xDF, 0xE8));
                itemName.setBackgroundColor(new BaseColor(0xC8, 0xDF, 0xE8));
                itemBuyPrice.setBackgroundColor(new BaseColor(0xC8, 0xDF, 0xE8));
                itemSellPrice.setBackgroundColor(new BaseColor(0xC8, 0xDF, 0xE8));
                itemQuantity.setBackgroundColor(new BaseColor(0xC8, 0xDF, 0xE8));
                itemGrossProfit.setBackgroundColor(new BaseColor(0xC8, 0xDF, 0xE8));
                itemNetProfit.setBackgroundColor(new BaseColor(0xC8, 0xDF, 0xE8));
            }
            idx++;

            // Sum total gross and net profit
            totalGrossProfit += item.calculateGrossProfit();
            totalNetProfit += item.calculateNetProfit();

            // Tambahin tiap cell ke tabel
            table.addCell(itemId);
            table.addCell(itemName);
            table.addCell(itemBuyPrice);
            table.addCell(itemSellPrice);
            table.addCell(itemQuantity);
            table.addCell(itemGrossProfit);
            table.addCell(itemNetProfit);
        }

        // Bikin row untuk total
        PdfPCell totalLabelCell = new PdfPCell(new Phrase("Total", header));
        PdfPCell totalGrossProfit = new PdfPCell(new Phrase(String.valueOf(totalGrossProfit), header));
        PdfPCell totalNetProfit = new PdfPCell(new Phrase(String.valueOf(totalNetProfit), header));

        // Styling Row Total
        totalLabelCell.setColspan(5);
        totalLabelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        totalLabelCell.setBackgroundColor(new BaseColor(0x8C, 0xAE, 0xBB));
        totalLabelCell.setPadding(5);

        totalGrossProfit.setBackgroundColor(new BaseColor(0x8C, 0xAE, 0xBB));
        totalGrossProfit.setHorizontalAlignment(Element.ALIGN_CENTER);

        totalNetProfit.setBackgroundColor(new BaseColor(0x8C, 0xAE, 0xBB));
        totalNetProfit.setHorizontalAlignment(Element.ALIGN_CENTER);

        // Tambahin row terakhir ke tabel
        table.addCell(totalLabelCell);
        table.addCell(totalGrossProfit);
        table.addCell(totalNetProfit);

        // Tambahin tabel ke dokumen
        document.add(table);

        // Close dokumen
        document.close();
        */
    }
}
