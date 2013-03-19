package database;

//test

import java.lang.ClassNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.text.ParseException;
import java.util.Date;

import GUI.DateToStringModifier;

import com.mysql.jdbc.PreparedStatement;

/**
 * This is a copy of Conn, changed to reflect the new simplified architecture
 * 
 * A client accesses the server and in turn the server will access the DB whenever needed, it will use this class as its gateway.
 * A method will be supplied here for whatever the server would need. You want to check if username and password match? 
 * Call getUser(username, password) and see if it returns null or not! The SQL queries themselves are written here.
 * 
 * @author Jama
 */
public class DBAccess {

  public static Connection con;

  public static void open() throws InstantiationException
	{
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no:3306/nilsiru_felles", "nilsiru_gruppe11@cura.itea.ntnu.no", "felles");
			System.out.println("Connection established"); //we get to this point, it's on. It's on like donkey kong.
		} catch (IllegalAccessException e) {
			System.err.println("IllegalAccessException!\n"+ e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("ClassNotFoundException!\n"+ e.getMessage());
			e.printStackTrace();
		} catch (SQLException e){
			System.err.println("SQL Exception!\n"+ e.getMessage());
			e.printStackTrace();
		}
	}

	public static void close()
	{
		if(con != null)
			try {
				con.close();
			} catch (SQLException e) {
				System.err.println("SQL Exception!\n"+ e.getMessage());
				e.printStackTrace();
			}
	}

	//---------------------------------------------------------
	//From here on out we need querys TODO

	/**
	 * Checks if username and password is correct
	 * @param user
	 * @param password
	 * @return true if correct, false if false
	 */
	public static boolean validateLogin(String user, String password)
	{
		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT * FROM Employee WHERE Username = '"+user+"' AND Password = '"+password+"'");

			while(rs.next())
				if(rs.getString("Username").equals(user) && rs.getString("Password").equals(password)) //Yes! We've found you in the DB!
					return true;

		} catch (SQLException e) {
			System.err.println("SQL Exception!\n"+ e.getMessage());
			e.printStackTrace();
		}

		return false;
	}

	public static void isUserNameTaken()
	{}

	public static void getThisWeeksEvents(String email, Date date, int year) {

		try {
			ResultSet rs;

			DateToStringModifier dtsm = new DateToStringModifier();
			int weekNumber = dtsm.getWeeksNumber(dtsm.getCompleteDate(date, year));

			PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement(
					"SELECT * FROM Event WHERE WeekNr=? AND Email =?");
			preparedStatement.setInt(1, weekNumber);
			preparedStatement.setString(2, email);

			rs = preparedStatement.executeQuery();

			while(rs.next())
			{
				System.out.println("Test of getThisWeeksEvents: "); //TODO

				System.out.println(rs.getString(1));
				System.out.println(rs.getString(2));
				System.out.println(rs.getString(3));
				System.out.println(rs.getString(4));
				System.out.println(rs.getString(5));
				System.out.println(rs.getString(6));
			}

		} catch (SQLException e) {
			System.err.println("SQL Exception!\n"+ e.getMessage());
			e.printStackTrace();
		}
	}

	public static void getEventByDate(String email, Date startDate)
	{
		try {
			PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement(
					"SELECT * FROM Event WHERE StartDate = ? AND Email = ?");

			preparedStatement.setDate(1, new java.sql.Date(startDate.getTime()));
			preparedStatement.setString(2, email);

			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next())
			{
				System.out.println("Test of getEventByDate: "); //TODO

				System.out.println(rs.getString(1));
				System.out.println(rs.getString(2));
				System.out.println(rs.getString(3));
			}
			
		} catch (SQLException e) {
			System.err.println("SQL Exception!\n"+ e.getMessage());
			e.printStackTrace();
		}
	}

	public static ResultSet getRoomsAndDate() throws SQLException {

		String sql = "SELECT L.Roomnr, StartTime, EndTime FROM Locale AS L INNER JOIN Event AS E ON (L.Roomnr=E.Roomnr)";

		PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement(sql);

		ResultSet resultSet = preparedStatement.executeQuery();

		return resultSet;
	}
	
	public static void addEvent()
	{
		
	}

	public static void removeEvent()
	{}

	public static void addParticipant()
	{}

	public static void removeParticipant()
	{}

	public static void addListParticipant()
	{}

	public static void getGroup()
	{}

	public static void getEventLocale()
	{}

	//END MYSQL queries
	//-------------------------------------------
}
