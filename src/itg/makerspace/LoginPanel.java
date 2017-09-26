package itg.makerspace;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import itg.makerspace.frameelements.Button;
import itg.makerspace.frameelements.TextField;

public class LoginPanel extends JPanel {
	
	private static final long serialVersionUID = 6513102805182196024L;
	public TextField emailField;
	public JPasswordField passwordField;
	public JButton btnLogin;
	public JLabel lblLoadingIcon;

	public LoginPanel(MainFrame frame) {
		setBackground(new Color(255,255,255));
		GridBagLayout gbl_loginPane = new GridBagLayout();
		gbl_loginPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
		gbl_loginPane.rowHeights = new int[]{30, 36, 36, 50, 47};
		gbl_loginPane.columnWidths = new int[]{100, 67, 100};
		gbl_loginPane.columnWeights = new double[]{0.0, 0.0, 0.0};
		setLayout(gbl_loginPane);
		
		JLabel lblLoggaIn = new JLabel("Logga In:");
		lblLoggaIn.setFont(new Font("Open Sans", Font.PLAIN, 18));
		GridBagConstraints gbc_lblLoggaIn = new GridBagConstraints();
		gbc_lblLoggaIn.gridwidth = 3;
		gbc_lblLoggaIn.insets = new Insets(0, 0, 5, 0);
		gbc_lblLoggaIn.gridx = 0;
		gbc_lblLoggaIn.gridy = 0;
		add(lblLoggaIn, gbc_lblLoggaIn);
		
		GridBagConstraints center = new GridBagConstraints();
		center.gridwidth = 3;
		center.insets = new Insets(0, 0, 5, 0);
		center.gridx = 0;
		center.gridy = 1;
		center.anchor = GridBagConstraints.CENTER;
		center.fill = GridBagConstraints.BOTH;
		emailField = new TextField("Email");
		emailField.setColumns(10);
		emailField.setPlaceHolder("Email");
		add(emailField, center);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.gridwidth = 3;
		gbc_passwordField.insets = new Insets(0, 0, 5, 0);
		gbc_passwordField.fill = GridBagConstraints.BOTH;
		gbc_passwordField.gridx = 0;
		gbc_passwordField.gridy = 2;
		passwordField.setText("password");
		passwordField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		passwordField.setBorder(BorderFactory.createCompoundBorder(passwordField.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
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
		add(passwordField, gbc_passwordField);
		
		btnLogin = new Button("Logga In");
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.insets = new Insets(0, 0, 5, 0);
		gbc_btnLogin.gridwidth = 3;
		gbc_btnLogin.gridx = 0;
		gbc_btnLogin.gridy = 3;
		add(btnLogin, gbc_btnLogin);
		
		ImageIcon loading = new ImageIcon(MainFrame.HOME_DIRECTORY + "/resources/loading.gif");
		lblLoadingIcon = new JLabel(loading);
		lblLoadingIcon.setVisible(false);
		GridBagConstraints gbc_lblLoading = new GridBagConstraints();
		gbc_lblLoading.insets = new Insets(0, 0, 0, 5);
		gbc_lblLoading.gridx = 1;
		gbc_lblLoading.gridy = 4;
		add(lblLoadingIcon, gbc_lblLoading);
		
		requestFocusInWindow();
		requestFocus();
	}
}