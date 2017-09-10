package itg.makerspace;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import org.json.JSONArray;
import org.json.JSONObject;

import itg.makerspace.authentication.AuthenticationManager;
import itg.makerspace.inventory.Inventory;
import itg.makerspace.inventory.InventoryItem;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 4237362507712699311L;
	public User currentUser;
	public LoginPanel loginPanel = new LoginPanel(this);
	public ActionPanel actionPanel = new ActionPanel();
	public NewLoanPanel newLoanPanel = new NewLoanPanel(this);
	public Scanner scanner;
	public Inventory inventory = new Inventory();
	public AuthenticationManager authManager = new AuthenticationManager();
	
	String[] columnNames = {"First Name",
            "Last Name",
            "Sport",
            "# of Years",
            "Vegetarian"};

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
		setBounds(0, 0, 700, 400);
		setLocationRelativeTo(null);
		setContentPane(loginPanel);
		setTitle("IT Gymnasiet Makerspace Inventering");
		scanner = new Scanner(this);
		inventory.loadInventoryFromDB();
		
		loginPanel.btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(loadUser(loginPanel.emailField.getText(), String.valueOf(loginPanel.passwordField.getPassword()))) {
					setContentPane(actionPanel);
					revalidate();
					validate();
					repaint();
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
		newLoanPanel.btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setContentPane(actionPanel);
				revalidate();
				validate();
				repaint();
			}
		});
		
		newLoanPanel.btnAddBarcode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(getContentPane().getName() == "newLoanPanel") {
					InventoryItem item = inventory.getItemFromBarcode(newLoanPanel.textBarcodeInput.getText());
					boolean hasItem = false;
					
					for(int i = 0; i < newLoanPanel.tableContent.getRowCount(); i++) {
						String itemName = (String) newLoanPanel.tableContent.getValueAt(i, 0);
						if(itemName.equals(item.name)) {
							hasItem = true;
							newLoanPanel.tableContent.setValueAt(((int) newLoanPanel.tableContent.getValueAt(i, 1)) + 1, i, 1);
						}
					}
					
					if(!hasItem) {
						Object[] newRow = new Object[] {item.name, 1};
						newLoanPanel.tableContent.addRow(newRow);
					}
					newLoanPanel.updateTable();
					newLoanPanel.textBarcodeInput.setText("");
				}
			}
		});
		
		KeyListener loginOnEnter = new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER && loadUser(loginPanel.emailField.getText(), String.valueOf(loginPanel.passwordField.getPassword()))) {
					setContentPane(actionPanel);
					revalidate();
					validate();
					repaint();
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
				JSONArray items = new JSONArray();
				
				for(int i = 0; i < newLoanPanel.tableContent.getRowCount(); i++) {
					JSONObject item = new JSONObject();
					item.put("item", newLoanPanel.tableContent.getValueAt(i, 0));
					item.put("quantity", newLoanPanel.tableContent.getValueAt(i, 1));
					
					items.put(item);
				}
				
				System.out.println(items.toString());
				if(authManager.sendNewLoan(currentUser.user_id, currentUser.security_key, items.toString())) {
					System.out.println("Works");
				}
			}
		});
	}
	
	public boolean loadUser(String email, String password) {
		String response = authManager.login("erik.landmark@live.se", "Fredo112");
		if(response != null) {
			JSONObject obj = new JSONObject(response);
			System.out.println(response);
			currentUser = new User(email, obj.getString("name"), obj.getInt("user_id"), obj.getString("security_key"));
			return true;
		}
		return false;
	}

	public void barcodeScannedEvent(String barcode) {
		requestFocus();
		requestFocusInWindow();
		if(getContentPane().getName() == "newLoanPanel") {
			InventoryItem item = inventory.getItemFromBarcode(barcode);
			boolean hasItem = false;
			
			for(int i = 0; i < newLoanPanel.tableContent.getRowCount(); i++) {
				String itemName = (String) newLoanPanel.tableContent.getValueAt(i, 0);
				if(itemName.equals(item.name)) {
					hasItem = true;
					newLoanPanel.tableContent.setValueAt(((int) newLoanPanel.tableContent.getValueAt(i, 1)) + 1, i, 1);
				}
			}
			
			if(!hasItem) {
				Object[] newRow = new Object[] {item.name, 1};
				newLoanPanel.tableContent.addRow(newRow);
			}
			newLoanPanel.updateTable();
		}
	}
}
