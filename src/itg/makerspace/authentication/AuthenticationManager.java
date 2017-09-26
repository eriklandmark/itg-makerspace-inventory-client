package itg.makerspace.authentication;

import itg.makerspace.MainFrame;

public class AuthenticationManager {
	
	public void login(MainFrame main, String email, String password) {
		LoginThread loginThread = new LoginThread(main, email, password);
		loginThread.start();
	}
	
	public void sendNewLoan(MainFrame main, int user_id, String auth_key, String items) {
		NewLoanThread loginThread = new NewLoanThread(main, user_id, auth_key, items);
		loginThread.start();
	}
}