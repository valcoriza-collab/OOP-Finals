import logic.InventoryManager;
import model.PackagedProduct;
import model.PerishableProduct;
import model.Product;
import model.InvalidProductException;

public class Main {
    public static void main(String[] args) {
        InventoryManager logic = new InventoryManager();
        
        try {
            System.out.println("1. Adding Records...");
            logic.addProduct(new PackagedProduct("P1", "Canned Sardines", 25.0, 50, "123456"));
            logic.addProduct(new PerishableProduct("P2", "Eggs", 10.0, 4, "2026-11-20"));
            logic.addProduct(new PackagedProduct("P3", "Noodles", 15.0, 30, "789012"));
            
            System.out.println("\n2. Searching Records...");
            System.out.println(logic.searchProduct("P1"));

            System.out.println("\n3. Editing Records...");
            logic.editProduct("P3", 16.0, 25);
            System.out.println(logic.searchProduct("P3"));

            System.out.println("\n4. Removing Records...");
            logic.removeProduct("P1");

            System.out.println("\n5. Recording Sales...");
            logic.recordSale("P2", 2);
            logic.recordSale("P3", 5);

            System.out.println("\n6. Daily Report:");
            System.out.println(logic.generateDailyReport());
           
            System.out.println("Testing Exception Catch:");
            logic.addProduct(new PackagedProduct("P4", "Bad Item", -5.0, -1, "000"));
            
        } catch (InvalidProductException e) {
            System.out.println("Error Caught: " + e.getMessage());
        }
    }
}