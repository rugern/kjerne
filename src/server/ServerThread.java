package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * There is one of of these threads for each user logged in, it holds the username
 * of the user.
 * @author Jama
 */
public class ServerThread extends Thread
{
	private Socket connection;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private int number;
	private boolean stop = false;
	private boolean loggedIn = false;
	private String user = null; 
	
	public ServerThread(Socket socket, int threadNumber)  
	{
		super("ServerThread");
		this.number = threadNumber;
		this.connection = socket;
		System.out.println("DEBUG #1: Serverthread made -- threadNumber: " + threadNumber);
		System.out.println("Connection received from " + connection.getInetAddress().getHostName());
		
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(socket.getInputStream());
			
		} catch (IOException e) {
			System.err.println("IOException!\n"+e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * Read incoming messages, parse message by using ServerUnpacker, send reply as returne by ServerUnpacker.
	 * 
	 * @author halvor
	 */
	public void run()
	{
		while (!stop)
		{
	        try {
				CommPack message = (CommPack) in.readObject(); //here is the packet receieved from client
			
				CommPack reply;
				reply = ServerPacketHandler.handlePacket(message, this);
				
				if (reply != null)
				{
					sendMessage(reply);
					System.out.println("Server thread #" + number + " sent reply to msg: " + message.getMessageName());
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				System.err.println("Class not recognized!");
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("IOException!");
				stopThread();
			}
	       
	        try {
				ServerThread.sleep(50);
			} catch (InterruptedException e) {
				System.err.println("InterruptedException!\n"+e.getMessage());
				e.printStackTrace();
			}
		}
		close();
	}

	/**
	 * sends a CommPack package through current connection
	 */
	public synchronized void sendMessage(CommPack msg)
	{
		try
		{
			out.writeObject(msg);
			out.flush();
			System.out.println("client>" + msg.getMessageName());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void stopThread()
	{
		stop = true;
	}
	
	/**
	 * closes the connection of this thread, if there is a user logged on,
	 * remove that user from the list of logged in clients.
	 */
	public void close()
	{
		loginFalse();
		out = null;
		in = null;
		connection = null;
	}
	
	/**
	 * User has been verified, add to list of logged in users
	 */
	public void loginTrue()
	{
		loggedIn = true;
		Server.logUserIn(this);
	}
	
	/**
	 * Logging off, remove from list of logged in
	 */
	public void loginFalse()
	{
		loggedIn = false;
		Server.logUserOff(this);
	}
	
	public boolean getLoggedIn()
	{
		return this.loggedIn;
	}
	
	public void setUser(String user)
	{
		this.user = user;
	}
	
	public String getUser()
	{
		return user;
	}
}
