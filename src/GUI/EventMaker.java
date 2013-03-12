package GUI;

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
	 * The data for this object is gotten via GUI.
	 * @return the created Event object
	 */
	public Event createEvent() {

		Event event = new Event();

		//TODO
		//1. get data from GUI
		//2. set data into event

		return event; //TODO currently returns an empty event, see above!
	}

	public void saveEvent() {

	}

	public void deleteEvent() {

	}


	public Event getEvent() {

		return null; //TODO
	}

	public boolean[][] getCalendar() {
		return calendar;

	}

}