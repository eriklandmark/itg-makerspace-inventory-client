package itg.makerspace.inventory;

public class InventoryItem {
	
	public int id = 0;
	public String name = "";
	public String description = "";
	public int quantity = 0;
	public String barcode = "";
	
	@Override
	public String toString() {
		return "ID: " + id + " name: " + name + " description: " + description + " barcode: " + barcode + " quantity: " + quantity;
	}
	
	public Object[] toArray() {
		return new Object[]{id,name,barcode,description,quantity};
	}
}
