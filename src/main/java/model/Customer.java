package model;

import java.sql.Date;
import java.sql.Timestamp;

public class Customer {

    private int id;
    private String name;
    private Date birthday;
    private int gender;
    private String mail;
    private String phone;
    private String address;
    private Timestamp modifyDateTime;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public int getGender() {
        return gender;
    }

    public String getMail() {
        return mail;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public Timestamp getModifyDateTime() {
        return modifyDateTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setModifyDateTime(Timestamp modifyDateTime) {
        this.modifyDateTime = modifyDateTime;
    }

    @Override
    public String toString() {
        return "ID: " + getId() + "\tName: " + getName() + "\tBirthday: " + getBirthday()
                + "\tGender: " + getGender() + "\tMail: " + getMail() + "\tPhone: "
                + getPhone() + "\tAddress: " + getAddress() + "\tModifyDateTime: "
                + getModifyDateTime();
    }
}
