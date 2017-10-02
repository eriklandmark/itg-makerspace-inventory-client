package itg.makerspace.authentication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;

import itg.makerspace.MainFrame;
import itg.makerspace.dialogs.ErrorDialog;
import itg.makerspace.dialogs.InformationDialog;

public class ReturnEverythingThread extends Thread {
	
	int user_id;
	String security_key;
	MainFrame mainFrame;
	
	public ReturnEverythingThread(MainFrame main, String security_key, int u_id) {
		user_id = u_id;
		this.security_key = security_key;
		mainFrame = main;
	}
	
	public void run() {
		try {
			String httpsURL = "http://" + AuthenticationManager.IP_ADRESS + "/remove-all-loan-item";
			System.out.println(httpsURL);
			String query = "security_key=" + URLEncoder.encode(String.valueOf(security_key),"UTF-8") + "&" + "user_id=" + URLEncoder.encode(String.valueOf(user_id),"UTF-8");
	
			URL myurl = new URL(httpsURL);
			HttpURLConnection con = (HttpURLConnection)myurl.openConnection();
			con.setRequestMethod("POST");
			con.setConnectTimeout(5000);
			con.setRequestProperty("Content-length", String.valueOf(query.length())); 
			con.setRequestProperty("Content-Type","application/x-www-form-urlencoded"); 
			con.setDoOutput(true); 
			con.setDoInput(true); 
	
			DataOutputStream output = new DataOutputStream(con.getOutputStream());  
			output.writeBytes(query);
			output.close();
			System.out.println(con.getResponseCode());
			if(con.getResponseCode() == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream())); 
				String answer = in.readLine();
				in.close();
				if(answer.equalsIgnoreCase("true")) {
					mainFrame.myLoansPanel.emptyTable();
				} else {
					InformationDialog dialog = new InformationDialog();
					dialog.open("Error with request!");
				}
			} else {
				ErrorDialog dialog = new ErrorDialog();
				dialog.open("Oops! Ett fel uppstod. Felkod: " + con.getResponseCode() + "\n" + "(" + con.getResponseMessage() + ")");
			}
		} catch (ConnectException e) {
			System.out.println(e.getMessage());
			ErrorDialog dialog = new ErrorDialog();
			dialog.open("Servern kunde inte hittas!\n404: Not Found.");
		} catch (SocketTimeoutException e) {
			System.out.println(e.getMessage());
			ErrorDialog dialog = new ErrorDialog();
			dialog.open("Servern kunde inte hittas!\n408: Timeout.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			ErrorDialog dialog = new ErrorDialog();
			dialog.open("Fel uppstod:\n" + e.getLocalizedMessage());
		}
    }
}
