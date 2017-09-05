package itg.makerspace;

import java.awt.Color;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;

public class InventoryPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;

	public InventoryPanel(MainFrame mainFrame) {
		setBackground(new Color(255,255,255));
		setLayout(new MigLayout("", "[grow]", "[][][][][][91.00][grow]"));
		
		JLabel lblNewLabel = new JLabel("New label");
		add(lblNewLabel, "cell 0 0");
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 6,grow");
		
		table = new JTable();
		scrollPane.setColumnHeaderView(table);
	}

}
