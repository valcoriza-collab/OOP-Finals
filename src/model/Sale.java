package model;

public class Sale {
    private String productName;
    private int quantity;
    private double totalAmount;

    public Sale(String productName, int quantity, double totalAmount) {
        this.productName = productName;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }

    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public double getTotalAmount() { return totalAmount; }

    @Override
    public String toString() {
        return quantity + "x " + productName + " (PHP " + totalAmount + ")";
    }
}