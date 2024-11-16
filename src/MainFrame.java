import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MainFrame extends JFrame {
	public MainFrame() {
        setTitle("M Fast Food");
        setSize(1000,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton managerButton = new JButton("Manager");
        JButton staffButton = new JButton("Staff");
        
        Font buttonFont = new Font("Arial", Font.PLAIN, 50);
        managerButton.setFont(buttonFont);
        staffButton.setFont(buttonFont);

        managerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManagerLoginFrame managerLoginFrame = new ManagerLoginFrame();
                managerLoginFrame.setVisible(true);
                setVisible(false);
            }
        });

        staffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StaffLoginFrame staffLoginFrame = new StaffLoginFrame();
                staffLoginFrame.setVisible(true);
                setVisible(false);
            }
        });

        JLabel titleLabel = new JLabel("M Fast Food");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 75));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 0, 20));
        buttonPanel.add(managerButton);
        buttonPanel.add(staffButton);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(titleLabel);
        panel.add(buttonPanel);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(panel);
        
    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}

class ManagerLoginFrame extends JFrame {
    public ManagerLoginFrame() {
        setTitle("Manager Login");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel staffIdLabel = new JLabel("Staff ID");
        JTextField staffIdField = new JTextField();
        JLabel passwordLabel = new JLabel("Password");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");
        
        Font labelFont = new Font("Arial", Font.PLAIN, 25);
        staffIdLabel.setFont(labelFont);
        passwordLabel.setFont(labelFont);
        staffIdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        staffIdField.setMaximumSize(new Dimension(200,100));
        passwordField.setMaximumSize(new Dimension(200,100));
        
        Font buttonFont = new Font("Arial", Font.PLAIN, 20);
        loginButton.setFont(buttonFont);
        backButton.setFont(buttonFont);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String staffId = staffIdField.getText();
                String password = String.valueOf(passwordField.getPassword());

                if (verifyManagerLogin(staffId, password)) {
                    ManagerSelectionFrame managerSelectionFrame = new ManagerSelectionFrame(staffId, password);
                    managerSelectionFrame.setVisible(true);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid staff ID or password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
                setVisible(false);
            }
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(staffIdLabel);
        panel.add(staffIdField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(buttonPanel);
        
        setLayout(new GridBagLayout());
        add(panel);
        
    }
    
    private boolean verifyManagerLogin(String dbUser, String dbPassword) {
	    try {
	        Class.forName("org.mariadb.jdbc.Driver");
	    } catch (ClassNotFoundException ex) {
	        ex.printStackTrace();
	        return false; // If the driver class is not found, return false
	    }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/M_Fast_Food", dbUser, dbPassword)) {
            String query = "SELECT * FROM staff WHERE staff_id = ? AND password = ? AND ifManager = true";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, dbUser);
            statement.setString(2, dbPassword);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // If there's a match, resultSet.next() will return true
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}

class StaffLoginFrame extends JFrame {
	public StaffLoginFrame() {
	    setTitle("Staff Login");
	    setSize(1000, 700);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLocationRelativeTo(null);

	    JLabel staffIdLabel = new JLabel("Staff ID");
	    JTextField staffIdField = new JTextField();
	    JLabel passwordLabel = new JLabel("Password");
	    JPasswordField passwordField = new JPasswordField();
	    JButton loginButton = new JButton("Login");
	    JButton backButton = new JButton("Back");

	    Font labelFont = new Font("Arial", Font.PLAIN, 25);
	    staffIdLabel.setFont(labelFont);
	    passwordLabel.setFont(labelFont);
	    staffIdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

	    staffIdField.setMaximumSize(new Dimension(200,100));
	    passwordField.setMaximumSize(new Dimension(200,100));

	    Font buttonFont = new Font("Arial", Font.PLAIN, 20);
	    loginButton.setFont(buttonFont);
	    backButton.setFont(buttonFont);

	    loginButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            String staffId = staffIdField.getText();
	            String password = String.valueOf(passwordField.getPassword());

	            if (verifyStaffLogin(staffId, password)) {
	                SelectActionFrame selectActionFrame = new SelectActionFrame(staffId, password);
	                selectActionFrame.setVisible(true);
	                setVisible(false);
	            } else {
	                JOptionPane.showMessageDialog(null, "Invalid staff ID or password.", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    });

	    backButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            MainFrame mainFrame = new MainFrame();
	            mainFrame.setVisible(true);
	            setVisible(false);
	        }
	    });

	    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    buttonPanel.add(loginButton);
	    buttonPanel.add(backButton);

	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.add(staffIdLabel);
	    panel.add(staffIdField);
	    panel.add(passwordLabel);
	    panel.add(passwordField);
	    panel.add(buttonPanel);

	    setLayout(new GridBagLayout());
	    add(panel);
	    
	}
    
    private boolean verifyStaffLogin(String dbUser, String dbPassword) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return false; // If the driver class is not found, return false
        }
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/M_Fast_Food", dbUser, dbPassword)) {
            String query = "SELECT * FROM staff WHERE staff_id = ? AND password = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, dbUser);
            statement.setString(2, dbPassword);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // If there's a match, login is successful
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false; // Login failed due to an exception
        }
    }
}

