package itg.makerspace.frameelements;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class Button extends JButton{

	private static final long serialVersionUID = 653980740406289059L;
	
	public Button(String text) {
		super(text);
		setBackground(new Color(229, 229, 229));
		setFont(new Font("Open Sans", Font.PLAIN, 14));
		setBorderPainted(false);
		setFocusPainted(false);
		setOpaque(true);
	}
	
	public void setFontSize(int size) {
		super.setFont(new Font("Open Sans", Font.PLAIN, size));
	}
}
