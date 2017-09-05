package itg.makerspace;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 4237362507712699311L;
	public LoginPanel loginPanel = new LoginPanel(this);
	public InventoryPanel inventoryPanel = new InventoryPanel(this);

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
		setContentPane(loginPanel);
	}

}
