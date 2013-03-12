package database;

import java.lang.ClassNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;

import com.mysql.jdbc.PreparedStatement;

public class Conn {

	public static void main(String args[]) throws SQLException {

		Connection connection = null;
		Statement statement = null;

		Query query = new Query();

		try {
			System.out.println("Connecting to database");
			Class.forName("com.mysql.jdbc.Driver");
			
			 connection = DriverManager.getConnection(
			 "jdbc:mysql://localhost:3306/fellesprosjekt", "root", "");

//			connection = DriverManager.getConnection(
//					"jdbc:mysql://mysql.stud.ntnu.no:3306/nilsiru_felles",
//					"nilsiru_gruppe11@cura.itea.ntnu.no", "felles");

			statement = connection.createStatement();

			query.addEvent(connection, "test", "testing@gmail.com", null, null, null, "hydro", null, (Integer) null);
			System.out.println("entered");
			
			
			query.getEventByDate(connection, "@gmail.com", "2013mar01");
			

		} catch (ClassNotFoundException e) {
			System.out.println("Error " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("Error " + e.getMessage());
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (statement != null) {
				statement.close();
			}
		}
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