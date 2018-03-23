package itg.makerspace.authentication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

import itg.makerspace.MainFrame;
import itg.makerspace.dialogs.ErrorDialog;
import itg.makerspace.dialogs.InformationDialog;

public class GetAllLoansThread extends Thread {
	
	MainFrame mainFrame;
	int user_id;
	String security_key;
	
	public GetAllLoansThread(MainFrame m, int id, String sec_key) {
		mainFrame = m;
		user_id = id;
		security_key = sec_key;
	}
	
	public void run() {
		try {
			String url = AuthenticationManager.IP_ADRESS + "/loans";
			System.out.println(url);
			String query = "user_id=" + URLEncoder.encode(String.valueOf(user_id),"UTF-8") + "&security_key=" + URLEncoder.encode(security_key,"UTF-8") + "&origin=2";
			
			URL myurl = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection)myurl.openConnection();
			con.setRequestMethod("GET");
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
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8")); 
				String answer = in.readLine();
				in.close();
				JSONObject obj = new JSONObject(answer);
				String status = obj.getString("status");
				if(status.equalsIgnoreCase("true")) {
					System.out.println(answer);
					mainFrame.getAllLoansSuccessfully(answer);
				} else {
					InformationDialog dialog = new InformationDialog();
					dialog.open(obj.getString("status_msg"));
					mainFrame.getAllLoansFails();
				}
			} else {
				ErrorDialog dialog = new ErrorDialog();
				dialog.open("Oops! Ett fel uppstod. Felkod: " + con.getResponseCode() + "\n" + "(" + con.getResponseMessage() + ")");
				mainFrame.getAllLoansFails();
			}
		} catch (ConnectException e) {
			System.out.println(e.getMessage());
			ErrorDialog dialog = new ErrorDialog();
			dialog.open("Servern kunde inte hittas!\n404: Not Found.");
			mainFrame.getAllLoansFails();
		} catch (SocketTimeoutException e) {
			System.out.println(e.getMessage());
			ErrorDialog dialog = new ErrorDialog();
			dialog.open("Servern kunde inte hittas!\n408: Timeout.");
			mainFrame.getAllLoansFails();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			ErrorDialog dialog = new ErrorDialog();
			dialog.open(e.getLocalizedMessage());
			mainFrame.getAllLoansFails();
			e.printStackTrace();
		}
    }
}
