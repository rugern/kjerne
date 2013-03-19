package server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import datamodell.Employee;

public class ServerPacketHandler {

	public synchronized static CommPack<?> handlePacket(CommPack<?> message, ServerThread thread)
	{
		ArrayList<?> params = message.getParamList();
		CommEnum header = message.getMessageName();

		switch(header) {
		case LOGIN:
			if(params.get(0) instanceof String && params.get(1) instanceof String) //ensure that proper type is kept TODO must be done for each case (e.g. in this case username and password will both obviously be strings)
				return handleLogin((String)params.get(0), (String)params.get(1)); //0 = username, 1 = password
			else System.err.println("Params casting failed! See ServerPacketHandler"); //for now, repeat this over each case too TODO
			break;
		case LOGINFAILED:
			break;
		case GETROOMSANDDATE:
			return handleRoomsAndDate();
		case LOGINSUCCESSFUL:
			break;
		case NOTLOGGEDIN:
			break;
		case GETEVENT:
			break;
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
			System.err.println("Header not recognized!");
			break;
		}

		return null; //TODO
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
					st.Alert(eventID);
			}
		}

		return new CommPack(CommEnum.ALERTSUCCESS, null);
	}

	private static CommPack<?> handleRoomsAndDate() {
		ArrayList<Object> al = new ArrayList<Object>();

		try {

			al.add(database.DBAccess.getRoomsAndDate());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
	 * @param user The username
	 * @param password
	 * @return CommPack with header LOGINFAILED or LOGINSUCCESSFUL
	 */
	private static CommPack<?> handleLogin(String user, String password) {

		Boolean loginSuccess = database.DBAccess.validateLogin(user, password);

		if(loginSuccess) //TODO Add one more condition for not already being logged in
		{
			//add user to list of logged in TODO
			return new CommPack(CommEnum.LOGINSUCCESSFUL, null);
		}

		return new CommPack(CommEnum.LOGINFAILED, null);
	}
}
