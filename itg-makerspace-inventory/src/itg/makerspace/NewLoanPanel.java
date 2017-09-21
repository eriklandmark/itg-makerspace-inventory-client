package itg.makerspace;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import itg.makerspace.frameelements.Button;

import javax.swing.JTextPane;

public class NewLoanPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private JScrollPane tablePane;
	public JLabel lblTitle;
	public JButton btnCancel;
	public JButton btnNewLoan;

	public NewLoanTable tableContent = new NewLoanTable();
	public JTextField textBarcodeInput;
	public JButton btnAddBarcode;

	public NewLoanPanel(MainFrame frame) {
		setName("newLoanPanel");
		setBackground(new Color(255, 255, 255));

		lblTitle = new JLabel("Nytt L\u00E5n");

		btnNewLoan = new Button("Spara");

		table = new JTable(tableContent);
		table.getColumnModel().getColumn(1).setMaxWidth(120);
		tablePane = new JScrollPane(table);
		table.removeColumn(table.getColumnModel().getColumn(2));

		btnCancel = new Button("Avbryt");

		JTextPane txtInfo = new JTextPane();
		txtInfo.setText(
				"F\u00F6r att g\u00F6ra ett nytt l\u00E5n, skannar du en produkt med den svarta scannern och produktern kommer att dyka upp i listan n\u00E4r du \u00E4r klar. Du kan \u00E4ven skriva in numret manuellt nedan.");
		txtInfo.setEditable(false);

		textBarcodeInput = new JTextField(13);

		textBarcodeInput.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		});

		btnAddBarcode = new Button("L\u00E4gg Till");

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout
				.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addGap(313).addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addGap(296))
						.addGroup(groupLayout.createSequentialGroup().addContainerGap().addGroup(groupLayout
								.createParallelGroup(Alignment.TRAILING)
								.addComponent(tablePane, GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(textBarcodeInput, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnAddBarcode, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGap(183)
										.addComponent(btnNewLoan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGap(18).addComponent(btnCancel, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(txtInfo, GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)).addGap(10)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(7).addComponent(lblTitle).addGap(23)
				.addComponent(txtInfo, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE).addGap(18)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(textBarcodeInput, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAddBarcode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewLoan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(tablePane, GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE).addContainerGap()));
		setLayout(groupLayout);

	}

	public void updateTable() {
		table.repaint();
		tablePane.repaint();
	}
}