package server;

import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * SocketServer listens to any connections made on its port. A SocketServer object must be constructed with a
 * valid, available port number as parameter. This connection uses ObjectStream which means that any class that 
 * implements the Serializable interface can be sent without being broken down. A CommPack object encapsulates
 * the payload with appropriate header from CommEnum.
 * 
 * PRELIMINARY
 * @author Jama
 * 
 */
public class SocketServer {

	private ServerSocket servSocket;
	private int port;
	private int backlog = 100; //The number of clients we can place in the connection queue, set to 100 for now TODO
	private ArrayList<ServerThread> threads = new ArrayList<ServerThread>();

	public SocketServer(int port) 
	{
		this.port = port;
	}

	public void run()
	{
		try
		{
			servSocket = new ServerSocket(port, backlog);
			System.out.println("Waiting for connection");
			
			ServerThread tempThread;
			int threadNumber = 0;
			
			/**
			 * Listen to incoming connections, separate them into different threads.
			 */
			while (true)
			{
				tempThread = new ServerThread(servSocket.accept(), threadNumber);
				tempThread.start();
				threads.add(tempThread);
				threadNumber ++;
			}

		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void close()
	{
		try
		{
			for (ServerThread thread: threads)
			{
				thread.close();
			}
			servSocket.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Searches the list of threads for the one matching user
	 * @param user The email of the user, we use that as the username
	 * @return The thread matching user, or null if no thread found.
	 */
	public ServerThread getThread(String user)
	{
		for (ServerThread st: threads)
		{
			if (st.getUser() == user)
			{
				return st;
			}
		}
		return null;
	}
	
	public ArrayList<ServerThread> getThreads()
	{
		return threads;
	}
}
