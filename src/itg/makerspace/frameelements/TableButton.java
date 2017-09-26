package itg.makerspace.frameelements;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class TableButton extends Button implements TableCellRenderer, TableCellEditor {
	
	private static final long serialVersionUID = 1L;

	  public TableButton(String text) {
	    super(text); 
	    
	  }

	  @Override 
	  public Component getTableCellRendererComponent(JTable table,
	    Object value, boolean isSelected, boolean hasFocus, int row, int col) {
	    return this;
	  }

	  @Override
	  public Component getTableCellEditorComponent(JTable table,
	      Object value, boolean isSelected, int row, int col) {
	    return this;
	  } 

	  @Override
	  public void addCellEditorListener(CellEditorListener arg0) {      
	  } 

	  @Override
	  public void cancelCellEditing() {
	  } 

	  @Override
	  public Object getCellEditorValue() {
	    return "";
	  }

	  @Override
	  public boolean isCellEditable(EventObject arg0) {
	    return true;
	  }

	  @Override
	  public void removeCellEditorListener(CellEditorListener arg0) {
	  }

	  @Override
	  public boolean shouldSelectCell(EventObject arg0) {
	    return true;
	  }

	  @Override
	  public boolean stopCellEditing() {
	    return true;
	  }
	}