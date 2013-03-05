package datamodell;

import java.sql.Time;
import java.util.Date;
import java.util.List;

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
	
	public Employee[] getEmployeeList() { //Get employees from database
		return employees;
	}
	
	public void eventInvitation() {
		for(Employee e: participants) {
			e.inviteToEvent(this);
		}
	}
	
	public void notifyDelete() {
		for(Employee e: participants) {
			e.notifyDeleteEvent(this);
		}
	}
	
//Reserve location in locales and set locale here
	public void reserveLocale(Locale locale, Time start, Time end) {
		locales.setReservedLocale(locale, start, end);
		setLocale(locale);
	}

//Reserve name as location (location is not meeting room)
	public void reserveLocale(String name, Time start, Time end) {
		Locale l = new Locale(name);
		locales.setReservedLocale(l, start, end);
		setLocale(l);
	}

//Returns available locales as boolean table (1 is reserved, 0 is not)
	public boolean[][] getAvailableLocales(Time start, Time end) {
		return locales.getLocales(start, end);
	}

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
	
	public void addParticipants(Employee participant) {
		participants.add(participant);
	}
	
	public Locales getLocales() {
		return locales;
	}
}
