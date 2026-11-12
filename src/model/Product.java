package model;

public abstract class Product {
    private String id;
    private String name;
    private double price;
    private int stock;

    public Product(String id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    
    public void setPrice(double price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }

    public abstract String getProductDetails();

    @Override
    public String toString() {
        return id + " | " + name + " | PHP " + price + " | Stock: " + stock + " | " + getProductDetails();
    }
}