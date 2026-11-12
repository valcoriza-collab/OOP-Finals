package model;

public class PackagedProduct extends Product {
    private String barcode;

    public PackagedProduct(String id, String name, double price, int stock, String barcode) {
        super(id, name, price, stock);
        this.barcode = barcode;
    }

    public String getBarcode() { return barcode; }

    @Override
    public String getProductDetails() {
        return "Packaged (Barcode: " + barcode + ")";
    }
}