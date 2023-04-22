package System;

public class ReportItem extends PurchasedItem{
    /* attributes */
    private double gross_profit;
    private double net_profit;

    /* ctor */
    public ReportItem(Item e, int quantity){
        super(e, quantity);
        this.gross_profit = this.quantity * this.sell_price;
        this.net_profit = this.gross_profit - (this.quantity * this.buy_price);
    }

    /* methods */
    public void calculateGrossProfit(){
        this.gross_profit = this.quantity * this.sell_price;
    }
    public void calculateNetProfit(){
        this.net_profit = this.gross_profit - (this.quantity * this.buy_price);
    }

    public double getGrossProfit(){
        return this.gross_profit;
    }
    public double getNetProfit(){
        return this.net_profit;
    }
}
