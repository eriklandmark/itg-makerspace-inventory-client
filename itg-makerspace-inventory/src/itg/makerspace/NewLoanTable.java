package itg.makerspace;

import javax.swing.table.DefaultTableModel;

public class NewLoanTable extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private static String[] columnNames = {"Namn", "Antal", "ID"};
	
	public NewLoanTable() {
		super(columnNames, 0);
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		if (column == 1) {
			return true;
		} else {
			return false;
		}
	}
}
