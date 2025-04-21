package com.warehouse.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBSQL {
    private Connection connection;

    // Constructor establishes the connection to the database.
    public DBSQL() {
        try {
            // Using SQLite for simplicity. Replace the connection string if using another
            // DB.
            String url = "jdbc:sqlite:warehouse.db";
            connection = DriverManager.getConnection(url);

            if (connection == null) {
                System.out.println("Database connection failed!");
            } else {
                System.out.println("Connected to database successfully.");
            }
            // Create the Users table if it does not exist.
            createEmployeeTable();
            createDockTable();
            createShiftTable();
            createTaskTable();
            createLogisticTruckTable();
            createProductTable();
            createReceiveListTable();
            createDispatchListTable();
            createSupplierWorkerTable();
            createTeamLeaderTable();
            createWorkerTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Create the Users table.
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Users (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "username TEXT UNIQUE NOT NULL, " + "password TEXT NOT NULL);";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert a sample user for testing purposes.
    public void insertUser(String username, String password) {
        String sql = "INSERT OR IGNORE INTO Users(username, password) VALUES(?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Validate user-provided credentials against those stored in the database.
    public boolean validateUser(String username, String password) {
        String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true; // Valid login
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createDockTable() {
        String sql = "CREATE TABLE docks ("
                + "id INT PRIMARY KEY, "
                + "location VARCHAR(100) NOT NULL"
                + ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'docks' created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Object[]> loadDocks() {
        List<Object[]> docks = new ArrayList<>();
        String sql = "SELECT id, location FROM docks";

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String location = rs.getString("location");
                docks.add(new Object[] { id, location });
            }
            System.out.println("Dock data loaded successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return docks;
    }

    public void insertDock(int id, String location) {
        String sql = "INSERT INTO docks (id, location) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, location);
            pstmt.executeUpdate();
            System.out.println("Dock added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDock(int id, String location) {
        String sql = "UPDATE docks SET location = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, location);
            pstmt.setInt(2, id);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Dock updated successfully!");
            } else {
                System.out.println("No dock found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDock(int id) {
        String sql = "DELETE FROM docks WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Dock deleted successfully!");
            } else {
                System.out.println("No dock found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createLogisticTruckTable() {
        String sql = "CREATE TABLE logistic_truck ("
                + "id INT PRIMARY KEY AUTO_INCREMENT, "
                + "plate_number VARCHAR(20) NOT NULL, "
                + "driver_name VARCHAR(50) NOT NULL"
                + ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'logistic_truck' created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Object[]> loadLogisticTrucks() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT * FROM logistic_truck";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Object[] { rs.getInt("id"), rs.getString("plate_number"), rs.getString("driver_name") });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insertLogisticTruck(int id, String plateNumber, String driverName) {
        String sql = "INSERT INTO logistic_truck (id, plate_number, driver_name) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, plateNumber);
            pstmt.setString(3, driverName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateLogisticTruck(int id, String plateNumber, String driverName) {
        String sql = "UPDATE logistic_truck SET plate_number = ?, driver_name = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, plateNumber);
            pstmt.setString(2, driverName);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteLogisticTruck(int id) {
        String sql = "DELETE FROM logistic_truck WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createProductTable() {
        String sql = "CREATE TABLE product ("
                + "id INT PRIMARY KEY AUTO_INCREMENT, "
                + "name VARCHAR(100) NOT NULL, "
                + "quantity INT NOT NULL, "
                + "location VARCHAR(100) NOT NULL"
                + ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'product' created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Object[]> loadProducts() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT * FROM product";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Object[] {
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getString("location")
                });
            }
            System.out.println("Product data loaded successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insertProduct(int id, String name, int quantity, String location) {
        String sql = "INSERT INTO product (id, name, quantity, location) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setInt(3, quantity);
            pstmt.setString(4, location);
            pstmt.executeUpdate();
            System.out.println("Product added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(int id, int quantity, String location) {
        String sql = "UPDATE product SET quantity = ?, location = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, quantity);
            pstmt.setString(2, location);
            pstmt.setInt(3, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product updated successfully!");
            } else {
                System.out.println("No product found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int id) {
        String sql = "DELETE FROM product WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product deleted successfully!");
            } else {
                System.out.println("No product found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createReceiveListTable() {
        String sql = "CREATE TABLE receive_list ("
                + "id INT PRIMARY KEY AUTO_INCREMENT, "
                + "product_id INT NOT NULL, "
                + "supplier_id INT NOT NULL, "
                + "quantity INT NOT NULL, "
                + "receive_date TEXT NOT NULL"
                + ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'receive_list' created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Object[]> loadReceiveLists() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT * FROM receive_list";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Object[] {
                        rs.getInt("id"),
                        rs.getInt("product_id"),
                        rs.getInt("supplier_id"),
                        rs.getInt("quantity"),
                        rs.getString("receive_date")
                });
            }
            System.out.println("ReceiveList data loaded successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insertReceiveList(int id, int productId, int supplierId, int quantity, String receiveDate) {
        String sql = "INSERT INTO receive_list (id, product_id, supplier_id, quantity, receive_date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, productId);
            pstmt.setInt(3, supplierId);
            pstmt.setInt(4, quantity);
            pstmt.setString(5, receiveDate);
            pstmt.executeUpdate();
            System.out.println("ReceiveList entry added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateReceiveList(int id, int quantity, String receiveDate) {
        String sql = "UPDATE receive_list SET quantity = ?, receive_date = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, quantity);
            pstmt.setString(2, receiveDate);
            pstmt.setInt(3, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("ReceiveList updated successfully!");
            } else {
                System.out.println("No ReceiveList found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteReceiveList(int id) {
        String sql = "DELETE FROM receive_list WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("ReceiveList entry deleted successfully!");
            } else {
                System.out.println("No ReceiveList found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createDispatchListTable() {
        String sql = "CREATE TABLE dispatch_list ("
                + "id INT PRIMARY KEY AUTO_INCREMENT, "
                + "product_id INT NOT NULL, "
                + "destination TEXT NOT NULL, "
                + "quantity INT NOT NULL, "
                + "dispatch_date TEXT NOT NULL"
                + ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'dispatch_list' created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Object[]> loadDispatchLists() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT * FROM dispatch_list";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Object[] {
                        rs.getInt("id"),
                        rs.getInt("product_id"),
                        rs.getString("destination"),
                        rs.getInt("quantity"),
                        rs.getString("dispatch_date")
                });
            }
            System.out.println("DispatchList data loaded successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insertDispatchList(int id, int productId, String destination, int quantity, String dispatchDate) {
        String sql = "INSERT INTO dispatch_list (id, product_id, destination, quantity, dispatch_date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, productId);
            pstmt.setString(3, destination);
            pstmt.setInt(4, quantity);
            pstmt.setString(5, dispatchDate);
            pstmt.executeUpdate();
            System.out.println("DispatchList entry added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDispatchList(int id, String destination, int quantity, String dispatchDate) {
        String sql = "UPDATE dispatch_list SET destination = ?, quantity = ?, dispatch_date = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, destination);
            pstmt.setInt(2, quantity);
            pstmt.setString(3, dispatchDate);
            pstmt.setInt(4, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("DispatchList updated successfully!");
            } else {
                System.out.println("No DispatchList found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDispatchList(int id) {
        String sql = "DELETE FROM dispatch_list WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("DispatchList entry deleted successfully!");
            } else {
                System.out.println("No DispatchList found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createSupplierWorkerTable() {
        String sql = "CREATE TABLE supplier_worker ("
                + "id INT PRIMARY KEY AUTO_INCREMENT, "
                + "name VARCHAR(100) NOT NULL, "
                + "contact_info TEXT, "
                + "assigned_dock_id INT"
                + ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'supplier_worker' created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Object[]> loadSupplierWorkers() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT * FROM supplier_worker";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Object[] {
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("contact_info"),
                        rs.getInt("assigned_dock_id")
                });
            }
            System.out.println("SupplierWorker data loaded successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insertSupplierWorker(int id, String name, String contactInfo, int assignedDockId) {
        String sql = "INSERT INTO supplier_worker (id, name, contact_info, assigned_dock_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, contactInfo);
            pstmt.setInt(4, assignedDockId);
            pstmt.executeUpdate();
            System.out.println("SupplierWorker added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSupplierWorker(int id, String contactInfo, int assignedDockId) {
        String sql = "UPDATE supplier_worker SET contact_info = ?, assigned_dock_id = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, contactInfo);
            pstmt.setInt(2, assignedDockId);
            pstmt.setInt(3, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("SupplierWorker updated successfully!");
            } else {
                System.out.println("No SupplierWorker found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSupplierWorker(int id) {
        String sql = "DELETE FROM supplier_worker WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("SupplierWorker deleted successfully!");
            } else {
                System.out.println("No SupplierWorker found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createWorkerTable() {
        String sql = "CREATE TABLE worker ("
                + "id INT PRIMARY KEY AUTO_INCREMENT, "
                + "name VARCHAR(100) NOT NULL, "
                + "position VARCHAR(100), "
                + "shift VARCHAR(50)"
                + ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'worker' created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Object[]> loadWorkers() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT * FROM worker";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Object[] {
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("position"),
                        rs.getString("shift")
                });
            }
            System.out.println("Worker data loaded successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insertWorker(int id, String name, String position, String shift) {
        String sql = "INSERT INTO worker (id, name, position, shift) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, position);
            pstmt.setString(4, shift);
            pstmt.executeUpdate();
            System.out.println("Worker added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateWorker(int id, String position, String shift) {
        String sql = "UPDATE worker SET position = ?, shift = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, position);
            pstmt.setString(2, shift);
            pstmt.setInt(3, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Worker updated successfully!");
            } else {
                System.out.println("No Worker found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteWorker(int id) {
        String sql = "DELETE FROM worker WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Worker deleted successfully!");
            } else {
                System.out.println("No Worker found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTeamLeaderTable() {
        String sql = "CREATE TABLE team_leader ("
                + "id INT PRIMARY KEY AUTO_INCREMENT, "
                + "name VARCHAR(100) NOT NULL, "
                + "department VARCHAR(100) NOT NULL, "
                + "shift VARCHAR(50)"
                + ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'team_leader' created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Object[]> loadTeamLeaders() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT * FROM team_leader";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Object[] {
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getString("shift")
                });
            }
            System.out.println("TeamLeader data loaded successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insertTeamLeader(int id, String name, String department, String shift) {
        String sql = "INSERT INTO team_leader (id, name, department, shift) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, department);
            pstmt.setString(4, shift);
            pstmt.executeUpdate();
            System.out.println("TeamLeader added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTeamLeader(int id, String department, String shift) {
        String sql = "UPDATE team_leader SET department = ?, shift = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, department);
            pstmt.setString(2, shift);
            pstmt.setInt(3, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("TeamLeader updated successfully!");
            } else {
                System.out.println("No TeamLeader found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTeamLeader(int id) {
        String sql = "DELETE FROM team_leader WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("TeamLeader deleted successfully!");
            } else {
                System.out.println("No TeamLeader found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createEmployeeTable() {
        String sql = "CREATE TABLE employees ("
                + "id INT PRIMARY KEY AUTO_INCREMENT, "
                + "name VARCHAR(50) NOT NULL, "
                + "role VARCHAR(50) NOT NULL, "
                + "hourly_rate DECIMAL(10,2) NOT NULL"
                + ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'employees' created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Object[]> loadEmployees() {
        List<Object[]> employees = new ArrayList<>();
        String sql = "SELECT id, name, role, hourly_rate FROM employees";

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String role = rs.getString("role");
                double hourlyRate = rs.getDouble("hourly_rate");
                employees.add(new Object[] { id, name, role, hourlyRate });
            }
            System.out.println("Employee data loaded successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public void insertEmployee(int id, String name, String role, double hourlyRate) {
        String sql = "INSERT INTO employees (id, name, role, hourly_rate) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, role);
            pstmt.setDouble(4, hourlyRate);
            pstmt.executeUpdate();
            System.out.println("Employee added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(int id, String role, double hourlyRate) {
        String sql = "UPDATE employees SET role = ?, hourly_rate = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, role);
            pstmt.setDouble(2, hourlyRate);
            pstmt.setInt(3, id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Employee updated successfully!");
            } else {
                System.out.println("No employee found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Employee deleted successfully!");
            } else {
                System.out.println("No employee found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createShiftTable() {
        String sql = "CREATE TABLE IF NOT EXISTS shifts ("
                + "shift_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "employee_id INTEGER NOT NULL, "
                + "shift_duration INTEGER CHECK (shift_duration IN (5, 10)), "
                + "FOREIGN KEY (employee_id) REFERENCES employees(id)"
                + ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'shifts' created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertShift(int employeeId, int shiftDuration) {
        String sql = "INSERT INTO shifts (employee_id, shift_duration) VALUES (?, ?)";

        // Validate shift duration before inserting
        if (shiftDuration != 5 && shiftDuration != 10) {
            System.out.println("Invalid shift duration! Only 5 or 10 hours are allowed.");
            return;
        }

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, employeeId);
            pstmt.setInt(2, shiftDuration);
            pstmt.executeUpdate();
            System.out.println("Shift added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Object[]> loadShifts() {
        List<Object[]> shifts = new ArrayList<>();
        String sql = "SELECT shift_id, employee_id, shift_duration FROM shifts";

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int shiftId = rs.getInt("shift_id");
                int employeeId = rs.getInt("employee_id");
                int shiftDuration = rs.getInt("shift_duration");

                shifts.add(new Object[] { shiftId, employeeId, shiftDuration });
            }
            System.out.println("Shift data loaded successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shifts;
    }

    public Object[] getEmployeeDetails(int employeeId) {
        String sql = "SELECT id, name, role, hourly_rate FROM employees WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, employeeId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Object[] {
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("role"),
                        rs.getDouble("hourly_rate")
                };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Object[]> getShiftsForEmployee(int employeeId) {
        List<Object[]> shifts = new ArrayList<>();
        String sql = "SELECT shift_id, employee_id, shift_duration FROM shifts WHERE employee_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, employeeId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                shifts.add(
                        new Object[] { rs.getInt("shift_id"), rs.getInt("employee_id"), rs.getInt("shift_duration") });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shifts;
    }

    public void createTaskTable() {
        String sql = "CREATE TABLE IF NOT EXISTS tasks ("
                + "task_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "title TEXT NOT NULL"
                + ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'tasks' created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertTask(int id, String title) {
        String sql = "INSERT INTO tasks (task_id ,title) VALUES (?,?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, title);
            pstmt.executeUpdate();
            System.out.println("Task added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Object[]> loadTasks() {
        List<Object[]> tasks = new ArrayList<>();
        String sql = "SELECT task_id, title FROM tasks";

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tasks.add(new Object[] {
                        rs.getInt("task_id"),
                        rs.getString("title")

                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public void updateTask(int taskId, String title) {
        String sql = "UPDATE tasks SET title = ? WHERE task_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setInt(5, taskId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Task updated successfully!");
            } else {
                System.out.println("No task found with ID: " + taskId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(int taskId) {
        String sql = "DELETE FROM tasks WHERE task_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, taskId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Task deleted successfully!");
            } else {
                System.out.println("No task found with ID: " + taskId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteData(String tableName, String primaryKeyColumn, String primaryKeyValue) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteData'");
    }

    public void insertData(String tableName, String[] columnNames, String[] values) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insertData'");
    }

    public void updateData(String tableName, String[] columnNames, String[] values, String primaryKeyColumn,
            String primaryKeyValue) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateData'");
    }

}