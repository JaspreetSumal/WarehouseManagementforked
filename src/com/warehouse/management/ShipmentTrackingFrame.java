package com.warehouse.management;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class ShipmentTrackingFrame extends JFrame {
    private DefaultListModel<File> fileListModel;
    private JList<File> fileList;
    private JButton btnAddFile;
    private JButton btnRemoveFile;

    public ShipmentTrackingFrame() {
        super("Shipping Tracking");

        // Initialize the list model and JList to show selected files.
        fileListModel = new DefaultListModel<>();
        fileList = new JList<>(fileListModel);
        fileList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                // Display the file name instead of the full path
                String fileName = ((File) value).getName();
                return super.getListCellRendererComponent(list, fileName, index, isSelected, cellHasFocus);
            }
        });
        JScrollPane scrollPane = new JScrollPane(fileList);
        scrollPane.setPreferredSize(new Dimension(300, 150));

        // Button to add files using a file chooser.
        btnAddFile = new JButton("Add File(s)");
        btnAddFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                // Allow selecting multiple files.
                fileChooser.setMultiSelectionEnabled(true);
                int returnValue = fileChooser.showOpenDialog(ShipmentTrackingFrame.this);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File[] selectedFiles = fileChooser.getSelectedFiles();
                    for (File file : selectedFiles) {
                        fileListModel.addElement(file);
                    }
                }
            }
        });

        // Button to remove the selected file from the list.
        btnRemoveFile = new JButton("Remove Selected File");
        btnRemoveFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIdx = fileList.getSelectedIndex();
                if (selectedIdx != -1) {
                    fileListModel.remove(selectedIdx);
                } else {
                    JOptionPane.showMessageDialog(ShipmentTrackingFrame.this,
                            "Please select a file to remove.",
                            "No Selection",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Create the File Management Panel (existing functionality)
        JPanel fileManagementPanel = new JPanel(new BorderLayout());
        fileManagementPanel.add(new JLabel("Manage Your Shipment Files", SwingConstants.CENTER), BorderLayout.NORTH);
        fileManagementPanel.add(scrollPane, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAddFile);
        buttonPanel.add(btnRemoveFile);
        fileManagementPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Create a Tabbed Pane to incorporate multiple modules.
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("File Management", fileManagementPanel);

        // Create additional panels as placeholders, each corresponding to your domain
        // classes.
        JPanel dispatchListPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dispatchListPanel.add(new JLabel("DispatchList module details go here."));
        tabbedPane.addTab("Dispatch List", dispatchListPanel);

        JPanel dockPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dockPanel.add(new JLabel("Dock module details go here."));
        tabbedPane.addTab("Dock", dockPanel);

        JPanel logisticTruckPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logisticTruckPanel.add(new JLabel("LogisticTruck module details go here."));
        tabbedPane.addTab("Logistic Truck", logisticTruckPanel);

        JPanel productPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        productPanel.add(new JLabel("Product module details go here."));
        tabbedPane.addTab("Product", productPanel);

        JPanel receiveListPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        receiveListPanel.add(new JLabel("ReceiveList module details go here."));
        tabbedPane.addTab("Receive List", receiveListPanel);

        JPanel supplierWorkerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        supplierWorkerPanel.add(new JLabel("SupplierWorker module details go here."));
        tabbedPane.addTab("Supplier Worker", supplierWorkerPanel);

        JPanel teamLeaderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        teamLeaderPanel.add(new JLabel("TeamLeader module details go here."));
        tabbedPane.addTab("Team Leader", teamLeaderPanel);

        JPanel workerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        workerPanel.add(new JLabel("Worker module details go here."));
        tabbedPane.addTab("Worker", workerPanel);

        // Add the Tabbed Pane to the Frame.
        add(tabbedPane, BorderLayout.CENTER);

        // Set basic frame settings.
        setSize(600, 400);
        setLocationRelativeTo(null); // centers the window.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // Main method for testing this frame independently.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ShipmentTrackingFrame().setVisible(true);
            }
        });
    }
}