package System;

public class Customer {
    protected int id;
    private static int customerCount = 1;
    protected Inventory<FixedBill> transaction;

    public Customer(int id) {
        this.id = id;
        this.transaction = new Inventory<FixedBill>();
    }
    public Customer(FixedBill bill) {
        this.id = customerCount;
        this.transaction = new Inventory<FixedBill>();
        this.transaction.addElement(bill);
        customerCount++;
    }

    public static int getCustomerCount() {
        return customerCount;
    }

    public int getId() {
        return id;
    }

    public Inventory<FixedBill> getTransaction() {
        return transaction;
    }

    public void setTransaction(Inventory<FixedBill> transactions) {
        this.transaction = transactions;
    }
}
