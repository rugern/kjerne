package datamodell;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnectionExample {
	
	private static String url = "jdbc:mysql://mysql.stud.ntnu.no:3306/nilsiru_felles";
	private static String user = "nilsiru_gruppe11@cura.itea.ntnu.no";
	private static String pw = "felles";
	private static String sql = "SELECT * FROM Ansatt";
	

	public static void main(String[] args) {
		Connection connection = null;
		Statement statement = null;
		
		//Connect to driver and database
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, pw);
			statement = connection.createStatement();
			System.out.println(statement.execute(sql));
		}catch (ClassNotFoundException event) {
			event.printStackTrace();
			System.out.println("Driver not found");
		}catch (SQLException event) {
			event.printStackTrace();
			System.out.println("Could not connect");
		//Close all connections
		}finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException event) {
					event.printStackTrace();
					System.out.println("Connection failed to close");
				}
			}if(statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("Statement failed to close");
				}
			}
		}
	}

}
