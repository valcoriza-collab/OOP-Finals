package logic;

import java.util.ArrayList;
import model.Product;
import model.Sale;
import model.InvalidProductException;

public class InventoryManager {
    private ArrayList<Product> productList = new ArrayList<>();
    private ArrayList<Sale> salesList = new ArrayList<>();

    public boolean addProduct(Product item) throws InvalidProductException {
        if (item.getPrice() < 0 || item.getStock() < 0) {
            throw new InvalidProductException("Price and stock cannot be negative.");
        }
        productList.add(item);
        return true;
    }

    public ArrayList<Product> getProducts() {
        return productList;
    }

    public Product searchProduct(String id) {
        for (Product p : productList) {
            if (p.getId().equalsIgnoreCase(id)) return p;
        }
        return null;
    }

    public boolean editProduct(String id, double newPrice, int newStock) {
        Product p = searchProduct(id);
        if (p != null) {
            p.setPrice(newPrice);
            p.setStock(newStock);
            return true;
        }
        return false;
    }

    public boolean removeProduct(String id) {
        Product p = searchProduct(id);
        if (p != null) {
            productList.remove(p);
            return true;
        }
        return false;
    }

    public void recordSale(String id, int quantity) {
        Product p = searchProduct(id);
        if (p != null && p.getStock() >= quantity) {
            p.setStock(p.getStock() - quantity);
            double total = p.getPrice() * quantity;
            salesList.add(new Sale(p.getName(), quantity, total));
        }
    }

    public String generateDailyReport() {
        StringBuilder report = new StringBuilder("--- SARI-SARI STORE DAILY REPORT ---\n");
        double totalRevenue = 0;
        report.append("Sales Today:\n");
        for (Sale s : salesList) {
            report.append("- ").append(s.toString()).append("\n");
            totalRevenue += s.getTotalAmount();
        }
        report.append("Total Revenue: PHP ").append(totalRevenue).append("\n\n");
        
        report.append("Low Stock Warning (<= 5):\n");
        for (Product p : productList) {
            if (p.getStock() <= 5) {
                report.append("- ").append(p.getName()).append(" (Stock: ").append(p.getStock()).append(")\n");
            }
        }
        return report.toString();
    }
}