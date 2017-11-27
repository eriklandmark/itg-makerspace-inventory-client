package itg.makerspace.panelelements;

import javax.swing.table.DefaultTableModel;

public class MyLoansTable extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private static String[] columnNames = {"Datum & Tid", "Namn", "Antal", "Lämna tillbaks?", "ITEM_ID", "LOAN_ID"};
	
	public MyLoansTable() {
		super(columnNames, 0);
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}