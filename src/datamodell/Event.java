package datamodell;

import java.util.ArrayList;
import java.util.List;
import client.SocketClient;
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
	private String adminEmail; //administrator (creator) of event
	private EventTypes eventTypes;
	//private SocketClient socket = new SocketClient(server, port); //TODO
	
	//Constructor
	public Event(int ID, String adminEmail, String startDate, String endDate, String locale, String description, String title, ArrayList<EventMaker> participants,
			EventTypes eventTypes) {
		this.adminEmail = adminEmail;
		this.startDate = startDate;
		this.endDate = endDate;
		this.locale = locale;
		this.description = description;
		this.title = title;
		this.participants = participants;
		this.setEventTypes(eventTypes);
		//socket.createEventQuery(adminEmail, startDate, endDate, locale, description, title, participants, eventTypes);
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
	 * Add list of participants to event
	 * @param List participants
	 */
	public void addListParticipants(ArrayList<EventMaker> participants) {
		participants.addAll(participants); //Adds participants to this.participants field
		for(EventMaker e: participants) {
			//socket.addParticipantQuery(e); //Adds participants in database
		}
	}
	
	//Remove list of participants from event
	public void removeParticipants(ArrayList<EventMaker> participants) {
		participants.removeAll(participants); //Removes participants in this.participants field
		for(EventMaker e: participants) {
			//socket.removeParticipantsQuery(e); //Removes participants in database
		}
	}
	
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
