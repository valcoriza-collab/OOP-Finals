package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import logic.InventoryManager;
import model.*;
import storage.StorageManager;

public class MainFrame {
    private JFrame frame;
    private InventoryManager manager;
    private StorageManager storage;
    private DefaultTableModel tableModel;
    
    private final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private final Color STATUS_BG = new Color(236, 240, 241);
    private final Font MAIN_FONT = new Font("SansSerif", Font.PLAIN, 14);

    public MainFrame(InventoryManager manager, StorageManager storage) {
        this.manager = manager;
        this.storage = storage;
    }

    public void displayUI() {
        frame = new JFrame("Sari-Sari Store Inventory System");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(frame, "Save data before exiting?", "Exit", JOptionPane.YES_NO_CANCEL_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    saveData();
                    System.exit(0);
                } else if (confirm == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            }
        });

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem("Save Data");
        JMenuItem exitItem = new JMenuItem("Exit");
        saveItem.addActionListener(e -> saveData());
        exitItem.addActionListener(e -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(MAIN_FONT);
        
        tabbedPane.addTab("Inventory Management", createInventoryTab());
        tabbedPane.addTab("Sales & Daily Report", createSalesTab());

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    private JPanel createInventoryTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField stockField = new JTextField();
        JTextField barcodeField = new JTextField();

        formPanel.add(new JLabel("Product ID:")); formPanel.add(idField);
        formPanel.add(new JLabel("Name:")); formPanel.add(nameField);
        formPanel.add(new JLabel("Price:")); formPanel.add(priceField);
        formPanel.add(new JLabel("Stock:")); formPanel.add(stockField);
        formPanel.add(new JLabel("Barcode (Packaged Only):")); formPanel.add(barcodeField);

        JButton addButton = new JButton("Add Packaged Product");
        addButton.setBackground(PRIMARY_COLOR);
        addButton.setForeground(Color.WHITE);
        addButton.setEnabled(false);
        
        KeyListener checkInput = new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                boolean filled = !idField.getText().isEmpty() && !nameField.getText().isEmpty() && !priceField.getText().isEmpty();
                addButton.setEnabled(filled);
            }
        };
        idField.addKeyListener(checkInput);
        nameField.addKeyListener(checkInput);
        priceField.addKeyListener(checkInput);

        formPanel.add(addButton);
        panel.add(formPanel, BorderLayout.NORTH);

        String[] columns = {"ID", "Name", "Price", "Stock", "Details"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable inventoryTable = new JTable(tableModel);
        inventoryTable.setFont(MAIN_FONT);
        inventoryTable.setRowHeight(25);
        panel.add(new JScrollPane(inventoryTable), BorderLayout.CENTER);

        refreshTable();

        JLabel statusLabel = new JLabel(" Ready");
        statusLabel.setOpaque(true);
        statusLabel.setBackground(STATUS_BG);
        panel.add(statusLabel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            try {
                double price = Double.parseDouble(priceField.getText());
                int stock = Integer.parseInt(stockField.getText());
                PackagedProduct p = new PackagedProduct(idField.getText(), nameField.getText(), price, stock, barcodeField.getText());
                
                manager.addProduct(p);
                statusLabel.setText(" Successfully added: " + p.getName());
                statusLabel.setForeground(new Color(39, 174, 96));
                
                refreshTable();
                
                idField.setText(""); nameField.setText(""); priceField.setText(""); stockField.setText(""); barcodeField.setText("");
                addButton.setEnabled(false);
            } catch (InvalidProductException ex) {
                statusLabel.setText(" Error: " + ex.getMessage());
                statusLabel.setForeground(Color.RED);
            } catch (NumberFormatException ex) {
                statusLabel.setText(" Error: Price and Stock must be numbers.");
                statusLabel.setForeground(Color.RED);
            }
        });

        return panel;
    }

    private void refreshTable() {
        if (tableModel == null) return;
        tableModel.setRowCount(0);
        for (Product p : manager.getProducts()) {
            String details = "";
            if (p instanceof PackagedProduct) {
                details = "Barcode: " + ((PackagedProduct) p).getBarcode();
            } else if (p instanceof PerishableProduct) {
                details = "Expires: " + ((PerishableProduct) p).getExpirationDate();
            }
            tableModel.addRow(new Object[]{p.getId(), p.getName(), p.getPrice(), p.getStock(), details});
        }
    }

    private JPanel createSalesTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new FlowLayout());
        JTextField idField = new JTextField(10);
        JTextField qtyField = new JTextField(5);
        JButton sellButton = new JButton("Process Sale");
        JButton reportButton = new JButton("Generate Daily Report");

        topPanel.add(new JLabel("Product ID:")); topPanel.add(idField);
        topPanel.add(new JLabel("Qty:")); topPanel.add(qtyField);
        topPanel.add(sellButton);
        topPanel.add(reportButton);
        panel.add(topPanel, BorderLayout.NORTH);

        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        panel.add(new JScrollPane(reportArea), BorderLayout.CENTER);

        sellButton.addActionListener(e -> {
            try {
                String id = idField.getText();
                int qty = Integer.parseInt(qtyField.getText());
                if (manager.recordSale(id, qty)) {
                    JOptionPane.showMessageDialog(frame, "Sale processed successfully!");
                    idField.setText(""); qtyField.setText("");
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(frame, "Error: Item not found or insufficient stock.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        reportButton.addActionListener(e -> reportArea.setText(manager.generateDailyReport()));

        return panel;
    }

    private void saveData() {
        try {
            storage.saveAllData(manager.getProducts(), manager.getSales());
            JOptionPane.showMessageDialog(frame, "Data saved successfully.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error saving data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}