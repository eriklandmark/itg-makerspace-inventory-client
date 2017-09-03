package itg.makerspace;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.Font;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 4237362507712699311L;
	private JPanel loginPane;
	private JTextField textField;
	private JPasswordField passwordField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 650, 400);
		setLocationRelativeTo(null);
		loginPane = new JPanel();
		loginPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(loginPane);
		loginPane.setBackground(new Color(255,255,255));
		GridBagLayout gbl_loginPane = new GridBagLayout();
		gbl_loginPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		gbl_loginPane.rowHeights = new int[]{30, 36, 36, 50};
		gbl_loginPane.columnWidths = new int[]{100, 29, 100};
		gbl_loginPane.columnWeights = new double[]{0.0, 0.0, 0.0};
		
		loginPane.setLayout(gbl_loginPane);
		
		JLabel lblLoggaIn = new JLabel("Logga In:");
		lblLoggaIn.setFont(new Font("Open Sans", Font.PLAIN, 18));
		GridBagConstraints gbc_lblLoggaIn = new GridBagConstraints();
		gbc_lblLoggaIn.gridwidth = 3;
		gbc_lblLoggaIn.insets = new Insets(0, 0, 5, 0);
		gbc_lblLoggaIn.gridx = 0;
		gbc_lblLoggaIn.gridy = 0;
		loginPane.add(lblLoggaIn, gbc_lblLoggaIn);
		
		
		GridBagConstraints center = new GridBagConstraints();
		center.gridwidth = 3;
		center.insets = new Insets(0, 0, 5, 0);
		center.gridx = 0;
		center.gridy = 1;
		center.anchor = GridBagConstraints.CENTER;
		center.fill = GridBagConstraints.BOTH;
		textField = new JTextField("Email");
		textField.setFont(new Font("Open Sans", Font.PLAIN, 14));
		textField.setColumns(10);
		textField.setForeground(Color.GRAY);
		textField.addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (textField.getText().equals("Email")) {
		        	textField.setText("");
		        	textField.setForeground(Color.BLACK);
		        }
		    }
		    @Override
		    public void focusLost(FocusEvent e) {
		        if (textField.getText().isEmpty()) {
		        	textField.setForeground(Color.GRAY);
		            textField.setText("Email");
		        }
		    }
		    });
		loginPane.add(textField, center);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.gridwidth = 3;
		gbc_passwordField.insets = new Insets(0, 0, 5, 0);
		gbc_passwordField.fill = GridBagConstraints.BOTH;
		gbc_passwordField.gridx = 0;
		gbc_passwordField.gridy = 2;
		passwordField.setText("password");
		
		passwordField.addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (String.valueOf(passwordField.getPassword()).equals("password")) {
		        	passwordField.setText("");
		        }
		    }
		    @Override
		    public void focusLost(FocusEvent e) {
		        if (String.valueOf(passwordField.getPassword()).isEmpty()) {
		        	passwordField.setText("password");
		        }
		    }
		    });
		loginPane.add(passwordField, gbc_passwordField);
		
		JButton btnCancel = new JButton("Avbryt");
		btnCancel.setBackground(new Color(229, 229, 229));
		btnCancel.setFont(new Font("Open Sans", Font.PLAIN, 14));
		btnCancel.setBorderPainted(false);
		btnCancel.setFocusPainted(false);
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.gridx = 0;
		gbc_btnCancel.gridy = 3;
		loginPane.add(btnCancel, gbc_btnCancel);
		
		JButton btnLogin = new JButton("Logga In");
		btnLogin.setBackground(new Color(229, 229, 229));
		btnLogin.setFont(new Font("Open Sans", Font.PLAIN, 14));
		btnLogin.setBorderPainted(false);
		btnLogin.setFocusPainted(false);
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLogin.gridx = 2;
		gbc_btnLogin.gridy = 3;
		loginPane.add(btnLogin, gbc_btnLogin);
	}

}
