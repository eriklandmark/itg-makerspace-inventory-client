package sqlitetojava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class postgres {
	
	

	public static void main(String[] args) {
		
		String url = "jdbc:postgresql://ec2-54-83-194-208.compute-1.amazonaws.com:5432/d8gr9qh51h9nku";
		String user = "fnkusucuathyqa";
		String password = "8cd7376f69b94fec5ebf676238f0a1f27962f2a8e88cc67672f7b3fd18dfc0dc";
		
		Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
	}

}
