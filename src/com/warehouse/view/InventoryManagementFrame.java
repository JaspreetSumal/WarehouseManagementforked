package com.warehouse.view;

import javax.swing.*;
import java.awt.*;

public class InventoryManagementFrame extends JFrame {
    public InventoryManagementFrame() {
        super("Inventory Management");

        // Create a Tabbed Pane to switch between different modules
        JTabbedPane tabbedPane = new JTabbedPane();

        // HR Management Tab
        JPanel hrPanel = new JPanel(new BorderLayout());
        hrPanel.add(new JLabel("HR Management module details go here.", SwingConstants.CENTER), BorderLayout.CENTER);
        tabbedPane.addTab("HR Management", hrPanel);

        // Shifts Management Tab
        JPanel shiftsPanel = new JPanel(new BorderLayout());
        shiftsPanel.add(new JLabel("Shifts Management module details go here.", SwingConstants.CENTER), BorderLayout.CENTER);
        tabbedPane.addTab("Shifts Management", shiftsPanel);

        // Reports Module Tab
        JPanel reportsPanel = new JPanel(new BorderLayout());
        reportsPanel.add(new JLabel("Reports module details go here.", SwingConstants.CENTER), BorderLayout.CENTER);
        tabbedPane.addTab("Reports", reportsPanel);

        // Add the Tabbed Pane to the Frame
        add(tabbedPane, BorderLayout.CENTER);

        // Set frame properties
        setSize(600, 400);
        setLocationRelativeTo(null); // Centers the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // Main method for testing independently
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InventoryManagementFrame().setVisible(true));
    }
}