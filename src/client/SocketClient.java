package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import server.CommPack;

/**
 * SocketClient creates a connection for the client to the server
 * The SocketClient object must be constructed with a valid server name)
 * and port. Make sure the port is available (e.g new SocketClient("localhost", 1337))
 * 
 * PRELIMINARY
 * @author Jama
 */
public class SocketClient {

	Socket clientSocket;
	ObjectOutputStream out; //Objects of a classes that implement Serializable can be sent out
	ObjectInputStream in; // --//-- in
	String servName;
	int port;
	SocketClientListener socketClientListener;
	
	private CommPack latest;

	public SocketClient(String server, int port) 
	{
		this.servName = server;
		this.port = port;
	}

	/**
	 * Must run this to establish connection
	 */
	public void run() throws IOException
	{
		try
		{
			clientSocket = new Socket(servName, port);
			System.out.println("Connected to "+servName+" in port: "+port);
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(clientSocket.getInputStream());
			
			//setup listener so that server can send updates to client
			socketClientListener = new SocketClientListener(in);
			socketClientListener.start();
		}
		catch(UnknownHostException e)
		{
			System.err.println("Couldn't find host: "+e.getMessage());
		}
	}

	/**
	 * Sends a message to the server, this message is an object of a class.
	 * @param o The object to be sent
	 * @author halvor
	 */
	public void sendMessage(CommPack o)
	{
		try
		{
			out.writeObject(o);
			out.flush();
			System.out.println("FromClientPack>" + o);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}		
	}

	public void close()
	{
		try
		{
			in.close();
			out.close();
			clientSocket.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}


}
