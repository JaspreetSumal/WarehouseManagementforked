// package com.warehouse.management;

// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

// public class HRFrame extends JFrame {
//     private JTextField txtEmployeeId, txtEmployeeName, txtRole, txtHourlyRate;
//     private JTextArea txtDisplay;
//     private JButton btnSubmit;

//     public HRFrame() {
//         super("HR Management");

//         // Layout Configuration
//         setLayout(new BorderLayout());
//         JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));

//         // Employee ID
//         inputPanel.add(new JLabel("Employee ID:"));
//         txtEmployeeId = new JTextField();
//         inputPanel.add(txtEmployeeId);

//         // Employee Name
//         inputPanel.add(new JLabel("Employee Name:"));
//         txtEmployeeName = new JTextField();
//         inputPanel.add(txtEmployeeName);

//         // Role
//         inputPanel.add(new JLabel("Role:"));
//         txtRole = new JTextField();
//         inputPanel.add(txtRole);

//         // Hourly Rate
//         inputPanel.add(new JLabel("Hourly Rate ($):"));
//         txtHourlyRate = new JTextField();
//         inputPanel.add(txtHourlyRate);

//         // Submit Button
//         btnSubmit = new JButton("Add Employee");
//         inputPanel.add(btnSubmit);

//         // Display Area
//         txtDisplay = new JTextArea();
//         txtDisplay.setEditable(false);
//         JScrollPane scrollPane = new JScrollPane(txtDisplay);

//         add(inputPanel, BorderLayout.NORTH);
//         add(scrollPane, BorderLayout.CENTER);

//         // Action Listener for Submit Button
//         btnSubmit.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 addEmployee();
//             }
//         });

//         // Window Settings
//         setSize(400, 300);
//         setLocationRelativeTo(null);
//         setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//     }

//     private void addEmployee() {
//         try {
//             int employeeId = Integer.parseInt(txtEmployeeId.getText().trim());
//             String employeeName = txtEmployeeName.getText().trim();
//             String role = txtRole.getText().trim();
//             double hourlyRate = Double.parseDouble(txtHourlyRate.getText().trim());

//             Employee employee = new Employee(employeeId, employeeName, role, hourlyRate);
//             txtDisplay.append("Employee Added:\n");
//             txtDisplay.append(employee.toString() + "\n\n");

//             // Clear fields after successful entry
//             txtEmployeeId.setText("");
//             txtEmployeeName.setText("");
//             txtRole.setText("");
//             txtHourlyRate.setText("");

//         } catch (NumberFormatException ex) {
//             JOptionPane.showMessageDialog(this, "Invalid input! Please enter valid data.", "Error", JOptionPane.ERROR_MESSAGE);
//         }
//     }

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(() -> new HRFrame().setVisible(true));
//     }
// }