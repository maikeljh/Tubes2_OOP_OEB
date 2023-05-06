package System;

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
public class SalesReport implements Serializable {
    /* attributes */
    private double totalGrossProfit;
    private double totalNetProfit;
    private Inventory<PurchasedItem> items;

    /* ctor */
    public SalesReport(){
        this.totalGrossProfit = 0;
        this.totalNetProfit = 0;
        items = new Inventory<PurchasedItem>();
    }

    /* methods */

    public int getElementIdx(String itemName){
        boolean found = false;
        int idx = 0;
        while (idx < items.getNeff() && !found){
            if (itemName == items.getElement(idx).getName()){
                found = true;
            } else {
                idx++;
            }
        }
        if (found){
            return idx;
        } else {
            return -1;
        }
    }

    public void updateReport(FixedBill fixedBill){
        // update items when a fixed bill is added
        for (PurchasedItem item : fixedBill.getItems().getList()){
            if (getElementIdx(item.getName()) == -1){
                // if item not in items list, then add item to list
                items.addElement(item);
            } else {
                // update item list
                int newQty = items.getElement(getElementIdx(item.getName())).getQuantity() + item.getQuantity();
                items.getElement(getElementIdx(item.getName())).setQuantity(newQty);
            }
            total_gross_profit += item.calculateGrossProfit();
            total_net_profit += item.calculateNetProfit();
        }
    }

    public void printReport() throws DocumentException, FileNotFoundException{
        // Create PDF for sales report
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("src/main/resources/files/Sales Report.pdf"));
        document.open();

