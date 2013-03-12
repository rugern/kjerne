package datamodell;

import GUI.*;
import java.sql.Time;
import java.util.Date;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.sql.SQLException;
//import java.sql.Statement;
import java.util.ArrayList;

public class Locales {
	
//	private static String url = "jdbc:mysql://mysql.stud.ntnu.no:3306/nilsiru_felles"; //address of mySQL server
//	private static String user = "nilsiru_gruppe11@cura.itea.ntnu.no"; //Effective username at server
//	private static String pw = "felles"; //Password
//	private static String sql = "SELECT Roomnr, Capacity, StartTime, EndTime FROM Locale AS L INNER JOIN Event AS E ON (L.Roomnr=E.Roomnr)"; //returns all rooms in use in a event, and the start time and end time for that event
//	private static ResultSet result; //Saves the result from query
//	private static ResultSetMetaData metadata; //Saves info about result
	
	@SuppressWarnings("null")
	public int[][] getLocales(Date start, Date end) {
//		Connection connection = null;
//		Statement statement = null;
		int[][] Rooms = null;
		int[][] availableRooms = null;
		
		
		Rooms = getRoomsAndDate(); //returns roomnr(int) in first column, size of room in second(int), start time(DateTime) for the event where the room is in use, and in the forth, it returns the end time(DateTime)
		
		int i=0;
		int j=0;
		
		while (i < Rooms.length) {
			if (Rooms[i][2].isAfter(end)) {
				availableRooms[j][0] = Rooms[i][0];
				availableRooms[j][1] = Rooms[i][1];
				j++;
			} else if (start.isAfter(Rooms[i][3])) {
				availableRooms[j][0] = Rooms[i][0];
				availableRooms[j][1] = Rooms[i][1];
				j++;
			}
			i++;
		}
		/**try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, pw);
			statement = connection.createStatement();
			
			result = statement.executeQuery(sql);
			
			int i = 1;
			
			try {
				while (result.next()) {
					Rooms[i-1][1] = result.getInt(1);
					Rooms[i-1][2] = result.getInt(2);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (ClassNotFoundException event){
			event.printStackTrace();
			System.out.println("Could not connect");
		} catch (SQLException event) {
			event.printStackTrace();
			System.out.println("Could not connect");
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
		}*/
		
		return availableRooms;
	}
}
