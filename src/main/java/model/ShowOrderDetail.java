package model;

public class ShowOrderDetail {

    private int id;
    private int productId;
    private double price;
    private int number;
    private double total;

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public double getPrice() {
        return price;
    }

    public int getNumber() {
        return number;
    }

    public double getTotal() {
        return total;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
