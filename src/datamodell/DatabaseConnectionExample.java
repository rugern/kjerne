package datamodell;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnectionExample {
	
	private static String url = "jdbc:mysql://mysql.stud.ntnu.no:3306/nilsiru_felles"; //address of mySQL server
	private static String user = "nilsiru_gruppe11@cura.itea.ntnu.no"; //Effective username at server
	private static String pw = "felles"; //Password
	private static String sql = "SELECT Navn FROM Ansatt"; //SQL-query used in example
	private static ResultSet result; //Saves the result from query
	private static ResultSetMetaData metadata; //Saves info about result

	public static void main(String[] args) {
		Connection connection = null; //Handles the connection
		Statement statement = null; //Handles query responses
		
		//Connects and processes information
		try {
			//Connect to driver and database
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, pw);
			statement = connection.createStatement();
			
			//Query the server
			result = statement.executeQuery(sql);
			
			//Fetch additional data (not necessary if you don't need it)
			metadata = result.getMetaData();
			String columnName = metadata.getColumnName(1); //Name of column specified
			int columnNumber = metadata.getColumnCount(); //Number of columns in result
			System.out.println(columnName);
			System.out.println(columnNumber);
			
			//Print all tuples in result
			int i = 1;
			try {
				while (result.next()) {
					System.out.println(result.getString(i));
					i++;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}catch (ClassNotFoundException event) {
			event.printStackTrace();
			System.out.println("Driver not found");
		}catch (SQLException event) {
			event.printStackTrace();
			System.out.println("Could not connect");
			
		//Close all connections (must be closed AFTER result is processed, else SQLException is fired)
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
