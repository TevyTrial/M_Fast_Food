# M Fast Food Management System

Welcome to the **M Fast Food Management System**. This system is designed to manage various aspects of a fast food restaurant, including operations for staff, managers, and order processing.

## Acknowledgments
- This is a group project and not all are my work. Special thanks to the team for their support and contributions, also with the aid of ChatGPT
- I have attached a demo video in the file, I hope it will be easier to review :)

## Features
- All input will change the database and display the new values in real-time.
### Manager interface
**Store Management**:
- **Staff Management**: Add, view, and manage staff details, including the ability to create database users with specific roles.
- **Product Management**: View, add, and manage products available for sale.
- **Warehouse Management**: Manage inventory levels, add new ingredients, and update existing ones.

**Performance Report**:
- **Item sale**: Showing product_id and total sale amount.
- **Performance Report**: Displaying sales report with Order_No, Income, cost and Net Profit.

### Staff interface
- **New order**: Create new orders, customize order details, and handle payments(Staff can have discount).
               : Displaying selected items, recipt.
- **Order Review**: Review past orders and their details.

## Getting Started

### Prerequisites

- **Java Development Kit (JDK)**
- **MySQL** database
- **JDBC Driver** for MariaDB

### Database Setup

1. **Install MySQL** and create a database named `M_Fast_Food`.
2. **Create the required tables** using the following SQL script:
3. **Feel free to insert different values into tables.

```sql
CREATE TABLE staff (
    staff_id INT PRIMARY KEY,
    s_name VARCHAR(50),
    password VARCHAR(50),
    ifManager BOOLEAN
);

CREATE TABLE product (
    product_id VARCHAR(10) PRIMARY KEY,
    product_name VARCHAR(100),
    price DECIMAL(10, 2)
);

CREATE TABLE inventory (
    ingredient_id VARCHAR(10) PRIMARY KEY,
    ingredient_name VARCHAR(100),
    amount INT,
    cost DECIMAL(10, 2)
);

CREATE TABLE ingredient (
    product_id VARCHAR(10),
    ingredient VARCHAR(10),
    amount INT,
    PRIMARY KEY (product_id, ingredient),
    FOREIGN KEY (product_id) REFERENCES product(product_id),
    FOREIGN KEY (ingredient) REFERENCES inventory(ingredient_id)
);

CREATE TABLE orders (
    order_no INT,
    product_id VARCHAR(10),
    amount INT,
    staff_id VARCHAR(10),
    PRIMARY KEY (order_no, product_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id),
    FOREIGN KEY (staff_id) REFERENCES staff(staff_id)
);

CREATE TABLE special (
    order_no INT,
    product_id VARCHAR(10),
    salt VARCHAR(10),
    ice VARCHAR(10),
    PRIMARY KEY (order_no, product_id),
    FOREIGN KEY (order_no) REFERENCES orders(order_no),
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

CREATE TABLE income (
    order_no INT,
    income DECIMAL(10, 2),
    cost DECIMAL(10, 2),
    PRIMARY KEY (order_no),
    FOREIGN KEY (order_no) REFERENCES orders(order_no)
);
```

### Project Setup
1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/M-Fast-Food.git
   cd M-Fast-Food
   ```

2. **Open the project** in your preferred Java IDE.

3. **Configure the database connection**:
   Ensure your database connection details are correct in the `DatabaseUtils` class:
   ```java
   class DatabaseUtils {
       public static Connection getConnection() throws SQLException {
           try {
               Class.forName("org.mariadb.jdbc.Driver");
           } catch (ClassNotFoundException ex) {
               ex.printStackTrace();
               throw new SQLException("Driver not found");
           }
           return DriverManager.getConnection("jdbc:mysql://localhost:3306/M_Fast_Food", "root", ""); // Update with your DB details
       }
   }
   ```

4. **Run the project**:
   - Execute the `MainFrame` class to start the application.

## Usage
- **Login**: Choose to log in as a manager or staff.
- **Manager**: Access options for managing stores, staff, warehouse, and generating reports.
- **Staff**: Access options for creating and reviewing orders.
- **Order Processing**: Customize orders, add items, and process payments.
- **Reports**: Generate and view performance and sales reports.


