package itg.makerspace.inventory;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import itg.makerspace.MainFrame;
import itg.makerspace.dialogs.InformationDialog;

public class Inventory {
	
	public static ArrayList<InventoryItem> inventory;
	
	public Inventory() {
		 inventory = new ArrayList<InventoryItem>();
	}
	
	public boolean loadInventoryFromDB() {
		String sql = "SELECT id, name, barcode, description, quantity, category FROM inventory_items";
        String url = "jdbc:sqlite:" + MainFrame.HOME_DIRECTORY + File.separator + "database" + File.separator + "inventory.sqlite";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Loaded database successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            InformationDialog dialog = new InformationDialog();
            dialog.open("Fel med databasen! \n" + e.getLocalizedMessage());
            return false;
        }
        try (Statement stmt = conn.createStatement(); 
        		ResultSet rs = stmt.executeQuery(sql)){
            
            while (rs.next()) {
            	InventoryItem item = new InventoryItem();
            	item.id = rs.getInt("id");
            	item.name = rs.getString("name");
            	item.barcode = rs.getString("barcode");
            	item.description = rs.getString("description");
            	item.quantity = rs.getInt("quantity");
            	item.category = rs.getInt("category");
            	inventory.add(item);
            }
            return true;
        } catch (SQLException e) {
        	InformationDialog dialog = new InformationDialog();
        	dialog.open(e.getMessage() + "\n" + e.getSQLState());
            System.out.println(e.getLocalizedMessage());
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
		InformationDialog dialog = new InformationDialog();
		dialog.open("Hittade inget med sträckkoden: " + barcode);
		return null;
	}
}
