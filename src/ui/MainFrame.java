package ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MainFrame {

    private JFrame frame;

    public void displayUI() {
        frame = new JFrame("Sari-Sari Store Inventory System");

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JLabel label = new JLabel(
            "Welcome to the Sari Sari store",
            SwingConstants.CENTER
        );

        frame.add(label);

        frame.setVisible(true);
    }
}