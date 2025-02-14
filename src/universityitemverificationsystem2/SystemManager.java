package universityitemverificationsystem2;

import java.sql.*;
import java.util.UUID;

public class SystemManager {

    public void registerUser(String name, String father_name, String department, int year, int id) {
        String query = "INSERT INTO users (name, father_name, department, year, id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
             
            pstmt.setString(1, name);
            pstmt.setString(2, father_name);
            pstmt.setString(3, department);
            pstmt.setInt(4, year);
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
            System.out.println("User registered successfully!");
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
        }
    }

  public boolean registerItem(String type_category, String model_brand, int user_id) {
    String userCheckQuery = "SELECT id FROM users WHERE id = ?";
    String insertQuery = "INSERT INTO items (type_category, model_brand, user_id) VALUES (?, ?, ?)";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement userCheckStmt = conn.prepareStatement(userCheckQuery);
         PreparedStatement pstmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

        userCheckStmt.setInt(1, user_id);
        ResultSet userRs = userCheckStmt.executeQuery();
        if (!userRs.next()) {
            System.out.println("Error: User ID " + user_id + " does not exist.");
            return false;
        }

        
        pstmt.setString(1, type_category);
        pstmt.setString(2, model_brand);
        pstmt.setInt(3, user_id);

        int rowsInserted = pstmt.executeUpdate();
        if (rowsInserted > 0) {
           
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int registrationId = generatedKeys.getInt(1);
                System.out.println("Item registered successfully.");
                System.out.println("---------------------------------");
                System.out.println(" Registration ID: " + registrationId);
                System.out.println("---------------------------------");
                return true;
            } else {
                System.out.println("Item registered, but failed to retrieve Registration ID.");
                return false;
            }
        } else {
            System.out.println("Failed to register item.");
            return false;
        }

    } catch (SQLException e) {
        System.err.println("Error registering item: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}

   public void verifyItem(int userId, int itemId) {

    String query = "SELECT items.id AS item_id, items.type_category, items.model_brand, " +
                   "users.name AS user_name, users.father_name, users.id AS user_id " +
                   "FROM items " +
                   "INNER JOIN users ON items.user_id = users.id " +
                   "WHERE items.id = ? AND users.id = ?";  // Match both item_id and user_id

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setInt(1, itemId);  // Set the item ID
        pstmt.setInt(2, userId);  // Set the user ID

        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            
            System.out.println("__________________________");
            System.out.println("Verification Successful!");
            System.out.println("__________________________");
            System.out.println("Item Category: " + rs.getString("type_category"));
            System.out.println("Item Model/Brand: " + rs.getString("model_brand"));
            System.out.println("User Name: " + rs.getString("user_name"));
            System.out.println("Father's Name: " + rs.getString("father_name"));
        } else {
            System.out.println("_____________________________________________________");
            System.out.println("No match found. Mismatched or unauthorized item/user.");
            System.out.println("_____________________________________________________");

            flagMismatchedItem(userId, itemId);
        }
    } catch (SQLException e) {
        System.out.println("______________________________________");
        System.err.println("Error verifying item: " + e.getMessage());
        System.out.println("______________________________________");
    }
}

private void flagMismatchedItem(int userId, int itemId) {
    String updateQuery = "UPDATE items SET flagged = 1 WHERE id = ? AND user_id != ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

        pstmt.setInt(1, itemId);
        pstmt.setInt(2, userId);

        int rowsUpdated = pstmt.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Item flagged as mismatched.");
        } else {
            System.out.println("No item found to flag for mismatching user ID and item ID.");
        }
    } catch (SQLException e) {
        System.err.println("Error flagging mismatched item: " + e.getMessage());
    }
}


    public void generateReport() {
    String query = "SELECT items.id AS item_id, items.type_category, items.model_brand, " +
                   "users.name AS owner_name, users.father_name, users.id AS user_id " +
                   "FROM items " +
                   "INNER JOIN users ON items.user_id = users.id";

    try (Connection conn = DatabaseConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {
        System.out.println("__________________________");
        System.out.println("Registered Items Report:");
        System.out.println("__________________________");
        while (rs.next()) {

            System.out.println("Item ID: " + rs.getInt("item_id"));
            System.out.println("Item Category: " + rs.getString("type_category"));
            System.out.println("Item Model/Brand: " + rs.getString("model_brand"));
            System.out.println("Owner Name: " + rs.getString("owner_name"));
            System.out.println("Owner Father's Name: " + rs.getString("father_name"));
            System.out.println("Owner ID: " + rs.getInt("user_id"));
            System.out.println("----------------------------");
        }
    } catch (SQLException e) {
        System.out.println("_____________________________________");
        System.err.println("Error generating report: " + e.getMessage());
        System.out.println("_____________________________________");
    }
    
 
}

public void displayFlaggedItems() {

    String query = "SELECT items.id AS item_id, items.type_category, items.model_brand, " +
                   "users.name AS owner_name, users.father_name, users.id AS user_id " +
                   "FROM items " +
                   "LEFT JOIN users ON items.user_id = users.id " +
                   "WHERE items.flagged = 1";

    try (Connection conn = DatabaseConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {
        
        System.out.println("________________________________");
        System.out.println("Flagged Mismatched Items Report:");
        System.out.println("________________________________");
        if (!rs.next()) {
            System.out.println("No flagged items found.");
        } else {
            do {
                
                System.out.println("Item ID: " + rs.getInt("item_id"));
                System.out.println("Item Category: " + rs.getString("type_category"));
                System.out.println("Item Model/Brand: " + rs.getString("model_brand"));
                System.out.println("Flagged - No valid owner found or mismatched user ID.");
                System.out.println("----------------------------");
            } while (rs.next());
        }
    } catch (SQLException e) {
        System.err.println("Error fetching flagged items: " + e.getMessage());
    }
}

}
