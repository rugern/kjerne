package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import GUI.DateToStringModifier;
import GUI.EventTypes;

import com.mysql.jdbc.PreparedStatement;

import datamodell.Employee;
import datamodell.Event;
import datamodell.EventMaker;
import datamodell.Locale;

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
			String description, String place, EventTypes eventtype, int roomNr, int weekNR) throws SQLException {

		PreparedStatement preparedStatement = (PreparedStatement) conn.connection
				.prepareStatement("INSERT INTO Event(EventID, Email, StartTime, EndTime, StartDate, EndDate, Place, Description, Title, MeetingOrEvent, RoomNR, weekNR ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");

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

	}

	public void updateEvent(String title, String email, String startTime,
			String endTime, String description, String locale,
			EventTypes eventtype, int roomNr, int eventID) throws SQLException {

		PreparedStatement preparedStatement = (PreparedStatement) conn.connection
				.prepareStatement("UPDATE Event SET Title = ?, Email = ?, StartTime = ?,  EndTime =?, " +
						"Description =?, Place = ?, MeetingOrEvent = ?, Roomnr = ? WHERE EventID = ?");
		
		preparedStatement.setString(1, title);
		preparedStatement.setString(2, email);
		preparedStatement.setString(3, startTime);
		preparedStatement.setString(4, endTime);
		preparedStatement.setString(5, description);
		preparedStatement.setString(6, locale);
		preparedStatement.setString(7, eventtype.toString());
		preparedStatement.setInt(8, roomNr);
		preparedStatement.setInt(9, eventID);
		
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
	public ArrayList<Event> getEventByDate(String email, String startDate, int year)
			throws SQLException {

		DateToStringModifier dtsm = new DateToStringModifier();

		PreparedStatement preparedStatement = (PreparedStatement) conn.connection
				.prepareStatement("SELECT DISTINCT Event.EventID, Event.Email, StartTime, EndTime, StartDate, EndDate, Description, Place, State, Title, MeetingOrEvent, Roomnr, weekNR FROM Event JOIN Participant WHERE StartDate = ? AND (Event.Email = ? OR Participant.Email = ?) ");

		preparedStatement.setString(1, startDate);
		preparedStatement.setString(2, email);
		preparedStatement.setString(3, email);

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
			EventTypes eventType;
			if(resultSet.getString("MeetingOrEvent") == "appointment") {
				eventType = EventTypes.appointment;
			}else {
				eventType = EventTypes.meeting;
			}
			int roomNr = resultSet.getInt("RoomNR");
			int weeknr = resultSet.getInt("weekNR");

			daysEventList.add(new Event(eventID, adminEmail, startingDate,
					endDate, startTime, endTime, place, description, title,
					employeeList, eventType, roomNr, weeknr));
		}

		return daysEventList;
	}

	public ArrayList<Event> getThisWeeksEvents(String email, String date, int year)
			throws ParseException, SQLException {

		SimpleDateFormat sdf;
        Calendar cal;
        Date dateObject;
        int weekNumber;
        sdf = new SimpleDateFormat("MM/dd/yyyy");
        dateObject = sdf.parse(date);
        cal = Calendar.getInstance();
        cal.setTime(dateObject);
        weekNumber = cal.get(Calendar.WEEK_OF_MONTH);
        System.out.println(weekNumber);
		
		ArrayList<Event> weekEventList = new ArrayList<Event>();
		ArrayList<EventMaker> employeeList = new ArrayList<EventMaker>();

		PreparedStatement preparedStatement = (PreparedStatement) conn.connection
				.prepareStatement("SELECT DISTINCT Event.EventID, Event.Email, StartTime, EndTime, StartDate, EndDate, Description, Place, State, Title, MeetingOrEvent, Roomnr, weekNR FROM Event JOIN Participant WHERE (Event.Email = ? OR Participant.Email = ?) AND weekNR = ?");

		preparedStatement.setString(1, email);
		preparedStatement.setString(2, email);
		preparedStatement.setInt(3, weekNumber);

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
			EventTypes eventType;
			if(resultSet.getString("MeetingOrEvent") == "appointment") {
				eventType = EventTypes.appointment;
			}else {
				eventType = EventTypes.meeting;
			}
			int roomNr = resultSet.getInt("RoomNR");
			int weekNR = resultSet.getInt("weekNR");

			weekEventList.add(new Event(eventID, adminEmail, startDate,
					endDate, startTime, endTime, place, description, title,
					employeeList, eventType, roomNr, weekNR));
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
