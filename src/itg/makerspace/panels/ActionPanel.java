package itg.makerspace.panels;

import javax.swing.JPanel;

import itg.makerspace.panelelements.Button;

import java.awt.GridBagLayout;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.Font;

public class ActionPanel extends JPanel {

	private static final long serialVersionUID = -6312722040327490999L;
	
	public JButton btnLogOut;
	public JButton btnNewLoan;
	public JButton btnShowLoans;
	private JLabel greetingLabel;

	public ActionPanel() {
		setBackground(Color.WHITE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowHeights = new int[]{45, 45, 45, 24, 0};
		gridBagLayout.columnWidths = new int[]{130};
		gridBagLayout.columnWeights = new double[]{0.0};
		setLayout(gridBagLayout);
		
		greetingLabel = new JLabel();
		greetingLabel.setFont(new Font("Open Sans", Font.PLAIN, 18));
		GridBagConstraints gbc_greetingLabel = new GridBagConstraints();
		gbc_greetingLabel.insets = new Insets(0, 0, 5, 0);
		gbc_greetingLabel.gridx = 0;
		gbc_greetingLabel.gridy = 0;
		add(greetingLabel, gbc_greetingLabel);
		
		btnNewLoan = new Button("Nytt L\u00E5n");
		GridBagConstraints gbc_btnNewLoan = new GridBagConstraints();
		gbc_btnNewLoan.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewLoan.gridx = 0;
		gbc_btnNewLoan.gridy = 1;
		gbc_btnNewLoan.anchor = GridBagConstraints.CENTER;
		gbc_btnNewLoan.fill = GridBagConstraints.BOTH;
		add(btnNewLoan, gbc_btnNewLoan);
		
		btnShowLoans = new Button("Se dina l\u00E5n");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 2;
		add(btnShowLoans, gbc_btnNewButton);
		
		btnLogOut = new Button("Logga Ut");
		GridBagConstraints gbc_btnLogOut = new GridBagConstraints();
		gbc_btnLogOut.gridx = 0;
		gbc_btnLogOut.gridy = 4;
		add(btnLogOut, gbc_btnLogOut);

	}
	
	public void setGreetingText(String text) {
		greetingLabel.setText(text);
	}
}
