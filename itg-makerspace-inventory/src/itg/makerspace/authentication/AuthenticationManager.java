package itg.makerspace.authentication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

import itg.makerspace.dialogs.InformationDialog;

public class AuthenticationManager {
	
	public String login(String email, String password) {
		try {
			
			//String httpsURL = "https://itg-makerspace.herokuapp.com/auth";
			String httpsURL = "http://192.168.1.216:9292/auth";
			String query = "email=" + URLEncoder.encode(email,"UTF-8") + "&" + "password=" + URLEncoder.encode(password,"UTF-8");
	
			URL myurl = new URL(httpsURL);
			HttpURLConnection con = (HttpURLConnection)myurl.openConnection();
			con.setRequestMethod("POST");
	
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
					return answer;
				} else {
					InformationDialog dialog = new InformationDialog();
					dialog.open(obj.getString("status_msg"));
				}
			} else {
				InformationDialog dialog = new InformationDialog();
				dialog.open("Oops! Ett fel uppstod. Felkod: " + con.getResponseCode() + "\n" + "(" + con.getResponseMessage() + ")");
			}
			return null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			InformationDialog dialog = new InformationDialog();
			dialog.open(e.getLocalizedMessage());
			return null;
		}
	}
	
	public boolean sendNewLoan(int user_id, String auth_key, String items) {
		try {
			//String httpsURL = "https://itg-makerspace.herokuapp.com/new-loan";
			String httpsURL = "http://192.168.1.216:9292/new-loan";
			String query = "user_id=" + URLEncoder.encode(String.valueOf(user_id), "UTF-8") + "&security_key=" + URLEncoder.encode(auth_key,"UTF-8") + "&items=" + URLEncoder.encode(items,"UTF-8");
	
			URL myurl = new URL(httpsURL);
			HttpURLConnection con = (HttpURLConnection)myurl.openConnection();
			con.setRequestMethod("POST");
	
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
					return true;
				} else {
					return false;	
				}
			} else {
				InformationDialog dialog = new InformationDialog();
				dialog.open("Oops! Ett fel uppstod. Felkod: " + con.getResponseCode() + "\n" + "(" + con.getResponseMessage() + ")");
			}
			return false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			InformationDialog dialog = new InformationDialog();
			dialog.open(e.getLocalizedMessage());
			return false;
		}
	}
}