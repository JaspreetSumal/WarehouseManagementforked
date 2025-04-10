package com.warehouse.management;

import javax.swing.*;
import java.awt.*;

public class OrderManagementFrame extends JFrame {
    public OrderManagementFrame() {
        super("Order Management");
        add(new JLabel("This is the Order Management module placeholder.", SwingConstants.CENTER), BorderLayout.CENTER);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}