class ManagerSelectionFrame extends JFrame {
	public ManagerSelectionFrame(String dbUser, String dbPassword) {
        setTitle("Manager Selection");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton storeManagementButton = new JButton("Store Management");
        JButton performanceReportButton = new JButton("Performance Report");
        JButton backButton = new JButton("Back");

        Font buttonFont = new Font("Arial", Font.PLAIN, 35);
        storeManagementButton.setFont(buttonFont);
        performanceReportButton.setFont(buttonFont);
        backButton.setFont(buttonFont);

        storeManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StoreManagementFrame storeManagementFrame = new StoreManagementFrame(dbUser, dbPassword);
                storeManagementFrame.setVisible(true);
                setVisible(false);
            }
        });

        performanceReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PerformanceReportFrame performanceReportFrame = new PerformanceReportFrame(dbUser, dbPassword);
                performanceReportFrame.setVisible(true);
                setVisible(false);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManagerLoginFrame managerLoginFrame = new ManagerLoginFrame();
                managerLoginFrame.setVisible(true);
                setVisible(false);
            }
        });

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(storeManagementButton);
        panel.add(performanceReportButton);
        panel.add(backButton);

        add(panel);
        
    }
}

class StoreManagementFrame extends JFrame {
	public StoreManagementFrame(String dbUser, String dbPassword) {
        setTitle("Store Management");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton warehouseManagementButton = new JButton("Warehouse Management");
        JButton productManagementButton = new JButton("Product Management");
        JButton staffManagementButton = new JButton("Staff Management");
        JButton backButton = new JButton("Back");
        
        Font buttonFont = new Font("Arial", Font.PLAIN, 35);
        warehouseManagementButton.setFont(buttonFont);
        productManagementButton.setFont(buttonFont);
        staffManagementButton.setFont(buttonFont);
        backButton.setFont(buttonFont);
        
        warehouseManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WarehouseManagementFrame warehouseManagementFrame = new WarehouseManagementFrame(dbUser, dbPassword);
                warehouseManagementFrame.setVisible(true);
                setVisible(false);
            }
        });

        productManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductManagementFrame productManagementFrame = new ProductManagementFrame(dbUser, dbPassword);
                productManagementFrame.setVisible(true);
                setVisible(false);
            }
        });

        staffManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StaffManagementFrame staffManagementFrame = new StaffManagementFrame(dbUser, dbPassword);
                staffManagementFrame.setVisible(true);
                setVisible(false);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManagerSelectionFrame managerSelectionFrame = new ManagerSelectionFrame(dbUser, dbPassword);
                managerSelectionFrame.setVisible(true);
                setVisible(false);
            }
        });

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10)); 
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(warehouseManagementButton);
        panel.add(productManagementButton);
        panel.add(staffManagementButton);
        panel.add(backButton);
        add(panel);
        
    }
}

class WarehouseManagementFrame extends JFrame {
    private DefaultTableModel tableModel;
    private JTextField amountTextField;
    private JComboBox<String> ingredientComboBox;

    public WarehouseManagementFrame(String dbUser, String dbPassword) {
        setTitle("Warehouse Management");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        ingredientComboBox = new JComboBox<>();
        amountTextField = new JTextField(10);

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load MariaDB JDBC driver.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/M_Fast_Food", dbUser, dbPassword)) {
            tableModel = new DefaultTableModel();
            tableModel.addColumn("Ingredient ID");
            tableModel.addColumn("Ingredient Name");
            tableModel.addColumn("Amount");

            String query = "SELECT ingredient_id, ingredient_name, amount FROM Inventory";
            try (PreparedStatement statement = conn.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String ingredientId = resultSet.getString("ingredient_id");
                    String ingredientName = resultSet.getString("ingredient_name");
                    int amount = resultSet.getInt("amount");
                    tableModel.addRow(new Object[]{ingredientId, ingredientName, amount});
                }
            }

