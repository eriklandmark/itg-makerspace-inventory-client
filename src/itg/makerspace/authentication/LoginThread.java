package itg.makerspace.authentication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

import itg.makerspace.MainFrame;
import itg.makerspace.dialogs.ErrorDialog;
import itg.makerspace.dialogs.InformationDialog;

public class LoginThread extends Thread {
	
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
			String httpsURL = AuthenticationManager.IP_ADRESS + "/api/user-authentication";
			String query = "user_email=" + URLEncoder.encode(email,"UTF-8") + "&" + "user_password=" + URLEncoder.encode(password,"UTF-8");
	
			URL myurl = new URL(httpsURL);
			HttpsURLConnection con = (HttpsURLConnection)myurl.openConnection();
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
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8")); 
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
				ErrorDialog dialog = new ErrorDialog();
				dialog.open("Oops! Ett fel uppstod. Felkod: " + con.getResponseCode() + "\n" + "(" + con.getResponseMessage() + ")");
				mainFrame.loginFailed();
			}
		} catch (ConnectException e) {
			System.out.println(e.getMessage());
			ErrorDialog dialog = new ErrorDialog();
			dialog.open("Servern kunde inte hittas!\n404: Not Found.");
			mainFrame.loginFailed();
		} catch (SocketTimeoutException e) {
			System.out.println(e.getMessage());
			ErrorDialog dialog = new ErrorDialog();
			dialog.open("Servern kunde inte hittas!\n408: Timeout.");
			mainFrame.loginFailed();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			ErrorDialog dialog = new ErrorDialog();
			dialog.open("Fel uppstod:\n" + e.getLocalizedMessage());
			mainFrame.loginFailed();
		}
    }
}
