package server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import datamodell.Employee;
import datamodell.Event;

public class ServerPacketHandler {

	private static ServerThread thread;

	public synchronized static CommPack<?> handlePacket(CommPack<?> message, ServerThread st)
	{
		try {
			ArrayList<?> params = message.getParamList();
			CommEnum header = message.getMessageName();
			thread = st;

			switch(header) {
			case LOGIN:
				if(params.get(0) instanceof String && params.get(1) instanceof String) //ensure that proper type is kept TODO must be done for each case (e.g. in this case username and password will both obviously be strings)
					return handleLogin((String)params.get(0), (String)params.get(1)); //0 = email, 1 = password
				else System.err.println("Params casting failed! See ServerPacketHandler"); //for now, repeat this over each case too TODO
				break;
			case GETINVITATIONLIST:
				return handleInvList((String) params.get(0));
			case GETRECIEVEDLIST:
				System.out.println("GETRECIEVEDLIST: "+params.get(0));
				return handleRecList((String) params.get(0));
			case LOGINFAILED:
				break;
			case GETROOMSANDDATE:
				return handleRoomsAndDate();
			case LOGINSUCCESSFUL:
				break;
			case GETEVENT:
				break;
			case UPDATEEVENT:
				return handleUpdateEvent(params);			
			case GETEVENTBYWEEK:
				handleGetEventByWeek((String)params.get(0), (Date)params.get(1), (Integer) params.get(2));
				break;
			case GETEVENTBYDATE:
				handleGetEventByDate((String)params.get(0), (Date)params.get(1));
				break;
			case ADDEVENT:
				break;
			case ADDPARTICIPANT:
				break;
			case GETEVENTS:
				break;
			case REMOVEEVENT:
				break;
			case REMOVEPARTICIPANT:
				break;
			case SUCCESS:
				break;
			case ADDLISTPARTICIPANT:
				break;
			case USERNAMEALREADYTAKEN:
				break;
			case ALERTEVENTCHANGED:
				handleAlertEventChanged((ArrayList<String>) params.get(0), (Integer) params.get(1), thread);
				break;
			case NEWINVITES:
				break;
			default:
				System.err.println("Server Header "+header+" not recognized!");
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null; //TODO
	}

	private static CommPack<?> handleUpdateEvent(ArrayList params) {
		
		//TODO
		System.out.println("Handling updateEvent!");
		database.DBAccess.updateEvent(params.get(0),
				params.get(1),
				params.get(2),
				params.get(3),
				params.get(4),
				params.get(5),
				params.get(6),
				params.get(7),
				params.get(8),
				params.get(9),
				params.get(10),
				params.get(11)
				);
		
		return new CommPack(CommEnum.EVENTUPDATED, null);
	}

	private static CommPack<?> handleRecList(String recieverEmail) throws SQLException {
		ArrayList<ArrayList> payload = database.DBAccess.getRecievedInvitations(recieverEmail);
		return new CommPack(CommEnum.RECEIVEDLIST, payload);
	}

	private static CommPack<?> handleInvList(String senderEmail) throws SQLException {
		ArrayList<ArrayList> payload = database.DBAccess.getSentInvitations(senderEmail);
		return new CommPack(CommEnum.INVITATIONLIST, payload);
	}

	/**
	 * Due to an event being changed, all the affected users must be alerted. This method goes through 
	 * the list of all threads and sends alert to those mathing the username string.
	 * @param usersAffected
	 * @param eventID
	 * @param thread
	 */
	private static CommPack<?> handleAlertEventChanged(ArrayList<String> usersAffected, int eventID, ServerThread thread) {

		//send alert to list of affected users th has changed!
		for(String user: usersAffected) //all the users affected
		{
			for(ServerThread st: Server.socket.getThreads()) //all the threads
			{
				if(user.equals(st.getUser()))
				{
					System.out.println("Found a hit: "+user+" matches "+st.getUser()+" Sending alert");
					st.Alert(eventID);
				}
			}
		}

		return new CommPack(CommEnum.ALERTSUCCESS, null);
	}

	private static CommPack<?> handleRoomsAndDate() throws SQLException {
		ArrayList<Object> al = new ArrayList<Object>();		
		al.add(database.DBAccess.getRoomsAndDate());

		return new CommPack(CommEnum.GETROOMSANDDATE, al);
	}

	private static CommPack<?> handleGetEventByDate(String email, Date startDate) {		
		database.DBAccess.getEventByDate(email, startDate);

		//CommPack<?> cp = new CommPack<Object>(CommEnum.GETEVENTBYDATE, payload);
		return null; //TODO
	}

	private static CommPack<?> handleGetEventByWeek(String email, Date date, int year) {

		ArrayList<Object> payload = new ArrayList<Object>();

		//testing that can be accessed
		database.DBAccess.getThisWeeksEvents(email, date, year);

		//testing that can be accessed
		CommPack<?> cp = new CommPack<Object>(CommEnum.GETEVENTBYWEEK, payload);
		return cp;
	}

	/**
	 * Checks if username and password exists in database, and if they're not already logged in
	 * @param email The email is the login field
	 * @param password
	 * @return CommPack with header LOGINFAILED or LOGINSUCCESSFUL
	 */
	private static CommPack<?> handleLogin(String email, String password) {

		Boolean loginSuccess = database.DBAccess.validateLogin(email, password);

		if(loginSuccess)
		{
			if(!Server.isUserLoggedOn(email)) {
				thread.setUser(email);
				ArrayList<String> payload = new ArrayList<String>();
				payload.add(email);
				return new CommPack(CommEnum.LOGINSUCCESSFUL, payload);
			} else
				return new CommPack(CommEnum.ALREADYLOGGEDIN, null);
		}

		return new CommPack(CommEnum.LOGINFAILED, null);
	}
}
