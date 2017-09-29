package itg.makerspace.panels;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class MyLoansPanel extends JPanel {
	private JTable table;

	/**
	 * Create the panel.
	 */
	public MyLoansPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{19, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblMinaLn = new JLabel("Mina l\u00E5n:");
		GridBagConstraints gbc_lblMinaLn = new GridBagConstraints();
		gbc_lblMinaLn.insets = new Insets(0, 0, 5, 5);
		gbc_lblMinaLn.gridx = 0;
		gbc_lblMinaLn.gridy = 1;
		add(lblMinaLn, gbc_lblMinaLn);
		
		JButton btnLmnaTillbakaAllt = new JButton("L\u00E4mna tillbaka allt");
		GridBagConstraints gbc_btnLmnaTillbakaAllt = new GridBagConstraints();
		gbc_btnLmnaTillbakaAllt.insets = new Insets(0, 0, 5, 5);
		gbc_btnLmnaTillbakaAllt.gridx = 2;
		gbc_btnLmnaTillbakaAllt.gridy = 1;
		add(btnLmnaTillbakaAllt, gbc_btnLmnaTillbakaAllt);
		
		JButton btnCancel = new JButton("Tillbaka");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 5, 0);
		gbc_btnCancel.gridx = 3;
		gbc_btnCancel.gridy = 1;
		add(btnCancel, gbc_btnCancel);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
	}

}
