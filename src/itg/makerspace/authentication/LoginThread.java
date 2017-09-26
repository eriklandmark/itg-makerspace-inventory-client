package itg.makerspace.authentication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;

import itg.makerspace.MainFrame;
import itg.makerspace.dialogs.InformationDialog;

public class LoginThread extends Thread {
	
	String ip_adress = "192.168.1.216:9292";
	MainFrame mainFrame;
	String email = "";
	String password = "";
	
	public LoginThread(MainFrame main, String em, String pw) {
		mainFrame = main;
		email = em;
		password = pw;
	}

	public void run() {
		try {
			//String httpsURL = "https://itg-makerspace.herokuapp.com/auth";
			String httpsURL = "http://" + ip_adress + "/auth";
			String query = "email=" + URLEncoder.encode(email,"UTF-8") + "&" + "password=" + URLEncoder.encode(password,"UTF-8");
	
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
				JSONObject obj = new JSONObject(answer);
				String status = obj.getString("status");
				if(status.equalsIgnoreCase("true")) {
					mainFrame.loginSuccessfull(answer);
				} else {
					InformationDialog dialog = new InformationDialog();
					dialog.open(obj.getString("status_msg"));
					mainFrame.loginFailed();
				}
			} else {
				InformationDialog dialog = new InformationDialog();
				dialog.open("Oops! Ett fel uppstod. Felkod: " + con.getResponseCode() + "\n" + "(" + con.getResponseMessage() + ")");
				mainFrame.loginFailed();
			}
		} catch (ConnectException e) {
			System.out.println(e.getMessage());
			InformationDialog dialog = new InformationDialog();
			dialog.open("Servern kunde inte hittas!\n404: Not Found.");
			mainFrame.loginFailed();
		} catch (SocketTimeoutException e) {
			System.out.println(e.getMessage());
			InformationDialog dialog = new InformationDialog();
			dialog.open("Servern kunde inte hittas!\n408: Timeout.");
			mainFrame.loginFailed();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			InformationDialog dialog = new InformationDialog();
			dialog.open("Fel uppstod:\n" + e.getLocalizedMessage());
			mainFrame.loginFailed();
		}
    }
}
