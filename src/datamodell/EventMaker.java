package datamodell;

public class EventMaker {
	
	private String email;
	private boolean[][] calendar;
	
	public EventMaker(String email) {
		this.email = email;
	}
	
	/**
	 * Log in method. The username and password is checked, throws exception if error.
	 * @param username
	 * @param password
	 * @throws WrongPasswordException if password wrong, UserNameNotFoundException 
	 * if username not found.
	 */
	public void log_in(String username, String password) throws Exception {
		
		//Exception if wrong password
		//throw new Exception("Incorrect password");
		
		//Exception if username not found
		//throw new Exception("Username not found");
	}
	
	/**
	 * User wants to create an event, we create the event object.
	 * The data for this object is gotten via GUI. The entire parameterlist
	 * is derived from the GUI.
	 * @return the created Event object
	 */
	public Event createEvent(String tittel, String beskrivelse, String dato, 
			int startHour, int startMin, int endHour, int endMin, 
			String romSted, EventTypes type, EventMaker deltagere, 
			Boolean lydVarsling, Boolean tekstVarsling) 
	{	
		Event event = new Event();
		
		//2. set data into event
		
		return event; //TODO currently returns an empty event, see above!
	}
	
	public void inviteToEvent(Event event) {
		//FireEvent mayhaps?
	}
	
	public void notifyDeleteEvent(Event event) {
		//Perhaps a fireEvent is in order here to harass the GUI into updating?
	}
	
	public void saveEvent() {
		
	}
	
	public void deleteEvent() {
		
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
	
}
