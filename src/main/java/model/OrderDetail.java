package model;

import java.util.ArrayList;

public class OrderDetail {

    private int id;
    private int orderId;
    private ArrayList productId;
    private double price;
    private ArrayList number;

    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public ArrayList getProductId() {
        return productId;
    }

    public double getPrice() {
        return price;
    }

    public ArrayList getNumber() {
        return number;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setProductId(ArrayList productId) {
        this.productId = productId;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setNumber(ArrayList number) {
        this.number = number;
    }

}
