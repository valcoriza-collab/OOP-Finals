package ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame {
    private JFrame frame;
    
    private static final Color BG_COLOR = new Color(240, 240, 240);
    private static final Color STATUS_COLOR = new Color(200, 255, 200);
    private static final Font MAIN_FONT = new Font("Arial", Font.PLAIN, 14);

    public void displayUI() {
        frame = new JFrame("Sari-Sari Store Inventory System");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);

        JTabbedPane tabbedPane = new JTabbedPane();
        
        JPanel inventoryPanel = new JPanel();
        inventoryPanel.setBackground(BG_COLOR);
        
        JButton actionButton = new JButton("Process Inventory");
        actionButton.setFont(MAIN_FONT);
        actionButton.setEnabled(false); 
        inventoryPanel.add(actionButton);
        
        JPanel reportsPanel = new JPanel();
        reportsPanel.setBackground(BG_COLOR);
        reportsPanel.add(new JLabel("Daily Report Details Will Appear Here", SwingConstants.CENTER));
        
        tabbedPane.addTab("Inventory", inventoryPanel);
        tabbedPane.addTab("Reports", reportsPanel);
        
        frame.add(tabbedPane, BorderLayout.CENTER);

        JLabel statusLabel = new JLabel(" Status: System Ready");
        statusLabel.setOpaque(true);
        statusLabel.setBackground(STATUS_COLOR);
        statusLabel.setFont(MAIN_FONT);
        frame.add(statusLabel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}