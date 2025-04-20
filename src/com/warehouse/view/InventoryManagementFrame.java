package com.warehouse.view;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import com.warehouse.model.DBSQL;

public class InventoryManagementFrame extends JFrame {
    private DefaultTableModel employeeTableModel, shiftTableModel, reportTableModel;
    private JTable employeeTable, shiftTable, reportTable;
    private DBSQL db;

    public InventoryManagementFrame() {
        super("Inventory Management");

        JTabbedPane tabbedPane = new JTabbedPane();
        db = new DBSQL();
        // -------------------- HR Management Tab --------------------
        JPanel hrPanel = new JPanel(new BorderLayout());
        employeeTableModel = new DefaultTableModel(new String[]{"ID", "Name", "Role", "Hourly Rate"}, 0);
        employeeTable = new JTable(employeeTableModel);
        hrPanel.add(new JScrollPane(employeeTable), BorderLayout.CENTER);

        JPanel hrControlPanel = new JPanel();
        JButton addEmployeeBtn = new JButton("Add Employee");
        JButton updateEmployeeBtn = new JButton("Update Employee");
        JButton removeEmployeeBtn = new JButton("Remove Employee");

        hrControlPanel.add(addEmployeeBtn);
        hrControlPanel.add(updateEmployeeBtn);
        hrControlPanel.add(removeEmployeeBtn);
        hrPanel.add(hrControlPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("HR Management", hrPanel);

        addEmployeeBtn.addActionListener(e -> addEmployee());
        updateEmployeeBtn.addActionListener(e -> updateEmployee());
        removeEmployeeBtn.addActionListener(e -> removeEmployee());  

        // -------------------- Shift Management Tab --------------------
        JPanel shiftsPanel = new JPanel(new BorderLayout());
        shiftTableModel = new DefaultTableModel(new String[]{"Employee ID", "Shift Duration", "Extra Hours"}, 0);
        shiftTable = new JTable(shiftTableModel);
        shiftsPanel.add(new JScrollPane(shiftTable), BorderLayout.CENTER);

        JPanel shiftControlPanel = new JPanel();
        JButton addShiftBtn = new JButton("Add Shift");
        JButton calculateHoursBtn = new JButton("Calculate Extra Hours");

        shiftControlPanel.add(addShiftBtn);
        shiftControlPanel.add(calculateHoursBtn);
        shiftsPanel.add(shiftControlPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("Shifts Management", shiftsPanel);

        addShiftBtn.addActionListener(e -> addShift());
        calculateHoursBtn.addActionListener(e -> calculateExtraHours());

        // -------------------- Reports Tab --------------------
        JPanel reportsPanel = new JPanel(new BorderLayout());
        reportTableModel = new DefaultTableModel(new String[]{"Employee ID", "Absences", "Extra Hours", "Payment Plan"}, 0);
        reportTable = new JTable(reportTableModel);
        reportsPanel.add(new JScrollPane(reportTable), BorderLayout.CENTER);

        JPanel reportControlPanel = new JPanel();
        JButton generateReportBtn = new JButton("Generate Report");

        reportControlPanel.add(generateReportBtn);
        reportsPanel.add(reportControlPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("Reports", reportsPanel);

        generateReportBtn.addActionListener(e -> generateReport());

        // -------------------- Finalize Layout --------------------
        add(tabbedPane, BorderLayout.CENTER);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // -------------------- HR Methods --------------------
    private void addEmployee() {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField roleField = new JTextField();
        JTextField hourlyRateField = new JTextField();

        panel.add(new JLabel("Employee ID:"));
        panel.add(idField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Role (Employee/Manager/Supervisor):"));
        panel.add(roleField);
        panel.add(new JLabel("Hourly Rate:"));
        panel.add(hourlyRateField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Employee", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String role = roleField.getText().trim();
            String hourlyRate = hourlyRateField.getText().trim();

            if (id.isEmpty() || name.isEmpty() || role.isEmpty() || hourlyRate.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.");
                return;
            }

            if (!name.matches("[a-zA-Z\\s]+") || !role.matches("[a-zA-Z\\s]+")) {
                JOptionPane.showMessageDialog(this, "Name and Role must contain only letters.");
                return;
            }

            try {
                db.insertEmployee(Integer.parseInt(id), name, role, Double.parseDouble(hourlyRate));
                int idInt = Integer.parseInt(id);
                if (idInt <= 0) throw new NumberFormatException();
                double rate = Double.parseDouble(hourlyRate);
                if (rate <= 0) throw new NumberFormatException();

                employeeTableModel.addRow(new Object[]{idInt, name, role, rate});
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID or Hourly Rate.");
            }
        }
    }

    private void updateEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Select an employee to update.");
            return;
        }

        JPanel panel = new JPanel(new GridLayout(4, 2));
        JTextField idField = new JTextField(employeeTableModel.getValueAt(selectedRow, 0).toString());
        idField.setEditable(false); // ID is shown but cannot be changed
        JTextField nameField = new JTextField(employeeTableModel.getValueAt(selectedRow, 1).toString());
        JTextField roleField = new JTextField(employeeTableModel.getValueAt(selectedRow, 2).toString());
        JTextField hourlyRateField = new JTextField(employeeTableModel.getValueAt(selectedRow, 3).toString());

        panel.add(new JLabel("Employee ID:"));
        panel.add(idField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Role (Employee/Manager/Supervisor):"));
        panel.add(roleField);
        panel.add(new JLabel("Hourly Rate:"));
        panel.add(hourlyRateField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Update Employee", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String role = roleField.getText().trim();
            String hourlyRate = hourlyRateField.getText().trim();

            if (name.isEmpty() || role.isEmpty() || hourlyRate.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.");
                return;
            }

            if (!name.matches("[a-zA-Z\\s]+") || !role.matches("[a-zA-Z\\s]+")) {
                JOptionPane.showMessageDialog(this, "Name and Role must contain only letters.");
                return;
            }

            try {
                double rate = Double.parseDouble(hourlyRate);
                if (rate <= 0) throw new NumberFormatException();

                employeeTableModel.setValueAt(name, selectedRow, 1);
                employeeTableModel.setValueAt(role, selectedRow, 2);
                employeeTableModel.setValueAt(rate, selectedRow, 3);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Hourly Rate.");
            }
        }
    }

    private void removeEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow >= 0) {
            employeeTableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Select an employee to remove.");
        }
    }

    // -------------------- Shift Management Methods --------------------
    private void addShift() {
        JPanel panel = new JPanel(new GridLayout(2, 2));

        JTextField idField = new JTextField();
        String[] options = {"5H", "10H"};
        JComboBox<String> shiftBox = new JComboBox<>(options);

        panel.add(new JLabel("Employee ID:"));
        panel.add(idField);
        panel.add(new JLabel("Shift Duration:"));
        panel.add(shiftBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Shift", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String empId = idField.getText().trim();
            String shiftTime = (String) shiftBox.getSelectedItem();

            if (empId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Employee ID is required.");
                return;
            }

            shiftTableModel.addRow(new Object[]{empId, shiftTime, "Pending"});
        }
    }

    private void calculateExtraHours() {
        int selectedRow = shiftTable.getSelectedRow();
        if (selectedRow >= 0) {
            shiftTableModel.setValueAt("Calculated", selectedRow, 2);
        } else {
            JOptionPane.showMessageDialog(this, "Select a shift to calculate extra hours.");
        }
    }

    // -------------------- Reports Methods --------------------
    private void generateReport() {
        String empId = JOptionPane.showInputDialog("Enter Employee ID:");
        String absences = JOptionPane.showInputDialog("Enter Number of Absences:");
        String extraHours = JOptionPane.showInputDialog("Enter Extra Hours:");
        String paymentPlan = JOptionPane.showInputDialog("Enter Payment Plan:");

        if (empId != null && absences != null && extraHours != null && paymentPlan != null) {
            reportTableModel.addRow(new Object[]{empId, absences, extraHours, paymentPlan});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InventoryManagementFrame().setVisible(true));
    }
}
