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
import itg.makerspace.dialogs.InformationDialog;

public class NewLoanThread extends Thread {
	
	MainFrame mainFrame;
	String ip_adress = "192.168.1.216:9292";
	int user_id = 0;
	String auth_key = "";
	String items = "";
	
	public NewLoanThread(MainFrame main, int u_id, String au_key, String things) {
		mainFrame = main;
		user_id = u_id;
		auth_key = au_key;
		items = things;
	}
	
	public void run() {
		try {
			//String httpsURL = "https://itg-makerspace.herokuapp.com/new-loan";
			String httpsURL = "http://" + ip_adress + "/new-loan";
			String query = "user_id=" + URLEncoder.encode(String.valueOf(user_id), "UTF-8") + "&security_key=" + URLEncoder.encode(auth_key,"UTF-8") + "&items=" + URLEncoder.encode(items,"UTF-8");
	
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
			
			if(con.getResponseCode() == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream())); 
				String answer = in.readLine();
				in.close();
				System.out.println(answer);
				if (answer.equalsIgnoreCase("true")) {
					mainFrame.newLoanSuccessfull();
				} else {
					InformationDialog dialog = new InformationDialog();
					dialog.open("Oops! Ett fel uppstod.\n" + "(" + con.getResponseMessage() + ")");
					mainFrame.newLoanFail();
				}
			} else {
				InformationDialog dialog = new InformationDialog();
				dialog.open("Oops! Ett fel uppstod. Felkod: " + con.getResponseCode() + "\n" + "(" + con.getResponseMessage() + ")");
				mainFrame.newLoanFail();
			}
		} catch (ConnectException e) {
			System.out.println(e.getMessage());
			InformationDialog dialog = new InformationDialog();
			dialog.open("Servern kunde inte hittas!\n404: Not Found.");
			mainFrame.newLoanFail();
		} catch (SocketTimeoutException e) {
			System.out.println(e.getMessage());
			InformationDialog dialog = new InformationDialog();
			dialog.open("Servern kunde inte hittas!\n408: Timeout.");
			mainFrame.newLoanFail();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			InformationDialog dialog = new InformationDialog();
			dialog.open("Fel uppstod:\n" + e.getLocalizedMessage());
			mainFrame.newLoanFail();
		}
	}
}
