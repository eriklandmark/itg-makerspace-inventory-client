package sqlitetojava;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
	
	public static void connect(String filename) {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:db/" + filename;
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
	
	public static void createNewDatabase(String fileName) {
		 
        String url = "jdbc:sqlite:db/" + fileName;
 
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public static void insert(String name, int capacity) {
        String sql = "INSERT INTO inventories(name,quantity) VALUES(?,?)";
        String url = "jdbc:sqlite:db/inventory.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, capacity);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public static void selectAll(){
        String sql = "SELECT id, name, quantity FROM inventories";
        String url = "jdbc:sqlite:db/inventory.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try (Statement stmt  = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" + 
                                   rs.getString("name") + "\t" +
                                   rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public static void update(int quantity, String name) {
        String sql = "UPDATE inventories SET quantity = ? "
                + "WHERE name = ?";
        String url = "jdbc:sqlite:db/inventory.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            pstmt.setString(2, name);
            pstmt.setInt(1, quantity);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

	
    public static void main(String[] args) {
        //selectAll();
        update(3, "Google");
    }
}
