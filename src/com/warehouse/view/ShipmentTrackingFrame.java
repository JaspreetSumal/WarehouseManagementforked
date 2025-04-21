package com.warehouse.view;

import com.warehouse.model.DBSQL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class ShipmentTrackingFrame extends JFrame {
    private DBSQL dbSQL;

    public ShipmentTrackingFrame() {
        super("Shipping Tracking");
        dbSQL = new DBSQL(); // Initialize database connection

        // Create a Tabbed Pane to incorporate multiple modules.
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Dispatch List",
                createDataEntryPanel("DispatchList", new String[] { "ID", "Name", "Dispatch Time" }));
        tabbedPane.addTab("Dock", createDataEntryPanel("Dock", new String[] { "DockID", "Location", "Status" }));
        tabbedPane.addTab("Logistic Truck",
                createDataEntryPanel("LogisticTruck", new String[] { "TruckID", "License Plate", "Capacity" }));
        tabbedPane.addTab("Product",
                createDataEntryPanel("Product", new String[] { "ProductID", "Name", "Quantity", "Price" }));
        tabbedPane.addTab("Receive List",
                createDataEntryPanel("ReceiveList", new String[] { "ReceiveID", "Supplier", "Received Date" }));
        tabbedPane.addTab("Supplier Worker",
                createDataEntryPanel("SupplierWorker", new String[] { "WorkerID", "Name", "Role" }));
        tabbedPane.addTab("Team Leader",
                createDataEntryPanel("TeamLeader", new String[] { "LeaderID", "Name", "Department" }));
        tabbedPane.addTab("Worker", createDataEntryPanel("Worker", new String[] { "WorkerID", "Name", "Shift" }));

        add(tabbedPane, BorderLayout.CENTER);

        // Set basic frame settings.
        setSize(800, 500);
        setLocationRelativeTo(null); // Centers the window.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private JPanel createDataEntryPanel(String tableName, String[] columnNames) {
        JPanel panel = new JPanel(new BorderLayout());

        // Table model with appropriate column names
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Create "Add" button
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            JPanel inputPanel = new JPanel(new GridLayout(columnNames.length, 2));
            JTextField[] textFields = new JTextField[columnNames.length];

            for (int i = 0; i < columnNames.length; i++) {
                inputPanel.add(new JLabel(columnNames[i] + ":"));
                textFields[i] = new JTextField();
                inputPanel.add(textFields[i]);
            }

            int result = JOptionPane.showConfirmDialog(ShipmentTrackingFrame.this, inputPanel,
                    "Enter " + tableName + " Data", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                Object[] rowData = new Object[columnNames.length];
                String[] values = new String[columnNames.length];

                for (int i = 0; i < columnNames.length; i++) {
                    rowData[i] = textFields[i].getText().trim();
                    values[i] = textFields[i].getText().trim();
                }
                tableModel.addRow(rowData);

                // If using a database, insert data into the database
                dbSQL.insertData(tableName, columnNames, values);
            }
        });

        // Create "Delete" button
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                // Get the primary key value for the row being deleted
                String primaryKeyValue = tableModel.getValueAt(selectedRow, 0).toString();
                String primaryKeyColumn = columnNames[0]; // Assuming the first column is the primary key

                // Remove the selected row from the table
                tableModel.removeRow(selectedRow);

                // If using a database, delete the corresponding data
                dbSQL.deleteData(tableName, primaryKeyColumn, primaryKeyValue);
            } else {
                JOptionPane.showMessageDialog(ShipmentTrackingFrame.this,
                        "Please select a row to delete.",
                        "No Selection",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        // Create "Update" button
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                JPanel inputPanel = new JPanel(new GridLayout(columnNames.length, 2));
                JTextField[] textFields = new JTextField[columnNames.length];

                // Populate text fields with selected row data for editing
                for (int i = 0; i < columnNames.length; i++) {
                    inputPanel.add(new JLabel(columnNames[i] + ":"));
                    textFields[i] = new JTextField(tableModel.getValueAt(selectedRow, i).toString());
                    inputPanel.add(textFields[i]);
                }

                int result = JOptionPane.showConfirmDialog(ShipmentTrackingFrame.this, inputPanel,
                        "Update " + tableName + " Data", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    Object[] rowData = new Object[columnNames.length];
                    String[] values = new String[columnNames.length];

                    for (int i = 0; i < columnNames.length; i++) {
                        rowData[i] = textFields[i].getText().trim();
                        values[i] = textFields[i].getText().trim();
                    }

                    // Update the table model with new values
                    for (int i = 0; i < columnNames.length; i++) {
                        tableModel.setValueAt(rowData[i], selectedRow, i);
                    }

                    // If using a database, update data in the database
                    String primaryKeyValue = tableModel.getValueAt(selectedRow, 0).toString();
                    String primaryKeyColumn = columnNames[0]; // Assuming the first column is the primary key
                    dbSQL.updateData(tableName, columnNames, values, primaryKeyColumn, primaryKeyValue);
                }
            } else {
                JOptionPane.showMessageDialog(ShipmentTrackingFrame.this,
                        "Please select a row to update.",
                        "No Selection",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        // Bottom panel for buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(addButton);
        bottomPanel.add(deleteButton);
        bottomPanel.add(updateButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    // Main method for testing this frame independently.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ShipmentTrackingFrame().setVisible(true));
    }
}
