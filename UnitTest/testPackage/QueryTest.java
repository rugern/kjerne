package testPackage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import GUI.EventTypes;
import database.Query;
import datamodell.Employee;
import datamodell.Event;
import datamodell.Locale;

public class QueryTest {
	
	Query q = new Query();
	
	//Div fields used in tests
	String email = "birger@firmax.com";
	String title = "Bursdag";
	String startDate = "21/05/2013";
	String endDate = "21/05/2013";
	String startTime = "17:00";
	String endTime = "19:00";
	String desc = "Fest/selskap";
	String place = "my crib!!1";
	int id = 1337;
	int roomNr = 1;
	
	/* FUNGERER! Kommentert ut fordi jeg ikke gidder å slette entry'en fra databasen hver gang
	//Assert that employees are added and retrieved correctly
	@Test
	public void addEmployeeTest() throws SQLException {
		q.addEmployee(email, "birger", "birdy", "Trondheim", 81549300, "birdynamnam");
		ArrayList<Employee> employeeList = q.getEmployees();
		for(Employee emp: employeeList) {
			if(emp.getEmail() == email) { //Equivalent to assertEquals(email, emp.getEmail())
				assertEquals("This employee is not admin", email, emp.getEmail());
				assertEquals("Name is not equal", "birger", emp.getName());
			}
		}
	}
	*/
	
	//Assert that events are added and retrieved correctly
	@Test
	public void addEventTest() throws SQLException {
		q.addEventToDatabase(title, email, id, startTime, endTime, startDate, endDate, desc, place, EventTypes.appointment, roomNr, 5);
		ArrayList<Event> eventList = q.getEventByDate(email, startDate, 2013);
		for(Event e: eventList) {
			if(e.getID() == id) {
				assertEquals("Title is not equal", title, e.getTitle());
				assertEquals("Description is not equal", desc, e.getDescription());
				assertEquals("Admin is not equal", email, e.getAdminEmail());
				assertEquals("End date is not equal", endDate, e.getEndDate());
				assertEquals("Start date is not equal", startDate, e.getStartDate());
				assertEquals("Start time is not equal", startTime, e.getStartTime());
				assertEquals("End time is not equal", endTime, e.getEndTime());
				assertEquals("Place is not equal", place, e.getPlace());
				assertEquals("Eventtype is not equal", EventTypes.appointment, e.getEventTypes());
			}
		}
		assertEquals("Could not delete event, or the event was not found", deleteEventTest());
	}
	
	//Assert that events are updated correctly
	@Test
	public void updateEventTest() throws SQLException {
		String newTitle = "Ikke et bursdagsselskap";
		q.updateEvent(newTitle, email, startTime, endTime, desc, place, EventTypes.appointment, roomNr, id);
		ArrayList<Event> eventList = q.getEventByDate(email, startDate, 2013);
		for(Event e: eventList) {
			if(e.getID() == id) {
				assertEquals("Title is not equal", newTitle, e.getTitle());
				return;
			}
		}fail("Could not find event");
	}
	
	//Assert that locales are retrieved correctly
	@Test
	public void getLocaleTest() throws SQLException {
		ArrayList<Locale> localesList = q.getLocale();
		for(Locale l: localesList) {
			switch(l.getID()) {
			case 1: assertEquals("Room 1 did not match capacity", 10, l.getCapcity());
					break;
			case 2: assertEquals("Room 2 did not match capacity", 20, l.getCapcity());
					break;
			case 3: assertEquals("Room 3 did not match capacity", 40, l.getCapcity());
					break;
			case 4: assertEquals("Room 4 did not match capacity", 200, l.getCapcity());
					break;
			case 5: assertEquals("Room 5 did not match capacity", 15, l.getCapcity());
					break;
			case 6: assertEquals("Room 6 did not match capacity", 12, l.getCapcity());
					break;
			}
		}
	}
	
	//Deletes event from DB. Can't be independent test as we can't control that the delete test is run before the create test
	public boolean deleteEventTest() throws SQLException {
		//Controlling that the event we want to delete is in the DB
		boolean found = false;
		ArrayList<Event> eventList = q.getEventByDate(email, startDate, 2013);
		for(Event e: eventList) {
			if(e.getID() == id) {
				found = true;
			}
		}if(!found) {
			fail("The event is not in the database, so there is nothing to delete!");
			return found;
		}
		q.deleteEvent(id, email);
		eventList = q.getEventByDate(email, startDate, 2013);
		for(Event e: eventList) {
			if(e.getID() == id) {
				found = false; //For convenience
			}
		}
		return found;
	}
}
