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
	
	public ArrayList getEmployeeList() {
		return null;
	}
	
	public void eventInvitation() {
		for(Employee e: participants) {
			e.invite(this);
		}
	}
	
	public void notifyDelete() {
		for(Employee e: participants) {
			e.notifyDelete(this);
		}
	}
	
	public void reserveLocale(Locale locale, Time start, Time end) {
		
	}
	
	public void reserveLocale(String name, Time start, Time end) {
		
	}
	
	public boolean[][] getAvailableLocales(Time start, Time end) {
		return locales.getLocales(start, end);
	}

}
