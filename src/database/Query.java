import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

public class Query {

	ResultSet resultSet;

	public void addEmployee(Connection connection, String email, String name,
			String username, String address, Integer telephonenumber,
			String password) throws SQLException {

		try {
			PreparedStatement preperadStatement = (PreparedStatement) connection
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

	public void addEvent(Connection connection,
			PreparedStatement preparedStatement, String title, String email,
			String startTime, String endTime, String description,
			String locale, EventTypes eventtype, int roomNr)
			throws SQLException {

		preparedStatement = (PreparedStatement) connection
				.prepareStatement("INSERT INTO Event(Title, Email, StartTime, EndTime, Description, Locale, MeetingOrEvent, RoomNR ) VALUES (?,?,?,?,?,?,?,?)");

		preparedStatement.setString(1, title);
		preparedStatement.setString(2, email);
		preparedStatement.setString(3, startTime);
		preparedStatement.setString(4, endTime);
		preparedStatement.setString(5, description);
		preparedStatement.setString(6, locale);
		preparedStatement.setString(7, eventtype.toString());
		preparedStatement.setInt(8, roomNr);

		// how to set message?

	}

	//Use own email if you want events associated with yourself, use employeeEmail if you want events associated with an employee
	public void getEventByDate(Connection connection, String email, String startDate)
			throws SQLException {

		PreparedStatement preparedStatement = (PreparedStatement) connection
				.prepareStatement("SELECT Title, Email, StartDate, Place FROM event WHERE StartDate=? AND Email =? ");

		preparedStatement.setString(1, startDate);
		preparedStatement.setString(2, email);

		resultSet = preparedStatement.executeQuery();
		

		while (resultSet.next()) {
			
			String sDate = resultSet.getString("StartDate");
			System.out.println(sDate);

		}

	}

	
	public void getThisWeeksEvents(Connection connection, String email){
		
		
	}
	
	public void getEmployees() {

	}

}
