package System;

public class SalesReport {
    /* attributes */
    private double total_gross_profit;
    private double total_net_profit;
    private Inventory<ReportItem> items;

    /* methods */
    public int getElementIdx(String itemName){
        Boolean found = false;
        int idx = 0;
        while (idx < items.getList().size() && !found){
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
    public void calculateReport(Inventory<FixedBill> transactions){
        for (FixedBill fixedBill : transactions.getList()){
            for (PurchasedItem purchasedItem : fixedBill.getItems().getList()){
                ReportItem reportItem = new ReportItem(purchasedItem, purchasedItem.getQuantity()); // convert PurchasedItem to ReportItem
                if (getElementIdx(purchasedItem.getName()) == -1){
                    // if not found on items, then add item to list
                    items.addElement(reportItem);
                } else {
                    // if item already on the items list, then update the item on the list
                    ReportItem salesReportItem = items.getElement(getElementIdx(purchasedItem.getName())); // ReportItem exists in items
                    reportItem.setQuantity(purchasedItem.getQuantity() + salesReportItem.getQuantity()); // set new quantity
                    reportItem.calculateGrossProfit(); // calculate new gross profit
                    reportItem.calculateNetProfit(); // calculate new net profit
                    items.setElement(getElementIdx(purchasedItem.getName()), reportItem);
                }
            }
        }
        // sum all gross and net profit
        total_gross_profit = 0;
        total_net_profit = 0;
        for (ReportItem reportItem : items.getList()){
            total_gross_profit += reportItem.getGrossProfit();
            total_net_profit += reportItem.getNetProfit();
        }
    }

    public void printReport(Inventory<FixedBill> transactions){

    }
}
