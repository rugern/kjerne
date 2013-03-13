package datamodell;

import java.util.ArrayList;
import java.util.List;

import GUI.EventTypes;

/**
 * An instance of the event class is constructed when an employee creates an calendar event.
 * It contains methods for setting and getting information about each event, and method calls
 * to the locales class to get available locales for the eventmaker of this instance.
 * @author Rugern
 *
 */
public class Event {
	
	private int ID;
	private String startDate; //Date also has hours and minutes, so we'll make startDate, endDate
	private String endDate; //these two contain all information needed for time
	private String locale; //The locale chosen for this event
	private String description;
	private String title;
	private ArrayList<EventMaker> participants; //Holds all participants in event, for easy notification 
	private EventMaker admin; //administrator (creator) of event
	private EventTypes eventTypes;
	
	//Constructor
	public Event(EventMaker maker, String startDate, String endDate, String locale, String description, String title, ArrayList<EventMaker> participants,
			EventTypes eventTypes) {
		admin = maker;
		this.startDate = startDate;
		this.endDate = endDate;
		this.locale = locale;
		this.description = description;
		this.title = title;
		this.participants = participants;
		this.setEventTypes(eventTypes);
	}
	
	/**
	 * Invitation to event is sent to following list of employees
	 */
	public void eventInvitation() {
		for(EventMaker e: participants) {
			e.inviteToEvent(this);
		}
	}
	
	/**
	 * Notifies participants that the event has been deleted
	 */
	public void notifyDelete() {
		for(EventMaker e: participants) {
			e.notifyDeleteEvent(this);
		}
	}
	
	/**
	 * Add single participant to event
	 * @param Employee participant
	 */
	public void addParticipants(EventMaker participant) {
		participants.add(participant);
	}
	
	/**
	 * Add list of participants to event
	 * @param List participants
	 */
	public void addListParticipants(ArrayList<EventMaker> participants)
	{
		participants.addAll(participants);
	}
	
	/**
	 * The methods commented out may not be necessary depending on final GUI, as methods can be called directly from GUI
	 * to the relevant class
	 */
	/*
	//Reserve location in locales and set locale field here
	public void reserveLocale(String locale, String start, String end) {
		locales.setReservedLocale(locale, start, end);
		setLocale(locale);
	}
	
	//Fetch employees from database
	public Employee[] getEmployeeList() { 
		Employee[] employeeList = query.getEmployees();
		return employeeList;
	}

	//Reserve a name as location (when location is not a meeting room)
	public void reserveLocale(String name, Time start, Time end) {
	Locale l = new Locale(name);
	locales.setReservedLocale(l, start, end);
	setLocale(l);
	}

	//Returns available locales' roomnumber (primary key for locale in database) in given timespan
	public int[][] getAvailableLocales(Date start, Date end) {
		return locales.getLocales(start, end);
	}
	*/

	//Only getters and setters from here and down
	public int getID() {
		return ID;
	}
	
	public void setID(int iD) {
		ID = iD;
	}
	
	public String getStartDate() {
		return startDate;
	}
	
	public void setStartDate(String sDate) {
		this.startDate = sDate;
	}
	
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
		
	public String getLocale() {
		return locale;
	}
	
	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public List<EventMaker> getParticipants() {
		return participants;
	}
	
	public EventMaker getAdmin() {
		return admin;
	}

	public void setAdmin(EventMaker admin) {
		this.admin = admin;
	}

	public EventTypes getEventTypes() {
		return eventTypes;
	}

	public void setEventTypes(EventTypes eventTypes) {
		this.eventTypes = eventTypes;
	}
}