            JTable table = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table);
            panel.add(scrollPane, BorderLayout.CENTER);

            JPanel controlPanel = new JPanel(new GridLayout(0, 2));
            
            JLabel ingredtentLabel = new JLabel("Ingredient: ");
            controlPanel.add(ingredtentLabel);
            
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                ingredientComboBox.addItem((String) tableModel.getValueAt(i, 1));
            }
            controlPanel.add(ingredientComboBox);

            JLabel amountLabel = new JLabel("Amount:");
            controlPanel.add(amountLabel);
            
            amountTextField = new JTextField();
            controlPanel.add(amountTextField);

            JButton addButton = new JButton("Add");
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addAmount();
                }
            });
            controlPanel.add(addButton);

            JButton subtractButton = new JButton("Subtract");
            subtractButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    subtractAmount();
                }
            });
            controlPanel.add(subtractButton);
            
            controlPanel.add(new JLabel(""));
            controlPanel.add(new JLabel(""));
            
            JButton addNewItemButton = new JButton("Add New Item");
            addNewItemButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addNewIngredient(); // Call method to handle adding new item
                }
            });
            controlPanel.add(addNewItemButton);
            
            JButton backButton = new JButton("Back");
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	StoreManagementFrame storeManagementFrame = new StoreManagementFrame(dbUser, dbPassword);
                	storeManagementFrame.setVisible(true);
                    setVisible(false);
                }
            });
            controlPanel.add(backButton);

            panel.add(controlPanel, BorderLayout.SOUTH);
            
            add(panel);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to fetch warehouse information.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addAmount() {
    	try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/M_Fast_Food", "root", "")) {
	        int selectedIndex = ingredientComboBox.getSelectedIndex();
	        if (selectedIndex == -1) {
	            // No item selected, handle this case if necessary
	            return;
	        }
	        
	        String ingredientId = (String) tableModel.getValueAt(selectedIndex, 0);
	        int amountToAdd = Integer.parseInt(amountTextField.getText());
	        int currentAmount = (int) tableModel.getValueAt(selectedIndex, 2);
	        tableModel.setValueAt(currentAmount + amountToAdd, selectedIndex, 2);
	        updateAmountInDatabase(ingredientId, currentAmount + amountToAdd);
    	
	        refreshTable(conn);
    	} catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add new ingredient.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void subtractAmount() {
    	try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/M_Fast_Food", "root", "")) {
	        int selectedIndex = ingredientComboBox.getSelectedIndex();
	        if (selectedIndex == -1) {
	            // No item selected, handle this case if necessary
	            return;
	        }
	        
	        String ingredientId = (String) tableModel.getValueAt(selectedIndex, 0);
	        int amountToSubtract = Integer.parseInt(amountTextField.getText());
	        int currentAmount = (int) tableModel.getValueAt(selectedIndex, 2);
	        tableModel.setValueAt(Math.max(0, currentAmount - amountToSubtract), selectedIndex, 2);
	        updateAmountInDatabase(ingredientId, Math.max(0, currentAmount - amountToSubtract));
    	
	        refreshTable(conn);
    	} catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add new ingredient.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void addNewIngredient() {
        String id = JOptionPane.showInputDialog(this, "Enter Ingredient ID:");
        String name = JOptionPane.showInputDialog(this, "Enter Ingredient Name:");
        String cost = JOptionPane.showInputDialog(this, "Enter Ingredient Cost:");
        String amount = JOptionPane.showInputDialog(this, "Enter Ingredient Amount:");

        if (id == null || name == null || cost == null || amount == null) {
            // User canceled input
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/M_Fast_Food", "root", "")) {
            String insertQuery = "INSERT INTO Inventory (ingredient_id, ingredient_name, cost, amount) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertStatement = conn.prepareStatement(insertQuery)) {
                insertStatement.setString(1, id);
                insertStatement.setString(2, name);
                insertStatement.setString(3, cost);
                insertStatement.setString(4, amount);
                insertStatement.executeUpdate();
            }
            refreshTable(conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add new ingredient.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void refreshTable(Connection conn) {
        tableModel.setRowCount(0);

        String query = "SELECT ingredient_id, ingredient_name, amount FROM Inventory";
        try (PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String ingredientId = resultSet.getString("ingredient_id");
                String ingredientName = resultSet.getString("ingredient_name");
                int amount = resultSet.getInt("amount");
                tableModel.addRow(new Object[]{ingredientId, ingredientName, amount});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to fetch warehouse information.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateAmountInDatabase(String ingredientId, int newAmount) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/M_Fast_Food", "root", "")) {
            String updateQuery = "UPDATE Inventory SET amount = ? WHERE ingredient_id = ?";
            try (PreparedStatement updateStatement = conn.prepareStatement(updateQuery)) {
                updateStatement.setInt(1, newAmount);
                updateStatement.setString(2, ingredientId);
                updateStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to update ingredient amount in the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

class ProductManagementFrame extends JFrame {
	private DefaultTableModel tableModel;

    public ProductManagementFrame(String dbUser, String dbPassword) {
        setTitle("Product Management");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load MariaDB JDBC driver.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/M_Fast_Food","root", "")) {
            tableModel = new DefaultTableModel();
            tableModel.addColumn("Product ID");
            tableModel.addColumn("Product Name");
            tableModel.addColumn("Ingredients");
            tableModel.addColumn("Price");

            String query = "SELECT p.product_id, p.product_name, GROUP_CONCAT(iv.ingredient_name SEPARATOR ', ') AS ingredients, p.price " +
                    "FROM product p " +
                    "INNER JOIN ingredient i ON p.product_id = i.product_id " +
                    "INNER JOIN Inventory iv ON i.ingredient = iv.ingredient_id " +
                    "GROUP BY p.product_id";
            try (PreparedStatement statement = conn.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
            	while (resultSet.next()) {
            	    String productId = resultSet.getString("product_id");
            	    String productName = resultSet.getString("product_name");
            	    String ingredients = resultSet.getString("ingredients"); // Now contains ingredient names
            	    double price = resultSet.getDouble("price");
            	    tableModel.addRow(new Object[]{productId, productName, ingredients, price});
            	}
            }


            JTable table = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table);

            panel.add(scrollPane, BorderLayout.CENTER);

            JPanel controlPanel = new JPanel(new FlowLayout());

            JButton addProductButton = new JButton("Add New Product");
            addProductButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addNewProduct();
                }
            });
            controlPanel.add(addProductButton);
            
            JButton backButton = new JButton("Back");
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	StoreManagementFrame storeManagementFrame = new StoreManagementFrame(dbUser, dbPassword);
                	storeManagementFrame.setVisible(true);
                    setVisible(false);
                }
            });
            controlPanel.add(backButton);
            
            panel.add(controlPanel, BorderLayout.SOUTH);

            add(panel);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to fetch product information.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addNewProduct() {
        String id = JOptionPane.showInputDialog(this, "Enter Product ID:");
        String name = JOptionPane.showInputDialog(this, "Enter Product Name:");
        String price = JOptionPane.showInputDialog(this, "Enter Product Price:");

        if (id == null || name == null || price == null) {
            // User canceled input
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/M_Fast_Food", "root", "")) {
            String insertProductQuery = "INSERT INTO product (product_id, product_name, price) VALUES (?, ?, ?)";
            try (PreparedStatement insertProductStatement = conn.prepareStatement(insertProductQuery)) {
                insertProductStatement.setString(1, id);
                insertProductStatement.setString(2, name);
                insertProductStatement.setString(3, price);
                insertProductStatement.executeUpdate();
                
	            int maxIngredients = 5;
	            List<String> ingredientNames = new ArrayList<>();
	            List<String> ingredientAmounts = new ArrayList<>();
	            
	            for (int i = 1; i <= maxIngredients; i++) {
	                String ingredientId = JOptionPane.showInputDialog(this, "Enter Ingredient ID for Ingredient " + i + " (or leave blank to finish):");
	                if (ingredientId == null || ingredientId.isEmpty()) {
	                    // No more ingredients to add
	                    break;
	                }
	                String ingredientName = getIngredientName(ingredientId);
	                if (ingredientName == null) {
	                    JOptionPane.showMessageDialog(this, "Invalid ingredient ID.", "Error", JOptionPane.ERROR_MESSAGE);
	                    return;
	                }
	                String ingredientAmount = JOptionPane.showInputDialog(this, "Enter Ingredient Amount for Ingredient " + i + ":");
	                if (ingredientAmount == null) {
	                    // User canceled input
	                    return;
	                }
	                ingredientNames.add(ingredientId); // Store ingredient names
	                ingredientAmounts.add(ingredientAmount);
	            }
	
	            String insertIngredientQuery = "INSERT INTO ingredient (product_id, ingredient, amount) VALUES (?, ?, ?)";
	            try (PreparedStatement insertIngredientStatement = conn.prepareStatement(insertIngredientQuery)) {
	                for (int i = 0; i < ingredientNames.size(); i++) {
	                    insertIngredientStatement.setString(1, id);
	                    insertIngredientStatement.setString(2, ingredientNames.get(i));
	                    insertIngredientStatement.setString(3, ingredientAmounts.get(i));
	                    insertIngredientStatement.executeUpdate();
	                }
	            }
            }
            refreshTable(conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add new product.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String getIngredientName(String ingredientId) throws SQLException {
        String ingredientName = null;
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/M_Fast_Food", "root", "")) {
            String query = "SELECT ingredient_name FROM Inventory WHERE ingredient_id = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, ingredientId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        ingredientName = resultSet.getString("ingredient_name");
                    } else {
                        ingredientName = null; // Ingredient not found
                    }
                }
            }
        }
        return ingredientName;
    }

    private void refreshTable(Connection conn) {
        tableModel.setRowCount(0);

        String query = "SELECT p.product_id, p.product_name, GROUP_CONCAT(iv.ingredient_name SEPARATOR ', ') AS ingredients, p.price " +
                "FROM product p " +
                "INNER JOIN ingredient i ON p.product_id = i.product_id " +
                "INNER JOIN Inventory iv ON i.ingredient = iv.ingredient_id " +
                "GROUP BY p.product_id";
        try (PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
        	while (resultSet.next()) {
        	    String productId = resultSet.getString("product_id");
        	    String productName = resultSet.getString("product_name");
        	    String ingredients = resultSet.getString("ingredients"); // Now contains ingredient names
        	    double price = resultSet.getDouble("price");
        	    tableModel.addRow(new Object[]{productId, productName, ingredients, price});
        	}

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to fetch product information.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

class StaffManagementFrame extends JFrame {
	private DefaultTableModel tableModel;
	private JTextField idTextField;
    private JTextField nameTextField;
    private JTextField passwordTextField;
    private JCheckBox managerCheckBox;
    
    public StaffManagementFrame(String dbUser, String dbPassword) {
        setTitle("Staff Management");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Staff ID");
        tableModel.addColumn("Staff Name");
        tableModel.addColumn("Password");
        tableModel.addColumn("Manager");
        
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load MariaDB JDBC driver.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/M_Fast_Food", dbUser, dbPassword)) {
            String query = "SELECT * FROM staff";
            try (PreparedStatement statement = conn.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int staffId = resultSet.getInt("staff_id");
                    String staffName = resultSet.getString("s_name");
                    String password = resultSet.getString("password");
                    boolean isManager = resultSet.getBoolean("ifManager");
                    tableModel.addRow(new Object[]{staffId, staffName, password, isManager});
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to fetch staff information.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new GridLayout(0, 2));

        idTextField = new JTextField(10);
        controlPanel.add(new JLabel("Staff ID:"));
        controlPanel.add(idTextField);

        nameTextField = new JTextField(10);
        controlPanel.add(new JLabel("Staff Name:"));
        controlPanel.add(nameTextField);

        passwordTextField = new JTextField(10);
        controlPanel.add(new JLabel("Password:"));
        controlPanel.add(passwordTextField);

        managerCheckBox = new JCheckBox("Manager");
        controlPanel.add(managerCheckBox);
        
        controlPanel.add(new JLabel(""));
        controlPanel.add(new JLabel(""));

        JButton addButton = new JButton("Add Staff");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStaff(dbUser, dbPassword);
            }
        });
        controlPanel.add(addButton);
        
        controlPanel.add(new JLabel(""));
        controlPanel.add(new JLabel(""));
        controlPanel.add(new JLabel(""));
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	StoreManagementFrame storeManagementFrame = new StoreManagementFrame(dbUser, dbPassword);
            	storeManagementFrame.setVisible(true);
                setVisible(false);
            }
        });
        controlPanel.add(backButton);

        panel.add(controlPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private void addStaff(String dbUser, String dbPassword) {
        int staffId = Integer.parseInt(idTextField.getText());
        String staffName = nameTextField.getText();
        String password = passwordTextField.getText();
        boolean isManager = managerCheckBox.isSelected() ? true : false;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/M_Fast_Food", "root", "")) {
            String insertQuery = "INSERT INTO staff (staff_id, s_name, password, ifManager) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertStatement = conn.prepareStatement(insertQuery)) {
                insertStatement.setInt(1, staffId);
                insertStatement.setString(2, staffName);
                insertStatement.setString(3, password);
                insertStatement.setBoolean(4, isManager);
                insertStatement.executeUpdate();
            }

            String createUserQuery = "CREATE USER '" + staffId + "'@'localhost' IDENTIFIED BY '" + password + "'";
            try (Statement createUserStatement = conn.createStatement()) {
                createUserStatement.executeUpdate(createUserQuery);
            }

            String grantQuery = "GRANT ALL PRIVILEGES ON M_Fast_Food.* TO '" + staffId + "'@'localhost'";
            try (Statement grantStatement = conn.createStatement()) {
                grantStatement.executeUpdate(grantQuery);
            }
            refreshTable();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add new staff.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0); 
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/M_Fast_Food", "root", "")) {
            String query = "SELECT * FROM staff";
            try (PreparedStatement statement = conn.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int staffId = resultSet.getInt("staff_id");
                    String staffName = resultSet.getString("s_name");
                    String password = resultSet.getString("password");
                    boolean isManager = resultSet.getBoolean("ifManager");
                    tableModel.addRow(new Object[]{staffId, staffName, password, isManager});
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to refresh staff information.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

class PerformanceReportFrame extends JFrame {
	public PerformanceReportFrame(String dbUser, String dbPassword) {
        setTitle("Performance Report");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton itemSaleButton = new JButton("Item Sale");
        JButton performanceButton = new JButton("Performance");
        JButton backButton = new JButton("Back");

        Font buttonFont = new Font("Arial", Font.PLAIN, 35);
        itemSaleButton.setFont(buttonFont);
        performanceButton.setFont(buttonFont);
        backButton.setFont(buttonFont);

        itemSaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaleFrame saleFrame = new SaleFrame(dbUser, dbPassword);
                saleFrame.setVisible(true);
                setVisible(false);
            }
        });

        performanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PerformanceFrame performanceFrame = new PerformanceFrame(dbUser, dbPassword);
                performanceFrame.setVisible(true);
                setVisible(false);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManagerSelectionFrame managerSelectionFrame = new ManagerSelectionFrame(dbUser, dbPassword);
                managerSelectionFrame.setVisible(true);
                setVisible(false);
            }
        });

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(itemSaleButton);
        panel.add(performanceButton);
        panel.add(backButton);

        add(panel);
    }
}

