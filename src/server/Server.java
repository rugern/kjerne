package server;

import java.util.ArrayList;

/**
 * Starts the socket, DBconnection and manages the logged in users
 * Manages the logged in users
 * @author Jama
 *
 */
public class Server {

  final static int PORT_NUMBER = 1337; //change portnumber here
	
	private static ArrayList<String> loggedInUsers; //Note: this is the email
	
	public static void main(String[] args)
	{
		try {
		//start DB 
		database.DBAccess.open();
		
		//start socket
		SocketServer socket = new SocketServer(PORT_NUMBER);
		socket.run();
		
		//close socket
		socket.close();
		
		//close DB connection
		database.DBAccess.close();
		
		} catch (InstantiationException e) {
			System.err.println("InstantiationException!\n"+e.getMessage());
			e.printStackTrace();
		}		
	}
	
	public static boolean logUserIn(ServerThread st)
	{
		return loggedInUsers.add(st.getUser());
	}
	
	public static void logUserOff(ServerThread st)
	{
		loggedInUsers.remove(st.getUser());
	}
}
