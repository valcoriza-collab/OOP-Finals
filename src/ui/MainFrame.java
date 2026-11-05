package ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MainFrame {

    private JFrame frame;

    public void displayUI() {
        frame = new JFrame("Team 2 Information System");

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JLabel label = new JLabel(
            "Welcome to the Information System",
            SwingConstants.CENTER
        );

        frame.add(label);

        frame.setVisible(true);
    }
}