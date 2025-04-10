
# Warehouse Management System (Java, SQLite, Swing)

This is a Java-based Warehouse Management System using Swing for the GUI and SQLite for the database.

## 📁 Project Structure

```
WAREHOUSEMANAGEMENT/
├── lib/                              # Contains external libraries (e.g., SQLite JDBC)
├── src/
│   └── com/
│       └── warehouse/
│           └── management/
│               ├── Dashboard.java             # The main dashboard UI class
│               ├── Dashboard.class            # Compiled version
│               ├── DBSQL.java                 # Handles database operations
│               ├── DBSQL.class                # Compiled version
│               ├── InventoryManagementFrame.java
│               ├── InventoryManagementFrame.class
│               ├── LoginPage.java             # Login UI class
│               ├── LoginPage.class
│               ├── OrderManagementFrame.java
│               ├── OrderManagementFrame.class
│               ├── ReportsFrame.java
│               ├── ReportsFrame.class
│               ├── ShipmentTrackingFrame.java
│               ├── ShipmentTrackingFrame.class
├── warehouse.db                       # SQLite database file
├── README.md                          # Project documentation
```

## 💻 How to Compile

Make sure you have `sqlite-jdbc-3.49.0.0.jar` in the `lib/` folder.

```bash
javac -cp ".;lib/sqlite-jdbc-3.49.0.0.jar" src/com/warehouse/management/*.java
```

> On Mac/Linux, replace `;` with `:`

## ▶️ How to Run

```bash
java -cp ".;lib/sqlite-jdbc-3.49.0.0.jar;src" com.warehouse.management.LoginPage
```

> Make sure your terminal is inside the root project folder.

## 🛠 Features (To be Implemented)
- [x] Login System
- [ ] Dashboard with navigation
- [ ] Inventory Management
- [ ] Order Management
- [ ] Shipment Tracking
- [ ] Reports Module

## 🧰 Requirements
- Java JDK 8 or higher
- VS Code or any IDE
- SQLite JDBC `.jar` from: [Maven Central](https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.49.0.0/)

## 📂 Database
The SQLite database is stored in `warehouse.db`. You can create the required tables using SQL or programmatically in `DBSQL.java`.

## 🧑‍💻 Author
Your Name — [your-email@example.com]  
Project for **Advanced OOP** (LaSalle College)

---

Feel free to improve the UI and functionality as needed!
