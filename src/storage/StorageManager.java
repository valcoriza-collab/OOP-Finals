package storage;

import java.io.*;
import java.util.ArrayList;
import model.*;

public class StorageManager {
    private static final String PRODUCT_FILE = "products.txt";
    private static final String SALE_FILE = "sales.txt";

    public void saveAllData(ArrayList<Product> products, ArrayList<Sale> sales) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PRODUCT_FILE))) {
            for (Product p : products) {
                if (p instanceof PackagedProduct) {
                    PackagedProduct pp = (PackagedProduct) p;
                    writer.println("PACKAGED," + pp.getId() + "," + pp.getName() + "," + pp.getPrice() + "," + pp.getStock() + "," + pp.getBarcode());
                } else if (p instanceof PerishableProduct) {
                    PerishableProduct pp = (PerishableProduct) p;
                    writer.println("PERISHABLE," + pp.getId() + "," + pp.getName() + "," + pp.getPrice() + "," + pp.getStock() + "," + pp.getExpirationDate());
                }
            }
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(SALE_FILE))) {
            for (Sale s : sales) {
                writer.println(s.getProductName() + "," + s.getQuantity() + "," + s.getTotalAmount());
            }
        }
    }

    public ArrayList<Product> loadProducts() throws IOException {
        ArrayList<Product> products = new ArrayList<>();
        File file = new File(PRODUCT_FILE);
        if (!file.exists()) return products;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals("PACKAGED") && parts.length == 6) {
                    products.add(new PackagedProduct(parts[1], parts[2], Double.parseDouble(parts[3]), Integer.parseInt(parts[4]), parts[5]));
                } else if (parts[0].equals("PERISHABLE") && parts.length == 6) {
                    products.add(new PerishableProduct(parts[1], parts[2], Double.parseDouble(parts[3]), Integer.parseInt(parts[4]), parts[5]));
                }
            }
        }
        return products;
    }

    public ArrayList<Sale> loadSales() throws IOException {
        ArrayList<Sale> sales = new ArrayList<>();
        File file = new File(SALE_FILE);
        if (!file.exists()) return sales;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    sales.add(new Sale(parts[0], Integer.parseInt(parts[1]), Double.parseDouble(parts[2])));
                }
            }
        }
        return sales;
    }
}