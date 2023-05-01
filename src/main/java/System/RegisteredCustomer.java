package System;

public abstract class RegisteredCustomer extends Customer {
    protected String name;
    protected String phoneNumber;
    protected int point;
    protected boolean activeStatus;

    public RegisteredCustomer(int customerId, String name, String phoneNumber, FixedBill bill) {
        super(customerId);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.point = 0;
        this.activeStatus = true;
        this.transaction.addElement(bill);
    }

    public String getName() {
        return this.name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public int getPoint() {
        return this.point;
    }

    public boolean getActiveStatus() {
        return this.activeStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void calculatePoint(double total_price) {this.point += (int) 0.01 * total_price;}

    public abstract double calculateDiscount(double total_price, boolean point);

}
