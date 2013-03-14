package database;

import java.lang.ClassNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;

import com.mysql.jdbc.PreparedStatement;

public class Conn {

	Connection connection;
	Statement statement;
	
	public Conn(){

		try {
			
			Class.forName("com.mysql.jdbc.Driver");

			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/fellesprosjekt", "root", "");
			
		} catch (ClassNotFoundException e) {
			System.out.println("Error " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("Error " + e.getMessage());
		}

		// connection = DriverManager.getConnection(
		// "jdbc:mysql://mysql.stud.ntnu.no:3306/nilsiru_felles",
		// "nilsiru_gruppe11@cura.itea.ntnu.no", "felles");

	}

	public static void main(String args[]) throws SQLException {

		Query query = new Query();

//		query.addEvent("test", "testing@gmail.com", null, null,
//				null, "hydro", null, (Integer) null);
		
		System.out.println("entered");

//		query.getEventByDate("@gmail.com", "2013mar01");

	}

	public static void addEmployee(Connection connection,
			PreparedStatement preperadStatement, String email, String name,
			String username, String password) throws SQLException {

		preperadStatement = (PreparedStatement) connection
				.prepareStatement("INSERT INTO Employee(Email, Name, username, password ) VALUES (?,?,?,?)");

		preperadStatement.setString(1, email);
		preperadStatement.setString(2, name);
		preperadStatement.setString(3, username);
		preperadStatement.setString(4, password);
		preperadStatement.executeUpdate();
	}
}