package itg.makerspace;

import java.awt.DefaultKeyboardFocusManager;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

import itg.makerspace.dialogs.InformationDialog;

public class Scanner {
	
	private StringBuilder scannedCode = new StringBuilder();
	private boolean isRecording = false;
	private MainFrame main;
	private int codeLength = 4;
	
	public Scanner(MainFrame mainFrame) {
		main = mainFrame;
		DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(dispatcher);
	}
	
	private KeyEventDispatcher dispatcher = new KeyEventDispatcher() {
		
	    public boolean dispatchKeyEvent(KeyEvent event) {
	        if(event.getID() == KeyEvent.KEY_TYPED) {
	        	if(event.getKeyChar() == '>') {
	        		scannedCode = new StringBuilder();
	        		isRecording = true;
	        	} else if (event.getKeyChar() == '<') {
	        		isRecording = false;
	        		String code = scannedCode.toString();
	        		
	        		if(code.startsWith(">") && code.length() == (1 + codeLength)) {
	        			boolean isValid = true;
	        			char[] chars = code.toCharArray();
	        			String newCode = "";
	        			for (int i = 1; i < chars.length; i++) {
	        				if (chars[i] < '0' && chars[i] > '9') {
	        					isValid = false;
	        					break;
	        				} else {
	        					newCode += chars[i];
	        				}
	        			}
	        			
	        			if (isValid) {
	        				main.barcodeScannedEvent(newCode);
	        			} else {
	        				InformationDialog dialog = new InformationDialog();
	        				dialog.open("Can't find item for: " + newCode);
	        			}
	        		}
	        	}
	        	
	        	if(isRecording) {
	        		try {
	        			scannedCode.append("" + event.getKeyChar());
	        		} catch(Exception e) {
	        			InformationDialog dialog = new InformationDialog();
	        			dialog.open("Error with scanner:\n" + e.getMessage());
	        		}
	        	}
	        }
	        return false;
	    }
	};
}
