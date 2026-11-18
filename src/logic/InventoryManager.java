package logic;

import java.util.ArrayList;
import model.Product;
import model.Sale;
import model.PackagedProduct;
import model.InvalidProductException;

public class InventoryManager {
    private ArrayList<Product> productList = new ArrayList<>();
    private ArrayList<Sale> salesList = new ArrayList<>();

    public void setProducts(ArrayList<Product> products) { this.productList = products; }
    public void setSales(ArrayList<Sale> sales) { this.salesList = sales; }
    public ArrayList<Product> getProducts() { return productList; }
    public ArrayList<Sale> getSales() { return salesList; }

    public boolean addProduct(Product item) throws InvalidProductException {
        // ID format check: purely letters and numbers, no special characters like '-'
        if (!item.getId().matches("^[a-zA-Z0-9]+$")) {
            throw new InvalidProductException("Product ID must be alphanumeric (no hyphens or spaces).");
        }

        if (item.getPrice() < 0 || item.getStock() < 0) {
            throw new InvalidProductException("Price and stock cannot be negative.");
        }
        
        if (item instanceof PackagedProduct) {
            String newBarcode = ((PackagedProduct) item).getBarcode();
            if (!newBarcode.matches("\\d{13}")) {
                throw new InvalidProductException("Barcode must be exactly 13 digits (EAN-13 format).");
            }
        }
        
        for (Product p : productList) {
            if (p.getId().equalsIgnoreCase(item.getId())) {
                throw new InvalidProductException("Product ID '" + item.getId() + "' already exists.");
            }
            
            if (p instanceof PackagedProduct && item instanceof PackagedProduct) {
                String existingBarcode = ((PackagedProduct) p).getBarcode();
                String newBarcode = ((PackagedProduct) item).getBarcode();
                
                if (existingBarcode.equalsIgnoreCase(newBarcode)) {
                    throw new InvalidProductException("Barcode '" + newBarcode + "' already exists.");
                }
            }
        }

        productList.add(item);
        return true;
    }

    public Product searchProduct(String id) {
        for (Product p : productList) {
            if (p.getId().equalsIgnoreCase(id)) return p;
        }
        return null;
    }

    public boolean removeProduct(String id) {
        Product p = searchProduct(id);
        if (p != null) {
            productList.remove(p);
            return true;
        }
        return false;
    }

    public boolean recordSale(String id, int quantity) {
        Product p = searchProduct(id);
        if (p != null && p.getStock() >= quantity) {
            p.setStock(p.getStock() - quantity);
            double total = p.getPrice() * quantity;
            salesList.add(new Sale(p.getName(), quantity, total));
            return true;
        }
        return false;
    }

    public String generateDailyReport() {
        StringBuilder report = new StringBuilder("=== SARI-SARI STORE DAILY REPORT ===\n\n");
        double totalRevenue = 0;
        report.append("--- SALES TODAY ---\n");
        if (salesList.isEmpty()) report.append("No sales recorded.\n");
        for (Sale s : salesList) {
            report.append(s.toString()).append("\n");
            totalRevenue += s.getTotalAmount();
        }
        report.append("\nTOTAL REVENUE: PHP ").append(String.format("%.2f", totalRevenue)).append("\n\n");
        
        report.append("--- LOW STOCK WARNING (<= 5) ---\n");
        boolean hasLowStock = false;
        for (Product p : productList) {
            if (p.getStock() <= 5) {
                report.append(p.getName()).append(" (Stock: ").append(p.getStock()).append(")\n");
                hasLowStock = true;
            }
        }
        if (!hasLowStock) report.append("All stock levels are sufficient.\n");
        
        return report.toString();
    }
}