class SaleFrame extends JFrame {
	private DefaultTableModel tableModel;
	public SaleFrame(String dbUser, String dbPassword) {
	    setTitle("Sales Report");
	    setSize(1000, 700);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLocationRelativeTo(null);

	    tableModel = new DefaultTableModel();
	    tableModel.addColumn("Product ID");
	    tableModel.addColumn("Total Sale Amount"); // Improved table header

	    try {
	        Class.forName("org.mariadb.jdbc.Driver");
	        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/M_Fast_Food", "root", "")) {
	            String query = "SELECT product_id, SUM(amount) as total_sale FROM Orders GROUP BY product_id";
	            PreparedStatement statement = conn.prepareStatement(query);
	            ResultSet resultSet = statement.executeQuery();
	            while (resultSet.next()) {
	                String productID = resultSet.getString("product_id");
	                int sale = resultSet.getInt("total_sale");
	                // Format sale amount, e.g., add currency symbol or commas
	                // You can use NumberFormat or DecimalFormat for better formatting
	                tableModel.addRow(new Object[]{productID, sale});
	            }
	        } catch (SQLException ex) {
	            // Handle SQL exception gracefully
	            JOptionPane.showMessageDialog(this, "Error accessing database: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
	            ex.printStackTrace();
	        }
	    } catch (ClassNotFoundException ex) {
	        // Handle class not found exception
	        JOptionPane.showMessageDialog(this, "Database driver not found.", "Error", JOptionPane.ERROR_MESSAGE);
	        ex.printStackTrace();
	    }

	    JButton back = new JButton("Back");
	    back.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            PerformanceReportFrame performanceReportFrame = new PerformanceReportFrame(dbUser, dbPassword);
	            performanceReportFrame.setVisible(true);
	            setVisible(false);
	        }
	    });

	    JTable table = new JTable(tableModel);
	    JScrollPane scrollPane = new JScrollPane(table); 
	    
	    JPanel panel = new JPanel(new BorderLayout());
	    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	    panel.add(scrollPane, BorderLayout.CENTER);
	    panel.add(back, BorderLayout.SOUTH);

	    // Add labels to explain the table
	    JLabel titleLabel = new JLabel("Sales Report");
	    titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
	    panel.add(titleLabel, BorderLayout.NORTH);

	    add(panel);
	}
}

