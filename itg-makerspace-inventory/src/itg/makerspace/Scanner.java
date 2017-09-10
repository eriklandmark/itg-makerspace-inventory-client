package itg.makerspace;

import java.awt.DefaultKeyboardFocusManager;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

public class Scanner {
	
	private StringBuilder scannedCode = new StringBuilder();
	private boolean isRecording = false;
	private MainFrame main;
	
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
	        		if(scannedCode.length() == 12) {
	        			main.barcodeScannedEvent(scannedCode.toString());
	        		}
	        	}
	        	
	        	if(isRecording) {
	        		try {
	        			int scannedChar = Integer.parseInt("" + event.getKeyChar());
	        			scannedCode.append(scannedChar);
	        		} catch(Exception e) {}
	        	}
	        }
	        return false;
	    }
	};
}
