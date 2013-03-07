package datamodell;

//Dette er en test fra Vikas

public class Employee extends EventMaker {

	private int ssn;
	
	public Employee(String email) {
		super(email);
	}

	
	/**
	 * This EventMaker has been invited to an event, add to list of invitations 
	 * and fire event so that user sees this if already logged on.
	 * @param event
	 */
	public void inviteToEvent(Event event) {
		//TODO PropertyChangeListener maybe?
		invitations.add(event);
	}
	
}
