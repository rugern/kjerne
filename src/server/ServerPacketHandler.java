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
		ArrayList<?> params = message.getParamList();
		CommEnum header = message.getMessageName();
		thread = st;

		switch(header) {
		case LOGIN:
			if(params.get(0) instanceof String && params.get(1) instanceof String) //ensure that proper type is kept TODO must be done for each case (e.g. in this case username and password will both obviously be strings)
				return handleLogin((String)params.get(0), (String)params.get(1)); //0 = email, 1 = password
			else System.err.println("Params casting failed! See ServerPacketHandler"); //for now, repeat this over each case too TODO
			break;
		case ALERTEVENTCHANGED:
			handleAlertEventChanged((ArrayList<String>) params.get(0), (Integer) params.get(1));
			break;
		case ALERTEVENTINVITE:
			handleAlertEventInvite((ArrayList<String>) params.get(0));
			break;
//		case DELETEEVENT:
//			handleDeleteEvent((Integer)params.get(0));
		default:
			System.err.println("Server Header "+header+" not recognized!");
			break;
		}
		
		return null; //TODO
	}

/*
	private static void handleDeleteEvent(Integer integer) {
		
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
*/

	/**
	 * Due to an event being changed, all the affected users must be alerted. This method goes through 
	 * the list of all threads and sends alert to those mathing the username string.
	 * @param usersAffected
	 * @param eventID
	 */
	private static CommPack<?> handleAlertEventChanged(ArrayList<String> usersAffected, int eventID) {

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
	
	private static CommPack<?> handleAlertEventInvite(ArrayList<String> usersAffected) {

		//send alert to list of affected users th has changed!
		for(String user: usersAffected) //all the users affected
		{
			for(ServerThread st: Server.socket.getThreads()) //all the threads
			{
				if(user.equals(st.getUser()))
				{
					System.out.println("Found a hit: "+user+" matches "+st.getUser()+" Sending alert");
					st.inviteAlert();
				}
			}
		}

		return new CommPack(CommEnum.ALERTSUCCESS, null);
	}
	
	
	

	/**
	 * Checks if username and password exists in database, and if they're not already logged in
	 * @param email The email is the login field
	 * @param password
	 * @return CommPack with header LOGINFAILED/ALREADYLOGGEDIN or LOGINSUCCESSFUL
	 */
	private static CommPack<?> handleLogin(String email, String password) {

		Boolean correctUser = database.DBAccess.validateLogin(email, password);

		if(correctUser)
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
