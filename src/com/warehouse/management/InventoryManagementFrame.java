package com.warehouse.management;

import javax.swing.*;
import java.awt.*;

public class InventoryManagementFrame extends JFrame {
    public InventoryManagementFrame() {
        super("Inventory Management");
        add(new JLabel("This is the Inventory Management module placeholder.", SwingConstants.CENTER), BorderLayout.CENTER);
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the frame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}