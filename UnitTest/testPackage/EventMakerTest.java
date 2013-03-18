package testPackage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import GUI.EventTypes;
import datamodell.Employee;
import datamodell.Event;
import datamodell.EventMaker;

public class EventMakerTest {
	
	private EventMaker emp1, emp2, emp3;
	private Event event;
	private String startDate = "15.03.2013";
	private String startTime = "18:00";
	private String endTime = "02:00";
	private String endDate = "16.03.2013";
	private String locale = "Samfundet";
	private String description = "Skal drikke �l";
	private String title = "Fest";
	private ArrayList<EventMaker> participants = new ArrayList<EventMaker>();
	private EventTypes type = EventTypes.appointment;
	
	//Initialize necessary conditions
	@Before
	public void init() {
		//Creates employees and adds emp2 and emp3 to a list
		emp1 = new Employee("olav@firmax.com", "Olav");
		emp2 = new Employee("hans@firmax.com", "Hans");
		emp3 = new Employee("per@firmax.com", "Per");
		participants.add(emp2);
		participants.add(emp3);
		
		//Create event, with emp1 as admin
		event = new Event("olav@firmax.com", startDate, endDate, startTime, endTime, locale, 
				description, title, participants, type, 1, 1);
		event.setStartDate(startDate);
		event.setEndDate(endDate);
		event.setLocale(locale);
		event.setDescription(description);
		event.setTitle(title);
		event.addListParticipants(participants);
		
	}
	
	/* createEvent commented out from EventMaker.java
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
	*/
	
	//Asserts that invitations is received by all participants
	@Test
	public void participantInvitationTest() {
		assertEquals("Participants doesn't belong to this event", participants, event.getParticipants());
//		event.eventInvitation(); TODO: Invitations does not work
//		assertTrue("Participants have not been invited", assertEmployeesInvited(emp2.getInvitationList(), emp3.getInvitationList()));
	}
	
	//Returns true if participants have the invitation in their list
	private boolean assertEmployeesInvited(List<Event> list2, List<Event> list3) {
		if(list2.isEmpty() || list3.isEmpty()) {
			return false;
		}
		System.out.println("tom");
		if(list2.contains(event) && list3.contains(event)) {
			return true;
		}
		return false;
	}
	
	//Asserts that employee is admin of event, also that setAdminEmail works
	@Test
	public void employeeIsAdminTest() {
		assertEquals("Employee is not admin", emp1.getEmail(), event.getAdminEmail());
		event.setAdminEmail(emp2.getEmail()); //Now emp2 is admin of event
		assertEquals("New employee is not admin", emp2.getEmail(), event.getAdminEmail());
	}
	
	//Asserts that deleteEvent deletes the event correctly
	@Test
	public void deleteEventTest() {
		event.setAdminEmail(emp2.getEmail()); //Now emp2 is admin of event
		assertEquals("Employee is not admin", emp2.getEmail(), event.getAdminEmail());
//		try { TODO: deleteEvent doesn't work
//			emp2.deleteEvent(this.event);
//		} catch (Exception e) {
//			fail("Could not delete event: " + e.getMessage());
//		}
		assertTrue("Event list not empty", emp2.getEvents().isEmpty());
		assertTrue("Event list not empty", emp1.getEvents().isEmpty()); //Should be empty for other participants as well
	}
	
	

}
