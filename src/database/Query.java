package database;

import GUI.*;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.ws.handler.MessageContext.Scope;

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

	public ArrayList<Employee> getEmployees() throws SQLException {

		PreparedStatement preparedStatement = (PreparedStatement) conn.connection
				.prepareStatement("SELECT * FROM Employee");

		resultSet = preparedStatement.executeQuery();

		ArrayList<Employee> employees = new ArrayList<Employee>();

		while (resultSet.next()) {

			String email = resultSet.getString("Email");
			String name = resultSet.getString("Name");

			employees.add(new Employee(email, name));
		}

		return employees;
	}

	public void addEventToDatabase(String title, String email, int eventID,
			String startTime, String endTime, String startDate, String endDate,
			String description, String place, EventTypes eventtype, int roomNr,
			int weekNR, ArrayList<EventMaker> participantList) throws SQLException {

		PreparedStatement preparedStatement = (PreparedStatement) conn.connection
				.prepareStatement("INSERT INTO Event(EventID, Email, StartTime, EndTime, StartDate, EndDate, Place, Description, Title, MeetingOrEvent, RoomNR, weekNR ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS );

		preparedStatement.setInt(1, eventID);
		preparedStatement.setString(2, email);
		preparedStatement.setString(3, startTime);
		preparedStatement.setString(4, endTime);
		preparedStatement.setString(5, startDate);
		preparedStatement.setString(6, endDate);
		preparedStatement.setString(7, place);
		preparedStatement.setString(8, description);
		preparedStatement.setString(9, title);
		preparedStatement.setString(10, eventtype.toString());
		preparedStatement.setInt(11, roomNr);
		preparedStatement.setInt(12, weekNR);
		
		
		preparedStatement.executeUpdate();
		
		resultSet = preparedStatement.getGeneratedKeys();
		
		PreparedStatement preparedStatement2 = (PreparedStatement) conn.connection.prepareStatement("INSERT INTO Participant(EventID, Email) VALUES (?,?)");

		int id = 0;

		while (resultSet.next()) {
			id = resultSet.getInt(1);
			System.out.println(id);
			
		}

		preparedStatement2.setInt(1, id);
		
		for (int i = 0; i < participantList.size(); i++) {
			preparedStatement2.setString(2, participantList.get(i).getEmail());
			preparedStatement2.executeUpdate();
		}
		

	}

	public void updateEvent(String title, String startTime, String endTime,
			String startDate, String endDate, String description,
			String locale, EventTypes eventtype, int roomNr, int eventID,
			int weekNR) throws SQLException {

		PreparedStatement preparedStatement = (PreparedStatement) conn.connection
				.prepareStatement("UPDATE Event SET Title = ?, StartTime = ? ,  EndTime = ? , StartDate = ?, EndDate = ?, Description = ?, Place = ?, MeetingOrEvent = ?,  RoomNR = ? , weekNR = ? WHERE EventID = ?");

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

	public ArrayList<Locale> getLocale() throws SQLException {

		PreparedStatement preparedStatement = (PreparedStatement) conn.connection
				.prepareStatement("SELECT * FROM Locale");

		resultSet = preparedStatement.executeQuery();

		ArrayList<Locale> locales = new ArrayList<Locale>();

		while (resultSet.next()) {
			int id = resultSet.getInt("Roomnr");
			int capacity = resultSet.getInt("Capacity");

			locales.add(new Locale(id, capacity));
		}

		return locales;

	}

	// Use own email if you want events associated with yourself, use
	// employeeEmail if you want events associated with an employee
	public ArrayList<Event> getEventByDate(String email, Date date, int year)
			throws SQLException {

		DateToStringModifier dtsm = new DateToStringModifier();

		String startDate = dtsm.getCompleteDate(date, year);

		PreparedStatement preparedStatement = (PreparedStatement) conn.connection
				.prepareStatement("SELECT DISTINCT Event.EventID, Event.Email, StartTime, EndTime, StartDate, EndDate, Description, Place, State, Title, MeetingOrEvent, Roomnr, weekNR FROM Event JOIN Participant WHERE Event.EventID = Participant.EventID AND StartDate = ? AND Participant.Email = ? ");

		preparedStatement.setString(1, startDate);
		preparedStatement.setString(2, email);

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
			int weeknr = resultSet.getInt("weekNR");

			daysEventList.add(new Event(eventID, adminEmail, startingDate,
					endDate, startTime, endTime, place, description, title,
					employeeList, EventTypes.meeting, roomNr, weeknr));

		}

		PreparedStatement preparedStatement2 = (PreparedStatement) conn.connection
				.prepareStatement("SELECT EventID, Email, StartTime, EndTime, StartDate, EndDate, Description, Place, State, Title, MeetingOrEvent, Roomnr, weekNR FROM Event WHERE StartDate = ? AND Email = ? ");

		preparedStatement2.setString(1, startDate);
		preparedStatement2.setString(2, email);

		resultSet = preparedStatement2.executeQuery();

		while (resultSet.next()) {

			int eventID = resultSet.getInt("EventID");
			// email from parameters
			String adminEmail = resultSet.getString("Email");
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

			daysEventList.add(new Event(eventID, adminEmail, startingDate,
					endDate, startTime, endTime, place, description, title,
					employeeList, EventTypes.meeting, roomNr, weeknr));
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
				.prepareStatement("SELECT DISTINCT Event.EventID, Event.Email, StartTime, EndTime, StartDate, EndDate, Description, Place, State, Title, MeetingOrEvent, Roomnr, weekNR FROM Event JOIN Participant WHERE Event.EventID = Participant.EventID  AND Participant.Email = ? AND weekNR = ?");

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

			weekEventList.add(new Event(eventID, adminEmail, startDate,
					endDate, startTime, endTime, place, description, title,
					employeeList, EventTypes.meeting, roomNr, weekNR));
		}

		PreparedStatement preparedStatement2 = (PreparedStatement) conn.connection
				.prepareStatement("SELECT EventID, Email, StartTime, EndTime, StartDate, EndDate, Description, Place, State, Title, MeetingOrEvent, Roomnr, weekNR FROM Event WHERE weekNR = ? AND Email = ? ");

		preparedStatement2.setInt(1, weekNumber);
		preparedStatement2.setString(2, email);

		resultSet = preparedStatement2.executeQuery();

		while (resultSet.next()) {

			int eventID = resultSet.getInt("EventID");
			// email from parameters
			String adminEmail = resultSet.getString("Email");
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

			weekEventList.add(new Event(eventID, adminEmail, startingDate,
					endDate, startTime, endTime, place, description, title,
					employeeList, EventTypes.meeting, roomNr, weeknr));
		}

		conn.connection.close();

		return weekEventList;
	}

	public void deleteEvent(int eventid, String email) throws SQLException {

		PreparedStatement preparedStatement = (PreparedStatement) conn.connection
				.prepareStatement("DELETE FROM Event WHERE EventID = ? AND Email=?");

		preparedStatement.setInt(1, eventid);
		preparedStatement.setString(2, email);

		preparedStatement.execute();
	}

}
