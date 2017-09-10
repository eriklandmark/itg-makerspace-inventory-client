package itg.makerspace.authentication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

public class AuthenticationManager {
	
	public String login(String email, String password) {
		try {
			String httpsURL = "https://itg-makerspace.herokuapp.com/auth";
			String query = "email=" + URLEncoder.encode(email,"UTF-8") + "&" + "password=" + URLEncoder.encode(password,"UTF-8");
	
			URL myurl = new URL(httpsURL);
			HttpsURLConnection con = (HttpsURLConnection)myurl.openConnection();
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
				if(status.equals("true")) {
					return answer;
				}
			}
			return null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public boolean sendNewLoan(int user_id, String auth_key, String items) {
		try {
			String httpsURL = "https://itg-makerspace.herokuapp.com/new-loan";
			String query = "user_id=" + URLEncoder.encode(String.valueOf(user_id), "UTF-8") + "&security_key=" + URLEncoder.encode(auth_key,"UTF-8") + "&items=" + URLEncoder.encode(items,"UTF-8");
	
			URL myurl = new URL(httpsURL);
			HttpsURLConnection con = (HttpsURLConnection)myurl.openConnection();
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
				return true;
			}
			return false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
}