package itg.makerspace.panelelements;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import itg.makerspace.MainFrame;
import itg.makerspace.authentication.ReturnItemThread;

public class Table extends JTable {

	private static final long serialVersionUID = 1L;
	private Table instance = this;
	private MainFrame mainFrame;
	
	public Table(MainFrame main, MyLoansTable tableContent, JScrollPane tablePane) {
		super(tableContent);
		mainFrame = main;
		
		getColumnModel().getColumn(0).setMaxWidth(180);
		getColumnModel().getColumn(0).setWidth(180);
		getColumnModel().getColumn(0).setMinWidth(180);
		getColumnModel().getColumn(2).setMaxWidth(120);
		setBackground(Color.WHITE);
		setOpaque(false);
		getTableHeader().setBackground(Color.WHITE);
		setShowGrid(false);
		removeColumn(getColumnModel().getColumn(4));
		removeColumn(getColumnModel().getColumn(4));
		
		getTableHeader().setBorder(BorderFactory.createEmptyBorder());
		getTableHeader().setBackground(new Color(229, 229, 229));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
		setFont(new Font("Open Sans", Font.PLAIN, 16));
		getTableHeader().setFont(new Font("Open Sans", Font.PLAIN, 18));
		setRowHeight(40);
		setRowMargin(10);
		getTableHeader().setPreferredSize(new Dimension(tablePane.getWidth(), 40));
		getTableHeader().setReorderingAllowed(false);
		setCellSelectionEnabled(false);
		setFocusable(false);
		
		TableButton buttonEditor = new TableButton("Lämna Tillbaka");
		
		getColumnModel().getColumn(3).setMaxWidth(150);
		getColumnModel().getColumn(3).setWidth(150);
		getColumnModel().getColumn(3).setMinWidth(150);
		getColumnModel().getColumn(3).setCellEditor(buttonEditor);
		getColumnModel().getColumn(3).setCellRenderer(buttonEditor);
		getColumnModel().getColumn(3).setModelIndex(2);
		
		for (int i = 0; i < getTableHeader().getColumnModel().getColumnCount(); i++) {
			getTableHeader().getColumnModel().getColumn(i).setHeaderRenderer(new TableCellRenderer() {
		        @Override
		        public Component getTableCellRendererComponent(JTable x, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		            JComponent component = (JComponent)instance.getTableHeader().getDefaultRenderer().getTableCellRendererComponent(instance, value, false, false, -1, -2);
		            component.setBackground(new Color(229, 229, 229));
		            component.setBorder(BorderFactory.createEmptyBorder());
		            return component;
		        }
		    });
		}
		
		addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				
				int row = instance.rowAtPoint(e.getPoint());
				int col = instance.columnAtPoint(e.getPoint());
				if (row >= 0 && col == 3) {
					ReturnItemThread returnItemThread = new ReturnItemThread(mainFrame, mainFrame.currentUser.security_key, mainFrame.currentUser.user_id, ((int)tableContent.getValueAt(row, 5)), ((int)tableContent.getValueAt(row, 4)), (int)mainFrame.myLoansPanel.spinner.getValue(), row);
					returnItemThread.start();
				}
			}
		});
	}

}
