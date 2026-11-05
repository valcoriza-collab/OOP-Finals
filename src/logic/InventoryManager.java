package logic;

import java.util.ArrayList;

public class InventoryManager {
    private ArrayList<Object> productList = new ArrayList<>();

    public boolean addProduct(Object item) {
        return true;
    }

    public ArrayList<Object> getProducts() {
        return productList;
    }

    public String generateDailyReport() {
        return "Daily Report: 0 items sold.";
    }
}