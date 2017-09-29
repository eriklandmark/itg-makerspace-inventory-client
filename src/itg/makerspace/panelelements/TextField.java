package itg.makerspace.panelelements;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class TextField extends JTextField {
	
	private static final long serialVersionUID = 1L;
	public String placeholderText = null;
	
	public TextField() {
		super();
		setFont(new Font("Open Sans", Font.PLAIN, 14));
		setForeground(Color.BLACK);
		setOpaque(true);
		setBorder(BorderFactory.createLineBorder(Color.black, 1));
		setBorder(BorderFactory.createCompoundBorder(getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	}
	
	public TextField(String text) {
		super(text);
		setFont(new Font("Open Sans", Font.PLAIN, 14));
		setForeground(Color.GRAY);
		setOpaque(true);
		setBorder(BorderFactory.createLineBorder(Color.black, 1));
		setBorder(BorderFactory.createCompoundBorder(getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	}
	
	public void setPlaceHolder(String placeHolderText) {
		setForeground(Color.GRAY);
		setText(placeHolderText);
		placeholderText = placeHolderText;
		addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (getText().equals(placeHolderText)) {
		        	setText("");
		        	setForeground(Color.BLACK);
		        }
		    }
		    @Override
		    public void focusLost(FocusEvent e) {
		        if (getText().isEmpty()) {
		        	setForeground(Color.GRAY);
		            setText(placeHolderText);
		        }
		    }
		});
	}
	
	public boolean textIsPlaceHolder() {
		return getText().equals(placeholderText);
	}
}
