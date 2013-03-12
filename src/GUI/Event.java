package GUI;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Event {

	private int ID;
	private Date date;
	private Time time;
	private Locale locale;
	private String description;
	private String title;
	private List<Employee> participants;
	private Locales locales;

	public Event() {
		locales = new Locales();	
	}

	//Need to get employees from database, implement later
	public Employee[] getEmployeeList() {
		return null; 

	}

	//Not final, but guessing call to employee-class to notify about event
	public void eventInvitation() {
		for(Employee e: participants){
//			e.inviteToEvent(this);	
		}
	}
	
	//Not final, but guessing call to employee-class to notify about event
	public void notifyDelete() {
		for(Employee e: participants) {
//			e.notifyDeleteEvent(this);
		}
	}

	//Reserve location in locales and set locale field here
	public void reserveLocale(Locale locale, Time start, Time end) {
//		locales.setReservedLocale(locale, start, end);
		setLocale(locale);
	}

	//Reserve a name as location (location is not a meeting room)
	public void reserveLocale(String name, Time start, Time end) {
		Locale l = new Locale(name);
//		locales.setReservedLocale(l, start, end);
		setLocale(l);
	}

	//Not final, this returns available locales as boolean table (1 is reserved, 0 is not)
	public boolean[][] getAvailableLocales(Time start, Time end) {
		return locales.getLocales(start, end);
	}

	//Add participants to event
	public void addParticipants(Employee participant) {
		participants.add(participant);
	}

	//Only getters and setters from here and down
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
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

	public Locales getLocales() {
		return locales;
	}
}

