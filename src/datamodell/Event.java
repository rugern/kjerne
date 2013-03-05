package datamodell;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Event {
	
	private int ID;
	private Date date;
	private Time time;
	private Locale locale;
	private String description;
	private String title;
	private Employee[] participants;
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
	
	public void reserveLocale(datamodell.Locale locale, Time start, Time end) {
		locales.setReservedLocale(locale, start, end);
	}
	
	public void reserveLocale(String name, Time start, Time end) {
		locales.setReservedLocale(name, start, end);
	}
	
	public boolean[][] getAvailableLocales(Time start, Time end) {
		return locales.getLocales(start, end);
	}

}
