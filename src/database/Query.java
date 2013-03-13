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
import datamodell.Employee;
import datamodell.Event;
import datamodell.EventMaker;

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

	public void getRoomsAndDate() throws SQLException {

		String sql = "SELECT Roomnr, StartTime, EndTime FROM Locale AS L INNER JOIN Event AS E ON (L.Roomnr=E.Roomnr)";

		PreparedStatement preparedStatement = (PreparedStatement) conn.connection
				.prepareStatement(sql);

		ResultSet resultSet = preparedStatement.executeQuery();

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
	public void getEventByDate(String email, String startDate)
			throws SQLException {

		PreparedStatement preparedStatement = (PreparedStatement) conn.connection
				.prepareStatement("SELECT Title, Email, StartDate, Place FROM event WHERE StartDate=? AND Email =? ");

		preparedStatement.setString(1, startDate);
		preparedStatement.setString(2, email);

		resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {

			String sDate = resultSet.getString("StartDate");
			System.out.println(sDate);

		}

	}

	public ArrayList<Event> getThisWeeksEvents(String email, Date date, int year)
			throws ParseException, SQLException {

		DateToStringModifier dtsm = new DateToStringModifier();

		int weekNumber = dtsm.getWeeksNumber(dtsm.getCompleteDate(date, year));

		PreparedStatement preparedStatement = (PreparedStatement) conn.connection
				.prepareStatement("SELECT Title, Email, StartDate, Place, EventID FROM event WHERE weekNR=? AND Email =? ");

		preparedStatement.setInt(1, weekNumber);
		preparedStatement.setString(2, email);

		ResultSet resultSet = preparedStatement.executeQuery();

		ArrayList<Event> weekEventList = new ArrayList<Event>();
		ArrayList<EventMaker> employeeList = new ArrayList<EventMaker>();

		System.out.println("hit");
		
		while (resultSet.next()) {

			String sDate = resultSet.getString("StartDate");

			String title = resultSet.getString("Title");
			String ownerMail = resultSet.getString("Email");
			int eventID = resultSet.getInt("EventID");

			EventMaker eventMaker = new Employee(email);

			Event testEvent = new Event(eventID, email, sDate, "2013/12/12", "place", "test","Mitt møte",employeeList, EventTypes.appointment);
			
			System.out.println(testEvent.getClass());
			
			weekEventList.add(testEvent);
			
			System.out.println(weekEventList.size());
		}

		return weekEventList;
	}

}
