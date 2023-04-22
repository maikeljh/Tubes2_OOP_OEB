package System;

public class Member extends Customer {
    private String name;
    private String phoneNumber;
    private int point;

    public Member(String name, String phoneNumber) {
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
}
