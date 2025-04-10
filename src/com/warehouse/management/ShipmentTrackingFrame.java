package com.warehouse.management;

import javax.swing.*;
import java.awt.*;

public class ShipmentTrackingFrame extends JFrame {
    public ShipmentTrackingFrame() {
        super("Shipment Tracking");
        add(new JLabel("This is the Shipment Tracking module placeholder.", SwingConstants.CENTER), BorderLayout.CENTER);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}