class PerformanceFrame extends JFrame {
	private DefaultTableModel tableModel;
    public PerformanceFrame(String dbUser, String dbPassword) {
        setTitle("Performance Report");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Order No");
        tableModel.addColumn("Income");
        tableModel.addColumn("Cost");
        tableModel.addColumn("Net Profit");

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/M_Fast_Food", dbUser, dbPassword)) {
                String query = "SELECT order_no, income, cost FROM income";
                PreparedStatement statement = conn.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int orderNo = resultSet.getInt("order_no");
                    double income = resultSet.getDouble("income");
                    double cost = resultSet.getDouble("cost");
                    double netProfit = income - cost;
                    tableModel.addRow(new Object[]{orderNo, income, cost, netProfit});
                }
            } catch (SQLException ex) {
                // Handle SQL exception gracefully
                JOptionPane.showMessageDialog(this, "Error accessing database: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } catch (ClassNotFoundException ex) {
            // Handle class not found exception
            JOptionPane.showMessageDialog(this, "Database driver not found.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PerformanceReportFrame performanceReportFrame = new PerformanceReportFrame(dbUser, dbPassword);
                performanceReportFrame.setVisible(true);
                setVisible(false);
            }
        });

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table); // Add table to scrollable pane
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(back, BorderLayout.SOUTH);

        // Add labels to explain the table
        JLabel titleLabel = new JLabel("Performance Report");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel, BorderLayout.NORTH);

        add(panel);
    }
}


