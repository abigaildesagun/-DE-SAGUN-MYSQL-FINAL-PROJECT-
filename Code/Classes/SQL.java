package classes;
import java.sql.*;

public class SQL {
    private Connection connection;
    private final String url = "jdbc:mysql://localhost:3306/pera_patrol_db";
    private final String user = "root";
    private final String password = "torameow";

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection successful.");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void populateBudgets(double totalBudget, double billsBudget, double wantsBudget, double savingsBudget) {
        String query = "INSERT INTO budgets (total_budget, bills_budget, wants_budget, savings_budget, remaining_bills, remaining_wants, remaining_savings) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, totalBudget);
            stmt.setDouble(2, billsBudget);
            stmt.setDouble(3, wantsBudget);
            stmt.setDouble(4, savingsBudget);
            stmt.setDouble(5, billsBudget);
            stmt.setDouble(6, wantsBudget);
            stmt.setDouble(7, savingsBudget);
            stmt.executeUpdate();
            System.out.println("Budgets populated successfully.");
        } catch (SQLException e) {
            System.out.println("Error populating budgets: " + e.getMessage());
        }
    }

    public void insertExpense(int categoryId, String description, double amount) {
        String query = "INSERT INTO expenses (category_id, description, amount) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, categoryId);
            stmt.setString(2, description);
            stmt.setDouble(3, amount);
            stmt.executeUpdate();
            System.out.println("Expense added successfully.");
        } catch (SQLException e) {
            System.out.println("Error inserting expense: " + e.getMessage());
        }
    }

    public void fetchExpensesByCategory(int categoryId) {
        String query = "SELECT description, amount FROM expenses WHERE category_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();
            System.out.printf("%-15s%-15s%n", "Description", "Amount");
            while (rs.next()) {
                String description = rs.getString("description");
                double amount = rs.getDouble("amount");
                System.out.printf("%-15s%-15.2f%n", description, amount);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching expenses: " + e.getMessage());
        }
    }

    public void updateExpensePrice(String label, int categoryId, double newAmount) {
        String findQuery = "SELECT expense_id FROM expenses WHERE description = ? AND category_id = ?";
        String updateQuery = "UPDATE expenses SET amount = ? WHERE expense_id = ?";
        
        try (PreparedStatement findStmt = connection.prepareStatement(findQuery)) {
            findStmt.setString(1, label);
            findStmt.setInt(2, categoryId);
            ResultSet rs = findStmt.executeQuery();
            
            if (rs.next()) {
                int expenseId = rs.getInt("expense_id");
                
                try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                    updateStmt.setDouble(1, newAmount);
                    updateStmt.setInt(2, expenseId);
                    int rowsUpdated = updateStmt.executeUpdate();
                    
                    if (rowsUpdated > 0) {
                        System.out.println("Expense updated successfully.");
                    } else {
                        System.out.println("Expense not found.");
                    }
                }
            } else {
                System.out.println("Expense with the given label and category not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating expense: " + e.getMessage());
        }
    }

    public void deleteExpense(String label, int categoryId) {
        String findQuery = "SELECT expense_id FROM expenses WHERE description = ? AND category_id = ?";
        String deleteQuery = "DELETE FROM expenses WHERE expense_id = ?";
        
        try (PreparedStatement findStmt = connection.prepareStatement(findQuery)) {
            findStmt.setString(1, label);
            findStmt.setInt(2, categoryId);
            ResultSet rs = findStmt.executeQuery();
            
            if (rs.next()) {
                int expenseId = rs.getInt("expense_id");
                
                try (PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
                    deleteStmt.setInt(1, expenseId);
                    int rowsDeleted = deleteStmt.executeUpdate();
                    
                    if (rowsDeleted > 0) {
                        System.out.println("Expense deleted successfully.");
                    } else {
                        System.out.println("Expense not found.");
                    }
                }
            } else {
                System.out.println("Expense with the given label and category not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting expense: " + e.getMessage());
        }
    }
    
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }

}

