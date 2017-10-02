package itg.makerspace.panels;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.NumberFormatter;

import itg.makerspace.MainFrame;
import itg.makerspace.authentication.ReturnEverythingThread;
import itg.makerspace.dialogs.DefaultQuestionDialog;
import itg.makerspace.dialogs.QuestionAction;
import itg.makerspace.panelelements.Button;
import itg.makerspace.panelelements.MyLoansTable;
import itg.makerspace.panelelements.Table;

import java.awt.Font;
import javax.swing.JSpinner;

public class MyLoansPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	public JButton btnDeleteAll;
	public JButton btnCancel;
	public MyLoansTable tableContent = new MyLoansTable();
	public JScrollPane tablePane;
	private MainFrame mainFrame;
	public JSpinner spinner;
	private JLabel lblSpinner;

	
	public MyLoansPanel(MainFrame main) {
		mainFrame = main;
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{19, 0, 124, 62, 20, 161, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{40, 37, 30, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblTitle = new JLabel("Mina l\u00E5n:");
		lblTitle.setFont(new Font("Open Sans", Font.PLAIN, 22));
		GridBagConstraints gbc_lblMinaLn = new GridBagConstraints();
		gbc_lblMinaLn.insets = new Insets(0, 0, 5, 5);
		gbc_lblMinaLn.gridx = 0;
		gbc_lblMinaLn.gridy = 1;
		add(lblTitle, gbc_lblMinaLn);
		
		lblSpinner = new JLabel("Antal att l\u00E4mna tillbaka:");
		lblSpinner.setFont(new Font("Open Sans", Font.PLAIN, 12));
		GridBagConstraints gbc_lblAntalAttLmna = new GridBagConstraints();
		gbc_lblAntalAttLmna.insets = new Insets(0, 0, 5, 5);
		gbc_lblAntalAttLmna.gridx = 2;
		gbc_lblAntalAttLmna.gridy = 1;
		add(lblSpinner, gbc_lblAntalAttLmna);

		SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
		spinner = new JSpinner(spinnerModel);
		spinner.setEditor(new JSpinner.NumberEditor(spinner,"###"));
		spinner.setFont(new Font("Open Sans", Font.PLAIN, 14));
		spinner.setBorder(BorderFactory.createEmptyBorder());
		JFormattedTextField txt = ((JSpinner.NumberEditor) spinner.getEditor()).getTextField();
		NumberFormatter formatter = ((NumberFormatter) txt.getFormatter());
		formatter.setAllowsInvalid(false);
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.BOTH;
		gbc_spinner.insets = new Insets(0, 0, 5, 5);
		gbc_spinner.gridx = 3;
		gbc_spinner.gridy = 1;
		add(spinner, gbc_spinner);
		
		btnDeleteAll = new Button("L\u00E4mna tillbaka allt");
		GridBagConstraints gbc_btnLmnaTillbakaAllt = new GridBagConstraints();
		gbc_btnLmnaTillbakaAllt.fill = GridBagConstraints.VERTICAL;
		gbc_btnLmnaTillbakaAllt.insets = new Insets(0, 0, 5, 5);
		gbc_btnLmnaTillbakaAllt.gridx = 5;
		gbc_btnLmnaTillbakaAllt.gridy = 1;
		btnDeleteAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultQuestionDialog dialog = new DefaultQuestionDialog();
				dialog.setText("Är du säker att du vill lämna tillbaka allt?");
				dialog.setAction(new QuestionAction() {
					@Override
					public void onAnswerEvent(ActionType actionType) {
						if (actionType.equals(ActionType.YES)) {
							ReturnEverythingThread thread = new ReturnEverythingThread(mainFrame, mainFrame.currentUser.security_key, mainFrame.currentUser.user_id);
							thread.start();
						}
					}
				});
				dialog.open();
			}
		});
		
		add(btnDeleteAll, gbc_btnLmnaTillbakaAllt);
		
		btnCancel = new Button("Tillbaka");
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.fill = GridBagConstraints.VERTICAL;
		gbc_btnCancel.insets = new Insets(0, 0, 5, 0);
		gbc_btnCancel.gridx = 7;
		gbc_btnCancel.gridy = 1;
		add(btnCancel, gbc_btnCancel);
		
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 8;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;

		tablePane = new JScrollPane();
		table = new Table(main, tableContent, tablePane);
		tablePane.setViewportView(table);
		tablePane.getViewport().setBackground(Color.WHITE);
		tablePane.setViewportBorder(null);
		tablePane.setBorder(BorderFactory.createEmptyBorder());
		tablePane.setBackground(Color.WHITE);
		add(tablePane, gbc_scrollPane);
	}
	
	public void updateTable() {
		table.repaint();
		tablePane.repaint();
	}

	public void emptyTable() {
		for (int i = tableContent.getRowCount() - 1; i >= 0; i--) {
			tableContent.removeRow(i);
		}
		updateTable();
	}
}
