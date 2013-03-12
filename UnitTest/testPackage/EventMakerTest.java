package testPackage;

import static org.junit.Assert.assertEquals;

import java.awt.List;
import java.util.ArrayList;

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
	private ArrayList<Employee> participants;
	
	//Initialize necessary testing instances
	@Before
	public void init() {
		//Creates employees and adds emp2 and emp3 to a list
		emp1 = new Employee("olav@firmax.com");
		emp2 = new Employee("hans@firmax.com");
		emp3 = new Employee("per@firmax.com");
		participants = new ArrayList();
		participants.add(emp2);
		participants.add(emp3);
		
		//Create event, with emp1 as admin
		event = new Event(emp1);
		event.setStartDate(startDate);
		event.setEndDate(endDate);
		event.setLocale(locale);
		event.setDescription(description);
		event.setTitle(title);
		event.addParticipants(emp2);
		event.addParticipants(emp3);
		
	}
	
	//Method throws exception if wrong password or username, test fails if it doesn't
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
		assertEquals("participants not equal", event.getParticipants(), event2.getParticipants());	
		assertEquals("email not equal", "olav@firmax.com", event.getAdmin().getEmail());
	}
	
	//Asserts that invitations is received by all participants
	@Test
	public void participantInvitationTest() {
		event.eventInvitation();
		assertTrue("Participants have not been invited", assertEmployeesInvited(emp2.getInvitationList(), emp3.getInvitationList()));
	}
	
	//Returns true if participants have the invitation in their list
	private boolean assertEmployeesInvited(ArrayList<Event> list2, ArrayList<Event> list3) {
		if(list2.isEmpty() || list3.isEmpty()) {
			return false;
		}
		if(list2.get(0)==event && list3.get(0)==event) {
			return true;
		}
		return false;
	}
	
	//Asserts that employee is admin of event
	@Test
	public void employeeIsAdminTest() {
		assertEquals("Employee is not admin", emp1, event.getAdmin());
		event.setAdmin(emp2); //Now emp2 is admin of event
		assertEquals("New employee is not admin", emp2, event.getAdmin());
	}
	
	//Asserts that deleteEvent deletes the event correctly
	@Test
	public void deleteEventTest() {
		AssertTrue("Event list not empty", emp1.getEvents().isEmpty());
	}
	
	

}
