package datamodell;

import GUI.*;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.management.Query;

import client.SocketClient;
import GUI.AddingEventGUI;
import GUI.DateToStringModifier;
import GUI.EventTypes;

/**
 * An instance of the event class is constructed when an employee creates an
 * calendar event. It contains methods for setting and getting information about
 * each event, and method calls to the locales class to get available locales
 * for the eventmaker of this instance.
 * 
 * @TODO We need to implement methods of communication between client socket and
 *       this class
 * @author Rugern
 * 
 */
public class Event implements Serializable {

	private int ID;
	private String startDate; // Date also has hours and minutes, so we'll make
								// startDate, endDate
	private String endDate; // these two contain all information needed for time
	private String place; // The locale chosen for this event
	private String description;
	private String title;
	private String startTime;
	private String endTime;
	private int weekNr;
	private int roomNr;
	private ArrayList<EventMaker> participants; // Holds all participants in
												// event, for easy notification
	private String adminEmail; // administrator (creator) of event
	private EventTypes eventTypes;

	private AddingEventGUI addingEventGUI = new AddingEventGUI();
	DateToStringModifier dateToStringModifier = new DateToStringModifier();

	// private SocketClient socket = new SocketClient(server, port); //TODO

	// Constructor
	public Event(int ID, String adminEmail, String startDate, String endDate, String startTime, String endTime,String place, String description, String title, ArrayList<EventMaker> participants,
			EventTypes eventTypes, int roomNr, int weekNR) {
		this.ID = ID;
		this.adminEmail = adminEmail;
		this.startDate = startDate;
		this.endDate = endDate;
		this.place = place;
		this.roomNr = roomNr;
		this.startTime = startTime;
		this.endTime = endTime; 
		this.description = description;
		this.title = title;
		this.participants = participants;
		this.weekNr = weekNR;
		this.participants = participants;
		this.setEventTypes(eventTypes);
		try {
			new database.Query().addEventToDatabase(title, adminEmail, ID, startTime, endTime, startDate, endDate, description, place, eventTypes,  roomNr, weekNR, participants);
		} catch (SQLException e) {
		}
		
		//socket.createEventQuery(adminEmail, startDate, endDate, locale, description, title, participants, eventTypes);
	}
	
	//Constructor without setting EventID - has to be set by the database
	public Event(String adminEmail, String startDate, String endDate, String startTime, String endTime,String place, String description, String title, ArrayList<EventMaker> participants,
			EventTypes eventTypes, int roomNr, int weekNR) {
		this.adminEmail = adminEmail;
		this.startDate = startDate;
		this.endDate = endDate;
		this.place = place;
		this.roomNr = roomNr;
		this.startTime = startTime;
		this.endTime = endTime; 
		this.description = description;
		this.title = title;
		this.weekNr = weekNR;
		this.participants = participants;
		this.setEventTypes(eventTypes);
		try {
			new database.Query().addEventToDatabase(title, adminEmail, ID, startTime, endTime, startDate, endDate, description, place, eventTypes,  roomNr, weekNR, participants);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//socket.createEventQuery(adminEmail, startDate, endDate, locale, description, title, participants, eventTypes);
	}

	// Only getters and setters from here and down

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

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

	public String getPlace() {
		return place;
	}

	public void setLocale(String place) {
		this.place = place;
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
	
	public int getRoomNR(){
		return this.roomNr;
	}

	public List<EventMaker> getParticipants() {
		return participants;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String admin) {
		this.adminEmail = admin;
	}

	public EventTypes getEventTypes() {
		return eventTypes;
	}

	public void setEventTypes(EventTypes eventTypes) {
		this.eventTypes = eventTypes;
	}
}
