package testPackage;

import static org.junit.Assert.*;

import java.awt.List;

import org.junit.Before;
import org.junit.Test;

import datamodell.Employee;
import datamodell.Event;

public class EventMakerTest {
	
	private Employee emp1, emp2, emp3;
	private Event event;
	private String startDate = "15.03.2013 18:00";
	private String endDate = "16.03.2013 02:00";
	private String locale = "Samfundet";
	private String description = "Skal drikke øl";
	private String title = "Fest";
	private List participants;
	
	
	@Before
	public void init() {
		emp1 = new Employee("olav@firmax.com");
		emp2 = new Employee("hans@firmax.com");
		emp3 = new Employee("per@firmax.com");
		List<Employee> participants = new List();
		participants.add(emp2);
		participants.add(emp3);
		
		event = new Event(emp1);
		event.addParticipants(participants);
		event.setStartDate(startDate);
		event.setEndDate(endDate);
		event.setLocale(locale);
		event.setDescription(description);
		event.setTitle(title);
		event.addListParticipants(participants);
	}
	
	//Throws exception if wrong password or username, test fails if not
	@Test(expected = Exception.class) 
	public void loginTest() throws Exception {
		emp.log_in("olav", "qwerty");
	}
	
	//Asserts if createEvent makes sets fields correctly
	@Test
	public void createEventTest() {
		Event event2 = emp1.createEvent(title, description, startDate, endDate, locale, null, participants, null, null);
		assertEquals("startDate not equal", event.getStartDate(), event2.getStartDate());
		assertEquals("endDate not equal", event.getEndDate(), event2.getEndDate());
		assertEquals("locale not equal", event.getLocale(), event2.getLocale());
		assertEquals("description not equal", event.getDescription(), event2.getDescription());
		assertEquals("title not equal", event.getTitle(), event2.getTitle());
		
	}
		
	
	

}
