package com.warehouse.management;

import javax.swing.*;
import java.awt.*;

public class TaskManagementFrame extends JFrame {
    public TaskManagementFrame() {
        super("Task Management");

        // Create a Tabbed Pane to switch between different modules
        JTabbedPane tabbedPane = new JTabbedPane();

        // Create Tab
        JPanel createPanel = new JPanel(new BorderLayout());
        createPanel.add(new JLabel("Create module details go here.", SwingConstants.CENTER), BorderLayout.CENTER);
        tabbedPane.addTab("Create", createPanel);

        // Update Tab
        JPanel updatePanel = new JPanel(new BorderLayout());
        updatePanel.add(new JLabel("Update module details go here.", SwingConstants.CENTER), BorderLayout.CENTER);
        tabbedPane.addTab("Update", updatePanel);

        // Delete Tab
        JPanel deletePanel = new JPanel(new BorderLayout());
        deletePanel.add(new JLabel("Delete module details go here.", SwingConstants.CENTER), BorderLayout.CENTER);
        tabbedPane.addTab("Delete", deletePanel);

        // Add the Tabbed Pane to the Frame
        add(tabbedPane, BorderLayout.CENTER);

        // Set frame properties
        setSize(600, 400);
        setLocationRelativeTo(null); // Centers the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // Main method for testing independently
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TaskManagementFrame().setVisible(true));
    }
}