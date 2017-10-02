package itg.makerspace.dialogs;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import itg.makerspace.dialogs.QuestionAction.ActionType;
import itg.makerspace.panelelements.Button;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.Color;

public class DefaultQuestionDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextPane txtpnMessage;
	private JButton btnYes;
	private JButton btnNo;
	private QuestionAction action = null;

	public DefaultQuestionDialog() {
		setBounds(0, 0, 450, 200);
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		setAutoRequestFocus(true);
		contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{200, 60, 20, 60, 200};
		gbl_contentPanel.rowHeights = new int[]{111, 36};
		gbl_contentPanel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0};
		gbl_contentPanel.rowWeights = new double[]{1.0, 0.0};
		contentPanel.setLayout(gbl_contentPanel);
		txtpnMessage = new JTextPane();
		txtpnMessage.setEditable(false);
		txtpnMessage.setFont(new Font("Open Sans", Font.PLAIN, 12));
		GridBagConstraints gbc_txtpnMessage = new GridBagConstraints();
		gbc_txtpnMessage.gridwidth = 5;
		gbc_txtpnMessage.insets = new Insets(0, 0, 5, 0);
		gbc_txtpnMessage.fill = GridBagConstraints.BOTH;
		gbc_txtpnMessage.gridx = 0;
		gbc_txtpnMessage.gridy = 0;
		contentPanel.add(txtpnMessage, gbc_txtpnMessage);
		StyledDocument doc = txtpnMessage.getStyledDocument();
		SimpleAttributeSet center_text = new SimpleAttributeSet();
		StyleConstants.setAlignment(center_text, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center_text, false);
		
		btnNo = new Button("NEJ");
		GridBagConstraints gbc_btnNej = new GridBagConstraints();
		gbc_btnNej.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNej.insets = new Insets(0, 0, 0, 5);
		gbc_btnNej.gridx = 1;
		gbc_btnNej.gridy = 1;
		btnNo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (action != null) {
					action.onAnswerEvent(ActionType.NO);
				}
				dispose();
			}
		});
		contentPanel.add(btnNo, gbc_btnNej);
		btnYes = new Button("JA");
		GridBagConstraints center = new GridBagConstraints();
		center.fill = GridBagConstraints.HORIZONTAL;
		center.insets = new Insets(0, 0, 0, 5);
		center.gridx = 3;
		center.gridy = 1;
		center.anchor = GridBagConstraints.CENTER;
		btnYes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (action != null) {
					action.onAnswerEvent(ActionType.YES);
				}
				dispose();
			}
		});
		contentPanel.add(btnYes, center);
	}
	
	public void setAction(QuestionAction action) {
		this.action = action;
	}
	
	public void open() {
		setVisible(true);
	}
	
	public void open(String msg) {
		open();
		setText(msg);
	}
	
	public void setText(String text) {
		txtpnMessage.setText(txtpnMessage.getText() + text);
	}
}