        // Add title
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 28, Font.BOLD, BaseColor.BLACK);
        Paragraph title = new Paragraph(new Phrase("Sales Report", titleFont));
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(30);
        document.add(title);

        // Add table
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);

        // Table header
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
        PdfPCell id = new PdfPCell(new Phrase("Product ID", headerFont));
        PdfPCell name = new PdfPCell(new Phrase("Product Name", headerFont));
        PdfPCell buyPrice = new PdfPCell(new Phrase("Buy Price", headerFont));
        PdfPCell sellPrice = new PdfPCell(new Phrase("Sell Price", headerFont));
        PdfPCell quantity = new PdfPCell(new Phrase("Quantity", headerFont));
        PdfPCell gross_profit = new PdfPCell(new Phrase("Gross Profit", headerFont));
        PdfPCell net_profit = new PdfPCell(new Phrase("Net Profit", headerFont));

        // Style table header
        id.setHorizontalAlignment(Element.ALIGN_CENTER);
        id.setVerticalAlignment(Element.ALIGN_MIDDLE);
        id.setBackgroundColor(new BaseColor(140, 174, 187));
        id.setPadding(5);

        name.setHorizontalAlignment(Element.ALIGN_CENTER);
        name.setVerticalAlignment(Element.ALIGN_MIDDLE);
        name.setBackgroundColor(new BaseColor(140, 174, 187));
        name.setPadding(5);

        buyPrice.setHorizontalAlignment(Element.ALIGN_CENTER);
        buyPrice.setVerticalAlignment(Element.ALIGN_MIDDLE);
        buyPrice.setBackgroundColor(new BaseColor(140, 174, 187));
        buyPrice.setPadding(5);

        sellPrice.setHorizontalAlignment(Element.ALIGN_CENTER);
        sellPrice.setVerticalAlignment(Element.ALIGN_MIDDLE);
        sellPrice.setBackgroundColor(new BaseColor(140, 174, 187));
        sellPrice.setPadding(5);

        quantity.setHorizontalAlignment(Element.ALIGN_CENTER);
        quantity.setVerticalAlignment(Element.ALIGN_MIDDLE);
        quantity.setBackgroundColor(new BaseColor(140, 174, 187));
        quantity.setPadding(5);

        gross_profit.setHorizontalAlignment(Element.ALIGN_CENTER);
        gross_profit.setVerticalAlignment(Element.ALIGN_MIDDLE);
        gross_profit.setBackgroundColor(new BaseColor(140, 174, 187));
        gross_profit.setPadding(5);

        net_profit.setHorizontalAlignment(Element.ALIGN_CENTER);
        net_profit.setVerticalAlignment(Element.ALIGN_MIDDLE);
        net_profit.setBackgroundColor(new BaseColor(140, 174, 187));
        net_profit.setPadding(5);

        // Add header cells to table
        table.addCell(id);
        table.addCell(name);
        table.addCell(buyPrice);
        table.addCell(sellPrice);
        table.addCell(quantity);
        table.addCell(gross_profit);
        table.addCell(net_profit);

        // Iterate every item
        int idx = 0;
        for (PurchasedItem item : items.getList()) {
            // Add cell
            PdfPCell itemId = new PdfPCell(new Phrase(String.valueOf(item.getItemID())));
            PdfPCell itemName = new PdfPCell(new Phrase(item.getName()));
            PdfPCell itemBuyPrice = new PdfPCell(new Phrase(String.valueOf(item.getBuyPrice())));
            PdfPCell itemSellPrice = new PdfPCell(new Phrase(String.valueOf(item.getSellPrice())));
            PdfPCell itemQuantity = new PdfPCell(new Phrase(String.valueOf(item.getQuantity())));
            PdfPCell itemGrossProfit = new PdfPCell(new Phrase(String.valueOf(item.calculateGrossProfit())));
            PdfPCell itemNetProfit = new PdfPCell(new Phrase(String.valueOf(item.calculateNetProfit())));

            // Style item's cells
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

            // Style row background colors
            if(idx % 2 != 0){
                itemId.setBackgroundColor(new BaseColor(200, 223, 232));
                itemName.setBackgroundColor(new BaseColor(200, 223, 232));
                itemBuyPrice.setBackgroundColor(new BaseColor(200, 223, 232));
                itemSellPrice.setBackgroundColor(new BaseColor(200, 223, 232));
                itemQuantity.setBackgroundColor(new BaseColor(200, 223, 232));
                itemGrossProfit.setBackgroundColor(new BaseColor(200, 223, 232));
                itemNetProfit.setBackgroundColor(new BaseColor(200, 223, 232));
            } else {
                itemId.setBackgroundColor(new BaseColor(243, 249, 251));
                itemName.setBackgroundColor(new BaseColor(243, 249, 251));
                itemBuyPrice.setBackgroundColor(new BaseColor(243, 249, 251));
                itemSellPrice.setBackgroundColor(new BaseColor(243, 249, 251));
                itemQuantity.setBackgroundColor(new BaseColor(243, 249, 251));
                itemGrossProfit.setBackgroundColor(new BaseColor(243, 249, 251));
                itemNetProfit.setBackgroundColor(new BaseColor(243, 249, 251));
            }
            idx++;

            // Add cells to table
            table.addCell(itemId);
            table.addCell(itemName);
            table.addCell(itemBuyPrice);
            table.addCell(itemSellPrice);
            table.addCell(itemQuantity);
            table.addCell(itemGrossProfit);
            table.addCell(itemNetProfit);
        }

        // Create total row
        PdfPCell totalLabelCell = new PdfPCell(new Phrase("Total", headerFont));
        PdfPCell totalGrossProfit = new PdfPCell(new Phrase(String.valueOf(this.totalGrossProfit), headerFont));
        PdfPCell totalNetProfit = new PdfPCell(new Phrase(String.valueOf(this.totalNetProfit), headerFont));

        // Style total row
        totalLabelCell.setColspan(5);
        totalLabelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        totalLabelCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        totalLabelCell.setBackgroundColor(new BaseColor(140, 174, 187));
        totalLabelCell.setPadding(5);

        totalGrossProfit.setBackgroundColor(new BaseColor(140, 174, 187));
        totalGrossProfit.setHorizontalAlignment(Element.ALIGN_CENTER);
        totalGrossProfit.setVerticalAlignment(Element.ALIGN_MIDDLE);

        totalNetProfit.setBackgroundColor(new BaseColor(140, 174, 187));
        totalNetProfit.setHorizontalAlignment(Element.ALIGN_CENTER);
        totalNetProfit.setVerticalAlignment(Element.ALIGN_MIDDLE);

        // Add total row cells to table
        table.addCell(totalLabelCell);
        table.addCell(totalGrossProfit);
        table.addCell(totalNetProfit);

        // Add table to document
        document.add(table);

        // Close document
        document.close();
    }
}
