package database;

//test

import java.lang.ClassNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import GUI.DateToStringModifier;
import GUI.EventTypes;

import com.mysql.jdbc.PreparedStatement;

import datamodell.Employee;
import datamodell.Event;
import datamodell.EventMaker;

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
			con = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no/nilsiru_felles", "nilsiru_gruppe11", "felles");
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
	 * Checks if email and password is correct
	 * @param email
	 * @param password
	 * @return true if correct, false if false
	 */
	public static boolean validateLogin(String email, String password)
	{
		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT * FROM Employee WHERE Email = '"+email+"' AND Password = '"+password+"'");

			while(rs.next())
				if(rs.getString("Email").equals(email) && rs.getString("Password").equals(password)) //Yes! We've found you in the DB!
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

	public static ArrayList<ArrayList> getSentInvitations(String senderEmail)
			throws SQLException {

		PreparedStatement preparedStatement = (PreparedStatement) con.
				prepareStatement("SELECT EventID, Email, StartTime, EndTime, StartDate, EndDate, Description, Place, State, Title, MeetingOrEvent, Roomnr, WeekNR FROM Event WHERE Email = ?"); //TODO Note: removed MeetingOrEvent

		preparedStatement.setString(1, senderEmail);

		ResultSet resultSet = preparedStatement.executeQuery();

		ArrayList<ArrayList> payload = new ArrayList<ArrayList>();

		while (resultSet.next()) {

			int eventID = resultSet.getInt("EventID");
			String adminEmail = resultSet.getString("Event.Email");
			String startTime = resultSet.getString("StartTime");
			String endTime = resultSet.getString("EndTime");
			String startingDate = resultSet.getString("StartDate");
			String endDate = resultSet.getString("EndDate");
			String description = resultSet.getString("Description");
			String place = resultSet.getString("Place");
			String state = resultSet.getString("State");
			String title = resultSet.getString("Title");
			String meetingOrEvent = resultSet.getString("MeetingOrEvent");
			int roomNr = resultSet.getInt("RoomNR");
			int weeknr = resultSet.getInt("weekNR");

			PreparedStatement preparedStatement2 = (PreparedStatement) con.
					prepareStatement("SELECT Participant.Email, Name from Participant Join Employee WHERE Employee.Email = Participant.Email AND EventID = ?");

			preparedStatement2.setInt(1, 2); //TODO set to 1

			ResultSet resultSet2 = preparedStatement2.executeQuery();

			ArrayList<String> employeeList = new ArrayList<String>();

			while (resultSet2.next()) {

				String email = resultSet2.getString("Email");
				String name = resultSet2.getString("Name");

				ArrayList<Object> row = new ArrayList<Object>();

				employeeList.add(email);

				if (employeeList.size() > 0) {

					System.out.println("Adding "+eventID);

					row.add(eventID);
					row.add(adminEmail);
					row.add(startingDate);
					row.add(endDate);
					row.add(startTime);
					row.add(endTime);
					row.add(place);
					row.add(description);
					row.add(title);
					row.add(employeeList);
					row.add(EventTypes.meeting);
					row.add(roomNr);
					row.add(weeknr);

					/*
					sentInvitationList.add(new Event(eventID, adminEmail,
							startingDate, endDate, startTime, endTime, place,
							description, title, employeeList,
							EventTypes.meeting, roomNr, weeknr));
					 */
				}
				payload.add(row);
			}
		}

		return payload;
	}

	public static ArrayList<ArrayList> getRecievedInvitations(String recieverEmail)
			throws SQLException {

		PreparedStatement preparedStatement = (PreparedStatement) con.
				prepareStatement("SELECT DISTINCT Event.EventID, Event.Email, StartTime, EndTime, StartDate, EndDate, Description, Place, State, Title, MeetingOrEvent, Roomnr, weekNR FROM Event JOIN Participant WHERE Event.EventID = Participant.EventID AND Participant.Email = ?");
		preparedStatement.setString(1, recieverEmail);

		ResultSet resultSet = preparedStatement.executeQuery();

		ArrayList<ArrayList> payload = new ArrayList<ArrayList>();

		while (resultSet.next()) {

			ArrayList<Object> row = new ArrayList<Object>();

			int eventID = resultSet.getInt("EventID");
			String adminEmail = resultSet.getString("Event.Email");
			String startTime = resultSet.getString("StartTime");
			String endTime = resultSet.getString("EndTime");
			String startingDate = resultSet.getString("StartDate");
			String endDate = resultSet.getString("EndDate");
			String description = resultSet.getString("Description");
			String place = resultSet.getString("Place");
			String state = resultSet.getString("State");
			String title = resultSet.getString("Title");
			String meetingOrEvent = resultSet.getString("MeetingOrEvent");
			int roomNr = resultSet.getInt("RoomNR");
			int weeknr = resultSet.getInt("weekNR");

			PreparedStatement preparedStatement2 = (PreparedStatement) con.
					prepareStatement("SELECT Participant.Email, Name from Participant Join Employee WHERE Employee.Email = Participant.Email AND EventID = ?");

			preparedStatement2.setInt(1, eventID);

			ResultSet resultSet2 = preparedStatement2.executeQuery();

			ArrayList<String> employeeList = new ArrayList<String>();

			while (resultSet2.next()) {
				String email = resultSet2.getString("Email");
				String name = resultSet2.getString("Name");
				employeeList.add(email);				
			}

			row.add(eventID);
			row.add(adminEmail);
			row.add(startingDate);
			row.add(endDate);
			row.add(startTime);
			row.add(endTime);
			row.add(place);
			row.add(description);
			row.add(title);
			row.add(employeeList);
			row.add(EventTypes.meeting);
			row.add(roomNr);
			row.add(weeknr);

			payload.add(row);
		}
		return payload;
	}

	public void updateEvent(String title, String startTime, String endTime,
			String startDate, String endDate, String description,
			String locale, EventTypes eventtype, int roomNr, int eventID,
			int weekNR) throws SQLException {

		PreparedStatement preparedStatement = (PreparedStatement) con.
				prepareStatement("UPDATE Event SET Title = ?, StartTime = ? ,  EndTime = ? , StartDate = ?, EndDate = ?, Description = ?, Place = ?, MeetingOrEvent = ?,  RoomNR = ? , weekNR = ? WHERE EventID = ?");

		preparedStatement.setString(1, title);
		preparedStatement.setString(2, startTime);
		preparedStatement.setString(3, endTime);
		preparedStatement.setString(4, startDate);
		preparedStatement.setString(5, endDate);
		preparedStatement.setString(6, description);
		preparedStatement.setString(7, locale);
		preparedStatement.setString(8, eventtype.toString());
		preparedStatement.setInt(9, roomNr);
		preparedStatement.setInt(10, weekNR);
		preparedStatement.setInt(11, eventID);

		preparedStatement.executeUpdate();
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
