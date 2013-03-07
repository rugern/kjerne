package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * There is one of of these threads for each user logged in, it holds the ID of the Owner logged in and handles all input and output.
 * 
 * PRELIMINARY
 * @author halvor //TODO
 */
public class ServerThread extends Thread
{
	private Socket connection;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private int number;
	private boolean stop = false;
	private boolean loggedIn = false;
	private Owner owner = null;
	
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
			// TODO Auto-generated catch block
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
				CommMessage<?> message = (CommMessage<?>) in.readObject();
				
				CommMessage<?> reply = ServerUnpacker.unpackServerMessage(message, this);
				
				if (reply != null)
				{
					sendMessage(reply);
					System.out.println("Server thread #" + number + " sent reply to msg: " + message.getMessageName());
				}
				//
				//System.out.println(message.getMessageName());
				//sendMessage(message);

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.err.println("Class not recognized");
			} catch (IOException e) {
				//e.printStackTrace();
				System.err.println("Socket closed!");
				stopThread();
			}
	        
	        
	        // sleep for a while! Hvorfor? Load? -Halvor
	        try {
				ServerThread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		close();

	}

	/**
	 * sends a CommMessage, synchronized because it is accessed both by this thread and the main thread in Server.java
	 * @param msg
	 * 
	 * @author halvor
	 */
	public synchronized void sendMessage(CommMessage<?> msg)
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
	 * closes the connection of this thread, if there is a user logged on, remove that user from the list of logged in clients.
	 * 
	 * @author halvor
	 */
	public void close()
	{
		if (getOwner() != null)
		{
			int clientIndex = Server.loggedInClients.indexOf(getOwner().getOwnerId());
			if (clientIndex != -1)
				Server.loggedInClients.remove(clientIndex);
		}
		out = null;
		in = null;
		connection = null;
	}
	
	public void setLoggedIn(boolean b)
	{
		loggedIn = b;
	}
	
	public boolean getLoggedIn()
	{
		return this.loggedIn;
	}
	
	public void setOwner(Owner o)
	{
		owner = o;
	}
	public Owner getOwner()
	{
		return owner;
	}
	
	

}
