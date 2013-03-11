package datamodell;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * An instance of the event class is constructed when an employee creates an calendar event.
 * It contains methods for setting and getting information about each event, and method calls
 * to the locales class to get available locales for the eventmaker of this instance.
 * @author Rugern
 *
 */
public class Event {
	
	private int ID;
	private Date startDate; //Date also has hours and minutes, so we'll make startDate, endDate
	private Date endDate; //these two contain all information needed for time
	private String locale; //The locale chosen for this event
	private String description;
	private String title;
	private List<Employee> participants; //Holds all participants in event, for easy notification 
	private Locales locales; //Instance holds methods for getting locales list and reservation of locale
	private EventMaker admin; //administrator (creator) of event
	private Query query; //Handles database queries
	
	//Constructor
	public Event(EventMaker maker) {
		admin = maker;
		locales = new Locales();	
	}
	
	//Fetch employees from database
	public Employee[] getEmployeeList() { 
		Employee[] employeeList = query.getEmployees();
		return employeeList;
	}
	
	/**
	 * Invitation to event is sent to following list of employees
	 */
	public void eventInvitation() {
		for(Employee e: participants) {
			e.inviteToEvent(this);
		}
	}
	
	/**
	 * Notifies participants that the event has been deleted
	 */
	public void notifyDelete() {
		for(Employee e: participants) {
			e.notifyDeleteEvent(this);
		}
	}
	
	//Reserve location in locales and set locale field here
	public void reserveLocale(String locale, int start, int end) {
		locales.setReservedLocale(locale, start, end);
		setLocale(locale);
	}

	//Reserve a name as location (when location is not a meeting room)
//	public void reserveLocale(String name, Time start, Time end) {
//		Locale l = new Locale(name);
//		locales.setReservedLocale(l, start, end);
//		setLocale(l);
//	}

	//Returns available locales' roomnumber (primary key for locale in database) in given timespan
	public int[][] getAvailableLocales(int start, int end) {
		return locales.getLocales(start, end);
	}

	/**
	 * Add single participant to event
	 * @param Employee participant
	 */
	public void addParticipants(Employee participant) {
		participants.add(participant);
	}
	
	/**
	 * Add list of participants to event
	 * @param List participants
	 */
	public void addListParticipants(List participants)
	{
		participants.addAll(participants);
	}

	//Only getters and setters from here and down
	public int getID() {
		return ID;
	}
	
	public void setID(int iD) {
		ID = iD;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date sDate) {
		this.startDate = sDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
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
	
	public List<Employee> getParticipants() {
		return participants;
	}
	
	public EventMaker getAdmin() {
		return admin;
	}

	public void setAdmin(EventMaker admin) {
		this.admin = admin;
	}
	
	//We might need this if encapsulating all time into date doesn't pan out
	/*	public Time getStartTime() {
			return startTime;
		}
		
		public Time getEndTime() {
			return endTime;
		}

		public void setEndTime(Time eTime) {
			this.endTime = eTime;
		}

		public void setStartTime(Time sTime) {
			this.startTime = sTime;
		}
	*/

}
