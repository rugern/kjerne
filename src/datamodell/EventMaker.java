package datamodell;

import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.List;

import GUI.EventTypes;
import client.SocketClient;

/**
 * EventMaker is the abstract superclass of both Employee and Group. An EventMaker object has
 * personal list of Event(s) that the person is participating in. The calendar boolean
 * 2D array is used for quick lookup of EventMaker's events (used in determining
 * whether or not EventMaker is available on a given date). 
 * Also of note is the invitations field, this stores events that haven't been responded to yet.
 * 
 * Note: consider changing name of class to something less ambiguous
 * @author Jama
 */
public abstract class EventMaker {
	
	private String email;
	private boolean[][] calendar;
	private List<Event> events; //list of events this user is connected to
	private List<Event> invitations; //TODO ensure accept and decline is handled
	private List<String> deletes; //A quick toString of an event right before deletion ensures that the user knows what has been deleted, once user is notified, delete the string from this list as well.
//	private SocketClient socket = new SocketClient(server, port); //TODO
	
	public EventMaker(String email) {
		this.setEmail(email);
	}
	
	/**
	 * @deprecated Server must handle login, obviously EventMaker has access
	 * to itself therefore authentication must happen outside of this class.
	 */
	public void log_in(String username, String password) throws Exception {
		//Exception if wrong password
		//throw new Exception("Incorrect password");
		
		//Exception if username not found
		//throw new Exception("Username not found");
	}
	
	/**
	 * User wants to create an event, we create the event object.
	 * The data for this object is gotten via GUI, the GUI calls the method.
	 * When the Event is created it is added to this EventMakers personal list of Events
	 * @parameter Event fields
	 * @return the created Event object
	 */
	public Event createEvent(int ID, String title, String description, String startDate, 
			String endDate, String locale, EventTypes type, ArrayList<EventMaker> participants, 
			Boolean lydVarsling, Boolean tekstVarsling) {
		
		Event event = new Event(0, email, startDate, endDate, locale, description, title, participants, type);
		return event;
	}
	
	/**
	 * This EventMaker has been invited to an event, add to list of invitations 
	 * and fire event so that user sees this if already logged on.
	 * @param event
	 */
	public void inviteToEvent(Event event) {
		//TODO PropertyChangeListener maybe?
		invitations.add(event);
	}
	
	/**
	 * This EventMaker is notified that an event has been deleted. Therefore
	 * the event is deleted from his personal list 'events'.
	 * 
	 */
	public boolean notifyDeleteEvent(Event event) {
		//TODO user must be notified via prompt, either whilst logged on, or next time
		//user logs on. This is where deletes list comes into play.
		addDeleted(event);
		return this.events.remove(event);
	}
	
	public void saveEvent() {
		
	}
	
	//Change event in this.events and database
//	public void changeEvent(Event event) {
//		if(email != event.getAdminEmail()) throw new Exception("Cannot change, user is not administrator");
//		else if(events.contains(event))//user is admin and event is in his list
//		{
//			event.eventInvitation(); //Gives invitation to changed event
//			socket.changeEventQuery(event); //Change event in database
//		}
//	}
	
	/**
	 * 
	 * @param The event to be deleted
	 * @throws Exception if this eventmaker is not admin of event. Therefore cannot delete
	 */
	public void deleteEvent(Event event) throws Exception {
		
		if(email != event.getAdminEmail()) throw new Exception("Cannot delete, user is not administrator");
		else if(events.contains(event))//user is admin and event is in his list
		{
			event.notifyDelete(); //tells everyone subscribing to event to delete it
//			socket.deleteEventQuery(event.getID()); //Delete event from database
		}
	}
	
	//Get this.calendar or coworkers calendar, specified by EventMaker's email
//	public ArrayList<Event> getDataBaseCalendar(String email) {
//		ArrayList<Event> calendar = socket.getCalendarQuery(email); //Returns coworker calendar from database
//		return calendar;
//	}
	
	//Get calendar for given week
//	public ArrayList<Event> getWeekCalendar(int weekNr) {
//		ArrayList<Event> weekList = socket.getWeekCalendarQuery(email, weekNr);
//		return weekList;
//	}
	
	
	/**
	 * events that haven't been responded to yet (accept/decline)
	 * TODO as soon as user has logged on, a check should be run on whether 
	 * there are any invitations that haven't been responded to
	 */
	public List<Event> getInvitationList()
	{
		return invitations;
	}
	
	public void addInvitation(Event event)
	{
		invitations.add(event);
	}
	
	/**
	 * Henter event. Ikke la deg lure av metodenavn!
	 * Her vil GUI sannsynligvis sende EVENT som er syntaktisk valgt av 
	 * bruker.
	 * @return ???
	 * @deprecated Skiten her er jo ikke nødvendig. GUI sender Event til EventMaker
	 * det eneste EventMaker trenger å huske på er seg selv.
	 */
	public Event getEvent() {
		
		return null; //TODO
	}
	
	public boolean[][] getCalendar() {
		return calendar;
	}

	public List<String> getDeletes() {
		return deletes;
	}

	public void addDeleted(Event event) {
		deletes.add(event.toString());
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<Event> getEvents() {
		return events;
	}
	
}
