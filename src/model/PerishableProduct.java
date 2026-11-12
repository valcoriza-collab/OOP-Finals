package model;

public class PerishableProduct extends Product {
    private String expirationDate;

    public PerishableProduct(String id, String name, double price, int stock, String expirationDate) {
        super(id, name, price, stock);
        this.expirationDate = expirationDate;
    }

    public String getExpirationDate() { return expirationDate; }

    @Override
    public String getProductDetails() {
        return "Perishable (Expires: " + expirationDate + ")";
    }
}