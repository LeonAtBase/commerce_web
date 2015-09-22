package model;

import java.sql.Timestamp;

public class Orders {

    private int id;
    private int customerId;
    private double amount;
    private Timestamp buyDateTime;

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public double getAmount() {
        return amount;
    }

    public Timestamp getBuyDateTime() {
        return buyDateTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setBuyDateTime(Timestamp buyDateTime) {
        this.buyDateTime = buyDateTime;
    }

    @Override
    public String toString() {
        return "id= " + id + ",\tcustomerId= " + customerId + ",\tamount= " + amount
                + ",\tbuyDateTime= " + buyDateTime;
    }
}
