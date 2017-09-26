package itg.makerspace.dialogs;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import itg.makerspace.frameelements.Button;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.Color;

public class InformationDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextPane txtpnMessage;

	public InformationDialog() {
		setBounds(0, 0, 450, 200);
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		setAutoRequestFocus(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{193};
		gbl_contentPanel.rowHeights = new int[]{19, 0, 56};
		gbl_contentPanel.columnWeights = new double[]{1.0};
		gbl_contentPanel.rowWeights = new double[]{0.0, 1.0, 0.0};
		contentPanel.setLayout(gbl_contentPanel);
		
		txtpnMessage = new JTextPane();
		txtpnMessage.setEditable(false);
		txtpnMessage.setFont(new Font("Open Sans", Font.PLAIN, 12));
		GridBagConstraints gbc_txtpnMessage = new GridBagConstraints();
		gbc_txtpnMessage.insets = new Insets(0, 0, 5, 0);
		gbc_txtpnMessage.fill = GridBagConstraints.BOTH;
		gbc_txtpnMessage.gridx = 0;
		gbc_txtpnMessage.gridy = 1;
		contentPanel.add(txtpnMessage, gbc_txtpnMessage);
		StyledDocument doc = txtpnMessage.getStyledDocument();
		SimpleAttributeSet center_text = new SimpleAttributeSet();
		StyleConstants.setAlignment(center_text, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center_text, false);
		JButton okButton = new Button("OK");
		GridBagConstraints center = new GridBagConstraints();
		center.gridx = 0;
		center.gridy = 2;
		center.anchor = GridBagConstraints.CENTER;
		contentPanel.add(okButton, center);
		okButton.setActionCommand("OK");
		okButton.requestFocusInWindow();
		okButton.requestFocus();
		getRootPane().setDefaultButton(okButton);
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	
	public void open(String msg) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		txtpnMessage.setText(msg);
	}

}