class SelectActionFrame extends JFrame {
	public SelectActionFrame(String dbUser, String dbPassword) {
        setTitle("Select Action");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton newOrderButton = new JButton("New Order");
        JButton orderReviewButton = new JButton("Order Review");
        JButton backButton = new JButton("Back");

        Font buttonFont = new Font("Arial", Font.PLAIN, 35);
        newOrderButton.setFont(buttonFont);
        orderReviewButton.setFont(buttonFont);
        backButton.setFont(buttonFont);

        newOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewOrderFrame newOrderFrame = new NewOrderFrame(dbUser, dbPassword);
                newOrderFrame.setVisible(true);
                setVisible(false);
            }
        });

        orderReviewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderReviewFrame orderReviewFrame = new OrderReviewFrame(dbUser, dbPassword);
                orderReviewFrame.setVisible(true);
                setVisible(false);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StaffLoginFrame staffLoginFrame = new StaffLoginFrame();
                staffLoginFrame.setVisible(true);
                setVisible(false);
            }
        });

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(newOrderButton);
        panel.add(orderReviewButton);
        panel.add(backButton);

        add(panel);
    }
}

class NewOrderFrame extends JFrame {
	
	private JLabel jLabel1,jLabel2;
    private JComboBox<String> opt;
    private JButton minus,plus,customize,addItem,pay;
    private JTextField num;
    private JTextArea selectedItem;
    
    public NewOrderFrame(String dbUser, String dbPassword) {
    	setTitle("Purchase Page");
        initComponents(dbUser, dbPassword);
    }
    
    
    private void initComponents(String dbUser, String dbPassword) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        jLabel1 = new JLabel("Menu");
        jLabel1.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setBounds(10, 20, 270, 40);
        add(jLabel1);

