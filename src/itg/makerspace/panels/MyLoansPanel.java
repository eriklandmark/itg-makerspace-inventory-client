package itg.makerspace.panels;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import itg.makerspace.MainFrame;
import itg.makerspace.authentication.ReturnItemThread;
import itg.makerspace.panelelements.Button;
import itg.makerspace.panelelements.MyLoansTable;
import itg.makerspace.panelelements.TableButton;

import java.awt.Font;

public class MyLoansPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	public JButton btnDeleteAll;
	public JButton btnCancel;
	public MyLoansTable tableContent = new MyLoansTable();
	public JScrollPane tablePane;
	private MainFrame mainFrame;

	
	public MyLoansPanel(MainFrame main) {
		mainFrame = main;
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{19, 0, 176, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{40, 0, 30, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblTitle = new JLabel("Mina l\u00E5n:");
		lblTitle.setFont(new Font("Open Sans", Font.PLAIN, 22));
		GridBagConstraints gbc_lblMinaLn = new GridBagConstraints();
		gbc_lblMinaLn.insets = new Insets(0, 0, 5, 5);
		gbc_lblMinaLn.gridx = 0;
		gbc_lblMinaLn.gridy = 1;
		add(lblTitle, gbc_lblMinaLn);
		
		btnDeleteAll = new Button("L\u00E4mna tillbaka allt");
		GridBagConstraints gbc_btnLmnaTillbakaAllt = new GridBagConstraints();
		gbc_btnLmnaTillbakaAllt.insets = new Insets(0, 0, 5, 5);
		gbc_btnLmnaTillbakaAllt.gridx = 2;
		gbc_btnLmnaTillbakaAllt.gridy = 1;
		add(btnDeleteAll, gbc_btnLmnaTillbakaAllt);
		
		btnCancel = new Button("Tillbaka");
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 5, 0);
		gbc_btnCancel.gridx = 4;
		gbc_btnCancel.gridy = 1;
		add(btnCancel, gbc_btnCancel);
		
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 5;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		
		table = new JTable(tableContent);
		tablePane = new JScrollPane(table);
		tablePane.getViewport().setBackground(Color.WHITE);
		tablePane.setViewportBorder(null);
		tablePane.setBorder(BorderFactory.createEmptyBorder());
		tablePane.setBackground(Color.WHITE);
		
		table.getColumnModel().getColumn(1).setMaxWidth(120);
		table.setBackground(Color.WHITE);
		table.setOpaque(false);
		table.getTableHeader().setBackground(Color.WHITE);
		table.setShowGrid(false);
		table.removeColumn(table.getColumnModel().getColumn(3));
		table.removeColumn(table.getColumnModel().getColumn(3));
		
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
		table.setCellSelectionEnabled(false);
		table.setFocusable(false);
		
		TableButton buttonEditor = new TableButton("Lämna Tillbaka");
		
		table.getColumnModel().getColumn(2).setMaxWidth(130);
		table.getColumnModel().getColumn(2).setWidth(130);
		table.getColumnModel().getColumn(2).setMinWidth(130);
		table.getColumnModel().getColumn(2).setCellEditor(buttonEditor);
		table.getColumnModel().getColumn(2).setCellRenderer(buttonEditor);
		table.getColumnModel().getColumn(2).setModelIndex(2);
		
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
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				
				int row = table.rowAtPoint(e.getPoint());
				int col = table.columnAtPoint(e.getPoint());
				if (row >= 0 && col == 2) {
					ReturnItemThread returnItemThread = new ReturnItemThread(mainFrame, ((int)tableContent.getValueAt(row, 4)), ((int)tableContent.getValueAt(row, 3)), 1, row);
					returnItemThread.start();
				}
			}
		});
		
		add(tablePane, gbc_scrollPane);
	}
	
	public void updateTable() {
		table.repaint();
		tablePane.repaint();
	}
}
