package itg.makerspace;

public class User {
	
	public String userEmail = "";
	public String fullName;
	public int user_id = 0;
	public String security_key = "";
	
	public User(String email, String name, int id, String key) {
		userEmail = email;
		fullName = name;
		user_id = id;
		security_key = key;
	}
}
