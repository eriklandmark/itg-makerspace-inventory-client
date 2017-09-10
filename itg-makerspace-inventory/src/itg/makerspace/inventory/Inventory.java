package itg.makerspace.inventory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Inventory {
	
	public static ArrayList<InventoryItem> inventory = new ArrayList<InventoryItem>();
	
	public Inventory() {
		
	}
	
	public boolean loadInventoryFromDB() {
		String sql = "SELECT id, name, barcode, description, quantity FROM inventory";
        String url = "jdbc:sqlite:database/inventory.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        try (Statement stmt  = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)){
            
            while (rs.next()) {
            	InventoryItem item = new InventoryItem();
            	item.id = rs.getInt("id");
            	item.name = rs.getString("name");
            	item.barcode = rs.getString("barcode");
            	item.description = rs.getString("description");
            	item.quantity = rs.getInt("quantity");
            	inventory.add(item);
            }
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
	}
	
	public boolean hasLoadedInventory() {
		if(inventory.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public InventoryItem getItemFromBarcode(String barcode) {
		for(int i = 0; i < inventory.size(); i++) {
			InventoryItem item = inventory.get(i);
			if (item.barcode.equals(barcode)) {
				return item;
			}
		}
		return null;
	}
}