        try {
            Queue<String> queue = new LinkedList<>();
            Class.forName("org.mariadb.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/M_Fast_Food","root","");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from product");
            while(rs.next()) {
                for(int i=0;i<rs.getMetaData().getColumnCount();i++) {
                    queue.add(rs.getString(i+1)+ " " +rs.getString(i+2) + " " +rs.getString(i+3));
                    i+=2;
                }
            }
            String[] items = new String[queue.size()];
            int index = 0;
            while (!queue.isEmpty()) {
                items[index] = queue.remove();
                index++;
            }
            
            opt = new JComboBox<>(items);
            opt.setBounds(10, 70, 270, 30);
            add(opt);
            
            stmt.close();
            con.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        minus = new JButton("-");
        minus.setBounds(10, 115, 50, 55);
        add(minus);

        num = new JTextField("0");
        num.setBounds(65, 115, 50, 55);
        add(num);

        plus = new JButton("+");
        plus.setBounds(120, 115, 50, 55);
        add(plus);

        customize = new JButton("Customize");
        customize.setBounds(175, 145, 105, 25);
        add(customize);
        
        addItem = new JButton("Add");
        addItem.setBounds(175, 115, 105, 25);
        add(addItem);

        selectedItem = new JTextArea();
        selectedItem.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(selectedItem);
        scrollPane.setBounds(10, 200, 270, 100);
        add(scrollPane);

        jLabel1 = new JLabel("Item(s) selected:");
        jLabel1.setBounds(10, 175, 200, 20);
        add(jLabel1);

        pay = new JButton("Purchase");
        pay.setBounds(190, 305, 90, 30);
        add(pay);
        
        JButton backButton = new JButton("Back");
        backButton.setBounds(10, 305, 90, 30);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	SelectActionFrame selectActionFrame = new SelectActionFrame(dbUser,dbPassword);
            	selectActionFrame.setVisible(true);
                setVisible(false);
            }
        });
        add(backButton);

        setSize(300, 380);
        setLocationRelativeTo(null);

        // Event Listeners
        
        minus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                int n = Integer.parseInt(num.getText());
                if (n > 0) {
                    n--;
                    num.setText(Integer.toString(n));
                }
            }
        });

        plus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                int n = Integer.parseInt(num.getText());
                n++;
                num.setText(Integer.toString(n));
            }
        });

        addItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String item = opt.getSelectedItem().toString();
                int n = Integer.parseInt(num.getText());
                if (n != 0) {
                    selectedItem.append(item + " " + n + "\n");
                }
            }
        });
        
        customize.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                JFrame additionalOptionsFrame = new JFrame("Additional Options");
                additionalOptionsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
                String [] special = {"normal", "more", "less"};
                String item = opt.getSelectedItem().toString();
                JComboBox<String> iceComboBox = new JComboBox<>(special);
                JComboBox<String> saltComboBox = new JComboBox<>(special);
                jLabel1 = new JLabel("Ice: ");
                jLabel2 = new JLabel("Salt:");
                if(item.contains("S00")){
                }else if(item.contains("C00")){
                    saltComboBox.setVisible(false);
                    jLabel2.setVisible(false);
                }else{
                    iceComboBox.setVisible(false);
                    jLabel1.setVisible(false);
                }
    
                JButton confirmButton = new JButton("Confirm");
                confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String selectedIceOption = iceComboBox.getSelectedItem().toString();
                        String selectedSaltOption = saltComboBox.getSelectedItem().toString();
                        
                        int n = Integer.parseInt(num.getText());
                        if (n != 0) {
                            selectedItem.append(item + " " + n + " ice: " + selectedIceOption + " salt: " + selectedSaltOption + "\n");
                        }
            
                        additionalOptionsFrame.dispose();
                    }
                });
    
                additionalOptionsFrame.setLayout(new GridLayout(3, 2));
    
                additionalOptionsFrame.add(jLabel1);
                additionalOptionsFrame.add(iceComboBox);
                additionalOptionsFrame.add(jLabel2);
                additionalOptionsFrame.add(saltComboBox);
                additionalOptionsFrame.add(new JLabel());
                additionalOptionsFrame.add(confirmButton);
    
                additionalOptionsFrame.setSize(300, 150);
                additionalOptionsFrame.setVisible(true);
            }
        });
        
        pay.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                double totalAmount = 0;
                double paidAmount = 0;
                double updatedIngredient = 0;
                String staffid = null;
                String password = null;
                Boolean isStaff = false;
                Boolean hvDiscount = false;
                int orderNo = 0;
                double cost = 0;
                double totalCost = 0;
                
                try{
                    Class.forName("org.mariadb.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/M_Fast_Food","root","");
                    Statement stmt = con.createStatement();
                    
                    //staff discount & service fee
                    JPanel panel = new JPanel();
                    panel.setLayout(new GridLayout(2, 2));
                    JLabel idLabel = new JLabel("Staff ID:");
                    JTextField idField = new JTextField();
                    panel.add(idLabel);
                    panel.add(idField);
                    
                    int result = JOptionPane.showOptionDialog(null, panel, "Staff Discount",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
                    
                    if (result == JOptionPane.OK_OPTION) {
                        staffid = idField.getText();
                    }
                    
                    ResultSet rs = stmt.executeQuery("select staff_id,password from staff");
                    while(rs.next()) {
                        for(int i=0;i<rs.getMetaData().getColumnCount();i++) {
                            if(!staffid.equals("null") && staffid.equals(rs.getString(i+1)) ){
                                rs = stmt.executeQuery("select sum(price) from orders left join product on orders.product_id = product.product_id where staff_id = " + staffid + " group by staff_id");
                                isStaff = true;
                                if(isStaff) {
                                	if(rs.next()){
	                                    String str = rs.getString(i+1);
	                                    int purchasedPrice = Integer.parseInt(str);
	                                    if (purchasedPrice < 200){
	                                        JOptionPane.showMessageDialog(null, "Login succuessful. You can enjoy 60% off.");
	                                        hvDiscount = true;
	                                    }else{
	                                        JOptionPane.showMessageDialog(null, "Login succuessful.");
	                                    }
	                                }else{
	                                    JOptionPane.showMessageDialog(null, "Login succuessful. You can enjoy 60% off.");
	                                    hvDiscount = true;
	                                }
                                }
                            }
                        }
                    }
                    
                    //record new order 
                    rs = stmt.executeQuery("select max(order_no) from orders");
                    if(rs.next()){
                        String str = rs.getString(1);
                        orderNo= Integer.parseInt(str)+1;
                    }
                    
                    String[] lines = selectedItem.getText().split("\n");
                    int price;
                    int quantity;
                    String ice,salt;
                    for (String line : lines) {
                        String[] parts = line.split(" ");
                        String productid = parts[0];
                        if(line.contains("ice")){
                            price = Integer.parseInt(parts[parts.length - 6]);
                            quantity = Integer.parseInt(parts[parts.length - 5]);
                            ice = parts[parts.length - 3];
                            if (ice.equals("normal")){
                                ice = null;
                            }
                            salt = parts[parts.length - 1];
                            if (salt.equals("normal")){
                                salt = null;
                            }
                            int flag = stmt.executeUpdate("insert into special values (" + orderNo + ",\"" + productid + "\",\"" + salt + "\",\"" + ice + "\")");
                        }else{
                            price = Integer.parseInt(parts[parts.length - 2]);
                            quantity = Integer.parseInt(parts[parts.length - 1]);
                        }
                        totalAmount += price * quantity;
                        
                        if(isStaff){
                            int flag = stmt.executeUpdate("insert into orders values (" + orderNo + ",\"" + productid + "\"," + quantity + "," + staffid + ")");
                        }else{
                            int flag = stmt.executeUpdate("insert into orders values (" + orderNo + ",\"" + productid + "\"," + quantity + ",null)");
                        }
                        
                        //inventory
                        rs = stmt.executeQuery("select ingredient.ingredient,ingredient.amount,inventory.amount,inventory.cost from ingredient join inventory on ingredient.ingredient=inventory.ingredient_id where product_id = \"" + productid + "\"");
                        while(rs.next()) {
                            for(int i=0;i<rs.getMetaData().getColumnCount();i++){
                                String ingredientId = rs.getString(i+1); 
                                String ingredientAmount = rs.getString(i+2);
                                String inventoryAmount = rs.getString(i+3);
                                cost = Double.parseDouble(rs.getString(i+4));
                                updatedIngredient= Integer.parseInt(inventoryAmount) - Integer.parseInt(ingredientAmount) * quantity;
                                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/M_Fast_Food", "root", "")) {
                                    String updateQuery = "UPDATE Inventory SET amount = ? WHERE ingredient_id = ?";
                                    try (PreparedStatement updateStatement = conn.prepareStatement(updateQuery)) {
                                        updateStatement.setDouble(1, updatedIngredient);
                                        updateStatement.setString(2, ingredientId);
                                        updateStatement.executeUpdate();
                                    }
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                }
                                totalCost += cost * quantity; 
                                i+=3;
                            }
                        }
                    }
                    stmt.close();
                    con.close();
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                }
                
            //receipt
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            if (isStaff) {
                if(hvDiscount){
                    paidAmount = totalAmount * 0.4;
                }else{
                    paidAmount = totalAmount;
                }
            } else {
                paidAmount = totalAmount * 1.1;
            }
            
            try{
                Class.forName("org.mariadb.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/m_fast_food","root","");
                Statement stmt = con.createStatement();
                int flag = stmt.executeUpdate("insert into income values (" + orderNo + "," + paidAmount + "," + totalCost + ")");
                stmt.close();
                con.close();
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
            
            JLabel itemLabel = new JLabel("Order No: " + orderNo);
            JTextArea receiptItem = new JTextArea("Ordered Item: \n" + selectedItem.getText());
            receiptItem.setEditable(false);
            JLabel priceLabel = new JLabel("Total Price: " + paidAmount);
            panel.add(itemLabel, BorderLayout.NORTH);
            panel.add(receiptItem, BorderLayout.CENTER);
            panel.add(priceLabel, BorderLayout.SOUTH);

            JOptionPane.showMessageDialog(null, panel, "Receipt", JOptionPane.INFORMATION_MESSAGE);
            
            SelectActionFrame selectActionFrame = new SelectActionFrame(dbUser, dbPassword);
            selectActionFrame.setVisible(true);
            dispose();
            
            }
        });
    }
}

