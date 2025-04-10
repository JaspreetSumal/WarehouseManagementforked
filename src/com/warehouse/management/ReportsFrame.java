package com.warehouse.management;

import javax.swing.*;
import java.awt.*;

public class ReportsFrame extends JFrame {
    public ReportsFrame() {
        super("Reports");
        add(new JLabel("This is the Reports module placeholder.", SwingConstants.CENTER), BorderLayout.CENTER);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}