package itg.makerspace;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JFrame;

import org.json.JSONArray;
import org.json.JSONObject;

import itg.makerspace.authentication.AuthenticationManager;
import itg.makerspace.authentication.GetAllLoansThread;
import itg.makerspace.authentication.ItemFromBarcodeThread;
import itg.makerspace.dialogs.InformationDialog;
import itg.makerspace.inventory.InventoryItem;
import itg.makerspace.log.Logger;
import itg.makerspace.panels.ActionPanel;
import itg.makerspace.panels.LoginPanel;
import itg.makerspace.panels.MyLoansPanel;
import itg.makerspace.panels.NewLoanPanel;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 4237362507712699311L;
	public MainFrame instance = this;
	public User currentUser;
	public LoginPanel loginPanel;
	public ActionPanel actionPanel;
	public NewLoanPanel newLoanPanel;
	public MyLoansPanel myLoansPanel;
	public Scanner scanner;
	public Logger logger;
	public AuthenticationManager authManager;
	public static String OS = System.getProperty("os.name");
	public static String HOME_DIRECTORY = "";
	
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
		if (MainFrame.OS.startsWith("Mac")) {
			HOME_DIRECTORY = "/Library/Application Support/itg-makerspace-inventory-client";
		} else if (MainFrame.OS.startsWith("Windows")) {
			HOME_DIRECTORY = System.getenv("APPDATA") + File.separator + "itg-makerspace-inventory-client";
		}
		authManager = new AuthenticationManager();
		scanner = new Scanner(this);
		logger = new Logger(this);
		loginPanel = new LoginPanel(this);
		actionPanel = new ActionPanel();
		newLoanPanel = new NewLoanPanel(this);
		myLoansPanel = new MyLoansPanel(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1200, 600);
		setLocationRelativeTo(null);
		setContentPane(loginPanel);
		setTitle("IT Gymnasiet Makerspace Inventering");
		
		loginPanel.btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!(loginPanel.emailField.getText() == "" || String.valueOf(loginPanel.passwordField.getPassword()) == "")) {
					loginPanel.lblLoadingIcon.setVisible(true);
					loginPanel.btnLogin.setEnabled(false);
					authManager.login(instance, loginPanel.emailField.getText(), String.valueOf(loginPanel.passwordField.getPassword()));
				}
			}
		});
		
		actionPanel.btnLogOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentUser = null;
				setContentPane(loginPanel);
				revalidate();
				validate();
				repaint();
			}
		});
		actionPanel.btnNewLoan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setContentPane(newLoanPanel);
				revalidate();
				validate();
				repaint();
			}
		});
		actionPanel.btnShowLoans.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionPanel.btnShowLoans.setEnabled(false);
				int rowCount = myLoansPanel.tableContent.getRowCount();
				for (int i = rowCount - 1; i >= 0; i--) {
					myLoansPanel.tableContent.removeRow(i);
				}
				GetAllLoansThread getAllLoansThread = new GetAllLoansThread(instance, currentUser.user_id, currentUser.security_key);
				getAllLoansThread.start();
			}
		});
		newLoanPanel.btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setContentPane(actionPanel);
				revalidate();
				validate();
				repaint();
			}
		});
		
		newLoanPanel.textBarcodeInput.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER) {
					if(getContentPane().getName() == "newLoanPanel" && newLoanPanel.textBarcodeInput.getText().length() > 0 && !newLoanPanel.textBarcodeInput.textIsPlaceHolder()) {
						ItemFromBarcodeThread barcodeRequest = new ItemFromBarcodeThread(instance, newLoanPanel.textBarcodeInput.getText());
						barcodeRequest.run();
						InventoryItem item = barcodeRequest.getItem();	
						if (item != null) {
							boolean hasItem = false;
							for(int i = 0; i < newLoanPanel.tableContent.getRowCount(); i++) {
								String itemName = (String) newLoanPanel.tableContent.getValueAt(i, 0);
								if(itemName.equals("  " + item.name)) {
									hasItem = true;
									newLoanPanel.tableContent.setValueAt(((int) newLoanPanel.tableContent.getValueAt(i, 1)) + 1, i, 1);
								}
							}
							
							if(!hasItem) {
								Object[] newRow = new Object[] {"  " + item.name, 1, "", item.id};
								newLoanPanel.tableContent.addRow(newRow);
							}
							
							newLoanPanel.updateTable();
							newLoanPanel.textBarcodeInput.setText("");
						}
					}
				}
			}
			public void keyReleased(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}
		});
		
		newLoanPanel.btnAddBarcode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(getContentPane().getName() == "newLoanPanel" && newLoanPanel.textBarcodeInput.getText().length() > 0 && !newLoanPanel.textBarcodeInput.textIsPlaceHolder()) {
					ItemFromBarcodeThread barcodeRequest = new ItemFromBarcodeThread(instance, newLoanPanel.textBarcodeInput.getText());
					barcodeRequest.run();
					InventoryItem item = barcodeRequest.getItem();	
					if (item != null) {
						boolean hasItem = false;
						for(int i = 0; i < newLoanPanel.tableContent.getRowCount(); i++) {
							String itemName = (String) newLoanPanel.tableContent.getValueAt(i, 0);
							if(itemName.equals("  " + item.name)) {
								hasItem = true;
								newLoanPanel.tableContent.setValueAt(((int) newLoanPanel.tableContent.getValueAt(i, 1)) + 1, i, 1);
							}
						}
						
						if(!hasItem) {
							Object[] newRow = new Object[] {"  " + item.name, 1, "", item.id};
							newLoanPanel.tableContent.addRow(newRow);
						}
						
						newLoanPanel.updateTable();
						newLoanPanel.textBarcodeInput.setText("");
					}
				}
			}
		});
		
		KeyListener loginOnEnter = new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					loginPanel.lblLoadingIcon.setVisible(true);
					authManager.login(instance, loginPanel.emailField.getText(), String.valueOf(loginPanel.passwordField.getPassword()));
				}
			}
			
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {}
		};
		
		loginPanel.emailField.addKeyListener(loginOnEnter);
		loginPanel.passwordField.addKeyListener(loginOnEnter);
		
		newLoanPanel.btnNewLoan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(newLoanPanel.tableContent.getRowCount() > 0) {
					JSONArray items = new JSONArray();
					newLoanPanel.lblLoadingIcon.setVisible(true);
					newLoanPanel.btnNewLoan.setEnabled(false);
					
					for(int i = 0; i < newLoanPanel.tableContent.getRowCount(); i++) {
						JSONObject item = new JSONObject();
						item.put("item", ((String)newLoanPanel.tableContent.getValueAt(i, 0)).substring(2));
						item.put("quantity", newLoanPanel.tableContent.getValueAt(i, 1));
						item.put("item_id", newLoanPanel.tableContent.getValueAt(i, 3));
						items.put(item);
					}
					
					System.out.println(items.toString());
					authManager.sendNewLoan(instance, currentUser.user_id, currentUser.security_key, items.toString());
				} else {
					InformationDialog dialog = new InformationDialog();
					dialog.open("Du måste först lägga till några saker..");
				}
			}
		});
		
		myLoansPanel.btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setContentPane(actionPanel);
				revalidate();
				validate();
				repaint();
			}
		});
	}
	
	public void loginSuccessfull(String response) {
		JSONObject obj = new JSONObject(response);
		System.out.println(response);
		currentUser = new User(obj.getString("email"), obj.getString("name"), obj.getInt("user_id"), obj.getString("security_key"));
		actionPanel.setGreetingText("Välkommen " + currentUser.fullName + "!");
		setContentPane(actionPanel);
		revalidate();
		validate();
		repaint();
		loginPanel.lblLoadingIcon.setVisible(false);
		loginPanel.btnLogin.setEnabled(true);
	}
	
	public void loginFailed() {
		loginPanel.lblLoadingIcon.setVisible(false);
		loginPanel.btnLogin.setEnabled(true);
	}
	
	public void newLoanSuccessfull() {
		InformationDialog dialog = new InformationDialog();
		dialog.open("Lån successfull!");
		setContentPane(actionPanel);
		revalidate();
		validate();
		repaint();
		int rowCount = newLoanPanel.tableContent.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
			newLoanPanel.tableContent.removeRow(i);
		}
		newLoanPanel.lblLoadingIcon.setVisible(false);
		newLoanPanel.btnNewLoan.setEnabled(true);
	}
	
	public void newLoanFail() {
		newLoanPanel.lblLoadingIcon.setVisible(false);
		newLoanPanel.btnNewLoan.setEnabled(true);
	}
	
	public void getAllLoansSuccessfully(String response) {
		JSONObject json = new JSONObject(response);
		JSONArray items = json.getJSONArray("items");
		int latestLoan = 0;
		
		for (int i = 0; i < items.length(); i++) {
			JSONObject loan = items.getJSONObject(i);
			int id = loan.getInt("item_id");
			int loan_id = loan.getInt("loan_id");
			System.out.println(loan.toString());
			Object[] obj = new Object[] {"", loan.getString("item_name"), loan.getInt("quantity"), "", id, loan_id};
			if (loan_id != latestLoan) {
				obj[0] = loan.getString("date_loaned").replace("_","  ");
				latestLoan = loan_id;
			}
			myLoansPanel.tableContent.addRow(obj);
		}
		
		myLoansPanel.updateTable();
		setContentPane(myLoansPanel);
		revalidate();
		validate();
		repaint();
		actionPanel.btnShowLoans.setEnabled(true);
	}
	
	public void getAllLoansFails() {
		actionPanel.btnShowLoans.setEnabled(true);
	}

	public void barcodeScannedEvent(String barcode) {
		requestFocus();
		requestFocusInWindow();
		if(getContentPane().getName() == "newLoanPanel") {
			ItemFromBarcodeThread barcodeRequest = new ItemFromBarcodeThread(instance, newLoanPanel.textBarcodeInput.getText());
			barcodeRequest.run();
			InventoryItem item = barcodeRequest.getItem();	
			if (item != null) {
				boolean hasItem = false;
				for(int i = 0; i < newLoanPanel.tableContent.getRowCount(); i++) {
					String itemName = (String) newLoanPanel.tableContent.getValueAt(i, 0);
					if(itemName.equals("  " + item.name)) {
						hasItem = true;
						newLoanPanel.tableContent.setValueAt(((int) newLoanPanel.tableContent.getValueAt(i, 1)) + 1, i, 1);
					}
				}
				
				if(!hasItem) {
					Object[] newRow = new Object[] {"  " + item.name, 1, "", item.id};
					newLoanPanel.tableContent.addRow(newRow);
				}
				
				if(newLoanPanel.textBarcodeInput.getText().contains(barcode)) {
					newLoanPanel.textBarcodeInput.setText(newLoanPanel.textBarcodeInput.getText().replaceAll(barcode, ""));
				}
				
				newLoanPanel.updateTable();
			}
		}
	}
}
