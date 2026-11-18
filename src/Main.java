import logic.InventoryManager;
import storage.StorageManager;
import ui.MainFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StorageManager storage = new StorageManager();
            InventoryManager manager = new InventoryManager();

            // Attempt to load data on startup
            try {
                manager.setProducts(storage.loadProducts());
                manager.setSales(storage.loadSales());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "No previous save data found or error loading files. Starting fresh.", "Load Info", JOptionPane.INFORMATION_MESSAGE);
            }

            // Launch UI
            MainFrame ui = new MainFrame(manager, storage);
            ui.displayUI();
        });
    }
}