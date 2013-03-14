package database;

import GUI.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.PreparedStatement;
import datamodell.*;

public class Query {

	ResultSet resultSet;

	Conn conn = new Conn();

	public void addEmployee(String email, String name, String username,
			String address, Integer telephonenumber, String password)
			throws SQLException {

		try {
			PreparedStatement preperadStatement = (PreparedStatement) conn.connection
					.prepareStatement("INSERT INTO Employee(Email, Name, username, address, TLF, password ) VALUES (?,?,?,?,?,?)");

			preperadStatement.setString(1, email);
			preperadStatement.setString(2, name);
			preperadStatement.setString(3, username);
			preperadStatement.setString(4, address);
			preperadStatement.setInt(5, telephonenumber);
			preperadStatement.setString(6, password);

			preperadStatement.executeUpdate();

		} catch (Exception e) {
			System.out.println("Error " + e.getMessage());
		}

	}

	public ResultSet getRoomsAndDate() throws SQLException {

		String sql = "SELECT Roomnr, StartTime, EndTime FROM Locale AS L INNER JOIN Event AS E ON (L.Roomnr=E.Roomnr)";

		PreparedStatement preparedStatement = (PreparedStatement) conn.connection
				.prepareStatement(sql);

		ResultSet resultSet = preparedStatement.executeQuery();

		return resultSet;
	}

	public void addEvent(String title, String email, String startTime,
			String endTime, String description, String locale,
			EventTypes eventtype, int roomNr) throws SQLException {

		PreparedStatement preparedStatement = (PreparedStatement) conn.connection
				.prepareStatement("INSERT INTO Event(Title, Email, StartTime, EndTime, Description, Locale, MeetingOrEvent, RoomNR ) VALUES (?,?,?,?,?,?,?,?)");

		preparedStatement.setString(1, title);
		preparedStatement.setString(2, email);
		preparedStatement.setString(3, startTime);
		preparedStatement.setString(4, endTime);
		preparedStatement.setString(5, description);
		preparedStatement.setString(6, locale);
		preparedStatement.setString(7, eventtype.toString());
		preparedStatement.setInt(8, roomNr);

		preparedStatement.executeUpdate();

	}

	public void updateEvent(String title, String email, String startTime,
			String endTime, String description, String locale,
			EventTypes eventtype, int roomNr, int eventID) throws SQLException {

		PreparedStatement preparedStatement = (PreparedStatement) conn.connection
				.prepareStatement("UPDATE Event SET Title = " + title
						+ ", Email = " + email + ", StartTime = " + startTime
						+ ",  EndTime =" + endTime + ", Description ="
						+ description + ", Locale = " + locale
						+ ", MeetingOrEvent = " + eventtype + ", RoomNR = "
						+ roomNr + "WHERE EventID = " + eventID);

		preparedStatement.executeUpdate();
	}

	// Use own email if you want events associated with yourself, use
	// employeeEmail if you want events associated with an employee
	public ArrayList<Event> getEventByDate(String email, Date date, int year)
			throws SQLException {

		DateToStringModifier dtsm = new DateToStringModifier();
		
		String startDate = dtsm.getCompleteDate(date, year);
		
		PreparedStatement preparedStatement = (PreparedStatement) conn.connection
				.prepareStatement("SELECT Event.EventID, Event.Email, StartTime, EndTime, StartDate, EndDate, Description, Place, State, Title, MeetingOrEvent, Roomnr, weekNR FROM Event, Participant WHERE Participant.Email= ? AND StartDate = ?");

		preparedStatement.setString(1, email);
		preparedStatement.setString(2, startDate);

		resultSet = preparedStatement.executeQuery();

		ArrayList<Event> daysEventList = new ArrayList<Event>();
		ArrayList<EventMaker> employeeList = new ArrayList<EventMaker>();

		while (resultSet.next()) {

			int eventID = resultSet.getInt("EventID");
			// email from parameters
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

			
			daysEventList.add(new Event(eventID, adminEmail, startingDate, endDate,
					startTime, endTime, place, description, title,
					employeeList, EventTypes.appointment));
		}

		return daysEventList;
	}

	public ArrayList<Event> getThisWeeksEvents(String email, Date date, int year)
			throws ParseException, SQLException {

		DateToStringModifier dtsm = new DateToStringModifier();

		int weekNumber = dtsm.getWeeksNumber(dtsm.getCompleteDate(date, year));

		ArrayList<Event> weekEventList = new ArrayList<Event>();
		ArrayList<EventMaker> employeeList = new ArrayList<EventMaker>();

		PreparedStatement preparedStatement = (PreparedStatement) conn.connection
				.prepareStatement("SELECT Event.EventID, Event.Email, StartTime, EndTime, StartDate, EndDate, Description, Place, State, Title, MeetingOrEvent, Roomnr, weekNR FROM Event, Participant WHERE Participant.Email= ? AND Event.weekNR = ?");

		preparedStatement.setString(1, email);
		preparedStatement.setInt(2, weekNumber);

		resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {

			int eventID = resultSet.getInt("EventID");
			// email from parameters
			String adminEmail = resultSet.getString("Event.Email");
			String startTime = resultSet.getString("StartTime");
			String endTime = resultSet.getString("EndTime");
			String startDate = resultSet.getString("StartDate");
			String endDate = resultSet.getString("EndDate");
			String description = resultSet.getString("Description");
			String place = resultSet.getString("Place");
			String state = resultSet.getString("State");
			String title = resultSet.getString("Title");
			String meetingOrEvent = resultSet.getString("MeetingOrEvent");
			int roomNr = resultSet.getInt("RoomNR");
			int weekNR = resultSet.getInt("weekNR");

			weekEventList.add(new Event(eventID, adminEmail, startDate, endDate,
					startTime, endTime, place, description, title,
					employeeList, EventTypes.appointment));
		}
		
		conn.connection.close();
		
		return weekEventList;
	}
	
	public void deleteEvent(int eventid, String email) throws SQLException{
		
		PreparedStatement preparedStatement = (PreparedStatement) conn.connection.prepareStatement("DELETE FROM Event WHERE EventID = ? AND Email=?");
		
		preparedStatement.setInt(1,eventid);
		preparedStatement.setString(2, email);
		
		preparedStatement.execute();
	}

	
}
