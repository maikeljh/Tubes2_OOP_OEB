package Core;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;

@Getter @Setter
@XmlRootElement
public class Bill implements Serializable {
    /* attributes */
    private int billID;
    protected Inventory<PurchasedItem> items;
    protected double totalPrice;
    protected double discount;
    protected String date;
    protected int customerID;
    private static int billCount = 0;

    /* constructors */
    public Bill(){
        this.items = new Inventory<PurchasedItem>();
    }

    public Bill(int billID) {
        this.items = new Inventory<PurchasedItem>();
        this.billID = billID;
    }

    public Bill(String date, double totalPrice, double discount) {
        billCount++;
        this.date = date;
        this.items = new Inventory<PurchasedItem>();
        this.billID = billCount;
        this.totalPrice = totalPrice;
        this.discount = discount;
    }

    public Bill(String date, int customerID, double totalPrice, double discount) {
        billCount++;
        this.date = date;
        this.customerID = customerID;
        this.items = new Inventory<PurchasedItem>();
        this.billID = billCount;
        this.totalPrice = totalPrice;
        this.discount = discount;
    }

    /* methods */
    public void printBill() throws DocumentException, FileNotFoundException {
        //
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("src/main/resources/files/Bill" + billID + ".pdf"));
        document.open();

        // Add title
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLACK);
        Paragraph title = new Paragraph(new Phrase("GoShOOP", titleFont));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Add subtitle
        Font subtitleFont = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD, BaseColor.BLACK);
        Paragraph subtitle = new Paragraph(new Phrase("Fixed Bill", subtitleFont));
        subtitle.setAlignment(Element.ALIGN_CENTER);
        document.add(subtitle);

        // Add bill id label
        Font textFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
        Paragraph billIDLabel = new Paragraph(new Phrase("Bill ID: " + this.billID, textFont));
        subtitle.setAlignment(Element.ALIGN_CENTER);
        subtitle.setSpacingAfter(10);
        document.add(billIDLabel);

        // Add date
        Paragraph dateLabel = new Paragraph(new Phrase(this.date, textFont));
        dateLabel.setAlignment(Element.ALIGN_LEFT);
        document.add(dateLabel);

        // Add customer id
        Paragraph customerIDLabel = new Paragraph(new Phrase("Customer " + this.customerID, textFont));
        customerIDLabel.setAlignment(Element.ALIGN_LEFT);
        customerIDLabel.setSpacingAfter(10);
        document.add(customerIDLabel);

        // Add table
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);

        // Table header
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
        PdfPCell id = new PdfPCell(new Phrase("Product ID", headerFont));
        PdfPCell name = new PdfPCell(new Phrase("Product Name", headerFont));
        PdfPCell quantity = new PdfPCell(new Phrase("Quantity", headerFont));
        PdfPCell price = new PdfPCell(new Phrase("Price", headerFont));
        PdfPCell amount = new PdfPCell(new Phrase("Amount", headerFont));

        // Style table header
        id.setHorizontalAlignment(Element.ALIGN_CENTER);
        id.setVerticalAlignment(Element.ALIGN_MIDDLE);
        id.setBackgroundColor(new BaseColor(140, 174, 187));
        id.setPadding(5);

        name.setHorizontalAlignment(Element.ALIGN_CENTER);
        name.setVerticalAlignment(Element.ALIGN_MIDDLE);
        name.setBackgroundColor(new BaseColor(140, 174, 187));
        name.setPadding(5);

        quantity.setHorizontalAlignment(Element.ALIGN_CENTER);
        quantity.setVerticalAlignment(Element.ALIGN_MIDDLE);
        quantity.setBackgroundColor(new BaseColor(140, 174, 187));
        quantity.setPadding(5);

        price.setHorizontalAlignment(Element.ALIGN_CENTER);
        price.setVerticalAlignment(Element.ALIGN_MIDDLE);
        price.setBackgroundColor(new BaseColor(140, 174, 187));
        price.setPadding(5);

        amount.setHorizontalAlignment(Element.ALIGN_CENTER);
        amount.setVerticalAlignment(Element.ALIGN_MIDDLE);
        amount.setBackgroundColor(new BaseColor(140, 174, 187));
        amount.setPadding(5);

        // Add header cells to table
        table.addCell(id);
        table.addCell(name);
        table.addCell(quantity);
        table.addCell(price);
        table.addCell(amount);

        // Iterate every item
        int idx = 0;
        for (PurchasedItem item : items.getBox()) {
            // Add cell
            PdfPCell itemId = new PdfPCell(new Phrase(String.valueOf(item.getItemID())));
            PdfPCell itemName = new PdfPCell(new Phrase(item.getName()));
            PdfPCell itemQuantity = new PdfPCell(new Phrase(String.valueOf(item.getQuantity())));
            PdfPCell itemPrice = new PdfPCell(new Phrase(String.valueOf(item.getSellPrice())));
            PdfPCell itemAmount = new PdfPCell(new Phrase(String.valueOf(item.getQuantity() * item.getSellPrice())));

            // Style item's cells
            itemId.setHorizontalAlignment(Element.ALIGN_CENTER);
            itemId.setPadding(5);

            itemName.setHorizontalAlignment(Element.ALIGN_LEFT);
            itemName.setPadding(5);

            itemQuantity.setHorizontalAlignment(Element.ALIGN_CENTER);
            itemQuantity.setPadding(5);

            itemPrice.setHorizontalAlignment(Element.ALIGN_RIGHT);
            itemPrice.setPadding(5);

            itemAmount.setHorizontalAlignment(Element.ALIGN_RIGHT);
            itemAmount.setPadding(5);

            // Style row background colors
            if(idx % 2 != 0){
                itemId.setBackgroundColor(new BaseColor(200, 223, 232));
                itemName.setBackgroundColor(new BaseColor(200, 223, 232));
                itemQuantity.setBackgroundColor(new BaseColor(200, 223, 232));
                itemPrice.setBackgroundColor(new BaseColor(200, 223, 232));
                itemAmount.setBackgroundColor(new BaseColor(200, 223, 232));
            } else {
                itemId.setBackgroundColor(new BaseColor(243, 249, 251));
                itemName.setBackgroundColor(new BaseColor(243, 249, 251));
                itemQuantity.setBackgroundColor(new BaseColor(243, 249, 251));
                itemPrice.setBackgroundColor(new BaseColor(243, 249, 251));
                itemAmount.setBackgroundColor(new BaseColor(243, 249, 251));
            }
            idx++;

            // Add cells to table
            table.addCell(itemId);
            table.addCell(itemName);
            table.addCell(itemQuantity);
            table.addCell(itemPrice);
            table.addCell(itemAmount);
        }

        // Create subtotal row
        PdfPCell subtotalLabelCell = new PdfPCell(new Phrase("Subtotal", headerFont));
        PdfPCell subtotalCell = new PdfPCell(new Phrase(String.valueOf(this.totalPrice), headerFont));

        // Style subtotal row
        subtotalLabelCell.setColspan(4);
        subtotalLabelCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        subtotalLabelCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        subtotalLabelCell.setBackgroundColor(new BaseColor(140, 174, 187));
        subtotalLabelCell.setPadding(5);

        subtotalCell.setBackgroundColor(new BaseColor(140, 174, 187));
        subtotalCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        subtotalCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        // Add total row cells to table
        table.addCell(subtotalLabelCell);
        table.addCell(subtotalCell);

        if (this.discount > 0){
            // Create discount row
            PdfPCell discountLabelCell = new PdfPCell(new Phrase("Discount " + this.discount*100 + "%", headerFont));
            PdfPCell discountCell = new PdfPCell(new Phrase(String.valueOf(this.calculateDiscount()), headerFont));

            // Style subtotal row
            discountLabelCell.setColspan(4);
            discountLabelCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            discountLabelCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            discountLabelCell.setBackgroundColor(new BaseColor(140, 174, 187));
            discountLabelCell.setPadding(5);

            discountCell.setBackgroundColor(new BaseColor(140, 174, 187));
            discountCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            discountCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

            // Add total row cells to table
            table.addCell(discountLabelCell);
            table.addCell(discountCell);
        }

        // Create total row
        PdfPCell totalLabelCell = new PdfPCell(new Phrase("Total", headerFont));
        PdfPCell totalCell = new PdfPCell(new Phrase(String.valueOf(this.calculateChargePrice()), headerFont));

        // Style subtotal row
        totalLabelCell.setColspan(4);
        totalLabelCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        totalLabelCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        totalLabelCell.setBackgroundColor(new BaseColor(140, 174, 187));
        totalLabelCell.setPadding(5);

        totalCell.setBackgroundColor(new BaseColor(140, 174, 187));
        totalCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        totalCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        // Add total row cells to table
        table.addCell(totalLabelCell);
        table.addCell(totalCell);

        // Add table to document
        document.add(table);

        document.close();
    }

    public PurchasedItem getItem(int idx){
        return items.getElement(idx);
    }

    public double calculateDiscount(){
        return discount * totalPrice;
    }

    public double calculateChargePrice(){
        return totalPrice - calculateDiscount();
    }

    public static void setBillCount(int count){
        billCount = count;
    }
}
