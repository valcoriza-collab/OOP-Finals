package ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import logic.InventoryManager;

public class Main {
    public static void main(String[] args) {
        InventoryManager manager = new InventoryManager();
        System.out.println("Sari-Sari Store Inventory System Initialized!");
        System.out.println(manager.generateDailyReport());
    }
}