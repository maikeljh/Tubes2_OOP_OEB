package System;

public class VIP extends Customer implements Payment{
    private String name;
    private String phoneNumber;
    private int point;

    public VIP(String name, String phoneNumber) {
        super();
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.point = 0;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int calculatePoint(int total_price){
        return (int) 0.01*total_price;
    }

    public double calculateDiscount(int total_price){
        return (double) this.point + 0.1 * total_price;
    }
}
