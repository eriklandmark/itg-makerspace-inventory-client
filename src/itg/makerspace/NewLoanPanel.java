package itg.makerspace;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import itg.makerspace.frameelements.Button;
import itg.makerspace.frameelements.TableButton;
import itg.makerspace.frameelements.TextField;

import javax.swing.JTextPane;

public class NewLoanPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public JTable table;
	private JScrollPane tablePane;
	public JLabel lblTitle;
	public JButton btnCancel;
	public JButton btnNewLoan;

	public NewLoanTable tableContent = new NewLoanTable();
	public TextField textBarcodeInput;
	public JButton btnAddBarcode;
	public JLabel lblLoadingIcon;
	private JLabel label;

	public NewLoanPanel(MainFrame frame) {
		setName("newLoanPanel");
		setBackground(new Color(255, 255, 255));

		lblTitle = new JLabel("Nytt L\u00E5n");
		lblTitle.setFont(new Font("Open Sans", Font.PLAIN, 22));

		btnNewLoan = new Button("Spara");
		
		table = new JTable(tableContent);
		table.getColumnModel().getColumn(1).setMaxWidth(120);
		table.setBackground(Color.WHITE);
		table.setOpaque(false);
		table.getTableHeader().setBackground(Color.WHITE);
		tablePane = new JScrollPane(table);
		table.setShowGrid(false);
		tablePane.setBackground(Color.WHITE);
		table.removeColumn(table.getColumnModel().getColumn(3));
		tablePane.getViewport().setBackground(Color.WHITE);
		tablePane.setViewportBorder(null);
		tablePane.setBorder(BorderFactory.createEmptyBorder());
		table.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
		table.getTableHeader().setBackground(new Color(229, 229, 229));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
		table.setFont(new Font("Open Sans", Font.PLAIN, 16));
		table.getTableHeader().setFont(new Font("Open Sans", Font.PLAIN, 18));
		table.setRowHeight(40);
		table.setRowMargin(10);
		table.getTableHeader().setPreferredSize(new Dimension(tablePane.getWidth(), 40));
		table.getTableHeader().setReorderingAllowed(false);
		
		for (int i = 0; i < table.getTableHeader().getColumnModel().getColumnCount(); i++) {
			table.getTableHeader().getColumnModel().getColumn(i).setHeaderRenderer(new TableCellRenderer() {
		        @Override
		        public Component getTableCellRendererComponent(JTable x, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		            JComponent component = (JComponent)table.getTableHeader().getDefaultRenderer().getTableCellRendererComponent(table, value, false, false, -1, -2);
		            component.setBackground(new Color(229, 229, 229));
		            component.setBorder(BorderFactory.createEmptyBorder());
		            return component;
		        }
		    });
		}
		
		TableButton buttonEditor = new TableButton("Ta Bort");
		
		table.getColumnModel().getColumn(2).setMaxWidth(100);
		table.getColumnModel().getColumn(2).setWidth(100);
		table.getColumnModel().getColumn(2).setMinWidth(100);
		table.getColumnModel().getColumn(2).setCellEditor(buttonEditor);
		table.getColumnModel().getColumn(2).setCellRenderer(buttonEditor);
		table.getColumnModel().getColumn(2).setModelIndex(2);
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				
				int row = table.rowAtPoint(e.getPoint());
				int col = table.columnAtPoint(e.getPoint());
				if (row >= 0 && col == 2) {
					tableContent.removeRow(row);
					updateTable();
				}
			}
		});
		
		btnCancel = new Button("Avbryt");

		JTextPane txtInfo = new JTextPane();
		txtInfo.setFont(new Font("Open Sans", Font.PLAIN, 14));
		txtInfo.setText("F\u00F6r att g\u00F6ra ett nytt l\u00E5n, skannar du en produkt med den svarta scannern och produktern kommer att dyka upp i listan nedan. Du kan \u00E4ven skriva in numret manuellt nedan.");
		txtInfo.setEditable(false);

		textBarcodeInput = new TextField();
		textBarcodeInput.setFont(new Font("Open Sans", Font.PLAIN, 14));
		textBarcodeInput.setPlaceHolder("Sträckkod..");

		textBarcodeInput.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		});
		

		btnAddBarcode = new Button("L\u00E4gg Till");
		
		ImageIcon loading = new ImageIcon(MainFrame.HOME_DIRECTORY + "/resources/loading.gif");
		lblLoadingIcon = new JLabel(loading);
		lblLoadingIcon.setVisible(false);
		
		label = new JLabel("");

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(txtInfo, GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)
							.addGap(260))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
							.addGap(579))))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(441)
					.addComponent(label)
					.addContainerGap(498, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(tablePane, GroupLayout.DEFAULT_SIZE, 915, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(textBarcodeInput, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnAddBarcode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 397, Short.MAX_VALUE)
					.addComponent(lblLoadingIcon, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnNewLoan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(16)
					.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(label)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblLoadingIcon, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblTitle)
									.addGap(19)
									.addComponent(txtInfo, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(textBarcodeInput, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnAddBarcode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnNewLoan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
							.addGap(10)))
					.addGap(17)
					.addComponent(tablePane, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
					.addContainerGap())
		);
		setLayout(groupLayout);

	}

	public void updateTable() {
		table.repaint();
		tablePane.repaint();
	}
}