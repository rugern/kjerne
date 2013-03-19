package client;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.swing.JOptionPane;

import server.CommPack;
import server.ServerThread;

/**
 * This is an attempt to make the connection asynchronous, we had to make it halfway because of time constraints.
 * Meaning that if there is a timeout of larger than 10 seconds the application will simply exit with an error explaining why.
 * This rarely happens though and since there is never any significant temporary data in the application the inconvenience is minor.
 * 
 * TODO in 2013 :p
 * Give each message sent to server an unique MessageID that the client waits for or some such.
 * 
 * 
 * @author halvor
 *
 */
public class SocketClientListener extends Thread
{
	static int count = 0;
	ObjectInputStream in;
	//LinkedList<CommMessage<?>> updateQueue; //A queue of updates pending towards client
	boolean stop = false;
	public SocketClientListener(ObjectInputStream in)
	{
		this.in = in;
		//updateQueue = new LinkedList<CommMessage<?>>();
	}
	@Override
	public void run()
	{
		listenToInput();
	}
	public void stopThread()
	{
		stop = true;
	}

	/**
	 * Listens for reply from server
	 */
	private void listenToInput()
	{
		while(!stop)
		{
			try {
				CommPack reply = (CommPack) in.readObject(); 
				System.out.println(reply.getMessageName()+" :"+reply.getParamList());

				ResultSet rs = (ResultSet)reply.getParamList().get(0);

				
				try {
					while(rs.next())
					{
						System.out.println(rs.getString(1));
						System.out.println(rs.getString(2));
						System.out.println(rs.getString(3));
						System.out.println(rs.getString(4));
						System.out.println(rs.getString(5));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				//update must be handled according to what is updated in calendar
				//TODO here

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.err.println("Class not recognized");
			} catch (IOException e) {
				//e.printStackTrace();
				System.err.println("Socket closed!");
				stopThread();
			}
		}
	}


	/**
	 * Gets Last Messages from the server
	 * @return LinkedList<CommMessage<?>>
	 * 
	 * @author halvor & Kyrre'
	 * @deprecated
	 */
	/*public CommMessage<?> getLatestMessage()
	{
		// hvis denne metoden blir kalt f�r listenToInput er ferdig -> vent litt s� gj�r det igjen.
		if (updateQueue.isEmpty())
		{	
			count += 100;
			System.out.println("EMPTY");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(count > 10000){
				JOptionPane.showMessageDialog(Client.hub,
						"Lost connection to the server.\nRestart the client and try again,\nor contact your system administrator", " Connection Error",
						JOptionPane.ERROR_MESSAGE);
				System.exit(0);	
			}
			return getLatestMessage();
		}
		count = 0;
		return updateQueue.pop();
	}
	 */}