class OrderReviewFrame extends JFrame {
	private DefaultTableModel tableModel;

    public OrderReviewFrame(String dbUser, String dbPassword) {
        setTitle("Order Review");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Order Number");
        tableModel.addColumn("Product ID");
        tableModel.addColumn("Product Name");
        tableModel.addColumn("Amount");
        tableModel.addColumn("Staff ID for Discount"); // Corrected column name

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/M_Fast_Food", "root", "")) {
                String query = "SELECT o.order_no, o.product_id, p.product_name, o.amount, o.staff_id FROM Orders AS o, product AS p WHERE p.product_id=o.product_id ORDER BY order_no";
                PreparedStatement statement = conn.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String orderNo = resultSet.getString("o.order_no"); // Corrected variable name
                    String productID = resultSet.getString("o.product_id");
                    String productName = resultSet.getString("p.product_name");
                    int amount = resultSet.getInt("o.amount");
                    String staffID = resultSet.getString("o.staff_id");
                    tableModel.addRow(new Object[]{orderNo, productID, productName, amount, staffID});
                }
            } catch (SQLException ex) {
                // Handle SQL exception gracefully
                JOptionPane.showMessageDialog(this, "Error accessing database: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } catch (ClassNotFoundException ex) {
            // Handle class not found exception
            JOptionPane.showMessageDialog(this, "Database driver not found.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SelectActionFrame selectActionFrame = new SelectActionFrame(dbUser, dbPassword);
                selectActionFrame.setVisible(true);
                setVisible(false);
            }
        });

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table); // Add table to scrollable pane
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(back, BorderLayout.SOUTH);

        // Add labels to explain the table
        JLabel titleLabel = new JLabel("Order Review");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel, BorderLayout.NORTH);

        add(panel);
    }
}

